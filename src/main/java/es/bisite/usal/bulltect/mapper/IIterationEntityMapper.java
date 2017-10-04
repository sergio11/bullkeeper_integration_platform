package es.bisite.usal.bulltect.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.IterationEntity;
import es.bisite.usal.bulltect.web.dto.response.IterationDTO;

/**
 * @author sergio
 */
@Mapper
public interface IIterationEntityMapper {
    
    @Mappings({
        @Mapping(source = "iterationEntity.startDate", target = "startDate", dateFormat = "yyyy/MM/dd HH:mm:ss"),
        @Mapping(source = "iterationEntity.finishDate", target = "finishDate", dateFormat = "yyyy/MM/dd HH:mm:ss")
    })
    @Named("iterationEntityToIterationDTO")
    IterationDTO iterationEntityToIterationDTO(IterationEntity iterationEntity); 
	
    @IterableMapping(qualifiedByName = "iterationEntityToIterationDTO")
    List<IterationDTO> iterationEntitiesToIterationDTOs(List<IterationEntity> iterationEntities);
}
