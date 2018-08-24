package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.ImageEntity;

/**
 * @author sergio
 */
@Repository
public interface ImageRepository extends MongoRepository<ImageEntity, ObjectId> {

}
