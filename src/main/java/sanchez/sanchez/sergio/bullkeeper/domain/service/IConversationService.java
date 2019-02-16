package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddMessageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ConversationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MessageDTO;

/**
 * Conversation Service
 * @author sergiosanchezsanchez
 *
 */
public interface IConversationService {
	
	
	/**
	 * Get Conversation Detail
	 * @param guardian
	 * @return
	 */
	ConversationDTO getConversationDetail(final ObjectId conversationId);
	
	/**
	 * Get Conversations By Member Id
	 * @param id
	 * @return
	 */
	Iterable<ConversationDTO> getConversationsByMemberId(final ObjectId id);
	
	/**
	 * Delete Conversations By Member Id
	 * @param id
	 */
	void deleteConversationsByMemberId(final ObjectId id);
	
	
	/**
	 * Get Conversation For Members
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	ConversationDTO getConversationForMembers(final ObjectId memberOne, final ObjectId memberTwo);
	
	/**
	 * Delete
	 * @param id
	 */
	void delete(final ObjectId id);
	
	
	/**
	 * Get Conversation Messages
	 * @param conversationId
	 * @return
	 */
	Iterable<MessageDTO> getConversationMessages(final ObjectId conversationId);
	
	/**
	 * Delete All Conversation Messages
	 * @param conversationId
	 */
	void deleteAllConversationMessages(final ObjectId conversationId);
	
	/**
	 * Delete Conversation Messages
	 * @param conversationId
	 * @param messageIds
	 */
	void deleteConversationMessages(final ObjectId conversationId, final List<ObjectId> messageIds);

	

	/**
	 * Save Message
	 * @param conversationId
	 * @param message
	 * @return
	 */
	MessageDTO saveMessage(final ObjectId conversationId, final AddMessageDTO message);
	
	/**
	 * Create Conversation
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	ConversationDTO createConversation(final ObjectId memberOne, final ObjectId memberTwo);
	
	/**
	 * Get Conversation Messages For Members
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	Iterable<MessageDTO> getConversationMessagesForMembers(final ObjectId memberOne, final ObjectId memberTwo);
	
	/**
	 * Mark Messages As Viewed
	 * @param messageIds
	 */
	void markMessagesAsViewed(final Iterable<ObjectId> messageIds);
	
}
