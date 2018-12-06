package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;


import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Iterables;

import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IConversationService;
import sanchez.sanchez.sergio.bullkeeper.exception.ConversationNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoMessagesFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ConversationShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddMessageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ConversationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MessageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ConversationResponseEnum;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@RestController("RestConversationController")
@Validated
@RequestMapping("/api/v1/conversations/")
@Api(tags = "conversations", value = "/conversations/", 
	description = "Conversations API", produces = "application/json")
public class ConversationController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(ConversationController.class);
	
	/**
	 * Conversation Service
	 */
	private final IConversationService conversationService;
	
	/**
	 * 
	 * @param conversationService
	 */
	public ConversationController(final IConversationService conversationService){
		this.conversationService = conversationService;
	}
	

	/**
	 * Get Conversation By Id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourConversation(#id) )")
    @ApiOperation(value = "GET_CONVERSATION_BY_ID", 
    	nickname = "GET_CONVERSATION_BY_ID", notes = "Get Conversation by Id",
    	response = ConversationDTO.class)
    public ResponseEntity<APIResponse<ConversationDTO>> getConversationById(
    		@ApiParam(name= "id", value = "Conversation Identifier", 
    				required = true)
				@Valid @ConversationShouldExists(message = "{conversation.id.notvalid}")
		 		@PathVariable String id) throws Throwable {
		
		
		logger.debug("Get Conversation by id -> " + id);
		
		return Optional.ofNullable(conversationService.getConversationDetail(new ObjectId(id)))
				.map(conversationDTO -> ApiHelper.<ConversationDTO>createAndSendResponse(ConversationResponseEnum.CONVERSATION_DETAIL, 
    		HttpStatus.OK, conversationDTO))
				.orElseThrow(() -> { throw new ConversationNotFoundException(); });
    }
	
	
	/**
	 * Delete Conversation By Id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourConversation(#id) )")
    @ApiOperation(value = "DELETE_CONVERSATION_BY_ID", 
    	nickname = "DELETE_CONVERSATION_BY_ID", notes = "Delete Conversation by Id",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteConversationById(
    		@ApiParam(name= "id", value = "Conversation Identifier", 
    				required = true)
				@Valid @ConversationShouldExists(message = "{conversation.id.notvalid}")
		 		@PathVariable String id) throws Throwable {
		
		logger.debug("Delete Conversation by id -> " + id);
		
		// Delete Conversation By Id
		conversationService.delete(new ObjectId(id));
		// Create and Send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.CONVERSATION_SUCCESSFULLY_DELETED, 
	    		HttpStatus.OK, this.messageSourceResolver.resolver("conversation.deleted"));
		
		
    }
	
	/**
	 * Get Conversation messages
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/messages", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourConversation(#id) )")
    @ApiOperation(value = "GET_CONVERSATION_MESSAGES", 
    	nickname = "GET_CONVERSATION_MESSAGES", notes = "Get Conversation Messages",
    	response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<MessageDTO>>> getConversationMessages(
    		@ApiParam(name= "id", value = "Conversation Identifier", 
    				required = true)
				@Valid @ConversationShouldExists(message = "{conversation.id.notvalid}")
		 		@PathVariable String id) throws Throwable{
		
		logger.debug("Get Conversation Messages for conversation -> " + id );
		// Get Conversation Messages
		final Iterable<MessageDTO> messageList = conversationService
				.getConversationMessages(new ObjectId(id));
		
		if(Iterables.size(messageList) == 0)
			throw new NoMessagesFoundException();
		
		// Create and Send response
		return ApiHelper.<Iterable<MessageDTO>>createAndSendResponse(ConversationResponseEnum.CONVERSATION_SUCCESSFULLY_DELETED, 
			    		HttpStatus.OK, messageList);
	
    }
	
	/**
	 * Delete Conversation messages
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/messages", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourConversation(#id) )")
    @ApiOperation(value = "DELETE_CONVERSATION_MESSAGES", 
    	nickname = "DELETE_CONVERSATION_MESSAGES", notes = "Delete Conversation Messages",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteConversationMessage(
    		@ApiParam(name= "id", value = "Conversation Identifier", 
    				required = true)
				@Valid @ConversationShouldExists(message = "{guardian.id.notvalid}")
		 		@PathVariable String id) throws Throwable {
		
		logger.debug("Delete all messages for conversation -> " + id );
		
		// Delete All Conversation Messages
		conversationService.deleteAllConversationMessages(new ObjectId(id));
		
		// Create and Send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.ALL_CONVERSATION_MESSAGES_SUCCESSFULLY_DELETED, 
			HttpStatus.OK, this.messageSourceResolver.resolver("all.conversation.messages.deleted"));
		
    }
	
	/**
	 * Delete Conversation messages
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/messages", method = RequestMethod.POST)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourConversation(#id) )")
    @ApiOperation(value = "ADD_MESSAGE", 
    	nickname = "ADD_MESSAGE", notes = "Add Message",
    	response = MessageDTO.class)
    public ResponseEntity<APIResponse<MessageDTO>> addMessage(
    		@ApiParam(name= "id", value = "Conversation Identifier", 
    				required = true)
				@Valid @ConversationShouldExists(message = "{guardian.id.notvalid}")
		 		@PathVariable String id,
		 	@ApiParam(value = "message", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody final AddMessageDTO message) throws Throwable {
		
		return null;
		
		
    }
	
	
	
	
	/**
	 * Get Conversation detail by kid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/self/{kid}", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_CONVERSATION_DETAIL", 
    	nickname = "GET_CONVERSATION_DETAIL", notes = "Get Conversation Detail",
    	response = ConversationDTO.class)
    public ResponseEntity<APIResponse<ConversationDTO>> getConversationDetailByKid(
    		@ApiParam(hidden = true) @CurrentUser 
				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name= "kid", value = "Kid Identifier", 
    				required = true)
				@Valid @KidShouldExists(message = "{kid.id.notvalid}")
		 			@PathVariable String kid) throws Throwable {
		
		logger.debug("Get Conversation by kid -> " + kid);
		
		return Optional.ofNullable(conversationService.getConversationByKidIdAndGuardianId(new ObjectId(kid), selfGuardian.getUserId()))
				.map(conversationDTO -> ApiHelper.<ConversationDTO>createAndSendResponse(ConversationResponseEnum.CONVERSATION_DETAIL, 
    		HttpStatus.OK, conversationDTO))
				.orElseThrow(() -> { throw new ConversationNotFoundException(); });
		
		
    }
	
	
	/**
	 * Get Conversation Messages By Kid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/self/{kid}/messages", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_CONVERSATION_MESSAGES", 
    	nickname = "GET_CONVERSATION_MESSAGES", notes = "Get Conversation Messages",
    	response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<MessageDTO>>> getConversationMessagesByKid(
    		@ApiParam(hidden = true) @CurrentUser 
				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name= "kid", value = "Kid Identifier", 
    				required = true)
				@Valid @KidShouldExists(message = "{kid.id.notvalid}")
		 		@PathVariable String kid) throws Throwable {
		
		logger.debug("Get Conversation Messages for kid -> " + kid );
		// Get Conversation Messages
		final Iterable<MessageDTO> messageList = conversationService
				.getConversationMessagesByKidIdAndGuardianId(new ObjectId(kid),
						selfGuardian.getUserId());
		
		if(Iterables.size(messageList) == 0)
			throw new NoMessagesFoundException();
		
		// Create and Send response
		return ApiHelper.<Iterable<MessageDTO>>createAndSendResponse(ConversationResponseEnum.CONVERSATION_SUCCESSFULLY_DELETED, 
			    		HttpStatus.OK, messageList);
		
		
    }
	
	
	/**
	 * Delete Conversation by kid
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/self/{kid}", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_CONVERSATION", 
    	nickname = "DELETE_CONVERSATION", notes = "Delete Conversation",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteConversation(
    		@ApiParam(hidden = true) @CurrentUser 
				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name= "kid", value = "kid Identifier", 
    				required = true)
				@Valid @KidShouldExists(message = "{kid.id.notvalid}")
		 			@PathVariable String kid) throws Throwable {
		
		logger.debug("Delete Conversation by kid -> " + kid);
		
		// Delete Conversation By Kid and Guardian Id
		conversationService.deleteByKidIdAndGuardianId(new ObjectId(kid),
				selfGuardian.getUserId());
		// Create and Send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.CONVERSATION_SUCCESSFULLY_DELETED, 
	    		HttpStatus.OK, this.messageSourceResolver.resolver("conversation.deleted"));
		
		
    }
	
	
	/**
	 * Delete All Conversation Messages by kid 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/self/{kid}/messages", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_CONVERSATION_MESSAGES", 
    	nickname = "DELETE_CONVERSATION_MESSAGES", notes = "Delete Conversation Messages",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteConversationMessagesByKid(
    		@ApiParam(hidden = true) @CurrentUser 
				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name= "kid", value = "kid Identifier", 
    				required = true)
				@Valid @KidShouldExists(message = "{kid.id.notvalid}")
		 		@PathVariable String kid) throws Throwable {

		logger.debug("Delete all messages for kid -> " + kid );
		
		// Delete All Conversation Messages
		conversationService.deleteAllConversationMessagesByKidIdAndGuardianId(new ObjectId(kid),
				selfGuardian.getUserId());
		
		// Create and Send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.ALL_CONVERSATION_MESSAGES_SUCCESSFULLY_DELETED, 
			HttpStatus.OK, this.messageSourceResolver.resolver("all.conversation.messages.deleted"));
		
    }
	
	
	/**
	 * Add Message
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/self/{kid}/messages", method = RequestMethod.POST)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ADD_MESSAGE", 
    	nickname = "ADD_MESSAGE", notes = "Add Message",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> addMessage(
    		@ApiParam(hidden = true) @CurrentUser 
				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name= "kid", value = "kid Identifier", 
    				required = true)
				@Valid @KidShouldExists(message = "{kid.id.notvalid}")
		 		@PathVariable String kid,
		 	@ApiParam(value = "message", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody final AddMessageDTO message) throws Throwable {
		
		return null;
		
    }
	
	/**
	 * 
	 */
	@PostConstruct
	protected void init() {
		Assert.notNull(conversationService, "Conversation Service can not be null");
	}

}
