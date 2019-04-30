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
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberOne = terminalRepository.findByKidId(
							new ObjectId(subscriberOne));
										
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberOne) {
			final AllMessagesDeletedSSE allMessageDeletedSSE = new AllMessagesDeletedSSE(
					subscriberOne,
					allConversationMessagesDeletedEvent.getConversation());
			allMessageDeletedSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), allMessageDeletedSSE);
		}

		
		final String subscriberTwo = allConversationMessagesDeletedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new AllMessagesDeletedSSE(
				subscriberTwo,
				allConversationMessagesDeletedEvent.getConversation()));
		
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberTwo = terminalRepository.findByKidId(
									new ObjectId(subscriberTwo));
		
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberTwo) {
			final AllMessagesDeletedSSE allMessageDeletedSSE = new AllMessagesDeletedSSE(
					subscriberTwo,
					allConversationMessagesDeletedEvent.getConversation());
			allMessageDeletedSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), allMessageDeletedSSE);
		}
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
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberOne = terminalRepository.findByKidId(
										new ObjectId(subscriberOne));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberOne) {
			final DeletedConversationSSE deletedConversationSSE = new DeletedConversationSSE(
					subscriberOne,
					conversationDeletedEvent.getConversation(),
					conversationDeletedEvent.getMemberOne(),
					conversationDeletedEvent.getMemberTwo());
			deletedConversationSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), deletedConversationSSE);
		}
		
		final String subscriberTwo = conversationDeletedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new DeletedConversationSSE(
				subscriberTwo,
				conversationDeletedEvent.getConversation(),
				conversationDeletedEvent.getMemberOne(),
				conversationDeletedEvent.getMemberTwo()));

		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberTwo = terminalRepository.findByKidId(
												new ObjectId(subscriberTwo));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberTwo) {
			final DeletedConversationSSE deletedConversationSSE = new DeletedConversationSSE(
					subscriberTwo,
					conversationDeletedEvent.getConversation(),
					conversationDeletedEvent.getMemberOne(),
					conversationDeletedEvent.getMemberTwo());
			deletedConversationSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), deletedConversationSSE);
		}

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
		
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberOne = terminalRepository.findByKidId(
												new ObjectId(subscriberOne));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberOne) {
			final DeletedMessagesSSE deletedMessagesSSE =new DeletedMessagesSSE(
						subscriberOne, conversationMessagesDeletedEvent.getConversation(),
						messageIds);
			deletedMessagesSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), deletedMessagesSSE);
		}
	
		final String subscriberTwo = conversationMessagesDeletedEvent.getMemberTwo();
	
		sseService.push(subscriberTwo, new DeletedMessagesSSE(
				subscriberTwo, conversationMessagesDeletedEvent.getConversation(),
				messageIds));
	
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberTwo = terminalRepository.findByKidId(
														new ObjectId(subscriberTwo));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberTwo) {
			final DeletedMessagesSSE deletedMessagesSSE =new DeletedMessagesSSE(
					subscriberTwo, conversationMessagesDeletedEvent.getConversation(),
								messageIds);
			deletedMessagesSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), deletedMessagesSSE);
		}
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
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberOne = terminalRepository.findByKidId(
																new ObjectId(subscriberOne));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberOne) {
			final MessageSavedSSE messageSavedSSE = new MessageSavedSSE(
					messageSavedEvent.getMessage().getFrom().getIdentity(),
					messageSavedEvent.getMessage().getIdentity(),
					messageSavedEvent.getMessage().getText(),
					messageSavedEvent.getMessage().getCreateAt(),
					messageSavedEvent.getMessage().getConversation(),
					messageSavedEvent.getMessage().getFrom(),
					messageSavedEvent.getMessage().getTo(),
					messageSavedEvent.getMessage().isViewed()
			);
			messageSavedSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), messageSavedSSE);
		}

		
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
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberTwo = terminalRepository.findByKidId(
																		new ObjectId(subscriberTwo));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberTwo) {
			final MessageSavedSSE messageSavedSSE = new MessageSavedSSE(
					messageSavedEvent.getMessage().getFrom().getIdentity(),
					messageSavedEvent.getMessage().getIdentity(),
					messageSavedEvent.getMessage().getText(),
					messageSavedEvent.getMessage().getCreateAt(),
					messageSavedEvent.getMessage().getConversation(),
					messageSavedEvent.getMessage().getFrom(),
					messageSavedEvent.getMessage().getTo(),
					messageSavedEvent.getMessage().isViewed()
			);
			messageSavedSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), messageSavedSSE);
		}
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
		
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberOne = terminalRepository.findByKidId(
																	new ObjectId(subscriberOne));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberOne) {
			final SetMessagesAsViewedSSE setMessagesAsViewedSSE = new SetMessagesAsViewedSSE(
					subscriberOne, setMessagesAsViewedEvent.getConversation(),
					messageIds);
			setMessagesAsViewedSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), setMessagesAsViewedSSE);
		}
		
		
		final String subscriberTwo = setMessagesAsViewedEvent.getMemberTwo();
		
		sseService.push(subscriberTwo, new SetMessagesAsViewedSSE(
				subscriberTwo, setMessagesAsViewedEvent.getConversation(),
				messageIds));
		
		// Find By Kid Id
		final Iterable<TerminalEntity> terminalsForSubscriberTwo = terminalRepository.findByKidId(
																			new ObjectId(subscriberTwo));
		// Push Event on each terminal
		for(final TerminalEntity terminal: terminalsForSubscriberTwo) {
			final SetMessagesAsViewedSSE setMessagesAsViewedSSE = new SetMessagesAsViewedSSE(
					subscriberTwo, setMessagesAsViewedEvent.getConversation(),
							messageIds);
			setMessagesAsViewedSSE.setSubscriberId(terminal.getId().toString());
			sseService.push(terminal.getId().toString(), setMessagesAsViewedSSE);
		}
		
	}
	

	
	
}
