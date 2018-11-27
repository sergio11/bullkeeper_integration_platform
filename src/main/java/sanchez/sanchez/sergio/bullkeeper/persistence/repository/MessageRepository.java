package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.MessageEntity;

/**
 * Message Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface MessageRepository 
		extends MongoRepository<MessageEntity, ObjectId>{
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	List<MessageEntity> findByConversationId(final ObjectId id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	long countByConversationId(final ObjectId id);
		
	/**
	 * 
	 * @param id
	 */
	void deleteByConversationId(final ObjectId id);
}
