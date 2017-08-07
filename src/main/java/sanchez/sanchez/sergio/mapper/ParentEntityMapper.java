package sanchez.sanchez.sergio.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ParentEntityMapper {
	
	@Autowired
    protected PasswordEncoder passwordEncoder;
    
    @Mappings({
        @Mapping(expression="java(parentEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(parentEntity.getChildren().size())", target = "children" )
    })
    @Named("parentEntityToParentDTO")
    public abstract ParentDTO parentEntityToParentDTO(ParentEntity parentEntity); 
	
    @IterableMapping(qualifiedByName = "parentEntityToParentDTO")
    public abstract List<ParentDTO> parentEntitiesToParentDTOs(List<ParentEntity> parentEntities);
    
    @Mappings({ 
		@Mapping(expression="java(passwordEncoder.encode(registerParentDTO.getPassword()))", target = "password")
	})
    public abstract ParentEntity registerParentDTOToParentEntity(RegisterParentDTO registerParentDTO);
}
