package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidRequestEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddKidRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceDTO;

/**
 * Geofence Entity Mapper
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class GeofenceEntityMapper {
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;

	/**
	 * Geofence Entity To Geofence DTO
	 * @param geofenceEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(geofenceEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(geofenceEntity.getType().name())", target = "type" ),
        @Mapping(expression="java(geofenceEntity.getKid().getId().toString())", target = "kid" )
    })
    @Named("geofenceEntityToGeofenceDTO")
    public abstract GeofenceDTO geofenceEntityToGeofenceDTO(final GeofenceEntity geofenceEntity); 
	
    /**
     * Geofence Entity To Geofence DTOs
     * @param geofenceEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "geofenceEntityToGeofenceDTO")
    public abstract Iterable<GeofenceDTO> geofenceEntityToGeofenceDTOs(Iterable<GeofenceEntity> geofenceEntities);
   
    
    /**
     * 
     * @param saveGeofenceDTO
     * @return
     */
   @Mappings({ 
	   @Mapping(expression="java((saveGeofenceDTO.getIdentity() != null && !saveGeofenceDTO.getIdentity().isEmpty()) ? new org.bson.types.ObjectId(saveGeofenceDTO.getIdentity()) : null )", 
   			target="id"),
       @Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveGeofenceDTO.getKid())))", target = "kid" ),
	   @Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceTransitionTypeEnum.valueOf(saveGeofenceDTO.getType()))", 
	   	target = "type" )
	})
    public abstract GeofenceEntity saveGeofenceDTOToGeofenceEntity(final SaveGeofenceDTO saveGeofenceDTO);
   
    
}
