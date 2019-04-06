package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DevicePhotoEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddDevicePhotoDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DevicePhotoDTO;

/**
 * Device Photo Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DevicePhotoEntityMapper {
	
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Terminal Repository
	 */
	@Autowired
	protected TerminalRepository terminalRepository;
	
		
    /**
   	 * Device Photo Entity to Device Photo
   	 * @param devicePhotoEntity
   	 * @return
   	 */
     @Mappings({
    	 @Mapping(expression="java(devicePhotoEntity.getId().toString())", 
         		target = "identity" ),
    	 @Mapping(expression="java(devicePhotoEntity.getKid().getId().toString())", target="kid"),
    	 @Mapping(expression="java(devicePhotoEntity.getTerminal().getId().toString())", target="terminal"),
     })
     @Named("devicePhotoEntityToDevicePhotoDTO")
     public abstract DevicePhotoDTO devicePhotoEntityToDevicePhotoDTO(final DevicePhotoEntity devicePhotoEntity); 
       
       
     /**
     * 
     * @param devicePhotoEntityList
     * @return
     */
     @IterableMapping(qualifiedByName = "devicePhotoEntityToDevicePhotoDTO")
     public abstract Iterable<DevicePhotoDTO> devicePhotoEntityToDevicePhotoDTO(
       		Iterable<DevicePhotoEntity> devicePhotoEntityList);
     
     /**
      * 
      * @param addDevicePhotoDTO
      * @return
      */
     @Mappings({
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(addDevicePhotoDTO.getKid())))", target="kid"),
     	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(addDevicePhotoDTO.getTerminal())))", target="terminal")
     })
     public abstract DevicePhotoEntity addDevicePhotoDtoToDevicePhotoEntity(final AddDevicePhotoDTO addDevicePhotoDTO);
     
     /**
      * 
      * @param addDevicePhotoDTO
      * @return
      */
     @IterableMapping(qualifiedByName = "addDevicePhotoDtoToDevicePhotoEntity")
     public abstract Iterable<DevicePhotoEntity> addDevicePhotoDtoToDevicePhotoEntity(final Iterable<AddDevicePhotoDTO> addDevicePhotoDTOs);
   

}
