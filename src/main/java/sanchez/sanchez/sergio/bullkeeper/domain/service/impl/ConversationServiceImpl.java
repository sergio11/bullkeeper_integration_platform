package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IConversationService;
import sanchez.sanchez.sergio.bullkeeper.exception.ConversationMemberNotExistsException;
import sanchez.sanchez.sergio.bullkeeper.mapper.ConversationEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.mapper.ConversationMessageEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.MessageEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PersonEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.service.ISseService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddMessageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ConversationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MessageDTO;

/**
 * Conversation Service Impl
 * @author sergiosanchezsanchez
 *
 */
@Service("ConversationService")
public class ConversationServiceImpl implements IConversationService {
	
	private static Logger logger = LoggerFactory.getLogger(ConversationServiceImpl.class);
	
	/**
	 *  Sse service
	 */
	private final ISseService messageSseService;
	
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
	 * Guardian Repository
	 */
	private final GuardianRepository guardianRepository;
	
	/**
	 * Kid Repository
	 */
	private final KidRepository kidRepository;
	
	/**
	 * Conversation Message Entity Mapper
	 */
	private final ConversationMessageEntityMapper conversationMessageEntityMapper;


	/**
	 * @param messageSseService
	 * @param conversationRepository
	 * @param conversationEntityMapper
	 * @param messageRepository
	 * @param guardianRepository
	 * @param kidRepository
	 */
	public ConversationServiceImpl(
			final ISseService messageSseService,
			final ConversationRepository conversationRepository,
			final ConversationEntityMapper conversationEntityMapper,
			final MessageRepository messageRepository,
			final GuardianRepository guardianRepository,
			final KidRepository kidRepository,
			final ConversationMessageEntityMapper conversationMessageEntityMapper) {
		super();
		this.messageSseService = messageSseService;
		this.conversationRepository = conversationRepository;
		this.conversationEntityMapper = conversationEntityMapper;
		this.messageRepository = messageRepository;
		this.guardianRepository = guardianRepository;
		this.kidRepository = kidRepository;
		this.conversationMessageEntityMapper = conversationMessageEntityMapper;
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
	 * Get Conversation Messages
	 */
	@Override
	public Iterable<MessageDTO> getConversationMessages(final ObjectId conversationId) {
		Assert.notNull(conversationId, "Conversation Id can not be null");
		
		// Get Message List
		final Iterable<MessageEntity> messagesEntities = 
				messageRepository.findByConversationIdOrderByCreateAtDesc(conversationId);
		
		
		// Map Results
		return conversationMessageEntityMapper.messageEntityToMessageDTOs(messagesEntities);
		
	}

	/**
	 * Delete All Conversation Messages
	 */
	@Override
	public void deleteAllConversationMessages(ObjectId conversationId) {
		Assert.notNull(conversationId, "Conversation Id can not be null");
		// Delete all messages
		messageRepository.deleteByConversationId(conversationId);
	}


	/**
	 * Save Message
	 * @param conversationId
	 * @param message
	 */
	@Override
	public MessageDTO saveMessage(ObjectId conversationId, AddMessageDTO message) {
		Assert.notNull(conversationId, "Conversation Id can not be null");
		Assert.notNull(message, "Message can not be null");
		Assert.notNull(message.getFrom(), "Message From can not be null");
		Assert.notNull(message.getTo(), "Message To can not be null");
		
		// Map To Message to save
		final MessageEntity messageToSave = conversationMessageEntityMapper
				.addMessageDTOToMessageEntity(message);
		
		// Save Message
		final MessageEntity messageEntitySaved = messageRepository
				.save(messageToSave);
		
		
		// Map results
		return conversationMessageEntityMapper
				.messageEntityToMessageDTO(messageEntitySaved);
		
	}

	/**
	 * Create Conversation
	 * @param memberOne
	 * @param memberTwo
	 */
	@Override
	public ConversationDTO createConversation(final ObjectId memberOne, final ObjectId memberTwo) {
		Assert.notNull(memberOne, "Member One can not be null");
		Assert.notNull(memberTwo, "Member Two can not be null");
		
		// Save Conversation
		final ConversationEntity conversationEntitySaved = conversationRepository
				.save(new ConversationEntity(getPersonEntity(memberOne), getPersonEntity(memberTwo)));
	
		// Map Conversation
		return conversationEntityMapper
				.conversationEntityToConversationDTO(conversationEntitySaved);
	}
	
	/**
	 * Delete Conversation Messages
	 * @param conversationId
	 * @param messageIds
	 */
	@Override
	public void deleteConversationMessages(final ObjectId conversationId,
			final List<ObjectId> messageIds) {
		Assert.notNull(conversationId, "Conversation Id can not be null");
		Assert.notNull(messageIds, "Message Ids can not be null");
		
		// Delete Conversation Messages
		messageRepository
			.deleteByConversationIdAndIdIn(conversationId, messageIds);
		
	}
	
	/**
	 * Get Conversations By Member Id
	 */
	@Override
	public Iterable<ConversationDTO> getConversationsByMemberId(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		// Find Conversations by member
		final Iterable<ConversationEntity> conversationList = 
				conversationRepository.findByMemberId(id);
		// Map Result
		return conversationEntityMapper
				.conversationEntityToConversationDTOs(conversationList);
	}

	/**
	 * Get Conversation For Member
	 */
	@Override
	public ConversationDTO getConversationForMembers(final ObjectId memberOne, final ObjectId memberTwo) {
		Assert.notNull(memberOne, "Member One can not be null");
		Assert.notNull(memberTwo, "Member Two can not be null");
		
		// Find Conversation For Members
		final ConversationEntity conversationSaved = 
				conversationRepository.findByMemberOneIdAndMemberTwoId(memberOne, memberTwo);
		
		return conversationEntityMapper
				.conversationEntityToConversationDTO(conversationSaved);
	}
	
	
	/**
	 * Get Conversation Messages For Members
	 */
	@Override
	public Iterable<MessageDTO> getConversationMessagesForMembers(
			final ObjectId memberOne, final ObjectId memberTwo) {
		Assert.notNull(memberOne, "Member One can not be null");
		Assert.notNull(memberTwo, "Member Two can not be null");
		
		// Find Conversation For Members
		final ConversationEntity conversationSaved = 
				conversationRepository.findByMemberOneIdAndMemberTwoId(memberOne, memberTwo);
		
		final List<MessageEntity> messages = 
				messageRepository.findByConversationIdOrderByCreateAtDesc(conversationSaved.getId());
		
		return conversationMessageEntityMapper
				.messageEntityToMessageDTOs(messages);
	}


	/**
	 * Delete Conversations By Member Id
	 */
	@Override
	public void deleteConversationsByMemberId(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		conversationRepository.deleteByMemberId(id);
	}
	
	/**
	 * Mark Messages As Viewed
	 */
	@Override
	public void setMessagesAsViewed(final Collection<ObjectId> messageIds) {
		Assert.notNull(messageIds, "Message Ids can not be null");
		
		messageRepository.markMessagesAsViewed(messageIds);
		
	}
	
	/**
	 * Map To Person
	 * @param id
	 * @return
	 */
	private PersonEntity getPersonEntity(final ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		return Optional.ofNullable((PersonEntity)guardianRepository.findOne(id))
				.map(Optional::of)
				.orElseGet(() -> Optional.ofNullable((PersonEntity)kidRepository.findOne(id)))
				.orElseThrow(() -> new ConversationMemberNotExistsException());
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
