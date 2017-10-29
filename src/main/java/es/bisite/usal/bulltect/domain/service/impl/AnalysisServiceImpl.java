package es.bisite.usal.bulltect.domain.service.impl;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.ListUtils;
import org.bson.types.ObjectId;
import org.jinstagram.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import es.bisite.usal.bulltect.domain.service.IAnalysisService;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;

@Service("analysisService")
public class AnalysisServiceImpl implements IAnalysisService {
	
	private Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);
	
	private final RestTemplate restTemplate;
	private final CommentRepository commentRepository;
	
	@Value("${domain.services.sentiment.analysis.url}")
	private String sentimentAnalysisServiceUrl;
	

	public AnalysisServiceImpl(RestTemplate restTemplate, CommentRepository commentRepository) {
		super();
		this.restTemplate = restTemplate;
		this.commentRepository = commentRepository;
	}
	
	@Override
	public void startSentimentAnalysisFor(Map<ObjectId, List<ObjectId>> commentsBySon) {
		
		 for (Map.Entry<ObjectId, List<ObjectId>> commentsBySonEntry : commentsBySon.entrySet())
	     {
			 
			 final ObjectId sonId = commentsBySonEntry.getKey();
			 final Collection<ObjectId> commentIds = commentsBySonEntry.getValue();
			 
			 try {
					logger.debug("Analysis Service URL -> " + sentimentAnalysisServiceUrl);
					ResponseEntity<Void> response = restTemplate.postForEntity(sentimentAnalysisServiceUrl, 
							String.join(",", commentIds.parallelStream().map((commentObjectId) -> commentObjectId.toString()).collect(Collectors.toList())),  Void.class);
					if(response.getStatusCode().equals(HttpStatus.OK)) {
						logger.debug("Sentiment Analysis in progress");
						commentRepository.startSentimentAnalysisFor(commentIds);
						
					} 
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
	     }
	}


	@Override
	public void startAnalysisFor(IterationWithTasksDTO iteration) {
		
		logger.debug("Start Analysis for Iteration");
		
		@SuppressWarnings("unchecked")
		Map<ObjectId, List<ObjectId>> commentsBySonForIteration = iteration.getTasks().parallelStream()
        		.filter(task -> task.getSuccess() && task.getComments().size() > 0)
        		.collect(Collectors.toMap(
        				task -> new ObjectId(task.getSon().getIdentity()), 
        				task -> task.getComments().parallelStream()
        				.map(comment -> new ObjectId(comment.getIdentity()))
        				.collect(Collectors.toList()),
        				(comments1, comments2) -> ListUtils.union(comments1, comments2)));
		
		
		if(!commentsBySonForIteration.isEmpty()) {
			
			startSentimentAnalysisFor(commentsBySonForIteration);
			
		} else {
			logger.debug("No Comments To Analisis ");
		}
		
		
	}
	
	@PostConstruct
    protected void init() {
        Assert.notNull(restTemplate, "RestTemplate can not be null");
        Assert.notNull(commentRepository, "Comment Repository cannot be null");
    }

}
