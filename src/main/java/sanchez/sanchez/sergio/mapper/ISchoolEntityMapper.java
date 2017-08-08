package sanchez.sanchez.sergio.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.dto.request.AddSchoolDTO;
import sanchez.sanchez.sergio.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.persistence.entity.SchoolEntity;

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
