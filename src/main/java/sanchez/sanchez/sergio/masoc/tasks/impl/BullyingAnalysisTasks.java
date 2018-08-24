package sanchez.sanchez.sergio.masoc.tasks.impl;

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

import sanchez.sanchez.sergio.masoc.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.AnalysisEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.BullyingResultsEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;

@Component
public class BullyingAnalysisTasks extends AbstractAnalysisTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(AdultAnalysisTasks.class);
	
	@Value("${task.analysis.bullying.number.of.comments}")
	private Integer maximumNumberOfCommentsForBullyingAnalysis;
	
	
	/**
	 * Task for bullying analysis scheduling.
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${task.analysis.bullying.scheduling.interval}")
    public void bullyingAnalysisScheduling() {
        logger.debug("scheduling bullying analysis for comments at " + new Date());
        Pageable pageable = new PageRequest(0, maximumNumberOfCommentsForBullyingAnalysis);
        Page<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsBullyingStatus(AnalysisStatusEnum.PENDING, pageable);
        if(pendingComments.hasContent())
        	startAnalysisFor(AnalysisTypeEnum.BULLYING, pendingComments.getContent());
        	
	}
	
	
	/**
	 * Task to cancel unfinished bullying analysis
	 */
	@Scheduled(cron = "${task.analysis.bullying.cancel.not.finished.interval}")
	public void cancelingUnfinishedBullyingAnalysisTasks(){
		logger.debug("Canceling unfinished bullying analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum.BULLYING, maximumHoursOfAnAnalysis);
	}
	
	
	/**
	 * Task to analyze the results of the analysis of bullying
	 */
	@Scheduled(cron = "${task.analysis.bullying.analyze.results.scheduling.interval}")
	public void bullyingAnalysisResults() {
		
		logger.debug("bullying analysis results");
		
		Map<SonEntity, Map<BullyingLevelEnum, Long>> bullyingBySon = sonRepository
				.findAllByResultsBullyingObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findAllBySonEntityIdAndAnalysisResultsBullyingStatus(sonEntity.getId(), AnalysisStatusEnum.FINISHED)
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getBullying())
						.filter(comment -> comment.getResult() != null)
						.collect(Collectors.groupingBy(analysisResult -> BullyingLevelEnum.fromResult(analysisResult.getResult()), Collectors.counting()))));
		
		logger.debug("Bullying by son -> " + bullyingBySon.toString());
		
		for (Map.Entry<SonEntity, Map<BullyingLevelEnum, Long>> bullyingBySonEntry : bullyingBySon.entrySet())
	     {
			
			final SonEntity sonEntity = bullyingBySonEntry.getKey();
			logger.debug("Analysis Drugs Results for -> " + sonEntity.getFullName());
			final Map<BullyingLevelEnum, Long> results = bullyingBySonEntry.getValue();
			final Integer totalComments = bullyingBySonEntry.getValue().values().stream().mapToInt(Number::intValue).sum();;
			final BullyingResultsEntity bullyingResultsEntity = sonEntity.getResults().getBullying();
			
			final Long totalCommentsAnalyzedForBullying = commentRepository.countByAnalysisResultsBullyingFinishAtGreaterThanEqual(bullyingResultsEntity.getDate());
			
			if(totalCommentsAnalyzedForBullying > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.bullying.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.bullying.total.analyzed.body", new Object[] { totalCommentsAnalyzedForBullying, prettyTime.format(bullyingResultsEntity.getDate()) }),
						sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
			}
			
			if(results.containsKey(BullyingLevelEnum.POSITIVE)) {
				
				final Long totalCommentsBullyingContent = results.get(BullyingLevelEnum.POSITIVE);
				final int percentage = Math.round((float)totalCommentsBullyingContent/totalComments*100);
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.bullying.negative.title"),
							messageSourceResolver.resolver("alerts.bullying.negative.low", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.bullying.negative.title"),
							messageSourceResolver.resolver("alerts.bullying.negative.medium", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.bullying.negative.title"),
							messageSourceResolver.resolver("alerts.bullying.negative.hight", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				}
				
			}

			sonRepository.updateBullyingResultsFor(sonEntity.getId(), 
					results.containsKey(BullyingLevelEnum.POSITIVE) ? results.get(BullyingLevelEnum.POSITIVE): 0L, 
							results.containsKey(BullyingLevelEnum.NEGATIVE) ? results.get(BullyingLevelEnum.NEGATIVE): 0L);
	     }
				
	}

}
