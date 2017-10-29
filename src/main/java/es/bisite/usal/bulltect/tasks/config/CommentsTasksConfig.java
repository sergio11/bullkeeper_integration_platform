package es.bisite.usal.bulltect.tasks.config;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.ListUtils;
import org.bson.types.ObjectId;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.domain.service.IAnalysisService;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.integration.service.IIntegrationFlowService;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SentimentLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.SentimentResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisEntity;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import io.jsonwebtoken.lang.Assert;

@Configuration
@EnableScheduling
public class CommentsTasksConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentsTasksConfig.class);
	
	private final IIntegrationFlowService itegrationFlowService;
	private final CommentRepository commentRepository;
	private final IAnalysisService analysisService;
	private final SonRepository sonRepository;
	private final IMessageSourceResolverService messageSourceResolver;
	private final IAlertService alertService;
	private final PrettyTime pt = new PrettyTime(LocaleContextHolder.getLocale());
	
	@Autowired
	public CommentsTasksConfig(IIntegrationFlowService itegrationFlowService, 
			CommentRepository commentRepository, IAnalysisService analysisService,
			SonRepository sonRepository, IMessageSourceResolverService messageSourceResolver,
			IAlertService alertService) {
		super();
		this.itegrationFlowService = itegrationFlowService;
		this.commentRepository = commentRepository;
		this.analysisService = analysisService;
		this.sonRepository = sonRepository;
		this.messageSourceResolver = messageSourceResolver;
		this.alertService = alertService;
	}

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${task.comments.scheduling.sentiment.analysis.for.unanalized.comments.interval}")
    public void schedulingSentimentAnalysisForUnanalizedComments() {
        logger.debug("scheduling sentiment analysis for unanalized comments at " + new Date());
        List<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsSentimentStatus(AnalysisStatusEnum.PENDING);
        if(!pendingComments.isEmpty()) {
        	logger.debug("Total unanalized Comments -> " + pendingComments.size());
        	
        	analysisService.startSentimentAnalysisFor(
        			pendingComments.parallelStream()
        			.collect(Collectors.toMap(
        					comment -> comment.getSonEntity().getId(),
        					comment -> Arrays.asList(comment.getId()),
        					(comments1, comments2) -> ListUtils.union(comments1, comments2)
        		    )));
        } else {
        	logger.debug("No unanalized Comments founded ");
        }
        	
    }
	
	@Scheduled(cron = "${task.comments.generating.sentiment.analysis.results.interval}")
	public void sentimentAnalysisResults() {
		logger.debug("sentiment analysis results");
		
		Map<SonEntity, Map<SentimentLevelEnum, Long>> sentimentResults = sonRepository.findAllByResultsSentimentObsolete(Boolean.TRUE).parallelStream()
			.collect(Collectors.toMap(
				sonEntity -> sonEntity,
				sonEntity -> commentRepository
				.findBySonEntityId(sonEntity.getId())
				.parallelStream()
				.map(comment -> comment.getAnalysisResults().getSentiment())
				.collect(Collectors.groupingBy(
					sentiment -> {
						
						SentimentLevelEnum sentimentLevel; 
						
						if(sentiment.getResult() >= -10 && sentiment.getResult() <= -5) {
							sentimentLevel = SentimentLevelEnum.NEGATIVE;
						} else if(sentiment.getResult() > -5 && sentiment.getResult() <= 5) {
							sentimentLevel = SentimentLevelEnum.NEUTRO;
						} else {
							sentimentLevel = SentimentLevelEnum.POSITIVE;
						}
						
						return sentimentLevel;
						
					},
					Collectors.counting()
				))
			));
		
		
		for (Map.Entry<SonEntity, Map<SentimentLevelEnum, Long>> sentimentResultEntry : sentimentResults.entrySet())
	     {
			
			final SonEntity sonEntity = sentimentResultEntry.getKey();
			final Integer totalComments = sentimentResultEntry.getValue().values().stream().mapToInt(Number::intValue).sum();
			
			final SentimentResultsEntity sentimentResultsEntity = sonEntity.getResults().getSentiment();
			
			final Long totalCommentsAnalyzedForSentiment = commentRepository.countByAnalysisResultsSentimentFinishAtGreaterThanEqual(sentimentResultsEntity.getDate());
			
			if(totalCommentsAnalyzedForSentiment > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.sentiment.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.sentiment.total.analyzed.body", new Object[] { totalCommentsAnalyzedForSentiment, pt.format(sentimentResultsEntity.getDate()) }),
						sonEntity.getId());
			}
			
			
			final Long totalCommentsAnalyzedForViolence = commentRepository.countByAnalysisResultsViolenceFinishAtGreaterThanEqual(sentimentResultsEntity.getDate());
			
			if(totalCommentsAnalyzedForViolence > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.violence.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.violence.total.analyzed.body", new Object[] { totalCommentsAnalyzedForViolence, pt.format(sentimentResultsEntity.getDate()) }),
						sonEntity.getId());
			}
			
			
			final Long totalCommentsAnalyzedForDrugs = commentRepository.countByAnalysisResultsDrugsFinishAtGreaterThanEqual(sentimentResultsEntity.getDate());

			if(totalCommentsAnalyzedForDrugs > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.drugs.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.drugs.total.analyzed.body", new Object[] { totalCommentsAnalyzedForDrugs, pt.format(sentimentResultsEntity.getDate()) }),
						sonEntity.getId());
			}
			
			if(sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.NEGATIVE)) {
				
				final Long totalNegativeComments = sentimentResultEntry.getValue().get(SentimentLevelEnum.NEGATIVE);
				final float percentage = totalNegativeComments/totalComments*100;
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.low", new Object[] { percentage }),
							sonEntity.getId());
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.medium", new Object[] { percentage }),
							sonEntity.getId());
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.hight", new Object[] { percentage }),
							sonEntity.getId());
				}
				
			}
			
			sentimentResultsEntity.setDate(new Date());
			sentimentResultsEntity.setObsolete(Boolean.FALSE);
			sentimentResultsEntity.setTotalNegative(sentimentResultEntry.getValue().get(SentimentLevelEnum.NEGATIVE));
			sentimentResultsEntity.setTotalNeutro(sentimentResultEntry.getValue().get(SentimentLevelEnum.NEUTRO));
			sentimentResultsEntity.setTotalPositive(sentimentResultEntry.getValue().get(SentimentLevelEnum.POSITIVE));
			
			sonRepository.save(sonEntity);
			
	     }
		
	}

    
    
    @PostConstruct
    protected void init() {
        Assert.notNull(itegrationFlowService, "Integration Flow Service can not be null");
        Assert.notNull(commentRepository, "Comment Repository can not be null");
        logger.debug("CommentsTasksConfig initialized ...");
    }

}
