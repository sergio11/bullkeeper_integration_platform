package es.bisite.usal.bulltect.tasks.impl;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.persistence.entity.AdultResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertCategoryEnum;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisEntity;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;

@Component
@ConditionalOnProperty(name = "task.analysis.adult.status", havingValue = "enable", matchIfMissing = false)
public class AdultAnalysisTasks extends AbstractAnalysisTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(AdultAnalysisTasks.class);
	
	
	@Value("${task.analysis.adult.number.of.comments}")
	private Integer maximumNumberOfCommentsForAdultAnalysis;
	
	/**
	 * Task for adult analysis scheduling.
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${task.analysis.adult.scheduling.interval}")
    public void adultAnalysisScheduling() {
        logger.debug("scheduling adult analysis for comments at " + new Date());
        Pageable pageable = new PageRequest(0, maximumNumberOfCommentsForAdultAnalysis);
        Page<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsAdultStatus(AnalysisStatusEnum.PENDING, pageable);
        if(pendingComments.hasContent())
        	startAnalysisFor(AnalysisTypeEnum.ADULT, pendingComments.getContent());
        	
	}
	
	/**
	 * Task to cancel unfinished adult analysis
	 */
	@Scheduled(cron = "${task.analysis.adult.cancel.not.finished.interval}")
	public void cancelingUnfinishedAdultAnalysisTasks(){
		logger.debug("Canceling unfinished adult analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum.ADULT, maximumHoursOfAnAnalysis);
	}
	
	/**
	 * Task to analyze the results of adult content analysis
	 */
	@Scheduled(cron = "${task.analysis.adult.analyze.results.scheduling.interval}")
	public void adultAnalysisResults() {
		
		logger.debug("adult analysis results");
		
		final Integer ADULT_CONTENT = 1;
		final Integer NO_ADULT_CONTENT = 0;
		
		Map<SonEntity, Map<Integer, Long>> adultContentBySon = sonRepository
				.findAllByResultsAdultObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findAllBySonEntityIdAndAnalysisResultsAdultStatus(sonEntity.getId(), AnalysisStatusEnum.FINISHED)
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getAdult())
						.filter(comment -> comment.getResult() != null)
						.collect(Collectors.groupingBy(AnalysisEntity::getResult, Collectors.counting()))));
		
		logger.debug("Adult content by son -> " + adultContentBySon.toString());
		
		for (Map.Entry<SonEntity, Map<Integer, Long>> adultContentBySonEntry : adultContentBySon.entrySet())
	     {
			
			final SonEntity sonEntity = adultContentBySonEntry.getKey();
			logger.debug("Analysis Adult Content Results for -> " + sonEntity.getFullName());
			final Integer totalComments = adultContentBySonEntry.getValue().values().stream().mapToInt(Number::intValue).sum();
			final Map<Integer, Long> results = adultContentBySonEntry.getValue();
			final AdultResultsEntity adultResultsEntity = sonEntity.getResults().getAdult();
			
			final Long totalCommentsAnalyzedForAdult = commentRepository.countByAnalysisResultsAdultFinishAtGreaterThanEqual(adultResultsEntity.getDate());
			
			if(totalCommentsAnalyzedForAdult > 0) {
				alertService.save(AlertLevelEnum.INFO, 
						messageSourceResolver.resolver("alerts.adult.total.analyzed.title"),
						messageSourceResolver.resolver("alerts.adult.total.analyzed.body", new Object[] { totalCommentsAnalyzedForAdult, prettyTime.format(adultResultsEntity.getDate()) }),
						sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
			}
			
			if(results.containsKey(ADULT_CONTENT)) {
				
				final Long totalCommentsAdultContent = results.get(ADULT_CONTENT);
				final int percentage = Math.round((float)totalCommentsAdultContent/totalComments*100);
				if(percentage <= 30) {
					alertService.save(AlertLevelEnum.SUCCESS, 
							messageSourceResolver.resolver("alerts.adult.negative.title"),
							messageSourceResolver.resolver("alerts.adult.negative.low", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else if(percentage > 30 && percentage <= 60) {
					alertService.save(AlertLevelEnum.WARNING, 
							messageSourceResolver.resolver("alerts.adult.negative.title"),
							messageSourceResolver.resolver("alerts.adult.negative.medium", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				} else {
					alertService.save(AlertLevelEnum.DANGER, 
							messageSourceResolver.resolver("alerts.adult.negative.title"),
							messageSourceResolver.resolver("alerts.adult.negative.hight", new Object[] { percentage }),
							sonEntity.getId(), AlertCategoryEnum.STATISTICS_SON);
				}
				
			}
			
			adultResultsEntity.setDate(new Date());
			adultResultsEntity.setObsolete(Boolean.FALSE);
			adultResultsEntity.setTotalCommentsAdultContent(results.containsKey(ADULT_CONTENT) ? results.get(ADULT_CONTENT) : 0L);
			adultResultsEntity.setTotalCommentsNoAdultContent(results.containsKey(NO_ADULT_CONTENT) ? results.get(NO_ADULT_CONTENT): 0L);
			sonRepository.save(sonEntity);
	     }
				
	}
	

}
