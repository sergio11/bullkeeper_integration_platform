package sanchez.sanchez.sergio.service.impl;

import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.models.IterationResult;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.service.IResultService;

/**
 *
 * @author sergio
 */
@Service("resultService")
public class ResultServiceImpl implements IResultService {
    
    private Logger logger = LoggerFactory.getLogger(ResultServiceImpl.class);
    
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveIteration(IterationResult iterationResult) {
        logger.debug("Total Task ..." + iterationResult.getTotalTask());
        logger.debug("Total Task Failed ..." + iterationResult.getTaskFailed());
        logger.debug("Comments To Save ..." + iterationResult.getComments().size());
        logger.debug(iterationResult.getComments().toString());
        commentRepository.save(iterationResult.getComments());
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(commentRepository, "CommentRepository cannot be null");
    }
    
}
