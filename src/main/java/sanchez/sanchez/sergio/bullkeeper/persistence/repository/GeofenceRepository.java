package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GeofenceEntity;

/**
 * Geofence Entity
 * @author sergio
 */
@Repository
public interface GeofenceRepository extends MongoRepository<GeofenceEntity, ObjectId> {

	/**
	 * Find All By Kid
	 * @param kid
	 * @return
	 */
	Iterable<GeofenceEntity> findAllByKid(final ObjectId kid);
	
	/**
	 * Delete All By Kid
	 * @param kid
	 */
	void deleteAllByKid(final ObjectId kid);
	
	/**
	 * Delete All By Kid And Id In
	 * @param kid
	 * @param ids
	 */
	void deleteAllByKidAndIdIn(final ObjectId kid, final List<ObjectId> ids);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	
}

