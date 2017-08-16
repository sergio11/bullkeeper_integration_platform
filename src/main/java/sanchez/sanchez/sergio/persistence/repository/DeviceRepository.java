package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.persistence.entity.DeviceEntity;

@Repository
public interface DeviceRepository extends MongoRepository<DeviceEntity, ObjectId> {
	Iterable<DeviceEntity> findByDeviceGroupNotificationKeyName(String groupName);
	DeviceEntity findByRegistrationToken(String registrationToken);
	DeviceEntity deleteByRegistrationToken(String registrationToken);
}
