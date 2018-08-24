package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.DeviceGroupEntity;

@Repository
public interface DeviceGroupRepository extends MongoRepository<DeviceGroupEntity, ObjectId> {
	@Query(value="{ 'notification_key_name' : ?0 }",fields="{ 'notification_key' : 1 }")
    String getNotificationKey(String notificationKeyName);
    DeviceGroupEntity findByNotificationKeyName(String notificationKeyName);
    Long countByNotificationKeyName(String notificationKeyName);
    DeviceGroupEntity findByOwnerId(ObjectId id);
}
