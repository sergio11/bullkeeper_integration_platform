package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SseEventEntity;

/**
 * Sse Event Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface SseEventRepository extends MongoRepository<SseEventEntity, ObjectId> {

	/**
	 * Find By Target
	 * @param id
	 */
	Iterable<SseEventEntity> findByTarget(final String target);
	
	/**
	 * Delete All By Target
	 * @param target
	 */
	void deleteAllByTarget(final String target);

}
