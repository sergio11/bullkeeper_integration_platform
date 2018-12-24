package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity;

/**
 * Contact Entity Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface ContactEntityRepository extends MongoRepository<ContactEntity, Long>{

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
	 * 
	 * @param kid
	 * @param terminal
	 * @return
	 */
	Iterable<ContactEntity> findAllByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param name
	 * @return
	 */
	Iterable<ContactEntity> findAllByKidIdAndTerminalIdAndNameIgnoreCaseContaining(final ObjectId kid, 
			final ObjectId terminal, final String text);
	

	
	/**
	 * 
	 * @param id
	 * @param kid
	 * @param terminal
	 * @return
	 */
	ContactEntity findOneByIdAndKidIdAndTerminalId(final ObjectId id, final ObjectId kid, final ObjectId terminal);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 */
	void deleteAllByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param ids
	 */
	void deleteByKidIdAndTerminalIdAndIdIn(final ObjectId kid, final ObjectId terminal, final List<ObjectId> ids);
	
	
	/**
	 * 
	 * @param id
	 * @param kid
	 * @param terminal
	 */
	void deleteByIdAndKidIdAndTerminalId(final ObjectId id, final ObjectId kid, final ObjectId terminal);
	
	/**
	 * Find One By Local id
	 * @param localId
	 * @return
	 */
	ContactEntity findOneByLocalId(final String localId);
}
