package sanchez.sanchez.sergio.bullkeeper.events.conversation.handler;

import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
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
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
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
	 * Terminal Repository
	 */
	private final TerminalRepository terminalRepository;

	/**
	 * 
	 * @param sseService
	 * @param terminalRepository
	 */
	public ConversationEventHandlers(
			final ISseService sseService,
			final TerminalRepository terminalRepository) {
		super();
		this.sseService = sseService;
		this.terminalRepository = terminalRepository;
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
		
		sendToAllTerminals(subscriberOne,
				new AllMessagesDeletedSSE(
						subscriberOne,
						allConversationMessagesDeletedEvent.getConversation()));
		
		final String subscriberTwo = allConversationMessagesDeletedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new AllMessagesDeletedSSE(
				subscriberTwo,
				allConversationMessagesDeletedEvent.getConversation()));
		
		sendToAllTerminals(subscriberTwo,
				new AllMessagesDeletedSSE(
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
		
		final String subscriberOne = conversationDeletedEvent.getMemberOne();
		
		sseService.push(subscriberOne, new DeletedConversationSSE(
				subscriberOne,
				conversationDeletedEvent.getConversation(),
				conversationDeletedEvent.getMemberOne(),
				conversationDeletedEvent.getMemberTwo()));
		
		sendToAllTerminals(subscriberOne,
				new DeletedConversationSSE(
						subscriberOne,
						conversationDeletedEvent.getConversation(),
						conversationDeletedEvent.getMemberOne(),
						conversationDeletedEvent.getMemberTwo()));
		
		final String subscriberTwo = conversationDeletedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new DeletedConversationSSE(
				subscriberTwo,
				conversationDeletedEvent.getConversation(),
				conversationDeletedEvent.getMemberOne(),
				conversationDeletedEvent.getMemberTwo()));
		
		sendToAllTerminals(subscriberTwo,
				new DeletedConversationSSE(
						subscriberTwo,
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
		
		
		sendToAllTerminals(subscriberOne,
				new DeletedMessagesSSE(
						subscriberOne, conversationMessagesDeletedEvent.getConversation(),
						messageIds));
		
	
		final String subscriberTwo = conversationMessagesDeletedEvent.getMemberTwo();
	
		sseService.push(subscriberTwo, new DeletedMessagesSSE(
				subscriberTwo, conversationMessagesDeletedEvent.getConversation(),
				messageIds));
	
		sendToAllTerminals(subscriberTwo,
				new DeletedMessagesSSE(
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
		
		
		final String subscriberOne = messageSavedEvent.getMessage().getFrom().getIdentity();
		
		// Push Event
		sseService.push(subscriberOne, new MessageSavedSSE(
				messageSavedEvent.getMessage().getFrom().getIdentity(),
				messageSavedEvent.getMessage().getIdentity(),
				messageSavedEvent.getMessage().getText(),
				messageSavedEvent.getMessage().getCreateAt(),
				messageSavedEvent.getMessage().getConversation(),
				messageSavedEvent.getMessage().getFrom(),
				messageSavedEvent.getMessage().getTo(),
				messageSavedEvent.getMessage().isViewed()
		));
		
		sendToAllTerminals(subscriberOne,
				new MessageSavedSSE(
						messageSavedEvent.getMessage().getFrom().getIdentity(),
						messageSavedEvent.getMessage().getIdentity(),
						messageSavedEvent.getMessage().getText(),
						messageSavedEvent.getMessage().getCreateAt(),
						messageSavedEvent.getMessage().getConversation(),
						messageSavedEvent.getMessage().getFrom(),
						messageSavedEvent.getMessage().getTo(),
						messageSavedEvent.getMessage().isViewed()
				));
		
		
		final String subscriberTwo = messageSavedEvent.getMessage().getTo().getIdentity();
		
		// Push Event
		sseService.push(subscriberTwo, new MessageSavedSSE(
				messageSavedEvent.getMessage().getTo().getIdentity(),
				messageSavedEvent.getMessage().getIdentity(),
				messageSavedEvent.getMessage().getText(),
				messageSavedEvent.getMessage().getCreateAt(),
				messageSavedEvent.getMessage().getConversation(),
				messageSavedEvent.getMessage().getFrom(),
				messageSavedEvent.getMessage().getTo(),
				messageSavedEvent.getMessage().isViewed()
		));
		
		sendToAllTerminals(subscriberTwo,
				new MessageSavedSSE(
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
		
		
		sendToAllTerminals(subscriberOne,
				new SetMessagesAsViewedSSE(
						subscriberOne, setMessagesAsViewedEvent.getConversation(),
						messageIds));
		
		
		final String subscriberTwo = setMessagesAsViewedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new SetMessagesAsViewedSSE(
				subscriberTwo, setMessagesAsViewedEvent.getConversation(),
				messageIds));
		
		sendToAllTerminals(subscriberTwo,
				new SetMessagesAsViewedSSE(
						subscriberTwo, setMessagesAsViewedEvent.getConversation(),
						messageIds));
		
	}
	
	
	/**
	 * Send To All Terminals
	 * @param kid
	 * @param data
	 */
	private <T extends AbstractSseData> void sendToAllTerminals(final String kid, final T data) {
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminals = terminalRepository.findByKidId(
					new ObjectId(kid));
								
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminals) {
			data.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), data);
		}
	}
	
	
}
