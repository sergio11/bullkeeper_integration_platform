package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.PasswordResetTokenEntity;
import es.bisite.usal.bulltect.web.dto.response.PasswordResetTokenDTO;

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
