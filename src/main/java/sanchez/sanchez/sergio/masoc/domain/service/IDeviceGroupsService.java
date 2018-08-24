package sanchez.sanchez.sergio.masoc.domain.service;

import java.util.Set;
import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.masoc.persistence.entity.DeviceEntity;
import sanchez.sanchez.sergio.masoc.web.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.DeviceGroupDTO;

/**
 * Device Groups Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IDeviceGroupsService {
	
	/**
	 * Get Device Group By Name
	 * @param name
	 * @return
	 */
	DeviceGroupDTO getDeviceGroupByName(final String name);
	
	/**
	 * Create Device Goup
	 * @param key
	 * @param name
	 * @param owner
	 * @return
	 */
	DeviceGroupDTO createDeviceGroup(final String key, final String name, final ObjectId owner);
	
	/**
	 * Create Device Group
	 * @param key
	 * @param name
	 * @param owner
	 * @param devices
	 * @return
	 */
	DeviceGroupDTO createDeviceGroup(final String key, final String name, final ObjectId owner, 
			final Set<DeviceEntity> devices);
	
	/**
	 * Add Device To Group
	 * @param deviceId
	 * @param token
	 * @param deviceGroupId
	 * @return
	 */
    DeviceDTO addDeviceToGroup(final String deviceId, final String token, 
    		final String deviceGroupId);
    
    /**
     * Remove Device
     * @param deviceId
     * @return
     */
    DeviceDTO removeDevice(final String deviceId);
    
    /**
     * Get Devices From Group
     * @param groupName
     * @return
     */
    Iterable<DeviceDTO> getDevicesFromGroup(final String groupName);
    
    /**
     * Get Notification Key
     * @param groupName
     * @return
     */
    String getNotificationKey(final String groupName);
    
    /**
     * Get Device By Device Id
     * @param deviceId
     * @return
     */
    DeviceDTO getDeviceByDeviceId(String deviceId);
    
    /**
     * Update Device Token
     * @param deviceId
     * @param newToken
     */
    void updateDeviceToken(final String deviceId, final String newToken);
    
    /**
     * Get Device Group By Owner
     * @param owner
     * @return
     */
    DeviceGroupDTO getDeviceGroupByOwner(ObjectId owner);
    
    /**
     * Remove Device Group Of
     * @param owner
     */
    void removeDeviceGroupOf(ObjectId owner);
}
