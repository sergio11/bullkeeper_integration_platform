package sanchez.sanchez.sergio.bullkeeper.events.contacts.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.contacts.ContactDisabledEvent;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.contact.ContactDisabledSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;

/**
 * Contact Event Handlers
 * @author ssanchez
 *
 */
@Component
public class ContactEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(ContactEventHandlers.class);
	
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
	public ContactEventHandlers(final ISseService sseService, final TerminalRepository terminalRepository) {
		super();
		this.sseService = sseService;
		this.terminalRepository = terminalRepository;
	}
	
	
	/**
	 * Handle Contact Disabled Event
	 * @param contactDisabledEvent
	 */
	@EventListener
	public void handle(final ContactDisabledEvent contactDisabledEvent) {
		Assert.notNull(contactDisabledEvent, "Contact Disabled event can not be null");
		
		final String subscriberId = contactDisabledEvent.getTerminal();
		
		sseService.push(subscriberId, new ContactDisabledSSE(
				subscriberId,
				contactDisabledEvent.getKid(),
				contactDisabledEvent.getTerminal(),
				contactDisabledEvent.getContact(),
				contactDisabledEvent.getLocalId()));
	}

}
