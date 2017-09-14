package es.bisite.usal.bullytect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bullytect.dto.response.PasswordResetTokenDTO;
import es.bisite.usal.bullytect.persistence.entity.PasswordResetTokenEntity;

/**
 * @author sergio
 */
@Mapper
public interface IPasswordResetTokenEntityMapper {
    
    @Mappings({
        @Mapping(expression="java(passwordResetTokenEntity.getUser().toString())", target = "user" ),
        @Mapping(source = "passwordResetTokenEntity.expiryDate", target = "expiryDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    })
    @Named("passwordResetTokenEntityToPasswordResetTokenDTO")
    PasswordResetTokenDTO passwordResetTokenEntityToPasswordResetTokenDTO(PasswordResetTokenEntity passwordResetTokenEntity); 
	
    @IterableMapping(qualifiedByName = "passwordResetTokenEntityToPasswordResetTokenDTO")
    Iterable<PasswordResetTokenDTO> passwordResetTokenEntityToPasswordResetTokenDTOs(Iterable<PasswordResetTokenEntity> passwordResetTokenEntities);
   
}
