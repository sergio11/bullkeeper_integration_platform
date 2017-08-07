package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;

/**
 *
 * @author sergio
 */
@Repository
public interface ParentRepository extends MongoRepository<ParentEntity, ObjectId> {
    
}
