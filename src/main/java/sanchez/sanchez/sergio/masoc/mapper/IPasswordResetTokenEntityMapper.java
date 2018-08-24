package sanchez.sanchez.sergio.masoc.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.masoc.persistence.entity.PasswordResetTokenEntity;
import sanchez.sanchez.sergio.masoc.web.dto.response.PasswordResetTokenDTO;

/**
 * @author sergio
 */
@Mapper
public interface IPasswordResetTokenEntityMapper {
    
    @Mappings({
        @Mapping(expression="java(passwordResetTokenEntity.getUser().toString())", target = "user" ),
        @Mapping(source = "passwordResetTokenEntity.expiryDate", target = "expiryDate", dateFormat = "yyyy/MM/dd HH:mm:ss")
    })
    @Named("passwordResetTokenEntityToPasswordResetTokenDTO")
    PasswordResetTokenDTO passwordResetTokenEntityToPasswordResetTokenDTO(PasswordResetTokenEntity passwordResetTokenEntity); 
	
    @IterableMapping(qualifiedByName = "passwordResetTokenEntityToPasswordResetTokenDTO")
    Iterable<PasswordResetTokenDTO> passwordResetTokenEntityToPasswordResetTokenDTOs(Iterable<PasswordResetTokenEntity> passwordResetTokenEntities);
   
}
