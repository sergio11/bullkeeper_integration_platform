package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

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
	 * Delete 
	 * @param ids
	 */
	void deleteBySupervisedChildrenEntityIdIn(final List<ObjectId> ids);
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	Iterable<ConversationEntity> findAllBySupervisedChildrenEntityIdIn(final List<ObjectId> ids);
	
	/**
	 * 
	 * @param id
	 * @param ids
	 * @return
	 */
	long countByIdAndSupervisedChildrenEntityIdIn(final ObjectId id, final List<ObjectId> ids);

	
	
}
