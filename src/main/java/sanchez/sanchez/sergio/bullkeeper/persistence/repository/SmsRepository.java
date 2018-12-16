package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsEntity;

/**
 * Sms Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface SmsRepository extends MongoRepository<SmsEntity, Long>{

	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * 
	 * @param id
	 * @param kid
	 * @param terminal
	 */
	SmsEntity findByIdAndKidIdAndTerminalId(final ObjectId id, final ObjectId kid, 
			final ObjectId terminal);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 */
	Iterable<SmsEntity> findByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 */
	void deleteByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * 
	 * @param id
	 * @param kid
	 * @param terminal
	 */
	void deleteByIdAndKidIdAndTerminalId(final ObjectId id, final ObjectId kid, 
			final ObjectId terminal);
	
	/**
	 * Find By Local Id
	 * @param localId
	 * @return
	 */
	SmsEntity findByLocalId(final String localId);
	

}
