package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.SchoolEntity;
import es.bisite.usal.bulltect.web.dto.request.AddSchoolDTO;
import es.bisite.usal.bulltect.web.dto.response.SchoolDTO;
import es.bisite.usal.bulltect.web.dto.response.SchoolNameDTO;

/**
 * @author sergio
 */
@Mapper
public interface ISchoolEntityMapper {
    
    @Mappings({
        @Mapping(expression="java(schoolEntity.getId().toString())", target = "identity" )
    })
    @Named("schoolEntityToSchoolDTO")
    SchoolDTO schoolEntityToSchoolDTO(SchoolEntity schoolEntity); 
	
    @IterableMapping(qualifiedByName = "schoolEntityToSchoolDTO")
    Iterable<SchoolDTO> schoolEntitiesToSchoolDTOs(Iterable<SchoolEntity> schoolEntities);
    
    @Named("addSchoolDTOToSchoolEntity")
    SchoolEntity addSchoolDTOToSchoolEntity(AddSchoolDTO addSchoolEntity);
    
    @IterableMapping(qualifiedByName = "addSchoolDTOToSchoolEntity")
    Iterable<SchoolEntity> addSchoolDTOsToSchoolEntities(Iterable<AddSchoolDTO> addSchoolEntities);
    
    @Mappings({
        @Mapping(expression="java(schoolEntity.getId().toString())", target = "identity" )
    })
    @Named("schoolEntityToSchoolNameDTO")
    SchoolNameDTO schoolEntityToSchoolNameDTO(SchoolEntity schoolEntity); 
    
    
}
