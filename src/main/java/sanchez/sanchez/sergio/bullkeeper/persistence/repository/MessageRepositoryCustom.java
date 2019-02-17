package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;


/**
 * Message Repository
 * @author sergio
 */
public interface MessageRepositoryCustom {
	
	/**
	 * Find First By Conversation and target member
	 * @param id
	 * @param to
	 * @return
	 */
	String findFirstByConversationIdAndToOrderByCreateAtDesc(final ObjectId id, final ObjectId to);
	
	/**
	 * @param id
	 * @return
	 */
	String findFirstByConversationIdOrderByCreateAtDesc(final ObjectId id);
	
	/**
	 * 
	 * @param conversation
	 * @param to
	 * @return
	 */
	long countByConversationIdAndToAndViewedFalse(final ObjectId conversation, final ObjectId to);
	
	
	/**
	 * Mark Messages As Viewed
	 * @param ids
	 */
	void markMessagesAsViewed(final Iterable<ObjectId> ids);
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	long countByFromIdAndToIdAndViewedFalse(final ObjectId from, final ObjectId to);
	
}
