package es.bisite.usal.bulltect.tasks.config;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.ListUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.domain.service.IAnalysisService;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.integration.service.IIntegrationFlowService;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.DrugsResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.SentimentLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.SentimentResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.entity.ViolenceResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.AdultResultsEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisEntity;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.BullyingResultsEntity;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import io.jsonwebtoken.lang.Assert;

@Configuration
@EnableScheduling
public class AnalysisTasksConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(AnalysisTasksConfig.class);
	
	private final IIntegrationFlowService itegrationFlowService;
	private final CommentRepository commentRepository;
	private final IAnalysisService analysisService;
	private final SonRepository sonRepository;
	private final IMessageSourceResolverService messageSourceResolver;
	private final IAlertService alertService;
	private final PrettyTime pt = new PrettyTime(LocaleContextHolder.getLocale());
	
	
	@Value("${task.analysis.maximum.hours.of.an.analysis}")
	private Integer maximumHoursOfAnAnalysis;
	@Value("${task.analysis.sentiment.number.of.comments}")
	private Integer maximumNumberOfCommentsForSentimentAnalysis;
	@Value("${task.analysis.violence.number.of.comments}")
	private Integer maximumNumberOfCommentsForViolenceAnalysis;
	@Value("${task.analysis.drugs.number.of.comments}")
	private Integer maximumNumberOfCommentsForDrugsAnalysis;
	@Value("${task.analysis.adult.number.of.comments}")
	private Integer maximumNumberOfCommentsForAdultAnalysis;
	
	@Autowired
	public AnalysisTasksConfig(IIntegrationFlowService itegrationFlowService, 
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
	private void startAnalysisFor(final AnalysisTypeEnum type, List<CommentEntity> comments) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(comments, "Comments can not be null");
		Assert.notEmpty(comments, "Comments can not be empty");
		
		logger.debug(String.format("%s analysis for a total of %d comments", type.name(), comments.size()));
		
		analysisService.startAnalysisFor(type, comments.parallelStream()
        			.collect(Collectors.toMap(
        					comment -> comment.getSonEntity().getId(),
        					comment -> Arrays.asList(comment.getId()),
        					(comments1, comments2) -> ListUtils.union(comments1, comments2)
        		    )));
		
		
	}
	
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
	 * Task for bullying analysis scheduling.
	 */
	@SuppressWarnings("unchecked")
	@Scheduled(cron = "${task.analysis.bullying.scheduling.interval}")
    public void bullyingAnalysisScheduling() {
        logger.debug("scheduling bullying analysis for comments at " + new Date());
        Pageable pageable = new PageRequest(0, maximumNumberOfCommentsForAdultAnalysis);
        Page<CommentEntity> pendingComments = commentRepository.findAllByAnalysisResultsBullyingStatus(AnalysisStatusEnum.PENDING, pageable);
        if(pendingComments.hasContent())
        	startAnalysisFor(AnalysisTypeEnum.BULLYING, pendingComments.getContent());
        	
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
	 * Task to cancel unfinished violence analysis
	 */
	@Scheduled(cron = "${task.analysis.violence.cancel.not.finished.interval}")
	public void cancelingUnfinishedViolenceAnalysisTasks(){
		logger.debug("Canceling unfinished violence analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum.VIOLENCE, maximumHoursOfAnAnalysis);
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
	 * Task to cancel unfinished bullying analysis
	 */
	@Scheduled(cron = "${task.analysis.bullying.cancel.not.finished.interval}")
	public void cancelingUnfinishedBullyingAnalysisTasks(){
		logger.debug("Canceling unfinished bullying analysis tasks");
		commentRepository.cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum.BULLYING, maximumHoursOfAnAnalysis);
	}
	


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
	
	
	@Scheduled(cron = "${task.analysis.violence.analyze.results.scheduling.interval}")
	public void violenceAnalysisResults() {
		
		logger.debug("violence analysis results");
		
		Map<SonEntity, Double> violenceAvgBySon = sonRepository
				.findAllByResultsViolenceObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findBySonEntityId(sonEntity.getId())
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getViolence())
						.mapToInt(AnalysisEntity::getResult)
						.average().orElse(0)));
		
		
		for (Map.Entry<SonEntity, Double> violenceAvgBySonEntry : violenceAvgBySon.entrySet())
	     {
			
			final SonEntity sonEntity = violenceAvgBySonEntry.getKey();
			final Double avgValue = violenceAvgBySonEntry.getValue();
			
			
			final ViolenceResultsEntity violenceResultsEntity = sonEntity.getResults().getViolence();
			violenceResultsEntity.setDate(new Date());
			violenceResultsEntity.setObsolete(Boolean.FALSE);
			violenceResultsEntity.setAvgValue(avgValue);
			sonRepository.save(sonEntity);
	     }
				
	}
	
	
	@Scheduled(cron = "${task.analysis.drugs.analyze.results.scheduling.interval}")
	public void drugsAnalysisResults() {
		
		logger.debug("drugs analysis results");
		
		Map<SonEntity, Double> drugsAvgBySon = sonRepository
				.findAllByResultsDrugsObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findBySonEntityId(sonEntity.getId())
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getDrugs())
						.mapToInt(AnalysisEntity::getResult)
						.average().orElse(0)));
		
		
		for (Map.Entry<SonEntity, Double> drugsAvgBySonEntry : drugsAvgBySon.entrySet())
	     {
			
			final SonEntity sonEntity = drugsAvgBySonEntry.getKey();
			final Double avgValue = drugsAvgBySonEntry.getValue();
			
			
			final DrugsResultsEntity drugsResultsEntity = sonEntity.getResults().getDrugs();
			drugsResultsEntity.setDate(new Date());
			drugsResultsEntity.setObsolete(Boolean.FALSE);
			drugsResultsEntity.setAvgValue(avgValue);
			sonRepository.save(sonEntity);
	     }
				
	}
	
	
	
	@Scheduled(cron = "${task.analysis.adult.analyze.results.scheduling.interval}")
	public void adultAnalysisResults() {
		
		logger.debug("adult analysis results");
		
		Map<SonEntity, Double> adultAvgBySon = sonRepository
				.findAllByResultsAdultObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findBySonEntityId(sonEntity.getId())
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getAdult())
						.mapToInt(AnalysisEntity::getResult)
						.average().orElse(0)));
		
		
		for (Map.Entry<SonEntity, Double> adultAvgBySonEntry : adultAvgBySon.entrySet())
	     {
			
			final SonEntity sonEntity = adultAvgBySonEntry.getKey();
			final Double avgValue = adultAvgBySonEntry.getValue();
			
			
			final AdultResultsEntity adultResultsEntity = sonEntity.getResults().getAdult();
			adultResultsEntity.setDate(new Date());
			adultResultsEntity.setObsolete(Boolean.FALSE);
			adultResultsEntity.setAvgValue(avgValue);
			sonRepository.save(sonEntity);
	     }
				
	}
	
	@Scheduled(cron = "${task.analysis.bullying.analyze.results.scheduling.interval}")
	public void bullyingAnalysisResults() {
		
		logger.debug("bullying analysis results");
		
		Map<SonEntity, Double> bullyingAvgBySon = sonRepository
				.findAllByResultsBullyingObsolete(Boolean.TRUE)
				.parallelStream()
				.collect(Collectors.toMap(
						sonEntity -> sonEntity,
						sonEntity -> commentRepository
						.findBySonEntityId(sonEntity.getId())
						.parallelStream()
						.map(comment -> comment.getAnalysisResults().getBullying())
						.mapToInt(AnalysisEntity::getResult)
						.average().orElse(0)));
		
		
		for (Map.Entry<SonEntity, Double> bullyingAvgBySonEntry : bullyingAvgBySon.entrySet())
	     {
			
			final SonEntity sonEntity = bullyingAvgBySonEntry.getKey();
			final Double avgValue = bullyingAvgBySonEntry.getValue();
			
			
			final BullyingResultsEntity bullyingResultsEntity = sonEntity.getResults().getBullying();
			bullyingResultsEntity.setDate(new Date());
			bullyingResultsEntity.setObsolete(Boolean.FALSE);
			bullyingResultsEntity.setAvgValue(avgValue);
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
