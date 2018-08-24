package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.DeviceEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.DeviceGroupEntity;

@Repository
public interface DeviceRepository extends MongoRepository<DeviceEntity, ObjectId>, DeviceRepositoryCustom {

    Iterable<DeviceEntity> findByDeviceGroup(DeviceGroupEntity deviceGroup);

    DeviceEntity findByRegistrationToken(String registrationToken);

    Long deleteByRegistrationToken(String registrationToken);
    
    Long deleteByDeviceId(String deviceId);
    
    Long countByRegistrationToken(String registrationToken);
    
    Long countByDeviceId(String deviceId);
    
    DeviceEntity findByDeviceId(String deviceId);
    
    Long countByDeviceGroupId(ObjectId deviceGroupId);
    
    Long deleteByDeviceGroup(DeviceGroupEntity deviceGroup);
}
