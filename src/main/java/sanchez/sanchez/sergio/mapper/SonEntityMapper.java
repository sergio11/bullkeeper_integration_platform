package sanchez.sanchez.sergio.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.persistence.repository.SchoolRepository;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SonEntityMapper {
	
	@Autowired
	protected SchoolRepository schoolRepository;
    
    @Mappings({
        @Mapping(expression="java(sonEntity.getId().toString())", target = "identity" ),
        @Mapping(source="sonEntity.school.name", target = "school" ),
        @Mapping(source = "sonEntity.birthdate", target = "birthdate", dateFormat = "dd/MM/yyyy"),
        @Mapping(source = "sonEntity.age", target = "age")
    })
    @Named("sonEntityToSonDTO")
    public abstract SonDTO sonEntityToSonDTO(SonEntity sonEntity); 
	
    @IterableMapping(qualifiedByName = "sonEntityToSonDTO")
    public abstract Iterable<SonDTO> sonEntitiesToSonDTOs(Iterable<SonEntity> sonEntities);
    
    @Mappings({
        @Mapping(expression="java(schoolRepository.findOne(new org.bson.types.ObjectId(registerSonDTO.getSchool())))", target = "school" )
    })
    public abstract SonEntity registerSonDTOToSonEntity(RegisterSonDTO registerSonDTO);
}
