package es.bisite.usal.bulltect.integration.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.jinstagram.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import es.bisite.usal.bulltect.domain.service.impl.IterationServiceImpl;
import es.bisite.usal.bulltect.integration.properties.IntegrationFlowProperties;
import es.bisite.usal.bulltect.integration.service.IItegrationFlowService;
import es.bisite.usal.bulltect.persistence.entity.CommentStatusEnum;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.persistence.repository.IterationRepository;
import es.bisite.usal.bulltect.persistence.repository.SocialMediaRepository;

@Service
public final class ItegrationFlowServiceImpl implements IItegrationFlowService {
	
	private Logger logger = LoggerFactory.getLogger(IterationServiceImpl.class);
	
	private final IntegrationFlowProperties integrationFlowProperties;
	private final SocialMediaRepository socialMediaRepository;
	private final IterationRepository iterationRepository;
	private final RestTemplate restTemplate;
	private final CommentRepository commentRepository;
	
	@Autowired
	public ItegrationFlowServiceImpl(IntegrationFlowProperties integrationFlowProperties,
			SocialMediaRepository socialMediaRepository, IterationRepository iterationRepository,
			@Qualifier("BasicRestTemplate") RestTemplate restTemplate, CommentRepository commentRepository) {
		super();
		this.integrationFlowProperties = integrationFlowProperties;
		this.socialMediaRepository = socialMediaRepository;
		this.iterationRepository = iterationRepository;
		this.restTemplate = restTemplate;
		this.commentRepository = commentRepository;
	}


	@Override
	public Date getDateForNextPoll() {
		Date scheduledFor = new Date();
    	Long socialMediaCount = socialMediaRepository.countByInvalidTokenFalse();
    	
    	if(socialMediaCount > integrationFlowProperties.getMinSocialMediaPerCycle()) {
    		
    		Double socialMediaPerCycle = Math.ceil( socialMediaCount * integrationFlowProperties.getPercentageSocialMedia() / 100);
        	Double totalCycles = Math.ceil(socialMediaCount / socialMediaPerCycle);
        	Double avgIterationDuration = iterationRepository.getAvgDuration().getAvgDuration();
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
	public void startSentimentAnalysisFor(List<ObjectId> commentsId) {
		
		try {
			logger.debug("Sentiment Service URL -> " + integrationFlowProperties.getSentimentServiceUrl());
			ResponseEntity<Void> response = restTemplate.postForEntity(integrationFlowProperties.getSentimentServiceUrl(), 
					String.join(",", commentsId.stream().map((commentObjectId) -> commentObjectId.toString()).collect(Collectors.toList())),  Void.class);
			if(response.getStatusCode().equals(HttpStatus.OK)) {
				logger.debug("Sentiment Analysis in progress");
				commentRepository.updateCommentStatus(commentsId, CommentStatusEnum.IN_PROGRESS);
			} 
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	@PostConstruct
    protected void init() {
        Assert.notNull(integrationFlowProperties, "Integration Flow Properties cannot be null");
        Assert.notNull(socialMediaRepository, "Social Media Repository cannot be null");
        Assert.notNull(iterationRepository, "IterationRepository cannot be null");
        Assert.notNull(restTemplate, "RestTemplate cannot be null");
        Assert.notNull(commentRepository, "Comment Repository can not be null");
    }

}
