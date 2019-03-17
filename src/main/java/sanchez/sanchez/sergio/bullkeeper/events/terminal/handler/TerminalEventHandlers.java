package sanchez.sanchez.sergio.bullkeeper.events.terminal.handler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.AllTerminalScreenStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalBedTimeStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalCameraStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalScreenStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalSettingsStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.UnlinkAllKidTerminalsEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.UnlinkTerminalEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalBedTimeStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalCameraStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalScreenStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalSettingsStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.UnlinkTerminalSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDTO;


/**
 * Terminal Event Handlers
 */
@Component
public class TerminalEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(TerminalEventHandlers.class);
	  
	/**
	 * SSE Service
	 */
	private final ISseService sseService;
	
	/**
	 * Terminal Service
	 */
	private final ITerminalService terminalService;

	/**
	 * 
	 * @param sseService
	 * @param terminalService
	 */
	public TerminalEventHandlers(
			final ISseService sseService,
			final ITerminalService terminalService) {
		super();
		this.sseService = sseService;
		this.terminalService = terminalService;
	}

	/**
	 * Handle for Terminal Bed Time Status Changed Event 
	 * @param terminalBedTimeStatusChangedEvent
	 */
	@EventListener
	public void handle(final TerminalBedTimeStatusChangedEvent 
			terminalBedTimeStatusChangedEvent) {
		Assert.notNull(terminalBedTimeStatusChangedEvent, "Event can not be null");
	
		final String subscriberId = terminalBedTimeStatusChangedEvent.getTerminal();
		
		// Bed Time Status
		final TerminalBedTimeStatusChangedSSE bedTimeStatus = 
				new TerminalBedTimeStatusChangedSSE(
						subscriberId, terminalBedTimeStatusChangedEvent.getKid(),
						terminalBedTimeStatusChangedEvent.getTerminal(),
						terminalBedTimeStatusChangedEvent.getEnabled()
						);
		
		
		// Push Event
		sseService.push(subscriberId, bedTimeStatus);
		
	}
	
	
	/**
	 * Handle for Terminal Camera Status Changed Event 
	 * @param terminalCameraStatusChangedEvent
	 */
	@EventListener
	public void handle(final TerminalCameraStatusChangedEvent 
			terminalCameraStatusChangedEvent) {
		Assert.notNull(terminalCameraStatusChangedEvent, "Event can not be null");
		
		final String subscriberId = terminalCameraStatusChangedEvent.getTerminal();
	
		// Camera Status
		final TerminalCameraStatusChangedSSE cameraStatus = 
				new TerminalCameraStatusChangedSSE(
						subscriberId, terminalCameraStatusChangedEvent.getKid(),
						terminalCameraStatusChangedEvent.getTerminal(),
						terminalCameraStatusChangedEvent.getEnabled()
						);
		
		
		// Push Event
		sseService.push(subscriberId, cameraStatus);
		
	}
	
	
	
	/**
	 * Handle for Terminal Screen Status Changed Event
	 * @param terminalScreenStatusChangedEvent
	 */
	@EventListener
	public void handle(final TerminalScreenStatusChangedEvent 
			terminalScreenStatusChangedEvent) {
		Assert.notNull(terminalScreenStatusChangedEvent, "Event can not be null");
		
		final String subscriberId = terminalScreenStatusChangedEvent.getTerminal();
		
		// Screen Status
		final TerminalScreenStatusChangedSSE terminalScreenStatus = 
				new TerminalScreenStatusChangedSSE(
						subscriberId, terminalScreenStatusChangedEvent.getKid(),
						terminalScreenStatusChangedEvent.getTerminal(),
						terminalScreenStatusChangedEvent.getEnabled()
						);
		
		
		// Push Event
		sseService.push(subscriberId, terminalScreenStatus);
	}
	
	/**
	 * Handle for Terminal Settings Status ChangedEvent
	 * @param terminalSettingsStatusChangedEvent
	 */
	@EventListener
	public void handle(final TerminalSettingsStatusChangedEvent 
			terminalSettingsStatusChangedEvent) {
		Assert.notNull(terminalSettingsStatusChangedEvent, "Event can not be null");
		
		final String subscriberId = terminalSettingsStatusChangedEvent.getTerminal();
		
		// Settings Status
		final TerminalSettingsStatusChangedSSE terminalSettingsStatus = 
				new TerminalSettingsStatusChangedSSE(
						subscriberId, terminalSettingsStatusChangedEvent.getKid(),
						terminalSettingsStatusChangedEvent.getTerminal(),
						terminalSettingsStatusChangedEvent.getEnabled()
						);
		
		
		// Push Event
		sseService.push(subscriberId, terminalSettingsStatus);
	}
	
	/**
	 * Handle for Unlink Terminal Event
	 * @param unlinkTerminalEvent
	 */
	@EventListener
	public void handle(final UnlinkTerminalEvent unlinkTerminalEvent) {
		Assert.notNull(unlinkTerminalEvent, "Event can not be null");
		
		final String subscriberId = unlinkTerminalEvent.getTerminal();
		
		final UnlinkTerminalSSE unlinkTerminalSSE = 
				new UnlinkTerminalSSE(subscriberId, unlinkTerminalEvent.getKid(),
						unlinkTerminalEvent.getTerminal());
		 
		// Push Event
		sseService.push(subscriberId, unlinkTerminalSSE);
	}
	
	
	/**
	 * Handle for Unlink All Kid Terminals Event
	 * @param unlinkAllKidTerminalsEvent
	 */
	@EventListener
	public void handle(final UnlinkAllKidTerminalsEvent unlinkAllKidTerminalsEvent) {
		Assert.notNull(unlinkAllKidTerminalsEvent, "Event can not be null");
	
		
		for(final TerminalDTO terminal: unlinkAllKidTerminalsEvent.getTerminals()) {
			
			final String subscriberId = terminal.getIdentity();
			
			final UnlinkTerminalSSE unlinkTerminalSSE = 
					new UnlinkTerminalSSE(subscriberId, terminal.getKid(),
							terminal.getIdentity());
			 
			// Push Event
			sseService.push(subscriberId, unlinkTerminalSSE);
			
		}
	
	}

	
	/**
	 * Handle for All Terminal Screen Status Changed Event
	 * @param allTerminalScreenStatusChangedEvent
	 */
	@EventListener
	public void handle(final AllTerminalScreenStatusChangedEvent 
			allTerminalScreenStatusChangedEvent) {
		Assert.notNull(allTerminalScreenStatusChangedEvent, "Event can not be null");
		
		
		final Iterable<TerminalDTO> terminals = terminalService
				.getTerminalsByKidId(allTerminalScreenStatusChangedEvent.getKid());
		
		for(final TerminalDTO terminal: terminals) {
			
			final String subscriberId = terminal.getIdentity();
			
			// Screen Status
			final TerminalScreenStatusChangedSSE terminalScreenStatus = 
					new TerminalScreenStatusChangedSSE(
							subscriberId, terminal.getKid(),
							terminal.getIdentity(),
							allTerminalScreenStatusChangedEvent.getEnabled());
			
			
			// Push Event
			sseService.push(subscriberId, terminalScreenStatus);
			
		}
		
	}
	
}
