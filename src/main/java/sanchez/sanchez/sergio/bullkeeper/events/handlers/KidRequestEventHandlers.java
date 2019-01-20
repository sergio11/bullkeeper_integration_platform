package sanchez.sanchez.sergio.bullkeeper.events.handlers;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.events.request.KidRequestCreatedEvent;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.request.KidRequestCreatedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;


/**
 * Kid Request Event Handlers
 */
@Component
public class KidRequestEventHandlers {
	
	private static Logger logger = LoggerFactory.getLogger(KidRequestEventHandlers.class);
	  
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
	public KidRequestEventHandlers(
			final ISseService sseService,
			final SupervisedChildrenRepository supervisedChildrenRepository) {
		super();
		this.sseService = sseService;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
	}

	/**
	 * Handle for Kid Request Created Event
	 * @param kidRequestCreatedEvent
	 */
	@EventListener
	public void handle(final KidRequestCreatedEvent kidRequestCreatedEvent) {
		Assert.notNull(kidRequestCreatedEvent, "Kid Request Created Event can not be null");
	
	
		final List<SupervisedChildrenEntity> supervisedChildren = supervisedChildrenRepository.findByKidIdAndRoleInAndIsConfirmedTrue(
				new ObjectId(kidRequestCreatedEvent.getKid().getIdentity()), 
				Arrays.asList(GuardianRolesEnum.PARENTAL_CONTROL_RULE_EDITOR,
						GuardianRolesEnum.ADMIN));
		
		for(final SupervisedChildrenEntity supervisedChildrenEntity: supervisedChildren) {
			final GuardianEntity guardian = supervisedChildrenEntity.getGuardian();
			
			final String subscriberId = guardian.getId().toString();
			
			// Kid Request Created SSE
			final KidRequestCreatedSSE kidRequestCreatedSSE = 
					new KidRequestCreatedSSE(subscriberId, kidRequestCreatedEvent.getIdentity(),
							kidRequestCreatedEvent.getKid(), kidRequestCreatedEvent.getTerminal(),
							kidRequestCreatedEvent.getType(), kidRequestCreatedEvent.getLocation());
		
			sseService.push(subscriberId, kidRequestCreatedSSE);
		}
		
	}
	
}
