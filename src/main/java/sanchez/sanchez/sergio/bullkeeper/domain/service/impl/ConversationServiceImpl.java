package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IConversationService;
import sanchez.sanchez.sergio.bullkeeper.mapper.ConversationEntityMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.MessageEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.sse.models.conversation.MessageSavedSSE;
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
	 * Supervised Children Repository
	 */
	private final SupervisedChildrenRepository supervisedChildrenRepository;

	

	/**
	 * @param messageSseService
	 * @param conversationRepository
	 * @param conversationEntityMapper
	 * @param messageRepository
	 * @param supervisedChildrenRepository
	 */
	public ConversationServiceImpl(
			final ISseService messageSseService,
			final ConversationRepository conversationRepository,
			final ConversationEntityMapper conversationEntityMapper,
			final MessageRepository messageRepository,
			final SupervisedChildrenRepository supervisedChildrenRepository) {
		super();
		this.messageSseService = messageSseService;
		this.conversationRepository = conversationRepository;
		this.conversationEntityMapper = conversationEntityMapper;
		this.messageRepository = messageRepository;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
	}
	
	/**
	 * Get All Conversations of guardian
	 */
	@Override
	public Iterable<ConversationDTO> getAllConversationsOfGuardian(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		
		Iterable<ConversationDTO> conversationDTOs = new ArrayList<>();
		
		// Get Supervised Children
		final List<SupervisedChildrenEntity> supervisedChildrenListSaved =
					supervisedChildrenRepository.findByGuardianId(guardian);
		
		if(supervisedChildrenListSaved != null 
				&& !supervisedChildrenListSaved.isEmpty()) {
			
			// Get All Conversation
			final Iterable<ConversationEntity> conversationList =
					conversationRepository.findAllBySupervisedChildrenEntityIdIn(
							supervisedChildrenListSaved
							.stream().map(model -> model.getId()).collect(Collectors.toList()));
			
			// Transforms results
			conversationDTOs = conversationEntityMapper.conversationEntityToConversationDTOs(conversationList);
			
		}
		
		
		return conversationDTOs;
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
		
		// Get Supervised Children
		final List<SupervisedChildrenEntity> supervisedChildrenListSaved =
					supervisedChildrenRepository.findByGuardianId(guardian);
				
		if(supervisedChildrenListSaved != null &&
				!supervisedChildrenListSaved.isEmpty())
			conversationRepository
					.deleteBySupervisedChildrenEntityIdIn(supervisedChildrenListSaved
							.stream().map(model -> model.getId()).collect(Collectors.toList()));
		
	}
	
	/**
	 * Delete By KId id and guardian Id
	 */
	@Override
	public void deleteByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian) {
		Assert.notNull(kid, "kid can not be null");
		Assert.notNull(guardian, "guardian can not be null");
		
		// Get Supervised Children
		final SupervisedChildrenEntity supervisedChildrenSaved =
						supervisedChildrenRepository.findByGuardianIdAndKidId(guardian, kid);
		
		if(supervisedChildrenSaved != null)
			conversationRepository
				.deleteBySupervisedChildrenEntityId(supervisedChildrenSaved.getId());
		
	}

	/**
	 * Get Conversation Messages
	 */
	@Override
	public Iterable<MessageDTO> getConversationMessages(final ObjectId conversationId) {
		Assert.notNull(conversationId, "Conversation Id can not be null");
		
		// Get Message List
		final Iterable<MessageEntity> messagesEntities = 
				messageRepository.findByConversationId(conversationId);
		
		// set viewed on true
		messagesEntities.forEach(message -> message.setViewed(true));
		
		messageRepository.save(messagesEntities);
		
		// Map Results
		return conversationEntityMapper.messageEntityToMessageDTOs(messagesEntities);
		
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
		
		// Get Supervised Children
		final SupervisedChildrenEntity supervisedChildrenSaved =
				supervisedChildrenRepository.findByGuardianIdAndKidId(guardian, kid);
		
		if(supervisedChildrenSaved != null) {
			
			final ConversationEntity conversationEntity = conversationRepository
					.findBySupervisedChildrenEntityId(supervisedChildrenSaved.getId());
			
			if(conversationEntity != null) {
				messageRepository.deleteByConversationId(conversationEntity.getId());
			}
			
		}
	
	}

	/**
	 * Get Conversation By Kid Id And GUardian Id
	 */
	@Override
	public ConversationDTO getConversationByKidIdAndGuardianId(final ObjectId kid, final ObjectId guardian) {
		Assert.notNull(kid, "kid can not be null");
		Assert.notNull(guardian, "guardian can not be null");
		
		ConversationDTO conversationDTO = null;
		
		// Get Supervised Children
		final SupervisedChildrenEntity supervisedChildrenSaved =
				supervisedChildrenRepository.findByGuardianIdAndKidId(guardian, kid);
		
		if(supervisedChildrenSaved != null) {
			
			final ConversationEntity conversationEntity = conversationRepository
					.findBySupervisedChildrenEntityId(supervisedChildrenSaved.getId());
			
			conversationDTO = conversationEntityMapper.conversationEntityToConversationDTO(conversationEntity);
			
		}
		
		// Map Results
		return conversationDTO;
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
		
		// Get Supervised Children
		final SupervisedChildrenEntity supervisedChildrenSaved =
					supervisedChildrenRepository.findByGuardianIdAndKidId(guardian, kid);
		
		if(supervisedChildrenSaved != null) {
		
			final ConversationEntity conversationEntity = conversationRepository
					.findBySupervisedChildrenEntityId(supervisedChildrenSaved.getId());
			
			if(conversationEntity != null) {
				
				logger.debug("Get Meesages for conversation id -> " + conversationEntity.getId().toString());
				// Find Messages by conversation id
				final Iterable<MessageEntity> messagesEntities = 
						messageRepository.findByConversationId(conversationEntity.getId());
				
				// set viewed on true
				messagesEntities.forEach(message -> message.setViewed(true));
				
				messageRepository.save(messagesEntities);
				
				// Map Result
				messages = conversationEntityMapper.messageEntityToMessageDTOs(messagesEntities);
			}
			
		}
		
		return messages;
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
		
		// Get Conversation
		final ConversationEntity conversationEntity = 
				conversationRepository.findOne(conversationId);
		
		
		// Create Message
		final MessageEntity messageEntity = new MessageEntity();
		messageEntity.setConversation(conversationEntity);
		messageEntity.setText(message.getText());
		
		final GuardianEntity guardianEntity = conversationEntity
				.getSupervisedChildrenEntity().getGuardian();
		final KidEntity kidEntity = conversationEntity
				.getSupervisedChildrenEntity().getKid();
		
		if(guardianEntity.getId()
				.equals(new ObjectId(message.getFrom()))) {
			messageEntity.setFrom(guardianEntity);
			messageEntity.setTo(kidEntity);
		} else {
			messageEntity.setFrom(kidEntity);
			messageEntity.setTo(guardianEntity);
		}
		
		// Save Message
		final MessageEntity messageEntitySaved = messageRepository
				.save(messageEntity);
		
		
		final String idFrom = messageEntitySaved.getFrom().getId().toString();
	
		// Push Event
		final MessageSavedSSE messageSavedSSE = new MessageSavedSSE(
				idFrom, idFrom, messageEntitySaved.getText());
		
		logger.debug("Message Saved SSE -> " + messageSavedSSE.toString());
		
		messageSseService.push(idFrom, messageSavedSSE);
		
		// Map results
		return conversationEntityMapper
				.messageEntityToMessageDTO(messageEntitySaved);
		
	}

	/**
	 * Create Conversation
	 * @param guardian
	 * @param kid
	 */
	@Override
	public ConversationDTO createConversation(ObjectId guardian, ObjectId kid) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		ConversationDTO conversationDTO = null;
		
		// Get Supervised Children
		final SupervisedChildrenEntity supervisedChildrenSaved =
				supervisedChildrenRepository.findByGuardianIdAndKidId(guardian, kid);
		
		if(supervisedChildrenSaved != null) {
			final ConversationEntity conversationEntitySaved = conversationRepository
				.save(new ConversationEntity(supervisedChildrenSaved));
			conversationDTO = conversationEntityMapper
					.conversationEntityToConversationDTO(conversationEntitySaved);
		}
			
		return conversationDTO;
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
		
		messageRepository
			.deleteByConversationIdAndIdIn(conversationId, messageIds);
		
	}

	/**
	 * @param kid
	 * @param guardian
	 * @param messageIds
	 */
	@Override
	public void deleteConversationMessagesByKidIdAndGuardianId(final ObjectId kid, 
			final ObjectId guardian, final List<ObjectId> messageIds) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(messageIds, "Message Ids can not be null");
		
		// Get Supervised Children
		final SupervisedChildrenEntity supervisedChildrenSaved =
				supervisedChildrenRepository.findByGuardianIdAndKidId(guardian, kid);
				
		if(supervisedChildrenSaved != null) {
			
			final ConversationEntity conversationEntity = conversationRepository
					.findBySupervisedChildrenEntityId(supervisedChildrenSaved.getId());
			
			if(conversationEntity != null) {
				messageRepository
					.deleteByConversationIdAndIdIn(conversationEntity.getId(), messageIds);
			}
			
		}
			
					
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
