package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;
import org.joda.time.LocalTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.masoc.persistence.entity.ScheduledBlockEntity;

/**
 * Scheduled Block Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface IScheduledBlockRepository extends MongoRepository<ScheduledBlockEntity, ObjectId>{

	/**
	 * Find By Son Id
	 * @param id
	 * @return
	 */
	Iterable<ScheduledBlockEntity> findBySonId(final ObjectId id);
	
	/**
	 * Count By
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * Delete By Son Id
	 * @param id
	 */
	void deleteBySonId(final ObjectId id);
	
	/**
	 * Count By Name
	 * @param name
	 * @return
	 */
	long countByName(final String name);
	
	/**
	 * Find All By Start At Less Than equal and end at greater than equal
	 * @param localTime
	 * @param localTime
	 * @return
	 */
	Iterable<ScheduledBlockEntity> findAllByStartAtLessThanEqualAndEndAtGreaterThanEqual(final LocalTime localTime);
	
}
