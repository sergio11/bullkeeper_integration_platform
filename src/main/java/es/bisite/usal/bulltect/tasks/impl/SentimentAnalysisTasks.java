package es.bisite.usal.bulltect.tasks.impl;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SentimentLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.SentimentResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;

@Component
public class SentimentAnalysisTasks extends AbstractAnalysisTasks {

	private static final Logger logger = LoggerFactory.getLogger(SentimentAnalysisTasks.class);
	
	@Value("${task.analysis.sentiment.number.of.comments}")
	private Integer maximumNumberOfCommentsForSentimentAnalysis;
	
	/**
	 *  Task for sentiment analysis scheduling.
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${task.analysis.sentiment.scheduling.interval}")
    public void sentimentAnalysisScheduling() {
        logger.debug("scheduling sentiment analysis for comments at " + new Date());
        
        Pageable pageable = new PageRequest(0, maximumNumberOfCommentsForSentimentAnalysis);
        Page<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsSentimentStatus(AnalysisStatusEnum.PENDING, pageable);
        if(pendingComments.hasContent()) 
        	startAnalysisFor(AnalysisTypeEnum.SENTIMENT, pendingComments.getContent());

    }
	
	
	/**
	 * Task to cancel unfinished sentiment analysis
	 */
	@Scheduled(cron = "${task.analysis.sentiment.cancel.not.finished.interval}")
	public void cancelingUnfinishedSentimentAnalysisTasks(){
		logger.debug("Canceling unfinished sentiment analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum.SENTIMENT, maximumHoursOfAnAnalysis);
	}
	
	
	/**
	 * Task to analyze the results of sentiment analysis
	 */
	@Scheduled(cron = "${task.analysis.sentiment.analyze.results.scheduling.interval}")
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
						messageSourceResolver.resolver("alerts.sentiment.total.analyzed.body", new Object[] { totalCommentsAnalyzedForSentiment, prettyTime.format(sentimentResultsEntity.getDate()) }),
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
	
	
}
