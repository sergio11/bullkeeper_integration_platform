package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofenceAddedEvent;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.location.GeofenceAddedSSE;
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
	
		// Find Terminal By Kid
		final List<TerminalEntity> kidTerminals = 
				terminalRepository.findByKidId(new ObjectId(geofenceAddedEvent.getKid()));
		
		for(final TerminalEntity terminal: kidTerminals) {
			
			// Subscriber Id
			final String subscriberId = terminal.getId().toString();
			
			final GeofenceAddedSSE geofenceAddedSSE = 
					new GeofenceAddedSSE(subscriberId, geofenceAddedEvent.getIdentity(),
							geofenceAddedEvent.getName(), geofenceAddedEvent.getLat(), 
							geofenceAddedEvent.getLog(), geofenceAddedEvent.getRadius(),
							geofenceAddedEvent.getType(), geofenceAddedEvent.getKid());
		
			sseService.push(subscriberId, geofenceAddedSSE);
		}

		
	}
	
}
