package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AdminDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserSystemEntityMapper {
	
	@Autowired
	protected KidRepository sonRepository;
    
    @Mappings({
        @Mapping(expression="java(userSystemEntity.getId().toString())", target = "identity" ),
        @Mapping(source = "userSystemEntity.birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "userSystemEntity.age", target = "age")
    })
    @Named("userSystemEntityToAdminDTO")
    public abstract AdminDTO userSystemEntityToAdminDTO(UserSystemEntity userSystemEntity);
       
}
