package sanchez.sanchez.sergio.bullkeeper.sse.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.MessageSavedAppEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.MessageSavedSSE;

/**
 * Conversation Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service
public class ConversationServiceSeeImpl extends
	SupportSseService<MessageSavedSSE>{
	
	private static Logger logger = LoggerFactory.getLogger(ConversationServiceSeeImpl.class);
	

	/**
	 * Push
	 */
	@Override
	public void push(final String subscriberId, final MessageSavedSSE data) {
		Assert.notNull(subscriberId, "Subscriber Id can not be null");
		Assert.notNull(data, "Data can not be null");
		logger.debug("Push Event to subscriber -> " + subscriberId);
		this.engine.run(this, subscriberId, data);
		
	}

	/**
	 * Handle
	 */
	@Override
	public void handle(final MessageSavedSSE eventData) {
		Assert.notNull(eventData, "Event Data can not be null");
		logger.debug("Handler Message Saved SSE -> " + eventData.toString());
		final MessageSavedAppEvent MessageSavedAppEvent = 
				new MessageSavedAppEvent(this, eventData.getSubscriberId(), eventData);
		this.applicationEventPublisher.publishEvent(MessageSavedAppEvent);
		
	}

	/**
	 * On Publish
	 */
	@EventListener(classes = MessageSavedAppEvent.class)
	public void onMessageSavedAppEventPublish(MessageSavedAppEvent event) {
		logger.debug("Send Event");
		send(event.getSubscriberId(), event.getMessage());
	}

}
