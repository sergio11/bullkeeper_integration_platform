package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;

/**
 * Conversation Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface ConversationRepository 
		extends MongoRepository<ConversationEntity, ObjectId>{
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	ConversationEntity findBySupervisedChildrenEntityId(final ObjectId id);
	
	/**
	 * 
	 * @param kid
	 * @param guardian
	 * @return
	 */
	ConversationEntity findBySupervisedChildrenEntityKidAndSupervisedChildrenEntityGuardian(final ObjectId kid, final ObjectId guardian);
	
	/**
	 * Find All Supervised Children Entity Guardian
	 * @return
	 */
	Iterable<ConversationEntity> findAllBySupervisedChildrenEntityGuardian(final ObjectId guardian);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	long countBySupervisedChildrenEntityId(final ObjectId id);
	
	/**
	 * 
	 * @param id
	 */
	void deleteBySupervisedChildrenEntityId(final ObjectId id);
	
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * 
	 * @param id
	 * @param guardian
	 * @return
	 */
	long countByIdAndSupervisedChildrenEntityGuardian(final ObjectId id, final ObjectId guardian);
	
	/**
	 * Delete By Supervised Children Entity Guardian
	 * @param guardian
	 */
	void deleteBySupervisedChildrenEntityGuardian(final ObjectId guardian);
	
	/**
	 * Delete By Kid And Guardian
	 * @param kid
	 * @param guardian
	 */
	void deleteBySupervisedChildrenEntityKidAndSupervisedChildrenEntityGuardian(final ObjectId kid, final ObjectId guardian);
	
}
