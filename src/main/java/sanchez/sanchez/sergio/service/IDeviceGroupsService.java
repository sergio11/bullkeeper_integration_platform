package sanchez.sanchez.sergio.service;


import java.util.Set;

import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.dto.response.DeviceGroupDTO;
import sanchez.sanchez.sergio.persistence.entity.DeviceEntity;

public interface IDeviceGroupsService {
	DeviceGroupDTO getDeviceGroupByName(String name);
	DeviceGroupDTO createDeviceGroup(String key, ObjectId owner);
	DeviceGroupDTO createDeviceGroup(String key, ObjectId owner, Set<DeviceEntity> devices);
    DeviceDTO addDeviceToGroup(String deviceId, String token, String deviceGroupId);
    DeviceDTO removeDevice(String deviceId);
    Iterable<DeviceDTO> getDevicesFromGroup(String groupName);
    String getNotificationKey(String groupName);
    DeviceDTO getDeviceByDeviceId(String deviceId);
    void updateDeviceToken(String deviceId, String newToken);
}
