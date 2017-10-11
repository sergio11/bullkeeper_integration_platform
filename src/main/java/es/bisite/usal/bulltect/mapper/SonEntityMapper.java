package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.repository.SchoolRepository;
import es.bisite.usal.bulltect.web.dto.request.RegisterSonDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateSonDTO;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SonEntityMapper {
	
	@Autowired
	protected SchoolRepository schoolRepository;
	
	@Autowired
	protected ISchoolEntityMapper schoolEntityMapper;
    
    @Mappings({
        @Mapping(expression="java(sonEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(schoolEntityMapper.schoolEntityToSchoolNameDTO(sonEntity.getSchool()))", target = "school" ),
        @Mapping(source = "sonEntity.birthdate", target = "birthdate", dateFormat = "yyyy/MM/dd"),
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
    
    @Mappings({
        @Mapping(expression="java(schoolRepository.findOne(new org.bson.types.ObjectId(updateSonDTO.getSchool())))", target = "school" )
    })
    public abstract SonEntity updateSonDTOToSonEntity(UpdateSonDTO updateSonDTO);
    
}
