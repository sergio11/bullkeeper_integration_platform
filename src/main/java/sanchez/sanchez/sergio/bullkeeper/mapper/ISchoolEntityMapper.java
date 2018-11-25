package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SchoolEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddSchoolDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SchoolDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SchoolNameDTO;

/**
 * @author sergio
 */
@Mapper
public interface ISchoolEntityMapper {
    
	/**
	 * 
	 * @param schoolEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(schoolEntity.getId().toString())", target = "identity" ),
        @Mapping(source = "schoolEntity.location.latitude", target = "latitude"),
        @Mapping(source = "schoolEntity.location.longitude", target = "longitude")
    })
    @Named("schoolEntityToSchoolDTO")
    SchoolDTO schoolEntityToSchoolDTO(SchoolEntity schoolEntity); 
	
    /**
     * 
     * @param schoolEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "schoolEntityToSchoolDTO")
    Iterable<SchoolDTO> schoolEntitiesToSchoolDTOs(Iterable<SchoolEntity> schoolEntities);
    
    @Mappings({
        @Mapping(source = "addSchoolEntity.latitude", target = "location.latitude"),
        @Mapping(source = "addSchoolEntity.longitude", target = "location.longitude")
    })
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
