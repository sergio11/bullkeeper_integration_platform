package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.funtime.DayScheduledSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.funtime.SaveFunTimeScheduledEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.funtime.DayScheduledSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.funtime.FunTimeScheduledSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;


/**
 * Fun Time Event Handler
 */
@Component
public class FunTimeEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(FunTimeEventHandlers.class);
	  
	/**
	 * SSE Service
	 */
	private final ISseService sseService;
	

	/**
	 * @param sseService
	 */
	public FunTimeEventHandlers(
			final ISseService sseService) {
		super();
		this.sseService = sseService;
	}

	/**
	 * Handle for Save Fun Time Scheduled Event
	 * @param saveFunTimeScheduledEvent
	 */
	@EventListener
	public void handle(final SaveFunTimeScheduledEvent saveFunTimeScheduledEvent) {
		Assert.notNull(saveFunTimeScheduledEvent, "Save Fun Time Scheduled Event can not be null");
	
	
		final String subscriberId = saveFunTimeScheduledEvent.getTerminal();
		
		// Fun Time Scheduled SSE
		final FunTimeScheduledSSE funTimeScheduledSSE = 
				new FunTimeScheduledSSE(subscriberId, 
						saveFunTimeScheduledEvent.getFunTime().getEnabled(),
						saveFunTimeScheduledEvent.getFunTime().getMonday(), 
						saveFunTimeScheduledEvent.getFunTime().getTuesday(),
						saveFunTimeScheduledEvent.getFunTime().getWednesday(),
						saveFunTimeScheduledEvent.getFunTime().getThursday(),
						saveFunTimeScheduledEvent.getFunTime().getFriday(),
						saveFunTimeScheduledEvent.getFunTime().getSaturday(),
						saveFunTimeScheduledEvent.getFunTime().getSunday());
	
		sseService.push(subscriberId, funTimeScheduledSSE);
		
	}
	
	/**
	 * Handle for Day Scheduled Saved Event
	 * @param saveFunTimeScheduledEvent
	 */
	@EventListener
	public void handle(final DayScheduledSavedEvent dayScheduledSavedEvent) {
		Assert.notNull(dayScheduledSavedEvent, "Day Scheduled Saved Event");
	
		final String subscriberId = dayScheduledSavedEvent.getTerminal();
		
		// Day Scheduled Saved SSE
		final DayScheduledSavedSSE dayScheduledSavedSSE = 
				new DayScheduledSavedSSE(subscriberId, 
						dayScheduledSavedEvent.getDayScheduled().getDay(),
						dayScheduledSavedEvent.getDayScheduled().getEnabled(),
						dayScheduledSavedEvent.getDayScheduled().getTotalHours());
	
		sseService.push(subscriberId, dayScheduledSavedSSE);
		
	}
	
}
