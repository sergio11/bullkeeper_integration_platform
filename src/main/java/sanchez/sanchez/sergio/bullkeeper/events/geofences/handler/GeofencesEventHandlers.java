package sanchez.sanchez.sergio.bullkeeper.events.geofences.handler;

import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.AllGeofencesDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofenceAddedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofenceDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofenceStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofencesDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.sse.models.location.AllGeofenceDeletedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.location.GeofenceAddedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.location.GeofenceDeletedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.location.GeofenceStatusChangedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.models.location.GeofencesDeletedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;


/**
 * Geofences Event Handlers
 */
@Component
public class GeofencesEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(GeofencesEventHandlers.class);
	  
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
	public GeofencesEventHandlers(
			final ISseService sseService,
			final TerminalRepository terminalRepository) {
		super();
		this.sseService = sseService;
		this.terminalRepository = terminalRepository;
	}
	


	/**
	 * Handle for Geofence Added Event
	 * @param geofenceAddedEvent
	 */
	@EventListener
	public void handle(final GeofenceAddedEvent geofenceAddedEvent) {
		Assert.notNull(geofenceAddedEvent, "Geofence Added Event can not be null");
		
		final GeofenceAddedSSE geofenceAddedSSE = new GeofenceAddedSSE();
		geofenceAddedSSE.setIdentity(geofenceAddedEvent.getIdentity());
		geofenceAddedSSE.setName(geofenceAddedEvent.getName());
		geofenceAddedSSE.setLat(geofenceAddedEvent.getLat());
		geofenceAddedSSE.setLog(geofenceAddedEvent.getLog());
		geofenceAddedSSE.setRadius(geofenceAddedEvent.getRadius());
		geofenceAddedSSE.setKid(geofenceAddedEvent.getKid());
		geofenceAddedSSE.setType(geofenceAddedEvent.getType());
		geofenceAddedSSE.setAddress(geofenceAddedEvent.getAddress());
		geofenceAddedSSE.setCreateAt(geofenceAddedEvent.getCreateAt());
		geofenceAddedSSE.setUpdateAt(geofenceAddedEvent.getUpdateAt());
		geofenceAddedSSE.setIsEnabled(geofenceAddedEvent.getIsEnabled());
		
		// Send Event to all terminals
		sendEventToAllTerminals(geofenceAddedEvent.getKid(), geofenceAddedSSE);

	}
	
	
	/**
	 * Handle for All Geofences Deleted Event
	 * @param geofenceAddedEvent
	 */
	@EventListener
	public void handle(final AllGeofencesDeletedEvent allGeofencesDeletedEvent) {
		Assert.notNull(allGeofencesDeletedEvent, "All Geofence Deleted Event can not be null");
	
		final AllGeofenceDeletedSSE event = new AllGeofenceDeletedSSE();
		event.setKid(allGeofencesDeletedEvent.getKid());
		
		// Send Event to all terminals
		sendEventToAllTerminals(allGeofencesDeletedEvent.getKid(), event);
	}
	
	/**
	 * Handle for Geofence Deleted Event
	 * @param geofenceAddedEvent
	 */
	@EventListener
	public void handle(final GeofenceDeletedEvent geofenceDeletedEvent) {
		Assert.notNull(geofenceDeletedEvent, "Geofence Deleted Event can not be null");
	
		final GeofenceDeletedSSE event = new GeofenceDeletedSSE();
		event.setIdentity(geofenceDeletedEvent.getIdentity());
		event.setKid(geofenceDeletedEvent.getKid());
		
		// Send Event to all terminals
		sendEventToAllTerminals(geofenceDeletedEvent.getKid(), event);
	}
	
	
	/**
	 * Handle for Geofences Deleted Event
	 * @param geofenceAddedEvent
	 */
	@EventListener
	public void handle(final GeofencesDeletedEvent geofencesDeletedEvent) {
		Assert.notNull(geofencesDeletedEvent, "Geofences Deleted Event can not be null");
		
		final GeofencesDeletedSSE event = new GeofencesDeletedSSE();
		event.setKid(geofencesDeletedEvent.getKid());
		
		// Send Event to all terminals
		sendEventToAllTerminals(geofencesDeletedEvent.getKid(), event);
		
	}
	
	/**
	 * Handle for Geofence Status Changed Event
	 * @param geofenceStatusChangedEvent
	 */
	@EventListener
	public void handle(final GeofenceStatusChangedEvent geofenceStatusChangedEvent) {
		Assert.notNull(geofenceStatusChangedEvent, "Geofences Status Changed Event can not be null");
	
		final GeofenceStatusChangedSSE event = new GeofenceStatusChangedSSE();
		event.setGeofence(geofenceStatusChangedEvent.getIdentity());
		event.setKid(geofenceStatusChangedEvent.getKid());
		event.setIsEnabled(geofenceStatusChangedEvent.getIsEnabled());
		
		// Send Event to all terminals
		sendEventToAllTerminals(geofenceStatusChangedEvent.getKid(), event);
	}
	
	/**
	 * Send Event To All Terminals
	 * @param kid
	 * @param event
	 */
	private void sendEventToAllTerminals(final String kid, final AbstractSseData event) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(event, "Event can not be null");
		
		// Find Terminal By Kid
		final List<TerminalEntity> kidTerminals = 
			terminalRepository.findByKidId(new ObjectId(kid));
						
		for(final TerminalEntity terminal: kidTerminals) {
			// Subscriber Id
			final String subscriberId = terminal.getId().toString();
			event.setSubscriberId(subscriberId);
			// Push Event		
			sseService.push(subscriberId, event);
		}
		
	}
	
}
