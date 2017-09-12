package sanchez.sanchez.sergio.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.dto.response.AdminDTO;
import sanchez.sanchez.sergio.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.persistence.repository.SonRepository;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class UserSystemEntityMapper {
	
	@Autowired
	protected SonRepository sonRepository;
    
    @Mappings({
        @Mapping(expression="java(userSystemEntity.getId().toString())", target = "identity" ),
        @Mapping(source = "userSystemEntity.birthdate", target = "birthdate", dateFormat = "dd/MM/yyyy"),
        @Mapping(source = "userSystemEntity.age", target = "age")
    })
    @Named("userSystemEntityToAdminDTO")
    public abstract AdminDTO userSystemEntityToAdminDTO(UserSystemEntity userSystemEntity);
       
}
