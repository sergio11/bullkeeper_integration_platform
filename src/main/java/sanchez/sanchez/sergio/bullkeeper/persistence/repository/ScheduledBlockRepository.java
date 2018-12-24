package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.joda.time.LocalTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScheduledBlockEntity;

/**
 * Scheduled Block Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface ScheduledBlockRepository extends MongoRepository<ScheduledBlockEntity, ObjectId> ,ScheduledBlockRepositoryCustom {

	/**
	 * Find By Kid Id
	 * @param id
	 * @return
	 */
	Iterable<ScheduledBlockEntity> findByKidId(final ObjectId id);
	
	/**
	 * Find By Kid Id
	 * @param id
	 * @param kid
	 * @return
	 */
	Iterable<ScheduledBlockEntity> findByIdNotAndKidId(final ObjectId id, final ObjectId kid);
	
	
	/**
	 * Find By Id And Kid Id
	 * @param block
	 * @param kid
	 * @return
	 */
	ScheduledBlockEntity findByIdAndKidId(final ObjectId block, final ObjectId kid);
	
	
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
	void deleteByKidId(final ObjectId id);
	
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
