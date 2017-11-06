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
import es.bisite.usal.bulltect.persistence.entity.AnalysisEntity;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.BullyingResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;

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
		
		final Integer BULLYING_CONTENT = 1;
		final Integer NOBULLYING_CONTENT = 0;
		
		Map<SonEntity, Map<Integer, Long>> bullyingBySon = sonRepository
				.findAllByResultsBullyingObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findBySonEntityId(sonEntity.getId())
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getBullying())
						.collect(Collectors.groupingBy(AnalysisEntity::getResult, Collectors.counting()))));
		
		
		for (Map.Entry<SonEntity, Map<Integer, Long>> bullyingBySonEntry : bullyingBySon.entrySet())
	     {
			
			final SonEntity sonEntity = bullyingBySonEntry.getKey();
			final Map<Integer, Long> results = bullyingBySonEntry.getValue();
			final Integer totalComments = bullyingBySonEntry.getValue().values().stream().mapToInt(Number::intValue).sum();;
			final BullyingResultsEntity bullyingResultsEntity = sonEntity.getResults().getBullying();
			
			final Long totalCommentsAnalyzedForBullying = commentRepository.countByAnalysisResultsBullyingFinishAtGreaterThanEqual(bullyingResultsEntity.getDate());
			
			if(totalCommentsAnalyzedForBullying > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.bullying.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.bullying.total.analyzed.body", new Object[] { totalCommentsAnalyzedForBullying, prettyTime.format(bullyingResultsEntity.getDate()) }),
						sonEntity.getId());
			}
			
			if(results.containsKey(BULLYING_CONTENT)) {
				
				final Long totalCommentsBullyingContent = results.get(BULLYING_CONTENT);
				final float percentage = totalCommentsBullyingContent/totalComments*100;
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.bullying.negative.title"),
							messageSourceResolver.resolver("alerts.bullying.negative.low", new Object[] { percentage }),
							sonEntity.getId());
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.bullying.negative.title"),
							messageSourceResolver.resolver("alerts.bullying.negative.medium", new Object[] { percentage }),
							sonEntity.getId());
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.bullying.negative.title"),
							messageSourceResolver.resolver("alerts.bullying.negative.hight", new Object[] { percentage }),
							sonEntity.getId());
				}
				
			}
			
			bullyingResultsEntity.setDate(new Date());
			bullyingResultsEntity.setObsolete(Boolean.FALSE);
			bullyingResultsEntity.setTotalCommentsBullying(results.get(BULLYING_CONTENT));
			bullyingResultsEntity.setTotalCommentsNoBullying(results.get(NOBULLYING_CONTENT));
			sonRepository.save(sonEntity);
	     }
				
	}

}
