package sanchez.sanchez.sergio.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.persistence.entity.DeviceEntity;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DeviceEntityMapper {

    @Mappings({
    	@Mapping(source = "deviceEntity.createAt", target = "createAt", dateFormat = "dd/MM/yyyy"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKeyName", target = "notificationKeyName"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKey", target = "notificationKey")
    })
    @Named("deviceEntityToDeviceDTO")
    public abstract DeviceDTO deviceEntityToDeviceDTO(DeviceEntity deviceEntity); 
	
    @IterableMapping(qualifiedByName = "deviceEntityToDeviceDTO")
    public abstract Iterable<DeviceDTO> deviceEntitiesToDeviceDTO(Iterable<DeviceEntity> deviceEntities);
}
