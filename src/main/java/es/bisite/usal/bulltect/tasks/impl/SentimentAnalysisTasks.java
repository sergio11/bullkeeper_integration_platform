package es.bisite.usal.bulltect.tasks.impl;

import java.util.Date;
import java.util.List;
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

import es.bisite.usal.bulltect.persistence.entity.AlertCategoryEnum;
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
		
		List<SonEntity> sonEntities = sonRepository.findAllByResultsSentimentObsolete(Boolean.TRUE);
		
		logger.debug(sonEntities.toString());
		
		Map<SonEntity, Map<SentimentLevelEnum, Long>> sentimentResults = sonEntities.parallelStream()
			.collect(Collectors.toMap(
				sonEntity -> sonEntity,
				sonEntity -> commentRepository
				.findAllBySonEntityIdAndAnalysisResultsSentimentStatus(sonEntity.getId(), AnalysisStatusEnum.FINISHED)
				.parallelStream()
				.map(comment -> comment.getAnalysisResults().getSentiment())
				.filter(comment -> comment.getResult() != null)
				.collect(Collectors.groupingBy(
					sentiment -> SentimentLevelEnum.fromResult(sentiment.getResult()),
					Collectors.counting()))));
		
		logger.debug("Sentiment by son -> " + sentimentResults.toString());
		
		for (Map.Entry<SonEntity, Map<SentimentLevelEnum, Long>> sentimentResultEntry : sentimentResults.entrySet())
	     {
			
			final SonEntity sonEntity = sentimentResultEntry.getKey();
			
			final Integer totalComments = sentimentResultEntry.getValue().values().stream().mapToInt(Number::intValue).sum();
			final SentimentResultsEntity sentimentResultsEntity = sonEntity.getResults().getSentiment();
			final Long totalCommentsAnalyzedForSentiment = commentRepository.countByAnalysisResultsSentimentFinishAtGreaterThanEqual(sentimentResultsEntity.getDate());
			
			logger.debug("Analysis Sentiment Results for -> " + sonEntity.getFullName() + " Total comments: " + totalComments + "totalCommentsAnalyzedForSentiment" + totalCommentsAnalyzedForSentiment);
			
			if(totalCommentsAnalyzedForSentiment > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.sentiment.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.sentiment.total.analyzed.body", new Object[] { totalCommentsAnalyzedForSentiment, prettyTime.format(sentimentResultsEntity.getDate()) }),
						sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
			}
			
			if(sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.NEGATIVE)) {
				
				final Long totalNegativeComments = sentimentResultEntry.getValue().get(SentimentLevelEnum.NEGATIVE);
	
				final int percentage = Math.round((float)totalNegativeComments/totalComments*100);
				logger.debug("Percentage -> " + percentage);
				
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.low", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.medium", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.hight", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				}
				
			}
			
			sentimentResultsEntity.setDate(new Date());
			sentimentResultsEntity.setObsolete(Boolean.FALSE);
			sentimentResultsEntity.setTotalNegative(sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.NEGATIVE)
					? sentimentResultEntry.getValue().get(SentimentLevelEnum.NEGATIVE) : 0L);
			sentimentResultsEntity.setTotalNeutro(sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.NEUTRO) ? 
					sentimentResultEntry.getValue().get(SentimentLevelEnum.NEUTRO): 0L);
			sentimentResultsEntity.setTotalPositive(sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.POSITIVE) ? 
					sentimentResultEntry.getValue().get(SentimentLevelEnum.POSITIVE): 0L);
			
			logger.debug("Sentiment Result -> " + sentimentResultsEntity.toString());
			
			sonRepository.save(sonEntity);
			
	     }
		
	}
	
	
}
