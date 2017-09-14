package es.bisite.usal.bullytect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bullytect.persistence.entity.DeviceEntity;
import es.bisite.usal.bullytect.persistence.entity.DeviceGroupEntity;

@Repository
public interface DeviceRepository extends MongoRepository<DeviceEntity, ObjectId>, DeviceRepositoryCustom {

    Iterable<DeviceEntity> findByDeviceGroup(DeviceGroupEntity deviceGroup);

    DeviceEntity findByRegistrationToken(String registrationToken);

    DeviceEntity deleteByRegistrationToken(String registrationToken);
    
    DeviceEntity deleteByDeviceId(String deviceId);
    
    Long countByRegistrationToken(String registrationToken);
    
    Long countByDeviceId(String deviceId);
    
    DeviceEntity findByDeviceId(String deviceId);
}
