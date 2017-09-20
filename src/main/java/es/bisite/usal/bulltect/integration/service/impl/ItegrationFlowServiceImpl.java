package es.bisite.usal.bulltect.integration.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	

	public ItegrationFlowServiceImpl(IntegrationFlowProperties integrationFlowProperties,
			SocialMediaRepository socialMediaRepository, IterationRepository iterationRepository) {
		super();
		this.integrationFlowProperties = integrationFlowProperties;
		this.socialMediaRepository = socialMediaRepository;
		this.iterationRepository = iterationRepository;
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

}
