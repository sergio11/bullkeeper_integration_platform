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

import es.bisite.usal.bulltect.persistence.entity.AlertCategoryEnum;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisEntity;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.DrugsLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.DrugsResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;

@Component
public class DrugsAnalysisTasks extends AbstractAnalysisTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(DrugsAnalysisTasks.class);
	
	@Value("${task.analysis.drugs.number.of.comments}")
	private Integer maximumNumberOfCommentsForDrugsAnalysis;
	
	/**
	 * Task for drugs analysis scheduling.
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${task.analysis.drugs.scheduling.interval}")
    public void drugsAnalysisScheduling() {
        logger.debug("scheduling drugs analysis for comments at " + new Date());
        Pageable pageable = new PageRequest(0, maximumNumberOfCommentsForDrugsAnalysis);
        Page<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsDrugsStatus(AnalysisStatusEnum.PENDING, pageable);
        if(pendingComments.hasContent())
        	startAnalysisFor(AnalysisTypeEnum.DRUGS, pendingComments.getContent());
        	
	}
	
	/**
	 * Task to cancel unfinished drugs analysis
	 */
	@Scheduled(cron = "${task.analysis.drugs.cancel.not.finished.interval}")
	public void cancelingUnfinishedDrugsAnalysisTasks(){
		logger.debug("Canceling unfinished drugs analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum.DRUGS, maximumHoursOfAnAnalysis);
	}
	
	
	/**
	 * Task to analyze the results of drug analysis
	 */
	@Scheduled(cron = "${task.analysis.drugs.analyze.results.scheduling.interval}")
	public void drugsAnalysisResults() {
		
		logger.debug("drugs analysis results");
		
		
		Map<SonEntity, Map<DrugsLevelEnum, Long>> drugsBySon = sonRepository
				.findAllByResultsDrugsObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findAllBySonEntityIdAndAnalysisResultsDrugsStatus(sonEntity.getId(), AnalysisStatusEnum.FINISHED)
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getDrugs())
						.filter(comment -> comment.getResult() != null)
						.collect(Collectors.groupingBy(analysisResult -> DrugsLevelEnum.fromResult(analysisResult.getResult()), Collectors.counting()))));
		logger.debug("Drugs by son -> " + drugsBySon.toString());
		
		for (Map.Entry<SonEntity, Map<DrugsLevelEnum, Long>> drugsBySonEntry : drugsBySon.entrySet())
	     {
			
			final SonEntity sonEntity = drugsBySonEntry.getKey();
			
			logger.debug("Analysis Drugs Results for -> " + sonEntity.getFullName());
			final Map<DrugsLevelEnum, Long> results = drugsBySonEntry.getValue();
			final Integer totalComments = drugsBySonEntry.getValue().values().stream().mapToInt(Number::intValue).sum();
			final DrugsResultsEntity  drugsResultsEntity = sonEntity.getResults().getDrugs();
			
			final Long totalCommentsAnalyzedForDrugs = commentRepository.countByAnalysisResultsDrugsFinishAtGreaterThanEqual(drugsResultsEntity.getDate());

			if(totalCommentsAnalyzedForDrugs > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.drugs.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.drugs.total.analyzed.body", new Object[] { totalCommentsAnalyzedForDrugs, prettyTime.format(drugsResultsEntity.getDate()) }),
						sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
			}
			
			if(results.containsKey(DrugsLevelEnum.POSITIVE)) {
				
				final Long totalDrugsComments = results.get(DrugsLevelEnum.POSITIVE);
				final int percentage = Math.round((float)totalDrugsComments/totalComments*100);
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.drugs.negative.title"),
							messageSourceResolver.resolver("alerts.drugs.negative.low", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.drugs.negative.title"),
							messageSourceResolver.resolver("alerts.drugs.negative.medium", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.drugs.negative.title"),
							messageSourceResolver.resolver("alerts.drugs.negative.hight", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				}
				
			}
		
			drugsResultsEntity.setDate(new Date());
			drugsResultsEntity.setObsolete(Boolean.FALSE);
			drugsResultsEntity.setTotalCommentsDrugs(results.containsKey(DrugsLevelEnum.POSITIVE) ? results.get(DrugsLevelEnum.POSITIVE): 0L);
			drugsResultsEntity.setTotalCommentsNoDrugs(results.containsKey(DrugsLevelEnum.NEGATIVE) ? results.get(DrugsLevelEnum.NEGATIVE): 0L);
			sonRepository.save(sonEntity);
	     }
				
	}
	

}