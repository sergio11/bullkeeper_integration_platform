package es.bisite.usal.bulltect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bulltect.persistence.entity.DeviceGroupEntity;
import es.bisite.usal.bulltect.web.dto.response.DeviceGroupDTO;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DeviceGroupEntityMapper {
	
    @Mappings({
    	@Mapping(expression="java(deviceGroupEntity.getId().toString())", target = "identity" ),
    	@Mapping(source = "deviceGroupEntity.createAt", target = "createAt", dateFormat = "yyyy/MM/dd"),
    	@Mapping(expression="java(deviceGroupEntity.getOwner().getId().toString())", target = "owner")
    })
    @Named("deviceGroupEntityToDeviceGroupDTO")
    public abstract DeviceGroupDTO deviceGroupEntityToDeviceGroupDTO(DeviceGroupEntity deviceGroupEntity); 
	
    @IterableMapping(qualifiedByName = "deviceGroupEntityToDeviceGroupDTO")
    public abstract Iterable<DeviceGroupDTO> deviceGroupEntitiesToDeviceGroupDTO(Iterable<DeviceGroupEntity> deviceGroupEntities);
}
