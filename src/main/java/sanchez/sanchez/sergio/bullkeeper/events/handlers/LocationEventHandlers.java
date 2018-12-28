package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.location.CurrentLocationUpdateEvent;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.location.CurrentLocationUpdateSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;

/**
 * Location Event Handlers
 * @author sergiosanchezsanchez
 *
 */
@Component
public class LocationEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(LocationEventHandlers.class);
	  
	/**
	 * SSE Service
	 */
	private final ISseService sseService;

	/**
	 * Supervised Repository
	 */
	private final SupervisedChildrenRepository supervisedChildrenRepository;
	
	/**
	 * 
	 * @param sseService
	 * @param supervisedChildrenRepository
	 */
	public LocationEventHandlers(
			final ISseService sseService,
			final SupervisedChildrenRepository supervisedChildrenRepository) {
		super();
		this.sseService = sseService;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
	}
	
	/**
	 * Handle for Current Location Update Event
	 * @param currentLocationUpdateEvent
	 */
	@EventListener
	public void handle(final CurrentLocationUpdateEvent currentLocationUpdateEvent) {
		Assert.notNull(currentLocationUpdateEvent, "Current Location Update can not be null");

		final CurrentLocationUpdateSSE currentLocationUpdateSSE = 
					new CurrentLocationUpdateSSE();
		currentLocationUpdateSSE.setAddress(
				currentLocationUpdateEvent.getLocation().getAddress());
		currentLocationUpdateSSE.setKid(
				currentLocationUpdateEvent.getKid());
		currentLocationUpdateSSE.setLat(
				currentLocationUpdateEvent.getLocation().getLat());
		currentLocationUpdateSSE.setLog(
				currentLocationUpdateEvent.getLocation().getLog());
		
		final List<SupervisedChildrenEntity> supervisedChildren = supervisedChildrenRepository.findByKidId(
				new ObjectId(currentLocationUpdateEvent.getKid()));
		
		for(final SupervisedChildrenEntity supervisedChildrenEntity: supervisedChildren) {
			final GuardianEntity guardian = supervisedChildrenEntity.getGuardian();
			currentLocationUpdateSSE.setSubscriberId(guardian.getId().toString());
			sseService.push(guardian.getId().toString(), currentLocationUpdateSSE);
		}
	}
	
}
