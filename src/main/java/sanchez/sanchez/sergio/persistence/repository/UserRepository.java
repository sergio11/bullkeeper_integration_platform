package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.UserEntity;


/**
 *
 * @author sergio
 */
@Repository
public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    
}
