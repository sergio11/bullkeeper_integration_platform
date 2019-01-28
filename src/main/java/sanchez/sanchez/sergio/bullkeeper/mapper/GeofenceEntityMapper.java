package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceAlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceAlertDTO;
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
        @Mapping(expression="java(geofenceEntity.getKid().getId().toString())", target = "kid" ),
        @Mapping(source = "geofenceEntity.createAt", 
			target = "createAt", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS"),
        @Mapping(source = "geofenceEntity.updateAt", 
			target = "updateAt", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS")
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
   
   /**
	 * Geofence Alert To Geofence Alert DTO
	 * @param geofenceAlertEntity
	 * @return
	 */
   @Mappings({
       @Mapping(expression="java(geofenceAlertEntity.getType().name())", target = "type" ),
       @Mapping(source = "geofenceAlertEntity.date", 
			target = "date", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS")
   })
   @Named("geofenceAlertEntityToGeofenceAlertDTO")
   public abstract GeofenceAlertDTO geofenceAlertEntityToGeofenceAlertDTO(final GeofenceAlertEntity geofenceAlertEntity); 
	
   /**
    * Geofence Alert Entity To Geofence Alert DTO
    * @param geofenceEntities
    * @return
    */
   @IterableMapping(qualifiedByName = "geofenceAlertEntityToGeofenceAlertDTO")
   public abstract Iterable<GeofenceAlertDTO> geofenceAlertEntityToGeofenceAlertDTOs(
		   Iterable<GeofenceAlertEntity> geofenceEntities);
  
  
}
