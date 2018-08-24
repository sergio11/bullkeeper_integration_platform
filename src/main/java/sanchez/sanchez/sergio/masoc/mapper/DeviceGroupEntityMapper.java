package sanchez.sanchez.sergio.masoc.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import sanchez.sanchez.sergio.masoc.persistence.entity.DeviceGroupEntity;
import sanchez.sanchez.sergio.masoc.web.dto.response.DeviceGroupDTO;

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
