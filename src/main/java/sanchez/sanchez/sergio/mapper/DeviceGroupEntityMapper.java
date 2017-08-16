package sanchez.sanchez.sergio.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.dto.response.DeviceGroupDTO;
import sanchez.sanchez.sergio.persistence.entity.DeviceGroupEntity;

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
