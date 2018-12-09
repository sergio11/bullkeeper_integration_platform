package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.MessageEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ConversationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MessageDTO;

/**
 * Conversation Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ConversationEntityMapper {
	
	/**
	 * SupervisedChildrenEntityMapper
	 */
	@Autowired
	protected SupervisedChildrenEntityMapper supervisedChildrenEntityMapper;
	
	/**
	 * Message Repository
	 */
	@Autowired
	protected MessageRepository messageRepository;
	
	/**
	 * Person Entity Mapper
	 */
	@Autowired
	protected PersonEntityMapper personEntityMapper;
	
	
	/**
	 * 
	 * @param conversationEntity
	 * @return
	 */
	@Mappings({
        @Mapping(expression="java(conversationEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(source = "conversationEntity.createAt", target = "createAt", 
        	dateFormat = "yyyy/MM/dd"),
        @Mapping(source = "conversationEntity.updateAt", target = "updateAt", 
    		dateFormat = "yyyy/MM/dd"),
        @Mapping(expression="java(supervisedChildrenEntityMapper"
        		+ ".supervisedChildrenEntityToKidGuardiansDTO(conversationEntity.getSupervisedChildrenEntity()))", 
        		target = "kidGuardian"),
        @Mapping(expression = "java(messageRepository.countByConversationId(conversationEntity.getId()))",
        	target = "messagesCount")
    })
    @Named("conversationEntityToConversationDTO")
    public abstract ConversationDTO conversationEntityToConversationDTO(
    		final ConversationEntity conversationEntity); 
	
    /**
     * 
     * @param conversationEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "conversationEntityToConversationDTO")
    public abstract Iterable<ConversationDTO> conversationEntityToConversationDTOs(final Iterable<ConversationEntity> conversationEntities);
    

    /**
	 * 
	 * @param conversationEntity
	 * @return
	 */
	@Mappings({
        @Mapping(expression="java(messageEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(source = "messageEntity.createAt", target = "createAt", 
        	dateFormat = "yyyy/MM/dd"),
        @Mapping(expression="java(messageEntity.getConversation()"
        		+ ".getId().toString())", target = "conversation"),
        @Mapping(expression="java(personEntityMapper.personEntityToPersonDTO(messageEntity.getFrom()))", 
			target = "from" ),
        @Mapping(expression="java(personEntityMapper.personEntityToPersonDTO(messageEntity.getTo()))", 
			target = "to" )
    })
    @Named("messageEntityToMessageDTO")
    public abstract MessageDTO messageEntityToMessageDTO(
    		final MessageEntity messageEntity); 
	
    /**
     * 
     * @param messageEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "messageEntityToMessageDTOs")
    public abstract Iterable<MessageDTO> messageEntityToMessageDTOs(final Iterable<MessageEntity> messageEntities);
    

    
}
