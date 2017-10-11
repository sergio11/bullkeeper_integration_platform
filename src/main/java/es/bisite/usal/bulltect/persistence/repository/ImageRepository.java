package es.bisite.usal.bulltect.persistence.repository;

import es.bisite.usal.bulltect.persistence.entity.ImageEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sergio
 */
@Repository
public interface ImageRepository extends MongoRepository<ImageEntity, ObjectId> {

}
