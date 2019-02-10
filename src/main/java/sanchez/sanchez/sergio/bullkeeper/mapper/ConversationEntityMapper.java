package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.MessageRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ConversationDTO;

/**
 * Conversation Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ConversationEntityMapper {
	
	
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
        @Mapping(expression="java(personEntityMapper"
        		+ ".personEntityToPersonDTO(conversationEntity.getMemberOne()))", 
        		target = "memberOne"),
        @Mapping(expression="java(personEntityMapper"
        		+ ".personEntityToPersonDTO(conversationEntity.getMemberTwo()))", 
        		target = "memberTwo"),
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
   
    
}
