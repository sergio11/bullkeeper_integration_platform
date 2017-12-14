package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.DeviceEntity;
import es.bisite.usal.bulltect.persistence.entity.DeviceGroupEntity;
import es.bisite.usal.bulltect.web.dto.response.DeviceDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DeviceEntityMapper {
	
	protected String parseOwnerId(DeviceEntity deviceEntity){
		String ownerId = null;
		if(deviceEntity.getDeviceGroup() != null) {
			final DeviceGroupEntity deviceGroup = deviceEntity.getDeviceGroup();
			if(deviceGroup.getOwner() != null)
				ownerId = deviceGroup.getOwner().getId().toString();
		}
		return ownerId;
	}
	

    @Mappings({
    	@Mapping(source = "deviceEntity.createAt", target = "createAt", dateFormat = "yyyy/MM/dd"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKeyName", target = "notificationKeyName"),
    	@Mapping(source = "deviceEntity.deviceGroup.notificationKey", target = "notificationKey"),
    	@Mapping(expression="java(parseOwnerId(deviceEntity))", target = "owner")
    })
    @Named("deviceEntityToDeviceDTO")
    public abstract DeviceDTO deviceEntityToDeviceDTO(DeviceEntity deviceEntity); 
	
    @IterableMapping(qualifiedByName = "deviceEntityToDeviceDTO")
    public abstract Iterable<DeviceDTO> deviceEntitiesToDeviceDTO(Iterable<DeviceEntity> deviceEntities);
}
