package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.DeviceEntity;
import es.bisite.usal.bulltect.web.dto.response.DeviceDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DeviceEntityMapper {

    @Mappings({
    	@Mapping(source = "deviceEntity.createAt", target = "createAt", dateFormat = "yyyy/MM/dd"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKeyName", target = "notificationKeyName"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKey", target = "notificationKey")
    })
    @Named("deviceEntityToDeviceDTO")
    public abstract DeviceDTO deviceEntityToDeviceDTO(DeviceEntity deviceEntity); 
	
    @IterableMapping(qualifiedByName = "deviceEntityToDeviceDTO")
    public abstract Iterable<DeviceDTO> deviceEntitiesToDeviceDTO(Iterable<DeviceEntity> deviceEntities);
}
