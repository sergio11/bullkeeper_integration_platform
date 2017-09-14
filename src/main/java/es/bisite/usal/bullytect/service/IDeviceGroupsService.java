package es.bisite.usal.bullytect.service;


import java.util.Set;

import org.bson.types.ObjectId;

import es.bisite.usal.bullytect.dto.response.DeviceDTO;
import es.bisite.usal.bullytect.dto.response.DeviceGroupDTO;
import es.bisite.usal.bullytect.persistence.entity.DeviceEntity;

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
