package sanchez.sanchez.sergio.service.impl;

import java.util.Set;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.dto.request.AddDeviceDTO;
import sanchez.sanchez.sergio.dto.response.DeviceDTO;
import sanchez.sanchez.sergio.dto.response.DeviceGroupDTO;
import sanchez.sanchez.sergio.fcm.properties.FCMCustomProperties;
import sanchez.sanchez.sergio.mapper.DeviceEntityMapper;
import sanchez.sanchez.sergio.mapper.DeviceGroupEntityMapper;
import sanchez.sanchez.sergio.persistence.entity.DeviceEntity;
import sanchez.sanchez.sergio.persistence.entity.DeviceGroupEntity;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.repository.DeviceGroupRepository;
import sanchez.sanchez.sergio.persistence.repository.DeviceRepository;
import sanchez.sanchez.sergio.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.service.IDeviceGroupsService;

@Service
public class DeviceGroupsServiceImpl implements IDeviceGroupsService {
	
	private final DeviceGroupRepository deviceGroupRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceEntityMapper deviceEntityMapper;
	private final DeviceGroupEntityMapper deviceGroupEntityMapper;
	private final ParentRepository parentRepository;
	private final FCMCustomProperties fcmCustomProperties;

	public DeviceGroupsServiceImpl(DeviceGroupRepository deviceGroupRepository, 
			DeviceRepository deviceRepository, DeviceEntityMapper deviceEntityMapper, 
			DeviceGroupEntityMapper deviceGroupEntityMapper, ParentRepository parentRepository,
			FCMCustomProperties fcmCustomProperties) {
		super();
		this.deviceGroupRepository = deviceGroupRepository;
		this.deviceRepository = deviceRepository;
		this.deviceEntityMapper = deviceEntityMapper;
		this.deviceGroupEntityMapper = deviceGroupEntityMapper;
		this.parentRepository = parentRepository;
		this.fcmCustomProperties = fcmCustomProperties;
	}

	@Override
	public DeviceGroupDTO getDeviceGroupByName(String name) {
		String notificationKeyName = String.format("%s_%d", fcmCustomProperties.getGroupPrefix(), name);
		DeviceGroupEntity deviceGroup = deviceGroupRepository.findByNotificationKeyName(notificationKeyName);
		return deviceGroupEntityMapper.deviceGroupEntityToDeviceGroupDTO(deviceGroup);
	}

	@Override
	public DeviceGroupDTO createDeviceGroup(String key, ObjectId owner) {
		ParentEntity parentEntity = parentRepository.findOne(owner);
		DeviceGroupEntity deviceGroupSaved = deviceGroupRepository.save(new DeviceGroupEntity(owner.toString(), key, parentEntity));
		return deviceGroupEntityMapper.deviceGroupEntityToDeviceGroupDTO(deviceGroupSaved);
	}

	@Override
	public DeviceGroupDTO createDeviceGroup(String key, ObjectId owner, Set<DeviceEntity> devices) {
		ParentEntity parentEntity = parentRepository.findOne(owner);
		DeviceGroupEntity deviceGroupSaved = deviceGroupRepository.save(new DeviceGroupEntity(owner.toString(), key, parentEntity));
		for(DeviceEntity device: devices){
			device.setDeviceGroup(deviceGroupSaved);
		}
		deviceRepository.save(devices);
		return deviceGroupEntityMapper.deviceGroupEntityToDeviceGroupDTO(deviceGroupSaved);
	}

	@Override
	public DeviceDTO addDeviceToGroup(String deviceId, String token, String deviceGroupId) {
		DeviceGroupEntity deviceGroup = deviceGroupRepository.findOne(new ObjectId(deviceGroupId));
		DeviceEntity deviceSaved = deviceRepository.save(new DeviceEntity(deviceId, token, deviceGroup));
		return deviceEntityMapper.deviceEntityToDeviceDTO(deviceSaved);
	}

	@Override
	public DeviceDTO removeDevice(String deviceId) {
		DeviceEntity deviceRemoved = deviceRepository.deleteByDeviceId(deviceId);
		return deviceEntityMapper.deviceEntityToDeviceDTO(deviceRemoved);
	}

	@Override
	public Iterable<DeviceDTO> getDevicesFromGroup(String groupName) {
		DeviceGroupEntity deviceGroup = deviceGroupRepository.findByNotificationKeyName(groupName);
		return deviceEntityMapper.deviceEntitiesToDeviceDTO(deviceRepository.findByDeviceGroup(deviceGroup));
	}

	@Override
	public String getNotificationKey(String groupName) {
		return deviceGroupRepository.getNotificationKey(groupName);
	}
	
	@Override
	public DeviceDTO getDeviceByDeviceId(String deviceId) {
		final DeviceEntity deviceEntity = deviceRepository.findByDeviceId(deviceId);
		return deviceEntityMapper.deviceEntityToDeviceDTO(deviceEntity);
	}
	
	@Override
	public void updateDeviceToken(String deviceId, String newToken) {
		deviceRepository.updateDeviceToken(deviceId, newToken);
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(deviceGroupRepository, "Device Group Repository can not be null");
		Assert.notNull(deviceRepository, "Device Repository can not be null");
		Assert.notNull(deviceEntityMapper, "Device Entity Mapper can not be null");
		Assert.notNull(deviceGroupEntityMapper, "Device Group Entity Mapper can not be null");
		Assert.notNull(parentRepository, "Parent Repository can not be null");
	}
}
