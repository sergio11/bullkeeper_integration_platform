
package es.bisite.usal.bulltect.persistence.repository;


import es.bisite.usal.bulltect.persistence.entity.TaskEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author sergio
 */
public interface TaskRepository extends MongoRepository<TaskEntity, ObjectId> {
    long deleteBySonEntity(ObjectId id);
}
