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
public interface GeofenceRepository extends MongoRepository<GeofenceEntity, ObjectId>
		, GeofenceRepositoryCustom {

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
	 * Delete By Kid And Id
	 * @param kid
	 * @param id
	 */
	void deleteByKidAndId(final ObjectId kid, final ObjectId id);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	
	/**
	 * Find By Kid And Id
	 * @param kid
	 * @param id
	 * @return
	 */
	GeofenceEntity findByKidAndId(final ObjectId kid, final ObjectId id);
	
}

