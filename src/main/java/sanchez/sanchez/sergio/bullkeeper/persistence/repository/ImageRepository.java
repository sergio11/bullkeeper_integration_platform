package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ImageEntity;

/**
 * @author sergio
 */
@Repository
public interface ImageRepository extends MongoRepository<ImageEntity, ObjectId> {
	
	
	/**
	 * Count By
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);

}
