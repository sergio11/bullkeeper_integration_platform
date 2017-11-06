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
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.entity.ViolenceResultsEntity;

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
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum.VIOLENCE, maximumHoursOfAnAnalysis);
	}
	
	/**
	 * Task to analyze the results of violence analysis
	 */
	@Scheduled(cron = "${task.analysis.violence.analyze.results.scheduling.interval}")
	public void violenceAnalysisResults() {
		
		logger.debug("violence analysis results");
		
		final Integer VIOLENCE = 1;
		final Integer NO_VIOLENCE = 0;
		
		
		Map<SonEntity, Map<Integer, Long>> violenceBySon = sonRepository
				.findAllByResultsViolenceObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findBySonEntityId(sonEntity.getId())
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getViolence())
						.collect(Collectors.groupingBy(AnalysisEntity::getResult, Collectors.counting()))));
						
		
		
		for (Map.Entry<SonEntity, Map<Integer, Long>> violenceBySonEntry : violenceBySon.entrySet())
	     {
			
			final SonEntity sonEntity = violenceBySonEntry.getKey();
			final Integer totalComments = violenceBySonEntry.getValue().values().stream().mapToInt(Number::intValue).sum();
			final Map<Integer, Long> results = violenceBySonEntry.getValue();
			
			final ViolenceResultsEntity  violenceResults = sonEntity.getResults().getViolence();
			
			final Long totalCommentsAnalyzedForViolence = commentRepository.countByAnalysisResultsViolenceFinishAtGreaterThanEqual(violenceResults.getDate());
			
			if(totalCommentsAnalyzedForViolence > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.violence.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.violence.total.analyzed.body", new Object[] { totalCommentsAnalyzedForViolence, prettyTime.format(violenceResults.getDate()) }),
						sonEntity.getId());
			}
			
			
			if(results.containsKey(VIOLENCE)) {
				
				final Long totalViolenceComments = results.get(VIOLENCE);
				final float percentage = totalViolenceComments/totalComments*100;
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.violence.negative.title"),
							messageSourceResolver.resolver("alerts.violence.negative.low", new Object[] { percentage }),
							sonEntity.getId());
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.violence.negative.title"),
							messageSourceResolver.resolver("alerts.violence.negative.medium", new Object[] { percentage }),
							sonEntity.getId());
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.violence.negative.title"),
							messageSourceResolver.resolver("alerts.violence.negative.hight", new Object[] { percentage }),
							sonEntity.getId());
				}
				
			}
			
		
			final ViolenceResultsEntity violenceResultsEntity = sonEntity.getResults().getViolence();
			violenceResultsEntity.setDate(new Date());
			violenceResultsEntity.setObsolete(Boolean.FALSE);
			violenceResultsEntity.setTotalNonViolentComments(results.get(NO_VIOLENCE));
			violenceResultsEntity.setTotalViolentComments(results.get(VIOLENCE));
			sonRepository.save(sonEntity);
	     }
				
	}

}
