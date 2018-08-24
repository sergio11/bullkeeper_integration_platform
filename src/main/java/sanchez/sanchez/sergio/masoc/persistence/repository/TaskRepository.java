
package sanchez.sanchez.sergio.masoc.persistence.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import sanchez.sanchez.sergio.masoc.persistence.entity.TaskEntity;

/**
 *
 * @author sergio
 */
public interface TaskRepository extends MongoRepository<TaskEntity, ObjectId> {
    long deleteBySonEntity(ObjectId id);
}
