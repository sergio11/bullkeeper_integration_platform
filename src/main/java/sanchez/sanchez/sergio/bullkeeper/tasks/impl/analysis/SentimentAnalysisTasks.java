package sanchez.sanchez.sergio.bullkeeper.tasks.impl.analysis;

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

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentResultsEntity;

/**
 * Sentiment Analysis Tasks
 * @author ssanchez
 *
 */
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
        Page<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsSentimentStatus(
        		AnalysisStatusEnum.PENDING, pageable);
        if(pendingComments.hasContent()) 
        	startAnalysisFor(AnalysisTypeEnum.SENTIMENT, pendingComments.getContent());

    }
	
	
	/**
	 * Task to cancel unfinished sentiment analysis
	 */
	@Scheduled(cron = "${task.analysis.sentiment.cancel.not.finished.interval}")
	public void cancelingUnfinishedSentimentAnalysisTasks(){
		logger.debug("Canceling unfinished sentiment analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNMinutes(AnalysisTypeEnum.SENTIMENT, maximumHoursOfAnAnalysis);
	}
	
	
	/**
	 * Task to analyze the results of sentiment analysis
	 */
	@Scheduled(cron = "${task.analysis.sentiment.analyze.results.scheduling.interval}")
	public void sentimentAnalysisResults() {
		logger.debug("sentiment analysis results");
		
		List<KidEntity> resultSentimentObsolete = sonRepository.findAllByResultsSentimentObsolete(Boolean.TRUE);
		
		logger.debug(resultSentimentObsolete.toString());
		
		Map<KidEntity, Map<SentimentLevelEnum, Long>> sentimentResults = resultSentimentObsolete.parallelStream()
			.collect(Collectors.toMap(
				sonEntity -> sonEntity,
				sonEntity -> commentRepository
				.findAllByKidIdAndAnalysisResultsSentimentStatus(sonEntity.getId(), AnalysisStatusEnum.FINISHED)
				.parallelStream()
				.map(comment -> comment.getAnalysisResults().getSentiment())
				.filter(comment -> comment.getResult() != null)
				.collect(Collectors.groupingBy(
					sentiment -> SentimentLevelEnum.fromResult(sentiment.getResult()),
					Collectors.counting()))));
		
		logger.debug("Sentiment by son -> " + sentimentResults.toString());
		
		for (Map.Entry<KidEntity, Map<SentimentLevelEnum, Long>> sentimentResultEntry : sentimentResults.entrySet())
	     {
			
			final KidEntity sonEntity = sentimentResultEntry.getKey();
			
			final SentimentResultsEntity sentimentResultsEntity = sonEntity.getResults().getSentiment();
			final Long totalCommentsAnalyzedForSentimentForThisPeriod = 
					commentRepository.countByKidIdAndAnalysisResultsSentimentFinishAtGreaterThanEqual(
							sonEntity.getId(), sentimentResultsEntity.getDate());
			
			
			if(totalCommentsAnalyzedForSentimentForThisPeriod > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.sentiment.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.sentiment.total.analyzed.body", new Object[] { totalCommentsAnalyzedForSentimentForThisPeriod,
								prettyTime.format(sentimentResultsEntity.getDate()) }),
						sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
			}
			
			if(sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.NEGATIVE)) {
				
				final Long totalCommentsAnalyzedForSentiment = 
						commentRepository.countByKidIdAndAnalysisResultsSentimentStatus(
								sonEntity.getId(), AnalysisStatusEnum.FINISHED);
				
				final Long totalNegativeComments = sentimentResultEntry.getValue().get(SentimentLevelEnum.NEGATIVE);
	
				final int percentage = Math.round((float)totalNegativeComments/totalCommentsAnalyzedForSentiment*100);
				logger.debug("Percentage -> " + percentage);
				
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.low", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.medium", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.sentiment.negative.title"),
							messageSourceResolver.resolver("alerts.sentiment.negative.hight", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
				}
				
			} else {
				
				alertService.save(AlertLevelEnum.SUCCESS, 
						messageSourceResolver.resolver("alerts.sentiment.negative.title"),
						messageSourceResolver.resolver("alerts.sentiment.negative.nothing"),
						sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
			}
			

			sonRepository.updateSentimentResultsFor(sonEntity.getId(), 
					sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.POSITIVE) ? 
					sentimentResultEntry.getValue().get(SentimentLevelEnum.POSITIVE): 0L, 
					sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.NEGATIVE)
					? sentimentResultEntry.getValue().get(SentimentLevelEnum.NEGATIVE) : 0L,
							sentimentResultEntry.getValue().containsKey(SentimentLevelEnum.NEUTRO) ? 
							sentimentResultEntry.getValue().get(SentimentLevelEnum.NEUTRO): 0L);
			
	     }
		
	}
	
	
}
