package sanchez.sanchez.sergio.bullkeeper.sse.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.sse.SubscriberSseEmitterCreated;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SseEventEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SseEventRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.SseEngine;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;

/**
 * Support SSE Service
 * @author sergiosanchezsanchez
 *
 * @param <T>
 */
@EnableAsync(proxyTargetClass = true)
@Service
public class SupportSseService
	implements ISseService  {
	
	protected static Logger logger = LoggerFactory.getLogger(SupportSseService.class);
	
	/**
	 * SSE Engine
	 */
	@Autowired
	protected SseEngine engine;
	
	/**
	 * Application Event Publisher
	 */
	@Autowired
	protected ApplicationEventPublisher applicationEventPublisher;
	
	/**
	 * Object Mapper
	 */
	@Autowired
	protected ObjectMapper objectMapper;
	
	/**
	 * SSE Event Repository
	 */
	@Autowired
	protected SseEventRepository sseEventRepository;
	
	
	/**
	 * Send
	 * @param subscriberId
	 * @param data
	 */
	protected void send(final String subscriberId, final Object data) {
		Assert.notNull(subscriberId, "Subscriber Id can not be null");
		Assert.notNull(data, "Data can not be null");
		final SseEmitter emitter = engine.getEmitters().get(subscriberId);
		try {
			logger.debug("Sending message through emmitter " + emitter.toString());
			emitter.send(data);
		} catch (IOException e) {
			logger.error("Error in emitter " + emitter + " while sending message");
			engine.getEmitters().remove(subscriberId);
		}
	}
	
	/**
	 * Push Event
	 */
	@Override
	public  <T extends AbstractSseData>  void push(final String subscriberId, T data) {
		Assert.notNull(subscriberId, "Subscriber Id can not be null");
		Assert.notNull(data, "Data can not be null");
		logger.debug("Push Event to subscriber -> " + subscriberId);
		this.engine.run(this, subscriberId, data);
	}
	
	/**
	 * Push Event
	 */
	@Override
	public <T extends AbstractSseData>  void push(final String subscriberId, final Iterable<T> data) {
		Assert.notNull(subscriberId, "Subscriber Id can not be null");
		Assert.notNull(data, "Data can not be null");
		logger.debug("Push Event to subscriber -> " + subscriberId);
		this.engine.run(this, subscriberId, data);
	}
	
	/**
	 * Push Event
	 */
	@Override
	public <T extends AbstractSseData>  void push(Iterable<String> subscriberIdList, T data) {
		Assert.notNull(subscriberIdList, "Subscriber Id List can not be null");
		Assert.notNull(data, "Data can not be null");
		for(final String subscriber: subscriberIdList)
			push(subscriber, data);
	}

	/**
	 * Push Event
	 */
	@Override
	public <T extends AbstractSseData>  void push(Iterable<String> subscriberIdList, Iterable<T> data) {
		Assert.notNull(subscriberIdList, "Subscriber Id List can not be null");
		Assert.notNull(data, "Data can not be null");
		for(final String subscriber: subscriberIdList)
			push(subscriber, data);
		
	}
	
	/**
	 * Handle Events
	 */
	@Override
	public <T extends AbstractSseData>  void handle(final T eventData) {
		Assert.notNull(eventData, "Event Data can not be null");
		try {
			send(eventData.getSubscriberId(), objectMapper.writeValueAsString(eventData));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Handle Events
	 */
	@Override
	public <T extends AbstractSseData>  void handle(final String subscriberId, final Iterable<T> eventDataList) {
		Assert.notNull(subscriberId, "Subscriber Id can not be null");
		Assert.notNull(eventDataList, "Event Data can not be null");
		try {
			send(subscriberId, objectMapper.writeValueAsString(eventDataList));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}	
		
	}

	/**
	 * Save Event Data
	 */
	@Override
	public <T extends AbstractSseData> void save(T eventData) {
		Assert.notNull(eventData, "Event Data can not be null");
		try {
			sseEventRepository.save(new SseEventEntity(eventData.getSubscriberId(),
					objectMapper.writeValueAsString(eventData)));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Save Event Data
	 */
	@Override
	public <T extends AbstractSseData> void save(String subscriberId, Iterable<T> eventDataList) {
		Assert.notNull(subscriberId, "Subscriber Id can not be null");
		Assert.notNull(eventDataList, "Event Data can not be null");
	
		final List<SseEventEntity> sseEvents = new ArrayList<>();
		for(final T eventData: eventDataList) {
			try {
				sseEvents.add(new SseEventEntity(eventData.getSubscriberId(),
					objectMapper.writeValueAsString(eventData)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
			
		if(!sseEvents.isEmpty())
			sseEventRepository.save(sseEvents);
	}
	
	/**
	 * 
	 * @param newAppInstalledEvent
	 */
	@Async
	@EventListener
	public void handle(final SubscriberSseEmitterCreated subscriberSseEmitterCreated) {
		Assert.notNull(subscriberSseEmitterCreated, "Subscriber SSE Emitter Created can not be null");
		
		// Find SSE Events
		final Iterable<SseEventEntity> sseEvents = sseEventRepository
				.findByTarget(subscriberSseEmitterCreated.getSubscriberId());
		
		if(Iterables.size(sseEvents) > 0) {
			for(final SseEventEntity sseEvent: sseEvents) 
				send(sseEvent.getTarget(), sseEvent.getMessage());
			// Delete All By Target
			sseEventRepository.deleteAllByTarget(subscriberSseEmitterCreated.getSubscriberId());
		}
	}
}
