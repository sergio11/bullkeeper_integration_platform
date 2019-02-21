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
        @Mapping(source = "schoolEntity.location.longitude", target = "longitude"),
        @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.util.Utils.getPhonePrefix(schoolEntity.getTfno()))", 
	    	target = "phonePrefix" ),
	    @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.util.Utils.getPhoneNumber(schoolEntity.getTfno()))", 
	    	target = "phoneNumber" )
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
    
    /**
     * 
     * @param addSchoolEntity
     * @return
     */
    @Mappings({
        @Mapping(source = "addSchoolDTO.latitude", target = "location.latitude"),
        @Mapping(source = "addSchoolDTO.longitude", target = "location.longitude"),
        @Mapping(expression="java(com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance().format(addSchoolDTO.getTfno(),"
        		+ " com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164))", target = "tfno" )
    })
    @Named("addSchoolDTOToSchoolEntity")
    SchoolEntity addSchoolDTOToSchoolEntity(AddSchoolDTO addSchoolDTO);
    
    /**
     * 
     * @param addSchoolEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "addSchoolDTOToSchoolEntity")
    Iterable<SchoolEntity> addSchoolDTOsToSchoolEntities(Iterable<AddSchoolDTO> addSchoolEntities);
    
    
    /**
     * 
     * @param schoolEntity
     * @return
     */
    @Mappings({
        @Mapping(expression="java(schoolEntity.getId().toString())", target = "identity" )
    })
    @Named("schoolEntityToSchoolNameDTO")
    SchoolNameDTO schoolEntityToSchoolNameDTO(SchoolEntity schoolEntity); 
   
}
