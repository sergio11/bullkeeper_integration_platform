package es.bisite.usal.bulltect.integration.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import es.bisite.usal.bulltect.domain.service.impl.IterationServiceImpl;
import es.bisite.usal.bulltect.integration.properties.IntegrationFlowProperties;
import es.bisite.usal.bulltect.integration.service.IItegrationFlowService;
import es.bisite.usal.bulltect.persistence.repository.IterationRepository;
import es.bisite.usal.bulltect.persistence.repository.SocialMediaRepository;

@Service
public final class ItegrationFlowServiceImpl implements IItegrationFlowService {
	
	private Logger logger = LoggerFactory.getLogger(IterationServiceImpl.class);
	
	private final IntegrationFlowProperties integrationFlowProperties;
	private final SocialMediaRepository socialMediaRepository;
	private final IterationRepository iterationRepository;
	private final RestTemplate restTemplate;
	

	public ItegrationFlowServiceImpl(IntegrationFlowProperties integrationFlowProperties,
			SocialMediaRepository socialMediaRepository, IterationRepository iterationRepository, RestTemplate restTemplate) {
		super();
		this.integrationFlowProperties = integrationFlowProperties;
		this.socialMediaRepository = socialMediaRepository;
		this.iterationRepository = iterationRepository;
		this.restTemplate = restTemplate;
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
		
		logger.debug("Piar URL -> " + integrationFlowProperties.getPiarUrl());
		String result = this.restTemplate.getForObject(integrationFlowProperties.getPiarUrl(), String.class);
		logger.debug("Result For Get -> " + result);
		Map<String, Integer> numbers = new HashMap<String, Integer>();
		numbers.put("x", 1);
		numbers.put("y", 1);
		HttpEntity<Map<String, Integer>> request = new HttpEntity<>(numbers);
		String resultPost = restTemplate.postForObject(integrationFlowProperties.getPiarUrl(), request, String.class);
		logger.debug("Result For Post -> " + resultPost);
	}
	
	@PostConstruct
    protected void init() {
        Assert.notNull(integrationFlowProperties, "Integration Flow Properties cannot be null");
        Assert.notNull(socialMediaRepository, "Social Media Repository cannot be null");
        Assert.notNull(iterationRepository, "IterationRepository cannot be null");
        Assert.notNull(restTemplate, "RestTemplate cannot be null");
    }

}
