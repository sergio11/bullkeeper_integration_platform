package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.conversation.AllConversationMessagesDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.conversation.ConversationDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.conversation.ConversationMessagesDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.conversation.MessageSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.conversation.SetMessagesAsViewedEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.conversation.AllMessagesDeletedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.conversation.DeletedConversationSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.conversation.DeletedMessagesSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.conversation.MessageSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.conversation.SetMessagesAsViewedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;


/**
 * Conversation Event Handlers
 */
@Component
public class ConversationEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(ConversationEventHandlers.class);
	  
	/**
	 * SSE Service
	 */
	private final ISseService sseService;

	/**
	 * 
	 * @param sseService
	 */
	public ConversationEventHandlers(
			final ISseService sseService) {
		super();
		this.sseService = sseService;
	}
	
	/**
	 * Handle For All Conversation Messages Deleted Event
	 * @param allConversationMessagesDeletedEvent
	 */
	@EventListener
	public void handle(final AllConversationMessagesDeletedEvent allConversationMessagesDeletedEvent) {
		Assert.notNull(allConversationMessagesDeletedEvent, "All Conversation Messages can not be null");
		
		final String subscriberOne = allConversationMessagesDeletedEvent.getMemberOne();
		
		sseService.push(subscriberOne, new AllMessagesDeletedSSE(
				subscriberOne,
				allConversationMessagesDeletedEvent.getConversation()));
		
		final String subscriberTwo = allConversationMessagesDeletedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new AllMessagesDeletedSSE(
				subscriberTwo,
				allConversationMessagesDeletedEvent.getConversation()));
	}
	
	/**
	 * Handle Conversation Deleted Event
	 * @param conversationDeletedEvent
	 */
	@EventListener
	public void handle(final ConversationDeletedEvent conversationDeletedEvent) {
		Assert.notNull(conversationDeletedEvent, "Conversation Deleted event can not be null");
		
		final String subscriberIdOne = conversationDeletedEvent.getMemberOne();
		
		sseService.push(conversationDeletedEvent.getMemberOne(), new DeletedConversationSSE(
				subscriberIdOne,
				conversationDeletedEvent.getConversation(),
				conversationDeletedEvent.getMemberOne(),
				conversationDeletedEvent.getMemberTwo()));
		
		final String subscriberIdTwo = conversationDeletedEvent.getMemberTwo();
		
		sseService.push(conversationDeletedEvent.getMemberOne(), new DeletedConversationSSE(
				subscriberIdTwo,
				conversationDeletedEvent.getConversation(),
				conversationDeletedEvent.getMemberOne(),
				conversationDeletedEvent.getMemberTwo()));
	}
	
	/**
	 * Handle Conversation Messages Deleted Event
	 * @param conversationMessagesDeletedEvent
	 */
	@EventListener
	public void handle(final ConversationMessagesDeletedEvent conversationMessagesDeletedEvent) {
		Assert.notNull(conversationMessagesDeletedEvent, "Conversation Messages Deleted Event can not be null");
		
		final String subscriberOne = conversationMessagesDeletedEvent.getMemberOne();
		
		final List<String> messageIds = conversationMessagesDeletedEvent.getMessageIds()
				.stream().map((objectId) -> objectId.toString()).collect(Collectors.toList());
		
		sseService.push(subscriberOne, new DeletedMessagesSSE(
				subscriberOne, conversationMessagesDeletedEvent.getConversation(),
				messageIds));
		
		final String subscriberTwo = conversationMessagesDeletedEvent.getMemberTwo();
	
		sseService.push(subscriberTwo, new DeletedMessagesSSE(
				subscriberTwo, conversationMessagesDeletedEvent.getConversation(),
				messageIds));
	}
	
	/**
	 * Handle Message Saved Event
	 * @param messageSavedEvent
	 */
	@EventListener
	public void handle(final MessageSavedEvent messageSavedEvent) {
		Assert.notNull(messageSavedEvent, "Message Saved Event can not be null");
		
		
		// Push Event
		sseService.push(messageSavedEvent.getMessage().getFrom().getIdentity(), new MessageSavedSSE(
				messageSavedEvent.getMessage().getFrom().getIdentity(),
				messageSavedEvent.getMessage().getIdentity(),
				messageSavedEvent.getMessage().getText(),
				messageSavedEvent.getMessage().getCreateAt(),
				messageSavedEvent.getMessage().getConversation(),
				messageSavedEvent.getMessage().getFrom(),
				messageSavedEvent.getMessage().getTo(),
				messageSavedEvent.getMessage().isViewed()
		));
		
		
		// Push Event
		sseService.push(messageSavedEvent.getMessage().getTo().getIdentity(), new MessageSavedSSE(
				messageSavedEvent.getMessage().getTo().getIdentity(),
				messageSavedEvent.getMessage().getIdentity(),
				messageSavedEvent.getMessage().getText(),
				messageSavedEvent.getMessage().getCreateAt(),
				messageSavedEvent.getMessage().getConversation(),
				messageSavedEvent.getMessage().getFrom(),
				messageSavedEvent.getMessage().getTo(),
				messageSavedEvent.getMessage().isViewed()
		));
	}
	
	/**
	 * @param setMessagesAsViewedEvent
	 */
	@EventListener
	public void handle(final SetMessagesAsViewedEvent setMessagesAsViewedEvent) {
		Assert.notNull(setMessagesAsViewedEvent, "Message Saved Event can not be null");
		
		final List<String> messageIds = setMessagesAsViewedEvent.getMessageIds()
				.stream().map((objectId) -> objectId.toString()).collect(Collectors.toList());
		
		final String subscriberOne = setMessagesAsViewedEvent.getMemberOne();
		
		sseService.push(subscriberOne, new SetMessagesAsViewedSSE(
				subscriberOne, setMessagesAsViewedEvent.getConversation(),
				messageIds));
		
		
		final String subscriberTwo = setMessagesAsViewedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new SetMessagesAsViewedSSE(
				subscriberTwo, setMessagesAsViewedEvent.getConversation(),
				messageIds));
		
	}
	
	
}
