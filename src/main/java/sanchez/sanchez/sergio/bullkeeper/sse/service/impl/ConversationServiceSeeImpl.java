package sanchez.sanchez.sergio.bullkeeper.sse.service.impl;

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
	

	/**
	 * Push
	 */
	@Override
	public void push(final String subscriberId, final MessageSavedSSE data) {
		Assert.notNull(subscriberId, "Subscriber Id can not be null");
		Assert.notNull(data, "Data can not be null");
		
		this.engine.run(this, data);
		
	}

	/**
	 * Handle
	 */
	@Override
	public void handle(final MessageSavedSSE eventData) {
		Assert.notNull(eventData, "Event Data can not be null");
	
		final MessageSavedAppEvent MessageSavedAppEvent = 
				new MessageSavedAppEvent(eventData.getSubscriberId());
		this.applicationEventPublisher.publishEvent(MessageSavedAppEvent);
		
	}

	/**
	 * On Publish
	 */
	@EventListener(classes = MessageSavedAppEvent.class)
	public void onMessageSavedAppEventPublish(MessageSavedAppEvent event) {
		send(event.getSubscriberId(), event.getSubscriberId());
	}

}
