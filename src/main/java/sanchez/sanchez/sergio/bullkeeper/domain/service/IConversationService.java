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
	 * Get All Conversations Of Guardian
	 * @param guardian
	 */
	Iterable<ConversationDTO> getAllConversationsOfGuardian(final ObjectId guardian);
	
	/**
	 * Get Conversation Detail
	 * @param guardian
	 * @return
	 */
	ConversationDTO getConversationDetail(final ObjectId conversationId);
	
	/**
	 * Delete
	 * @param id
	 */
	void delete(final ObjectId id);
	
	/**
	 * Delete by guardian id
	 * @param id
	 */
	void deleteByGuardianId(final ObjectId guardian);
	
	/**
	 * Delete By Kid Id And Guardian Id
	 * @param kid
	 */
	void deleteByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian);
	
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
	 * Delete All Conversation Messages
	 * @param kid
	 * @param guardian
	 */
	void deleteAllConversationMessagesByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian);
	
	
	/**
	 * Delete Conversation Messages
	 * @param conversationId
	 * @param messageIds
	 */
	void deleteConversationMessages(final ObjectId conversationId, final List<ObjectId> messageIds);
	
	/**
	 * Delete Conversation Messages
	 * @param kid
	 * @param guardian
	 * @param messageIds
	 */
	void deleteConversationMessagesByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian, final List<ObjectId> messageIds);
	
	
	/**
	 * Get Conversation By Kid and GUardian
	 * @param kid
	 * @param guardian
	 * @return
	 */
	ConversationDTO getConversationByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian);
	
	
	/**
	 * Get Conversation Messages by Kid id and guardian id
	 * @param kid
	 * @param guardian
	 * @return
	 */
	Iterable<MessageDTO> getConversationMessagesByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian);
	
	/**
	 * Save Message
	 * @param conversationId
	 * @param message
	 * @return
	 */
	MessageDTO saveMessage(final ObjectId conversationId, final AddMessageDTO message);
	
	/**
	 * Create Conversation
	 * @param guardian
	 * @param kid
	 * @return
	 */
	ConversationDTO createConversation(final ObjectId guardian, final ObjectId kid);
	
}
