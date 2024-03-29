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
		extends MongoRepository<ConversationEntity, ObjectId>, ConversationRepositoryCustom {

	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
}
