package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

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
	 * Delete By Terminal Id And Kid Id
	 * @param terminal
	 * @param kid
	 */
	void deleteByTerminalIdAndKidId(final ObjectId terminal, final ObjectId kid);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * Count By Phone Number
	 * @param phoneNumber
	 * @return
	 */
	long countByPhoneNumber(final String phoneNumber);
	
	/**
	 * Delete By Id And Kid Id And Terminal Id
	 * @param id
	 * @param kid
	 * @param terminal
	 */
	void deleteByIdAndKidIdAndTerminalId(final ObjectId id, 
			final ObjectId kid, final ObjectId terminal);
	
	
	/**
	 * Delete By Phone Number And Kid Id And Terminal Id
	 * @param phoneNumber
	 * @param kid
	 * @param terminal
	 */
	void deleteByPhoneNumberAndKidIdAndTerminalId(final String phoneNumber, 
			final ObjectId kid, final ObjectId terminal);
	
	/**
	 * @param number
	 * @param phoneNumber
	 * @param kid
	 * @param terminal
	 * @return
	 */
	long countByNumberOrPhoneNumberAndKidIdAndTerminalId(
			final String number, final String phoneNumber, 
			final ObjectId kid, final ObjectId terminal);
	
	/**
	 * @param numberList
	 * @param phoneNumberList
	 * @param kid
	 * @param terminal
	 * @return
	 */
	long countByNumberInOrPhoneNumberInAndKidIdAndTerminalId(
			final List<String> number, final List<String> phoneNumber, 
			final ObjectId kid, final ObjectId terminal);

	
}
