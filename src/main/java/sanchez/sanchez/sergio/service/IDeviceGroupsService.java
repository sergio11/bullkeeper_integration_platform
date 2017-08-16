package sanchez.sanchez.sergio.service;


import java.util.Set;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.dto.response.DeviceGroupDTO;
import sanchez.sanchez.sergio.persistence.entity.DeviceEntity;

public interface IDeviceGroupsService {
	DeviceGroupDTO getDeviceGroupByName(String name);
	DeviceGroupDTO createDeviceGroup(String name, String key, ObjectId owner);
	DeviceGroupDTO createDeviceGroup(String name, String key, ObjectId owner, Set<DeviceEntity> devices);
    DeviceDTO addDeviceToGroup(String registrationToken, String deviceGroupId);
    DeviceDTO removeDevice(String registrationToken);
    Iterable<DeviceDTO> getDevicesFromGroup(String groupName);
    String getNotificationKey(String groupName);
}
