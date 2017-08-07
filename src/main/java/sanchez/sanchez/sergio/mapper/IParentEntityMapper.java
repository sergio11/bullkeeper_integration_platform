package sanchez.sanchez.sergio.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;

/**
 * @author sergio
 */
@Mapper
public interface IParentEntityMapper {
    
    @Mappings({
        @Mapping(expression="java(parentEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(parentEntity.getChildren().size())", target = "children" )
    })
    @Named("parentEntityToParentDTO")
    ParentDTO parentEntityToParentDTO(ParentEntity parentEntity); 
	
    @IterableMapping(qualifiedByName = "parentEntityToParentDTO")
    List<ParentDTO> parentEntitiesToParentDTOs(List<ParentEntity> parentEntities);
}
