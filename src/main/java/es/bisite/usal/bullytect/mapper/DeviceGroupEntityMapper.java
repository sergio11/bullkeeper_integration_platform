package es.bisite.usal.bullytect.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import es.bisite.usal.bullytect.dto.response.DeviceGroupDTO;
import es.bisite.usal.bullytect.persistence.entity.DeviceGroupEntity;

/**
 * @author sergio
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class DeviceGroupEntityMapper {
    @Mappings({
    	@Mapping(expression="java(deviceGroupEntity.getId().toString())", target = "identity" ),
    	@Mapping(source = "deviceGroupEntity.createAt", target = "createAt", dateFormat = "dd/MM/yyyy")
    })
    @Named("deviceGroupEntityToDeviceGroupDTO")
    public abstract DeviceGroupDTO deviceGroupEntityToDeviceGroupDTO(DeviceGroupEntity deviceGroupEntity); 
	
    @IterableMapping(qualifiedByName = "deviceGroupEntityToDeviceGroupDTO")
    public abstract Iterable<DeviceGroupDTO> deviceGroupEntitiesToDeviceGroupDTO(Iterable<DeviceGroupEntity> deviceGroupEntities);
}
