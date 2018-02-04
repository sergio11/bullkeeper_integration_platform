package es.bisite.usal.bulltect.domain.service;


import java.util.Set;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.persistence.entity.DeviceEntity;
import es.bisite.usal.bulltect.web.dto.response.DeviceDTO;
import es.bisite.usal.bulltect.web.dto.response.DeviceGroupDTO;

public interface IDeviceGroupsService {
	DeviceGroupDTO getDeviceGroupByName(String name);
	DeviceGroupDTO createDeviceGroup(String key, String name, ObjectId owner);
	DeviceGroupDTO createDeviceGroup(String key, String name, ObjectId owner, Set<DeviceEntity> devices);
    DeviceDTO addDeviceToGroup(String deviceId, String token, String deviceGroupId);
    DeviceDTO removeDevice(String deviceId);
    Iterable<DeviceDTO> getDevicesFromGroup(String groupName);
    String getNotificationKey(String groupName);
    DeviceDTO getDeviceByDeviceId(String deviceId);
    void updateDeviceToken(String deviceId, String newToken);
    DeviceGroupDTO getDeviceGroupByOwner(ObjectId owner);
    void removeDeviceGroupOf(ObjectId owner);
}
