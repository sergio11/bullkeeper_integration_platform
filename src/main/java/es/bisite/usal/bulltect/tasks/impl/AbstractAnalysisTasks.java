package es.bisite.usal.bulltect.tasks.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.domain.service.IAnalysisService;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.integration.service.IIntegrationFlowService;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.persistence.repository.SonRepository;
import io.jsonwebtoken.lang.Assert;

public abstract class AbstractAnalysisTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractAnalysisTasks.class);
	
	
	@Autowired
	protected IIntegrationFlowService itegrationFlowService;
	
	@Autowired
	protected CommentRepository commentRepository;
	
	@Autowired
	protected IAnalysisService analysisService;
	
	@Autowired
	protected SonRepository sonRepository;
	
	@Autowired
	protected IMessageSourceResolverService messageSourceResolver;
	
	@Autowired
	protected IAlertService alertService;
	
	@Autowired
	protected PrettyTime prettyTime;
	
	@Value("${task.analysis.maximum.hours.of.an.analysis}")
	protected Integer maximumHoursOfAnAnalysis;
	
	@SuppressWarnings("unchecked")
	protected void startAnalysisFor(final AnalysisTypeEnum type, List<CommentEntity> comments) {
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

}
