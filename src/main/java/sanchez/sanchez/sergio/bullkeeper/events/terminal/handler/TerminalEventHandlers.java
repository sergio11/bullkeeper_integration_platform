package sanchez.sanchez.sergio.bullkeeper.events.terminal.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalBedTimeStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalCameraStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalScreenStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalSettingsStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalBedTimeStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalCameraStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalScreenStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.terminal.TerminalSettingsStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;


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
	 * 
	 * @param sseService
	 */
	public TerminalEventHandlers(
			final ISseService sseService) {
		super();
		this.sseService = sseService;
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
	
	
	
	
	
}
