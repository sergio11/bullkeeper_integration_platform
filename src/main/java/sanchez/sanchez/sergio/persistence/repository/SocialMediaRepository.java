package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;


/**
 *
 * @author sergio
 */
@Repository
public interface SocialMediaRepository extends MongoRepository<SocialMediaEntity, ObjectId> {
    
}
