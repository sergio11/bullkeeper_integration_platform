package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IConversationService;
import sanchez.sanchez.sergio.bullkeeper.mapper.ConversationEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.MessageEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.MessageSavedSSE;
import sanchez.sanchez.sergio.bullkeeper.sse.service.impl.SupportSseService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ConversationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MessageDTO;

/**
 * Conversation Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service("ConversationService")
public class ConversationServiceImpl implements IConversationService {
	
	/**
	 * Message Sse service
	 */
	private final SupportSseService<MessageSavedSSE> messageSseService;
	
	/**
	 * Conversation Repository
	 */
	private final ConversationRepository conversationRepository;
	
	/**
	 * Conversation Entity Mapper
	 */
	private final ConversationEntityMapper conversationEntityMapper;
	
	/**
	 * Message Repository
	 */
	private final MessageRepository messageRepository;
	

	/**
	 * 
	 * @param messageSseService
	 */
	public ConversationServiceImpl(
			final SupportSseService<MessageSavedSSE> messageSseService,
			final ConversationRepository conversationRepository,
			final ConversationEntityMapper conversationEntityMapper,
			final MessageRepository messageRepository) {
		super();
		this.messageSseService = messageSseService;
		this.conversationRepository = conversationRepository;
		this.conversationEntityMapper = conversationEntityMapper;
		this.messageRepository = messageRepository;
	}
	
	/**
	 * Get All Conversations of guardian
	 */
	@Override
	public Iterable<ConversationDTO> getAllConversationsOfGuardian(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		
		// Get All Conversation
		final Iterable<ConversationEntity> conversationList =
				conversationRepository.findAllBySupervisedChildrenEntityGuardian(guardian);
		// Transforms results
		return conversationEntityMapper.conversationEntityToConversationDTOs(conversationList);
	}

	/**
	 * Get Conversation Detail
	 */
	@Override
	public ConversationDTO getConversationDetail(final ObjectId conversationId) {
		Assert.notNull(conversationId, "Conversation id can not be null");
		
		final ConversationEntity conversationEntity = 
				conversationRepository.findOne(conversationId);
		// Map conversation
		return conversationEntityMapper.conversationEntityToConversationDTO(conversationEntity);
	}

	/**
	 * Delete
	 */
	@Override
	public void delete(final ObjectId id) {
		Assert.notNull(id, "id can not be null");
		conversationRepository.delete(id);
	}

	/**
	 * Delete By Guardian Id
	 */
	@Override
	public void deleteByGuardianId(final ObjectId guardian) {
		Assert.notNull(guardian, "guardian can not be null");
		
		conversationRepository.deleteBySupervisedChildrenEntityGuardian(guardian);
		
	}
	
	/**
	 * Delete By KId id and guardian Id
	 */
	@Override
	public void deleteByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian) {
		Assert.notNull(kid, "kid can not be null");
		Assert.notNull(guardian, "guardian can not be null");
		
		conversationRepository
			.deleteBySupervisedChildrenEntityKidAndSupervisedChildrenEntityGuardian(kid, guardian);
		
	}

	/**
	 * Get Conversation Messages
	 */
	@Override
	public Iterable<MessageDTO> getConversationMessages(final ObjectId conversationId) {
		Assert.notNull(conversationId, "Conversation Id can not be null");
		
		// Get Message List
		final Iterable<MessageEntity> messageList = 
				messageRepository.findByConversationId(conversationId);
		// Map Results
		return conversationEntityMapper.messageEntityToMessageDTOs(messageList);
		
	}

	/**
	 * Delete All Conversation Messages
	 */
	@Override
	public void deleteAllConversationMessages(ObjectId conversationId) {
		Assert.notNull(conversationId, "Conversation Id can not be null");
		
		// Delete all messages
		messageRepository.deleteByConversationId(conversationId);
		// Delete conversation
		conversationRepository.delete(conversationId);
		
	}

	/**
	 * Delete All Conversation Messages By Kid Id And Guardian Id
	 */
	@Override
	public void deleteAllConversationMessagesByKidIdAndGuardianId(final ObjectId kid, 
			final ObjectId guardian) {
		Assert.notNull(kid, "kid can not be null");
		Assert.notNull(guardian, "guardian can not be null");
		
		final ConversationEntity conversationEntity = conversationRepository
				.findBySupervisedChildrenEntityKidAndSupervisedChildrenEntityGuardian(kid, guardian);
		
		if(conversationEntity != null) {
			messageRepository.deleteByConversationId(conversationEntity.getId());
		}
		
	}

	/**
	 * Get Conversation By Kid Id And GUardian Id
	 */
	@Override
	public ConversationDTO getConversationByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian) {
		Assert.notNull(kid, "kid can not be null");
		Assert.notNull(guardian, "guardian can not be null");
		
		
		final ConversationEntity conversationEntity = conversationRepository
			.findBySupervisedChildrenEntityKidAndSupervisedChildrenEntityGuardian(kid, guardian);
	
		// Map Results
		return conversationEntityMapper.conversationEntityToConversationDTO(conversationEntity);
	}

	/**
	 * Get Conversation Message BY Kid Id And Guardian Id
	 */
	@Override
	public Iterable<MessageDTO> getConversationMessagesByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian) {
		Assert.notNull(kid, "kid can not be null");
		Assert.notNull(guardian, "guardian can not be null");
		
		// Messages
		Iterable<MessageDTO> messages = new ArrayList<>();
		
		final ConversationEntity conversationEntity = conversationRepository
				.findBySupervisedChildrenEntityKidAndSupervisedChildrenEntityGuardian(kid, guardian);
		
		if(conversationEntity != null) {
			
			// Find Messages by conversation id
			final Iterable<MessageEntity> messagesEntities = 
					messageRepository.findByConversationId(conversationEntity.getId());
			
			// Map Result
			messages = conversationEntityMapper.messageEntityToMessageDTOs(messagesEntities);
		}
		
		return messages;
	}
	
	/**
	 * Init
	 */
	@PostConstruct
    protected void init() {
		Assert.notNull(messageSseService, "Message Sse Service can not be null");
		Assert.notNull(conversationRepository, "Conversation Repository can not be null");
		Assert.notNull(conversationEntityMapper, "Coversation ENtity can not be null");
	}
}
