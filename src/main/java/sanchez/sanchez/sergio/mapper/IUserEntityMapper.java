package sanchez.sanchez.sergio.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.dto.UserDTO;
import sanchez.sanchez.sergio.persistence.entity.UserEntity;

/**
 * @author sergio
 */
@Mapper
public interface IUserEntityMapper {
    
    @Named("userEntityToUserDTO")
    UserDTO userEntityToUserDTO(UserEntity userEntity); 
	
    @IterableMapping(qualifiedByName = "userEntityToUserDTO")
    List<UserDTO> userEntitiesToUserDTOs(List<UserEntity> userEntities);
}
