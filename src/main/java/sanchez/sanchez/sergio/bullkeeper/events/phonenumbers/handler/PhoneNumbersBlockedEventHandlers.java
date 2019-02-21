package sanchez.sanchez.sergio.bullkeeper.events.phonenumbers.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.events.phonenumbers.AddPhoneNumberBlockedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.phonenumbers.DeleteAllPhoneNumberBlockedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.phonenumbers.DeletePhoneNumberBlockedEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.phonenumbers.AddPhoneNumberBlockedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.phonenumbers.DeleteAllPhoneNumberBlockedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.phonenumbers.DeletePhoneNumberBlockedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;

/**
 * Phone Numbers Event Handlers
 */
@Component
public class PhoneNumbersBlockedEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(PhoneNumbersBlockedEventHandlers.class);
	    
	/**
	 * SSE Service
	 */
	private final ISseService sseService;
	

	/**
	 * 
	 * @param sseService
	 */
	public PhoneNumbersBlockedEventHandlers(final ISseService sseService) {
		super();
		this.sseService = sseService;
	}

	/**
	 * Handle Add Phone Number Blocked Event
	 * @param addPhoneNumberBlockedEvent
	 */
	@EventListener
	public void handle(final AddPhoneNumberBlockedEvent addPhoneNumberBlockedEvent) {
		Assert.notNull(addPhoneNumberBlockedEvent, "Add Phone Number Blocked Event can not be null");
		
		final AddPhoneNumberBlockedSSE addPhoneNumberSSE = 
				new AddPhoneNumberBlockedSSE();
		addPhoneNumberSSE.setIdentity(addPhoneNumberBlockedEvent.getIdentity());
		addPhoneNumberSSE.setKid(addPhoneNumberBlockedEvent.getKid());
		addPhoneNumberSSE.setPrefix(addPhoneNumberBlockedEvent.getPrefix());
		addPhoneNumberSSE.setNumber(addPhoneNumberBlockedEvent.getNumber());
		addPhoneNumberSSE.setPhoneNumber(addPhoneNumberBlockedEvent.getPhoneNumber());
		addPhoneNumberSSE.setTerminal(addPhoneNumberBlockedEvent.getTerminal());
		addPhoneNumberSSE.setSubscriberId(addPhoneNumberBlockedEvent.getTerminal());
		addPhoneNumberSSE.setBlockedAt(addPhoneNumberBlockedEvent.getBlockedAt());
		// Push Event
		sseService.push(addPhoneNumberSSE.getSubscriberId(), addPhoneNumberSSE);
	}
	
	
	/**
	 * Handle Delete All Phone Number Blocked Event
	 * @param deleteAllPhoneNumberBlockedEvent
	 */
	@EventListener
	public void handle(final DeleteAllPhoneNumberBlockedEvent deleteAllPhoneNumberBlockedEvent) {
		Assert.notNull(deleteAllPhoneNumberBlockedEvent, "Delete All Phone Number Blocked Event can not be null");
		
		final String subscriberId = deleteAllPhoneNumberBlockedEvent.getTerminal();
	
		final DeleteAllPhoneNumberBlockedSSE 
			deleteAllPhoneNumberBlockedSSE = new DeleteAllPhoneNumberBlockedSSE();
		
		deleteAllPhoneNumberBlockedSSE.setKid(deleteAllPhoneNumberBlockedEvent.getKid());
		deleteAllPhoneNumberBlockedSSE.setTerminal(deleteAllPhoneNumberBlockedEvent.getTerminal());
		deleteAllPhoneNumberBlockedSSE.setSubscriberId(subscriberId);
		// Push Event
		sseService.push(subscriberId, deleteAllPhoneNumberBlockedSSE);
	}
	
	/**
	 * Handle Delete Phone Number Blocked Event
	 * @param deletePhoneNumberBlockedEvent
	 */
	@EventListener
	public void handle(final DeletePhoneNumberBlockedEvent deletePhoneNumberBlockedEvent) {
		Assert.notNull(deletePhoneNumberBlockedEvent, "Delete Phone Number Blocked Event can not be null");
		
		final String subscriberId = deletePhoneNumberBlockedEvent.getTerminal();
		
		final DeletePhoneNumberBlockedSSE deletePhoneNumberBlockedSSE 
			= new DeletePhoneNumberBlockedSSE();
		deletePhoneNumberBlockedSSE.setKid(deletePhoneNumberBlockedEvent.getKid());
		deletePhoneNumberBlockedSSE.setTerminal(deletePhoneNumberBlockedEvent.getTerminal());
		deletePhoneNumberBlockedSSE.setSubscriberId(subscriberId);
		deletePhoneNumberBlockedSSE.setIdOrPhoneNumber(deletePhoneNumberBlockedEvent.getIdOrPhoneNumber());
		
		// Push Event
		sseService.push(subscriberId, deletePhoneNumberBlockedSSE);
	}
}
