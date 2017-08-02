package sanchez.sanchez.sergio.service.impl;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.persistence.repository.IterationRepository;
import sanchez.sanchez.sergio.service.IIterationService;

/**
 *
 * @author sergio
 */
@Service("iterationService")
public class IterationServiceImpl implements IIterationService {
    
    private Logger logger = LoggerFactory.getLogger(IterationServiceImpl.class);
    
    @Autowired
    private IterationRepository iterationRepository;

    @Override
    public void save(IterationEntity iterationEntity) {
        logger.debug("Total Task ..." + iterationEntity.getTotalTasks());
        logger.debug("Total Task Failed ..." + iterationEntity.getTotalFailedTasks());
        iterationRepository.save(iterationEntity);
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(iterationRepository, "IterationRepository cannot be null");
    }
    
}
