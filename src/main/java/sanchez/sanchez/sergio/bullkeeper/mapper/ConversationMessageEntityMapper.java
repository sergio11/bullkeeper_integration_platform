package sanchez.sanchez.sergio.bullkeeper.mapper;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.exception.MemberNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.MessageEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PersonEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddMessageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MessageDTO;

/**
 * Conversation Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ConversationMessageEntityMapper {
	
	/**
	 * Conversation Repository
	 */
	@Autowired
	protected ConversationRepository conversationRepository;
	
	/**
	 * Guardian Repository
	 */
	@Autowired
	protected GuardianRepository guardianRepository;
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Person Entity Mapper
	 */
	@Autowired
	protected PersonEntityMapper personEntityMapper;
	
	/**
	 * Map To Person
	 * @param id
	 * @return
	 */
	protected PersonEntity mapToPersonEntity(final String id) {
		return Optional.ofNullable((PersonEntity)guardianRepository.findOne(new ObjectId(id)))
				.map(Optional::of)
				.orElseGet(() -> Optional.ofNullable((PersonEntity)kidRepository.findOne(new ObjectId(id))))
				.orElseThrow(() -> new MemberNotFoundException());
	}

    /**
	 * 
	 * @param conversationEntity
	 * @return
	 */
	@Mappings({
        @Mapping(expression="java(messageEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(source = "messageEntity.createAt", target = "createAt", 
        	dateFormat = "yyyy/MM/dd HH:mm:ss.SSS"),
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
    
    /**
     * Add Message DTO to Message Entity
     * @param messageDTO
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(conversationRepository.findOne(new org.bson.types.ObjectId(messageDTO.getConversation())))", 
        		target = "conversation" ),
    	@Mapping(expression="java(mapToPersonEntity(messageDTO.getFrom()))", target="from"),
    	@Mapping(expression="java(mapToPersonEntity(messageDTO.getTo()))", target="to")
    	
    })
    @Named("addMessageDTOToMessageEntity")
    public abstract MessageEntity addMessageDTOToMessageEntity(final AddMessageDTO messageDTO);
    
    
    /**
     * @param addMessageDTOList
     * @return
     */
    @IterableMapping(qualifiedByName = "addMessageDTOToMessageEntity")
    public abstract Iterable<MessageEntity> addMessageDTOToMessageEntityList(final Iterable<AddMessageDTO> addMessageDTOList);
}
