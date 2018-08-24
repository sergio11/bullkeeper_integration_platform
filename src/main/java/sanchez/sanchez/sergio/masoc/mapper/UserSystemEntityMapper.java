package sanchez.sanchez.sergio.masoc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.masoc.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.dto.response.AdminDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserSystemEntityMapper {
	
	@Autowired
	protected SonRepository sonRepository;
    
    @Mappings({
        @Mapping(expression="java(userSystemEntity.getId().toString())", target = "identity" ),
        @Mapping(source = "userSystemEntity.birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "userSystemEntity.age", target = "age")
    })
    @Named("userSystemEntityToAdminDTO")
    public abstract AdminDTO userSystemEntityToAdminDTO(UserSystemEntity userSystemEntity);
       
}
