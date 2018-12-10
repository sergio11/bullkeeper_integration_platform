package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CallDetailEntity;

/**
 * Call Detail Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface CallDetailRepository extends MongoRepository<CallDetailEntity, Long>{
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * Find By Kid Id And Terminal Id
	 * @param kid
	 * @param terminal
	 * @return
	 */
	Iterable<CallDetailEntity> findByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * Find By Terminal Id
	 * @param terminal
	 * @return
	 */
	Iterable<CallDetailEntity> findByTerminalId(final ObjectId terminal);
	
	/**
	 * 
	 * @param call
	 * @param kid
	 * @param terminal
	 * @return
	 */
	CallDetailEntity findOneByIdAndKidIdAndTerminalId(final ObjectId call, final ObjectId kid, final ObjectId terminal);

	/**
	 * 
	 * @param kid
	 * @param terminal
	 */
	void deleteByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * @param call
	 * @param kid
	 * @param terminal
	 */
	void deleteByIdAndKidIdAndTerminalId(final ObjectId call, final ObjectId kid, final ObjectId terminal);
	
}
