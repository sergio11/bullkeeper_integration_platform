package es.bisite.usal.bulltect.domain.service.impl;

import java.util.Set;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import es.bisite.usal.bulltect.domain.service.IDeviceGroupsService;
import es.bisite.usal.bulltect.domain.service.ITokenGeneratorService;
import es.bisite.usal.bulltect.fcm.properties.FCMCustomProperties;
import es.bisite.usal.bulltect.mapper.DeviceEntityMapper;
import es.bisite.usal.bulltect.mapper.DeviceGroupEntityMapper;
import es.bisite.usal.bulltect.persistence.entity.DeviceEntity;
import es.bisite.usal.bulltect.persistence.entity.DeviceGroupEntity;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.repository.DeviceGroupRepository;
import es.bisite.usal.bulltect.persistence.repository.DeviceRepository;
import es.bisite.usal.bulltect.persistence.repository.ParentRepository;
import es.bisite.usal.bulltect.persistence.repository.PendingDeviceRepository;
import es.bisite.usal.bulltect.web.dto.response.DeviceDTO;
import es.bisite.usal.bulltect.web.dto.response.DeviceGroupDTO;
import io.jsonwebtoken.lang.Assert;

@Service
public class DeviceGroupsServiceImpl implements IDeviceGroupsService {
	
	private final DeviceGroupRepository deviceGroupRepository;
	private final DeviceRepository deviceRepository;
	private final DeviceEntityMapper deviceEntityMapper;
	private final DeviceGroupEntityMapper deviceGroupEntityMapper;
	private final ParentRepository parentRepository;
    private final PendingDeviceRepository pendingDeviceRepository;
	

	public DeviceGroupsServiceImpl(DeviceGroupRepository deviceGroupRepository, 
			DeviceRepository deviceRepository, DeviceEntityMapper deviceEntityMapper, 
			DeviceGroupEntityMapper deviceGroupEntityMapper, ParentRepository parentRepository,
			PendingDeviceRepository pendingDeviceRepository) {
		super();
		this.deviceGroupRepository = deviceGroupRepository;
		this.deviceRepository = deviceRepository;
		this.deviceEntityMapper = deviceEntityMapper;
		this.deviceGroupEntityMapper = deviceGroupEntityMapper;
		this.parentRepository = parentRepository;
		this.pendingDeviceRepository = pendingDeviceRepository;
	}

	@Override
	public DeviceGroupDTO getDeviceGroupByName(String name) {
		DeviceGroupEntity deviceGroup = deviceGroupRepository.findByNotificationKeyName(name);
		return deviceGroupEntityMapper.deviceGroupEntityToDeviceGroupDTO(deviceGroup);
	}
	
	@Override
	public DeviceGroupDTO getDeviceGroupByOwner(ObjectId owner) {
		Assert.notNull(owner, "Owner can not be null");
		DeviceGroupEntity deviceGroup = deviceGroupRepository.findByOwnerId(owner);
		return deviceGroupEntityMapper.deviceGroupEntityToDeviceGroupDTO(deviceGroup);
	}

	@Override
	public DeviceGroupDTO createDeviceGroup(String key, String name, ObjectId owner) {
		ParentEntity parentEntity = parentRepository.findOne(owner);
		DeviceGroupEntity deviceGroupSaved = deviceGroupRepository.save(new DeviceGroupEntity(name, key, parentEntity));
		return deviceGroupEntityMapper.deviceGroupEntityToDeviceGroupDTO(deviceGroupSaved);
	}

	@Override
	public DeviceGroupDTO createDeviceGroup(String key, String name, ObjectId owner, Set<DeviceEntity> devices) {
		ParentEntity parentEntity = parentRepository.findOne(owner);
		DeviceGroupEntity deviceGroupSaved = deviceGroupRepository.save(new DeviceGroupEntity(name, key, parentEntity));
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
         final DeviceEntity deviceDeleted = deviceRepository.findByDeviceId(deviceId);
         deviceRepository.delete(deviceDeleted);
         final ObjectId deviceGroupId = deviceDeleted.getDeviceGroup().getId(); 
         final Long totalDevicesIntoGroup = deviceRepository.countByDeviceGroupId(deviceGroupId);
         if(totalDevicesIntoGroup == 0)
        	 deviceGroupRepository.delete(deviceGroupId);
        return deviceEntityMapper.deviceEntityToDeviceDTO(deviceDeleted);
         
         
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
	
	@Override
	public void removeDeviceGroupOf(ObjectId owner) {
		final DeviceGroupEntity deviceGroup = deviceGroupRepository.findByOwnerId(owner);
		if(deviceGroup != null) {
			deviceRepository.deleteByDeviceGroup(deviceGroup);
			deviceGroupRepository.delete(deviceGroup);
			pendingDeviceRepository.deleteByOwner(owner);
		}
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(deviceGroupRepository, "Device Group Repository can not be null");
		Assert.notNull(deviceRepository, "Device Repository can not be null");
		Assert.notNull(deviceEntityMapper, "Device Entity Mapper can not be null");
		Assert.notNull(deviceGroupEntityMapper, "Device Group Entity Mapper can not be null");
		Assert.notNull(parentRepository, "Parent Repository can not be null");
		Assert.notNull(pendingDeviceRepository, "Parent Repository can not be null");
		
	}
}
