package sanchez.sanchez.sergio.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.dto.response.IterationDTO;
import sanchez.sanchez.sergio.mapper.IIterationEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.persistence.repository.IterationRepository;
import sanchez.sanchez.sergio.service.IIterationService;

/**
 * @author sergio
 */
@Service("iterationService")
public class IterationServiceImpl implements IIterationService {
    
    private Logger logger = LoggerFactory.getLogger(IterationServiceImpl.class);
    
    private final IterationRepository iterationRepository;
    private final IIterationEntityMapper iterationEntityMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public IterationServiceImpl(IterationRepository iterationRepository, 
            IIterationEntityMapper iterationEntityMapper, SimpMessagingTemplate simpMessagingTemplate) {
        this.iterationRepository = iterationRepository;
        this.iterationEntityMapper = iterationEntityMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    
  
    @Override
    public void save(IterationEntity iterationEntity) {
        logger.debug("Total Task ..." + iterationEntity.getTotalTasks());
        logger.debug("Total Task Failed ..." + iterationEntity.getTotalFailedTasks());
        IterationEntity iterationToSend = iterationRepository.save(iterationEntity);
        simpMessagingTemplate.convertAndSend("/topic/iterations/new", 
                iterationEntityMapper.iterationEntityToIterationDTO(iterationToSend));
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
    
    @Override
	public Date getLastProbing() {
    	PageRequest request = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "finishDate"));
    	List<IterationEntity> iterations = iterationRepository.findAll(request).getContent();
    	return iterations.size() > 0 ? iterations.get(0).getFinishDate() : null;
	}
    
    @Override
	public Page<IterationDTO> findPaginated(Pageable pageable) {
    	Page<IterationEntity> iterationsPage = iterationRepository.findAll(pageable);
        return iterationsPage.map(new Converter<IterationEntity, IterationDTO>(){
            @Override
            public IterationDTO convert(IterationEntity ite) {
               return iterationEntityMapper.iterationEntityToIterationDTO(ite);
            }
        });
	}
        
    @Override
    public List<IterationDTO> allIterations() {
        return iterationEntityMapper.iterationEntitiesToIterationDTOs(iterationRepository.findAll());
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(iterationRepository, "IterationRepository cannot be null");
        Assert.notNull(iterationEntityMapper, "IIterationEntityMapper cannot be null");
        Assert.notNull(simpMessagingTemplate, "SimpMessagingTemplate cannot be null");
    }
}
