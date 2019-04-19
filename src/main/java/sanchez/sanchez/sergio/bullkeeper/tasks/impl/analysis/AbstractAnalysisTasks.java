package sanchez.sanchez.sergio.bullkeeper.tasks.impl.analysis;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAnalysisService;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.integration.service.IIntegrationFlowService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;

public abstract class AbstractAnalysisTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractAnalysisTasks.class);
	
	
	@Autowired
	protected IIntegrationFlowService itegrationFlowService;
	
	@Autowired
	protected CommentRepository commentRepository;
	
	@Autowired
	protected IAnalysisService analysisService;
	
	@Autowired
	protected KidRepository sonRepository;
	
	@Autowired
	protected IMessageSourceResolverService messageSourceResolver;
	
	@Autowired
	protected IAlertService alertService;
	
	@Autowired
	protected PrettyTime prettyTime;
	
	@Value("${task.analysis.maximum.hours.of.an.analysis}")
	protected Integer maximumHoursOfAnAnalysis;
	
	
	/**
	 * 
	 * @param type
	 * @param comments
	 */
	@SuppressWarnings("unchecked")
	protected void startAnalysisFor(final AnalysisTypeEnum type, List<CommentEntity> comments) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(comments, "Comments can not be null");
		Assert.notEmpty(comments, "Comments can not be empty");
		
		logger.debug(String.format("%s analysis for a total of %d comments", type.name(), comments.size()));
		
		analysisService.startAnalysisFor(type, comments.parallelStream()
        			.collect(Collectors.toMap(
        					comment -> comment.getKid().getId(),
        					comment -> Arrays.asList(comment.getId()),
        					(comments1, comments2) -> ListUtils.union(comments1, comments2)
        		    )));
		
		
	}

}
