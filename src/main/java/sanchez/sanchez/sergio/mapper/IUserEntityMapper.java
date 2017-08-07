package sanchez.sanchez.sergio.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.dto.response.UserDTO;
import sanchez.sanchez.sergio.persistence.entity.UserEntity;

/**
 * @author sergio
 */
@Mapper
public interface IUserEntityMapper {
    
    @Mappings({
        @Mapping(expression="java(userEntity.getId().toString())", target = "identity" )
    })
    @Named("userEntityToUserDTO")
    UserDTO userEntityToUserDTO(UserEntity userEntity); 
	
    @IterableMapping(qualifiedByName = "userEntityToUserDTO")
    Iterable<UserDTO> userEntitiesToUserDTOs(Iterable<UserEntity> userEntities);
}
