package sanchez.sanchez.sergio.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.dto.IterationDTO;
import sanchez.sanchez.sergio.persistence.entity.IterationEntity;

/**
 * @author sergio
 */
@Mapper
public interface IIterationEntityMapper {
    
    @Mappings({
        @Mapping(source = "iterationEntity.startDate", target = "startDate", dateFormat = "dd/MM/yyyy"),
        @Mapping(source = "iterationEntity.finishDate", target = "finishDate", dateFormat = "dd/MM/yyyy")
    })
    @Named("iterationEntityToIterationDTO")
    IterationDTO iterationEntityToIterationDTO(IterationEntity iterationEntity); 
	
    @IterableMapping(qualifiedByName = "iterationEntityToIterationDTO")
    List<IterationDTO> iterationEntitiesToIterationDTOs(List<IterationEntity> iterationEntities);
}
