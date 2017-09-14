package es.bisite.usal.bullytect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bullytect.dto.request.AddSchoolDTO;
import es.bisite.usal.bullytect.dto.response.SchoolDTO;
import es.bisite.usal.bullytect.persistence.entity.SchoolEntity;

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
}
