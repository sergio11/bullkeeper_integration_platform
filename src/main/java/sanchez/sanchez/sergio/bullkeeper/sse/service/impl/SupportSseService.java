package sanchez.sanchez.sergio.bullkeeper.sse.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.sse.SseEngine;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;

/**
 * Support SSE Service
 * @author sergiosanchezsanchez
 *
 * @param <T>
 */
public abstract class SupportSseService<T extends AbstractSseData> 
	implements ISseService<T>  {
	
	protected static Logger logger = LoggerFactory.getLogger(ConversationServiceSeeImpl.class);
	
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


}
