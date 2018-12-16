package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PhoneNumberBlockedEntity;

/**
 * Phone Number Blocked Repository
 * @author sergiosanchezsanchez
 *
 */
public interface PhoneNumberBlockedRepository 
	extends MongoRepository<PhoneNumberBlockedEntity, ObjectId>  {

	/**
	 * 
	 * @param terminal
	 * @param kid
	 * @param id
	 */
	void deleteByTerminalIdAndKidIdAndId(final ObjectId terminal, final ObjectId kid, final ObjectId id);
	
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
	void deleteByIdAndKidIdAndTerminalId(final ObjectId id, 
			final ObjectId kid, final ObjectId terminal);
	
}
