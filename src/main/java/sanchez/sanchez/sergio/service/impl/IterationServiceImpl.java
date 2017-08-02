package sanchez.sanchez.sergio.service.impl;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.dto.IterationDTO;
import sanchez.sanchez.sergio.mapper.IIterationEntityMapper;
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
    
    private final IterationRepository iterationRepository;
    private final IIterationEntityMapper iterationEntityMapper;

    public IterationServiceImpl(IterationRepository iterationRepository, IIterationEntityMapper iterationEntityMapper) {
        this.iterationRepository = iterationRepository;
        this.iterationEntityMapper = iterationEntityMapper;
    }
    
  
    @Override
    public void save(IterationEntity iterationEntity) {
        logger.debug("Total Task ..." + iterationEntity.getTotalTasks());
        logger.debug("Total Task Failed ..." + iterationEntity.getTotalFailedTasks());
        iterationRepository.save(iterationEntity);
    }
    
   
    @Override
    public Long getTotalIterations() {
        return iterationRepository.count();
    }

    @Override
    public Page<IterationDTO> findPaginated(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size);
        Page<IterationEntity> iterationsPage = iterationRepository.findAll(pageable);
        return iterationsPage.map(new Converter<IterationEntity, IterationDTO>(){
            @Override
            public IterationDTO convert(IterationEntity ite) {
               return iterationEntityMapper.iterationEntityToIterationDTO(ite);
            }
        });
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(iterationRepository, "IterationRepository cannot be null");
        Assert.notNull(iterationEntityMapper, "IIterationEntityMapper cannot be null");
    }
    
}
