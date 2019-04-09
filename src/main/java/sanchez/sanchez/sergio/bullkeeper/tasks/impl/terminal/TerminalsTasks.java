package sanchez.sanchez.sergio.bullkeeper.tasks.impl.terminal;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.UnlinkTerminalEvent;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDTO;

/**
 * Terminals Tasks
 * @author ssanchez
 *
 */
@Component
public class TerminalsTasks {
	
	
	private static final Logger logger = LoggerFactory.getLogger(TerminalsTasks.class);
	
	/**
	 * Alerts Service
	 */
	private final IAlertService alertsService;
	
	/**
	 * Terminal Service
	 */
	private final ITerminalService terminalService;
	
	/**
	 * Supervised Repository
	 */
	private final SupervisedChildrenRepository supervisedChildrenRepository;
	
	
	/**
	 * Message Source Resolver
	 */
    protected IMessageSourceResolverService messageSourceResolver;
    

    /**
     * Application Event Publisher
     */
    protected ApplicationEventPublisher applicationEventPublisher;
	
	/**
	 * 
	 * @param alertsService
	 * @param terminalService
	 * @param supervisedChildrenRepository
	 * @param messageSourceResolver
	 * @param applicationEventPublisher
	 */
	@Autowired
	public TerminalsTasks(
			final IAlertService alertsService,
			final ITerminalService terminalService,
			final SupervisedChildrenRepository supervisedChildrenRepository,
			final IMessageSourceResolverService messageSourceResolver,
			final ApplicationEventPublisher applicationEventPublisher) {
		super();
		this.alertsService = alertsService;
		this.terminalService = terminalService;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
		this.messageSourceResolver = messageSourceResolver;
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	/**
	 * Generate Heartbeat Alerts
	 */
	@Scheduled(cron = "${task.terminal.generate.heartbeat.alerts}")
    public void generateHeartbeatAlerts() {
        logger.debug("Generate Heartbeat Alerts ...");
        
        final Iterable<TerminalDTO> terminalEntities = 
        		terminalService.getTerminalsWithTheHeartbeatThresholdExceeded();
        
        for(final TerminalDTO terminal: terminalEntities) {
        	final List<SupervisedChildrenEntity> supervisedChildrenList = supervisedChildrenRepository.findByKidIdAndRoleInAndIsConfirmedTrue(
    				new ObjectId(terminal.getKid()), 
    				Arrays.asList(GuardianRolesEnum.PARENTAL_CONTROL_RULE_EDITOR,
    						GuardianRolesEnum.ADMIN));
        	
        	for(final SupervisedChildrenEntity supervisedChildrenEntity: supervisedChildrenList) {
        		
        		
        		applicationEventPublisher
        			.publishEvent(new TerminalStatusChangedEvent(this, 
        					supervisedChildrenEntity.getKid().getId().toString(),
        					supervisedChildrenEntity.getGuardian().getId().toString(),
        					TerminalStatusEnum.DETACHED
        					));
        		
        
        		alertsService.save(AlertLevelEnum.DANGER, 
        				messageSourceResolver.resolver("terminal.heartbeat.threshold.exceeded.title", new Object[] {
        						terminal.getDeviceName(), 
        						terminal.getModel()
        				}, supervisedChildrenEntity.getGuardian().getLocale()),
        				messageSourceResolver.resolver("terminal.heartbeat.threshold.exceeded.description", new Object[] {
        						terminal.getDeviceName(), 
        						terminal.getModel(),
        						terminal.getHeartbeat().getAlertThresholdInMinutes()
        				}, supervisedChildrenEntity.getGuardian().getLocale()), supervisedChildrenEntity.getKid().getId(), 
            			supervisedChildrenEntity.getGuardian().getId(), AlertCategoryEnum.TERMINALS);
        	
        	}
        }
        
    }

    /**
     * Init
     */
    @PostConstruct
    protected void init() {
        logger.debug("init Terminals Tasks ...");
    }

}
