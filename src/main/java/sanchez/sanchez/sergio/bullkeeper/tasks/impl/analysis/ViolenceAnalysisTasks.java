package sanchez.sanchez.sergio.bullkeeper.tasks.impl.analysis;

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

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceResultsEntity;

/**
 * Violence Analysis Tasks
 * @author ssanchez
 *
 */
@Component
public class ViolenceAnalysisTasks extends AbstractAnalysisTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(ViolenceAnalysisTasks.class);
	
	@Value("${task.analysis.violence.number.of.comments}")
	private Integer maximumNumberOfCommentsForViolenceAnalysis;
	
	
	/**
	 * Task for violence analysis scheduling.
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${task.analysis.violence.scheduling.interval}")
    public void violenceAnalysisScheduling() {
        logger.debug("scheduling violence analysis for comments at " + new Date());
        
        Pageable pageable = new PageRequest(0, maximumNumberOfCommentsForViolenceAnalysis);
        Page<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsViolenceStatus(AnalysisStatusEnum.PENDING, pageable);
        if(pendingComments.hasContent())
        	startAnalysisFor(AnalysisTypeEnum.VIOLENCE, pendingComments.getContent());
        	
	}
	
	/**
	 * Task to cancel unfinished violence analysis
	 */
	@Scheduled(cron = "${task.analysis.violence.cancel.not.finished.interval}")
	public void cancelingUnfinishedViolenceAnalysisTasks(){
		logger.debug("Canceling unfinished violence analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNMinutes(AnalysisTypeEnum.VIOLENCE, maximumHoursOfAnAnalysis);
	}
	
	/**
	 * Task to analyze the results of violence analysis
	 */
	@Scheduled(cron = "${task.analysis.violence.analyze.results.scheduling.interval}")
	public void violenceAnalysisResults() {
		
		logger.debug("violence analysis results");
	
	
		Map<KidEntity, Map<ViolenceLevelEnum, Long>> violenceBySon = sonRepository
				.findAllByResultsViolenceObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findAllByKidIdAndAnalysisResultsViolenceStatus(sonEntity.getId(), AnalysisStatusEnum.FINISHED)
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getViolence())
						.filter(comment -> comment.getResult() != null)
						.collect(Collectors.groupingBy(analysisResult -> ViolenceLevelEnum.fromResult(analysisResult.getResult()), Collectors.counting()))));
						
		logger.debug("Violence by son -> " + violenceBySon.toString());
		
		for (Map.Entry<KidEntity, Map<ViolenceLevelEnum, Long>> violenceBySonEntry : violenceBySon.entrySet())
	     {
			
			final KidEntity sonEntity = violenceBySonEntry.getKey();
			logger.debug("Analysis Violence Results for -> " + sonEntity.getFullName());
			
			final Map<ViolenceLevelEnum, Long> results = violenceBySonEntry.getValue();
			
			final ViolenceResultsEntity  violenceResults = sonEntity.getResults().getViolence();
			
			final Long totalCommentsAnalyzedForViolenceForThisPeriod = commentRepository
					.countByAnalysisResultsViolenceFinishAtGreaterThanEqual(violenceResults.getDate());
			
			
			if(totalCommentsAnalyzedForViolenceForThisPeriod > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.violence.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.violence.total.analyzed.body", new Object[] { totalCommentsAnalyzedForViolenceForThisPeriod, 
								prettyTime.format(violenceResults.getDate()) }),
						sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
			}
			
			
			if(results.containsKey(ViolenceLevelEnum.POSITIVE)) {
				
				final Long totalCommentsAnalyzedForViolence = 
						commentRepository.countByAnalysisResultsViolenceStatus(AnalysisStatusEnum.FINISHED);
				
				final Long totalViolenceComments = results.get(ViolenceLevelEnum.POSITIVE);
				final int percentage = Math.round((float)totalViolenceComments/totalCommentsAnalyzedForViolence*100);
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.violence.negative.title"),
							messageSourceResolver.resolver("alerts.violence.negative.low", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.violence.negative.title"),
							messageSourceResolver.resolver("alerts.violence.negative.medium", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.violence.negative.title"),
							messageSourceResolver.resolver("alerts.violence.negative.hight", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_KID);
				}
				
			}
			
			sonRepository.updateViolenceResultsFor(sonEntity.getId(), 
					results.containsKey(ViolenceLevelEnum.POSITIVE) ? results.get(ViolenceLevelEnum.POSITIVE): 0L, 
					results.containsKey(ViolenceLevelEnum.NEGATIVE) ? results.get(ViolenceLevelEnum.NEGATIVE) : 0L);
	     }
				
	}

}
