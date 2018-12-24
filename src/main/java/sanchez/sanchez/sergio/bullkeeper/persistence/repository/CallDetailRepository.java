package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

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
	 * Count By Kid And Terminal
	 * @param kid
	 * @param terminal
	 * @return
	 */
	long countByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
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
	

	/**
	 * @param kid
	 * @param terminal
	 */
	void deleteByKidIdAndTerminalIdAndIdIn(final ObjectId kid, final ObjectId terminal, final List<ObjectId> ids);
	
	/**
	 * Find One By Local Id
	 * @param localId
	 * @return
	 */
	CallDetailEntity findOneByLocalId(final String localId);
	
}
