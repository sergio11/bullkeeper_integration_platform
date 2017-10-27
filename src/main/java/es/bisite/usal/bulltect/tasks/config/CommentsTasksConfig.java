package es.bisite.usal.bulltect.tasks.config;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import es.bisite.usal.bulltect.integration.service.IItegrationFlowService;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.CommentStatusEnum;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import io.jsonwebtoken.lang.Assert;

@Configuration
@EnableScheduling
public class CommentsTasksConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentsTasksConfig.class);
	
	private final IItegrationFlowService itegrationFlowService;
	private final CommentRepository commentRepository;
	
	@Autowired
	public CommentsTasksConfig(IItegrationFlowService itegrationFlowService, CommentRepository commentRepository) {
		super();
		this.itegrationFlowService = itegrationFlowService;
		this.commentRepository = commentRepository;
	}

	@Scheduled(cron = "${task.comments.scheduled.unanalyzed.comments}")
    public void scheduledUnanalyzedComments() {
        logger.debug("scheduled Unanalyzed Comments ...");
        List<CommentEntity> pendingComments = commentRepository.findAllByStatus(CommentStatusEnum.PENDING);
        if(!pendingComments.isEmpty())
        	itegrationFlowService.startSentimentAnalysisFor(
        			pendingComments.stream().map((comment) -> comment.getId()).collect(Collectors.toList()));
    }
    
    @Scheduled(cron = "${task.comments.cancel.comment.analysis}")
    public void cancelCommentAnalysis() {
        logger.debug("cancel comment analysis ...");
        commentRepository.cancelCommentsInprogress();
    }
    
    
    @PostConstruct
    protected void init() {
        Assert.notNull(itegrationFlowService, "Integration Flow Service can not be null");
        Assert.notNull(commentRepository, "Comment Repository can not be null");
        logger.debug("CommentsTasksConfig initialized ...");
    }

}
