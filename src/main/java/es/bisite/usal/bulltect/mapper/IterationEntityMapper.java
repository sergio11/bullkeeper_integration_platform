package es.bisite.usal.bulltect.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import es.bisite.usal.bulltect.persistence.entity.IterationEntity;
import es.bisite.usal.bulltect.web.dto.response.IterationDTO;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;

/**
 * @author sergio
 */
@Mapper
public abstract class IterationEntityMapper {
	
	@Autowired
	protected TaskEntityMapper taskEntityMapper;
    
    @Mappings({
        @Mapping(source = "iterationEntity.startDate", target = "startDate", dateFormat = "yyyy/MM/dd HH:mm:ss")
    })
    @Named("iterationEntityToIterationDTO")
    public abstract IterationDTO iterationEntityToIterationDTO(IterationEntity iterationEntity); 
	
    @IterableMapping(qualifiedByName = "iterationEntityToIterationDTO")
    public abstract List<IterationDTO> iterationEntitiesToIterationDTOs(List<IterationEntity> iterationEntities);
    
    @Mappings({
        @Mapping(source = "iterationEntity.startDate", target = "startDate", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(source = "iterationEntity.finishDate", target = "finishDate", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(expression="java(taskEntityMapper.taskEntitiesToTaskDTOs(iterationEntity.getTasks()))", target = "tasks")
    })
    @Named("iterationEntityToIterationWithTasksDTO")
    public abstract IterationWithTasksDTO iterationEntityToIterationWithTasksDTO(IterationEntity iterationEntity); 

    
    @IterableMapping(qualifiedByName = "iterationEntityToIterationWithTasksDTO")
    public abstract List<IterationWithTasksDTO> iterationEntitiesToIterationWithTasksDTOs(List<IterationEntity> iterationEntities);
    
}
