package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.DeviceEntity;
import es.bisite.usal.bulltect.persistence.entity.DeviceGroupEntity;

@Repository
public interface DeviceRepository extends MongoRepository<DeviceEntity, ObjectId>, DeviceRepositoryCustom {

    Iterable<DeviceEntity> findByDeviceGroup(DeviceGroupEntity deviceGroup);

    DeviceEntity findByRegistrationToken(String registrationToken);

    Long deleteByRegistrationToken(String registrationToken);
    
    Long deleteByDeviceId(String deviceId);
    
    Long countByRegistrationToken(String registrationToken);
    
    Long countByDeviceId(String deviceId);
    
    DeviceEntity findByDeviceId(String deviceId);
}
