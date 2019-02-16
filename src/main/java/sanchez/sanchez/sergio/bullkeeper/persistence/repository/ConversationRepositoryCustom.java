package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;

/**
 *
 * @author sergio
 */
public interface ConversationRepositoryCustom {
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Iterable<ConversationEntity> findByMemberId(final ObjectId id);
	
	/**
	 * Delete By Member Id
	 * @param id
	 */
	void deleteByMemberId(final ObjectId id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	long countByMemberId(final ObjectId id);
	
	/**
	 * 
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	ConversationEntity findByMemberOneIdAndMemberTwoId(
			final ObjectId memberOne,
			final ObjectId memberTwo);
	
}
