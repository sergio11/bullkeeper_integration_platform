package es.bisite.usal.bulltect.integration.service.impl;

import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.domain.service.IAnalysisService;
import es.bisite.usal.bulltect.domain.service.IIterationService;
import es.bisite.usal.bulltect.domain.service.impl.IterationServiceImpl;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.integration.properties.IntegrationFlowProperties;
import es.bisite.usal.bulltect.integration.service.IIntegrationFlowService;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.IterationEntity;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.persistence.repository.SocialMediaRepository;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import es.bisite.usal.bulltect.web.dto.response.CommentDTO;
import org.apache.commons.collections.ListUtils;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Map;

@Service("integrationFlowService")
public final class ItegrationFlowServiceImpl implements IIntegrationFlowService {
	
	private Logger logger = LoggerFactory.getLogger(ItegrationFlowServiceImpl.class);
	
	private final IntegrationFlowProperties integrationFlowProperties;
	private final SocialMediaRepository socialMediaRepository;
	private final IAlertService alertService;
	private final IMessageSourceResolverService messageSourceResolver;
	private final IIterationService iterationService;
	
	
	@Autowired
	public ItegrationFlowServiceImpl(IntegrationFlowProperties integrationFlowProperties,
			SocialMediaRepository socialMediaRepository, IIterationService iterationService,
			RestTemplate restTemplate, CommentRepository commentRepository, IAlertService alertService,
			IMessageSourceResolverService messageSourceResolver) {
		super();
		this.integrationFlowProperties = integrationFlowProperties;
		this.socialMediaRepository = socialMediaRepository;
		this.iterationService = iterationService;
		this.alertService = alertService;
		this.messageSourceResolver = messageSourceResolver;
	}


	@Override
	public Date getDateForNextPoll() {
		Date scheduledFor = new Date();
    	Long socialMediaCount = socialMediaRepository.countByInvalidTokenFalse();
    	
    	if(socialMediaCount > integrationFlowProperties.getMinSocialMediaPerCycle()) {
    		
    		Double socialMediaPerCycle = Math.ceil( socialMediaCount * integrationFlowProperties.getPercentageSocialMedia() / 100);
        	Double totalCycles = Math.ceil(socialMediaCount / socialMediaPerCycle);
        	Double avgIterationDuration = iterationService.getAvgDuration();
        	Long nextIterationMilli = Math.round(totalCycles * avgIterationDuration + totalCycles * integrationFlowProperties.getFlowFixedDelayMillis() - avgIterationDuration);
        	Long currentTime = new Date().getTime();
        	
        	logger.debug("SOCIAL MEDIA COUNT -> " + socialMediaCount);
        	logger.debug("PERCENTAGE SOCIAL MEDIA PER ITERAATION -> " + integrationFlowProperties.getPercentageSocialMedia());
        	logger.debug("SOCIAL MEDIA PER CYCLE -> " + (socialMediaCount / socialMediaPerCycle));
        	logger.debug("TOTAL CYCLES -> " + totalCycles);
        	logger.debug("AVG ITERATION DURATION -> " + avgIterationDuration);
        	logger.debug("NEXT ITERATION MILLI -> " +  nextIterationMilli);
        	logger.debug("CURRENT TIME -> " +  currentTime);
        	
        	scheduledFor = new Date(currentTime + nextIterationMilli);
    	}
    
    	return scheduledFor;
	}
	
	@Override
	public void scheduleSocialMediaForNextIteration(IterationWithTasksDTO iteration) {
		logger.debug("Schedule Social Media For Next Iteration ....");
		
		// plan the next review of these social media
        Date scheduledFor = getDateForNextPoll();
        socialMediaRepository.setScheduledForAndLastProbing(iteration.getTasks().stream().map(task -> new ObjectId(task.getSocialMediaId())).collect(Collectors.toList()), 
        		scheduledFor, iteration.getFinishDate());
		
	}
	
	
	@Override
	public void generateAlertsForIteration(IterationWithTasksDTO iteration) {
		logger.debug("Generate Alerts for this iteration");
		// Generate informative alerts for the users for whom comments have been obtained, check those users for review.
        @SuppressWarnings("unchecked")
		Map<SonDTO, List<CommentDTO>> commentsBySonForIteration = iteration.getTasks().parallelStream()
        		.filter(task -> task.getSuccess() && task.getComments().size() > 0)
        		.collect(Collectors.toMap(
        				task -> task.getSon(), 
        				task -> task.getComments(),
        				(comments1, comments2) -> ListUtils.union(comments1, comments2)));
        
        
        for (Map.Entry<SonDTO, List<CommentDTO>> commentsBySonEntry : commentsBySonForIteration.entrySet())
        {
        	
        	final ObjectId sonId = new ObjectId(commentsBySonEntry.getKey().getIdentity());
        	
        	Map<String, List<CommentDTO>> commentsBySocialMedia = commentsBySonEntry.getValue().stream()
        			  .collect(Collectors.groupingBy(CommentDTO::getSocialMedia));
        	
        	StringBuilder sb = new StringBuilder();
        	
        	for (Map.Entry<String, List<CommentDTO>> commentsBySocialMediaEntry : commentsBySocialMedia.entrySet())
            {
        		sb.append(messageSourceResolver.resolver("alerts.iteration.comments.by.social.media", new Object[] { commentsBySocialMediaEntry.getKey(), commentsBySocialMediaEntry.getValue().size() }));
            }
        
        	
        	// save alert
        	alertService.save(AlertLevelEnum.INFO, 
        			messageSourceResolver.resolver("alerts.iteration.comments.title"),
        			sb.toString(), 
        			sonId);
        	
        }
		
	}


	
	@PostConstruct
    protected void init() {
        Assert.notNull(integrationFlowProperties, "Integration Flow Properties cannot be null");
        Assert.notNull(socialMediaRepository, "Social Media Repository cannot be null");
    }

}
