
package sanchez.sanchez.sergio.bullkeeper.persistence.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TaskEntity;

/**
 *
 * @author sergio
 */
public interface TaskRepository extends MongoRepository<TaskEntity, ObjectId> {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    long deleteByKidId(ObjectId id);
}
