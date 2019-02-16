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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IConversationService;
import sanchez.sanchez.sergio.bullkeeper.exception.ConversationNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoConversationFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoMessagesFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ConversationShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.bullkeeper.util.ValidList;
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
			+ "&& @authorizationService.isMemberOfTheConversation(#id) )")
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
			+ "&& @authorizationService.isMemberOfTheConversation(#id) )")
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
			+ "&& @authorizationService.isMemberOfTheConversation(#id) )")
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
		
		conversationService.markMessagesAsViewed(Iterables.<MessageDTO, ObjectId>transform(messageList, new Function<MessageDTO, ObjectId>() {
			@Override
			public ObjectId apply(MessageDTO message) {
				return new ObjectId(message.getIdentity());
			}
		}));
		
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
			+ "&& @authorizationService.isMemberOfTheConversation(#id) )")
    @ApiOperation(value = "DELETE_CONVERSATION_MESSAGES", 
    	nickname = "DELETE_CONVERSATION_MESSAGES", notes = "Delete Conversation Messages",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteConversationMessage(
    		@ApiParam(name= "id", value = "Conversation Identifier", 
    				required = true)
				@Valid @ConversationShouldExists(message = "{guardian.id.notvalid}")
		 		@PathVariable String id,
		 	@ApiParam(value = "messages", required = false) 
				@RequestParam(name="messages" , required=false)
				 	final ValidList<ObjectId> messagesIds) throws Throwable {
		
		logger.debug("Delete all messages for conversation -> " + id );
		
		if(messagesIds != null && 
				!messagesIds.isEmpty())
			conversationService.deleteConversationMessages(new ObjectId(id), messagesIds);
		else
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
			+ "&& @authorizationService.isMemberOfTheConversation(#id) )")
    @ApiOperation(value = "ADD_MESSAGE", 
    	nickname = "ADD_MESSAGE", notes = "Add Message",
    	response = MessageDTO.class)
    public ResponseEntity<APIResponse<MessageDTO>> addMessage(
    		@ApiParam(name= "id", value = "Conversation Identifier", 
    				required = true)
				@Valid @ConversationShouldExists(message = "{conversation.id.notvalid}")
		 		@PathVariable String id,
		 	@ApiParam(value = "message", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody final AddMessageDTO message) throws Throwable {
		
		logger.debug("Add Message to conversation with id -> " + id);
		
		// Save Message
		final MessageDTO messageDTO = 
				conversationService.saveMessage(new ObjectId(id), message);
		
		// Create and Send response
		return ApiHelper.<MessageDTO>createAndSendResponse(ConversationResponseEnum.MESSAGE_SUCCESSFULLY_CREATED, 
					HttpStatus.OK, messageDTO);
				
    }
	

	/**
	 * Get All Conversation for authenticated user
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/members/self", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() )")
    @ApiOperation(value = "GET_ALL_CONVERSATION_FOR_SELF_USER", 
    	nickname = "GET_ALL_CONVERSATION_FOR_SELF_USER", notes = "Get All Conversation for self user",
    	response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<ConversationDTO>>> getAllConversationForSelfUser(
    		@ApiParam(hidden = true) @CurrentUser 
				final CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
		
		
		// Get All Conversation for self user id
		final Iterable<ConversationDTO> conversationList = conversationService
				.getConversationsByMemberId(selfGuardian.getUserId());
		
		// check if empty
		if(Iterables.size(conversationList) == 0)
			throw new NoConversationFoundException();
		
		// Create and send response
		return ApiHelper.<Iterable<ConversationDTO>>createAndSendResponse(ConversationResponseEnum.ALL_SELF_CONVERSATIONS, 
	    		HttpStatus.OK, conversationList);
	
    }
	
	/**
	 * Delete All Conversation for self user
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/members/self", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() )")
    @ApiOperation(value = "DELETE_ALL_CONVERSATION_FOR_SELF_USER", 
    	nickname = "DELETE_ALL_CONVERSATION_FOR_SELF_USER", notes = "Delete All Conversation for self user",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAllConversationForSelfUser(
    		@ApiParam(hidden = true) @CurrentUser 
				final CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
		
		
		// Delete All Conversation for self user id
		conversationService
				.deleteConversationsByMemberId(selfGuardian.getUserId());

		// Create and send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.ALL_CONVERSATION_FOR_SELF_USER_DELETED, 
	    		HttpStatus.OK, messageSourceResolver.resolver("all.conversations.for.self.user.deleted"));
	
    }
	
	
	/**
	 * Get All Conversation for authenticated user
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/members/{member}", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
			+ "&& @authorizationService.isYourGuardianAndCanEditKidInformation(#member) )")
    @ApiOperation(value = "GET_ALL_CONVERSATIONS_FOR_MEMBER", 
    	nickname = "GET_ALL_CONVERSATIONS_FOR_MEMBER", notes = "Get All Conversation for member",
    	response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<ConversationDTO>>> getAllConversationsForMember(
    		@ApiParam(name= "member", value = "Member Identifier", required = true)
				@Valid @ValidObjectId(message = "{no.valid.object.id}")
		 			@PathVariable String member) throws Throwable {
	
		
		// Get All Conversation for member
		final Iterable<ConversationDTO> conversationList = conversationService
				.getConversationsByMemberId(new ObjectId(member));
		
		// check if empty
		if(Iterables.size(conversationList) == 0)
			throw new NoConversationFoundException();
		
		// Create and send response
		return ApiHelper.<Iterable<ConversationDTO>>createAndSendResponse(ConversationResponseEnum.ALL_SELF_CONVERSATIONS, 
	    		HttpStatus.OK, conversationList);
	
    }
	
	/**
	 * Delete All Conversation for member
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/members/{member}", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() )")
    @ApiOperation(value = "DELETE_ALL_CONVERSATION_FOR_MEMBER", 
    	nickname = "DELETE_ALL_CONVERSATION_FOR_MEMBER", notes = "Delete All Conversation for Member",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAllConversationForMember(
    		@ApiParam(name= "member", value = "Member Identifier", required = true)
			@Valid @ValidObjectId(message = "{no.valid.object.id}")
	 			@PathVariable String member) throws Throwable {
		
		
		// Delete All Conversation for member
		conversationService
				.deleteConversationsByMemberId(new ObjectId(member));

		// Create and send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.ALL_CONVERSATION_FOR_MEMBER_DELETED, 
	    		HttpStatus.OK, messageSourceResolver.resolver("all.conversations.for.member.deleted"));
	
    }
	
	/**
	 * Get Conversation detail
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	@RequestMapping(value = "/members/{memberOne}/{memberTwo}", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.checkIfTheyCanTalk(#memberOne, #memberTwo) )")
    @ApiOperation(value = "GET_CONVERSATION_DETAIL", 
    	nickname = "GET_CONVERSATION_DETAIL", notes = "Get Conversation Detail",
    	response = ConversationDTO.class)
    public ResponseEntity<APIResponse<ConversationDTO>> getConversationDetailForMembers(
    		@ApiParam(name= "memberOne", value = "Member One Identifier", 
				required = true)
    		@Valid @ValidObjectId(message = "{no.valid.object.id}")
 				@PathVariable String memberOne,
    		@ApiParam(name= "memberTwo", value = "Member Two Identifier", 
    				required = true)
				@Valid @ValidObjectId(message = "{no.valid.object.id}")
		 			@PathVariable String memberTwo) throws Throwable {

		// Get Conversation For Members
		return Optional.ofNullable(conversationService
				.getConversationForMembers(new ObjectId(memberOne), new ObjectId(memberTwo)))
				.map(conversationDTO -> ApiHelper.<ConversationDTO>createAndSendResponse(ConversationResponseEnum.CONVERSATION_DETAIL, 
    		HttpStatus.OK, conversationDTO))
				.orElseThrow(() -> { throw new ConversationNotFoundException(); });

    }
	
	
	/**
	 * Create Conversation
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	@RequestMapping(value = "/members/{memberOne}/{memberTwo}", method = RequestMethod.POST)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.checkIfTheyCanTalk(#memberOne, #memberTwo) )")
    @ApiOperation(value = "CREATE_CONVERSATION_FOR_MEMBERS", 
    	nickname = "CREATE_CONVERSATION_FOR_MEMBERS", notes = "Create Conversation for members",
    	response = ConversationDTO.class)
    public ResponseEntity<APIResponse<ConversationDTO>> createConversationForMembers(
    		@ApiParam(name= "memberOne", value = "Member One Identifier", 
				required = true)
    		@Valid @ValidObjectId(message = "{no.valid.object.id}")
 				@PathVariable String memberOne,
    		@ApiParam(name= "memberTwo", value = "Member Two Identifier", 
    				required = true)
				@Valid @ValidObjectId(message = "{no.valid.object.id}")
		 			@PathVariable String memberTwo) throws Throwable {
		
		// Find Conversation To Delete
		final ConversationDTO conversationSavedDTO = Optional.ofNullable(conversationService.getConversationForMembers(
						new ObjectId(memberOne), new ObjectId(memberTwo)))
				.orElseGet(() -> conversationService.createConversation(
						new ObjectId(memberOne), new ObjectId(memberTwo)));

		// Create And Send Response
		return ApiHelper.<ConversationDTO>createAndSendResponse(ConversationResponseEnum.CONVERSATION_DETAIL, 
	    		HttpStatus.OK, conversationSavedDTO);

    }
	
	/**
	 * Delete Conversation For Members
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	@RequestMapping(value = "/members/{memberOne}/{memberTwo}", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.checkIfTheyCanTalk(#memberOne, #memberTwo) )")
    @ApiOperation(value = "DELETE_CONVERSATION_FOR_MEMBERS", 
    	nickname = "DELETE_CONVERSATION_FOR_MEMBERS", notes = "Delete Conversation For Members",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteConversationForMembers(
    		@ApiParam(name= "memberOne", value = "Member One Identifier", 
				required = true)
    		@Valid @ValidObjectId(message = "{no.valid.object.id}")
				@PathVariable String memberOne,
    		@ApiParam(name= "memberTwo", value = "Member Two Identifier", 
    				required = true)
				@Valid @ValidObjectId(message = "{no.valid.object.id}")
		 			@PathVariable String memberTwo) throws Throwable {

		// Find Conversation To Delete
		final ConversationDTO conversationToDelete = Optional.ofNullable(conversationService.getConversationForMembers(
				new ObjectId(memberOne), new ObjectId(memberTwo)))
			.orElseThrow(() -> { throw new ConversationNotFoundException(); });
		
		// Delete Conversation
		conversationService.delete(new ObjectId(conversationToDelete.getIdentity()));
		
		// Create and Send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.CONVERSATION_SUCCESSFULLY_DELETED, 
	    		HttpStatus.OK, this.messageSourceResolver.resolver("conversation.deleted"));
	
    }
	
	
	/**
	 * Get Conversation Messages For Members
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	@RequestMapping(value = "/members/{memberOne}/{memberTwo}/messages", method = RequestMethod.GET)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.checkIfTheyCanTalk(#memberOne, #memberTwo) )")
    @ApiOperation(value = "GET_CONVERSATION_MESSAGES_FOR_MEMBERS", 
    	nickname = "GET_CONVERSATION_MESSAGES_FOR_MEMBERS", notes = "Get Conversation Messages For Members",
    	response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<MessageDTO>>> getConversationMessagesForMembers(
    		@ApiParam(name= "memberOne", value = "Member One Identifier", 
				required = true)
    		@Valid @ValidObjectId(message = "{no.valid.object.id}")
				@PathVariable String memberOne,
    		@ApiParam(name= "memberTwo", value = "Member Two Identifier", 
    				required = true)
				@Valid @ValidObjectId(message = "{no.valid.object.id}")
		 		@PathVariable String memberTwo) throws Throwable {
		
		// Find Conversation
		final ConversationDTO conversationDTO = Optional.ofNullable(conversationService.getConversationForMembers(
						new ObjectId(memberOne), new ObjectId(memberTwo)))
					.orElseThrow(() -> { throw new ConversationNotFoundException(); });
		
		// Get Conversation Messages
		final Iterable<MessageDTO> messageList = conversationService
				.getConversationMessages(new ObjectId(conversationDTO.getIdentity()));
		
		if(Iterables.size(messageList) == 0)
			throw new NoMessagesFoundException();
		
		conversationService.markMessagesAsViewed(Iterables.<MessageDTO, ObjectId>transform(messageList, new Function<MessageDTO, ObjectId>() {
			@Override
			public ObjectId apply(MessageDTO message) {
				return new ObjectId(message.getIdentity());
			}
		}));
		
		// Create and Send response
		return ApiHelper.<Iterable<MessageDTO>>createAndSendResponse(ConversationResponseEnum.ALL_CONVERSATION_MESSAGES, 
			    		HttpStatus.OK, messageList);
		
		
    }
	
	/**
	 * Delete Conversation Messages for members
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	@RequestMapping(value = "/members/{memberOne}/{memberTwo}/messages", method = RequestMethod.DELETE)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.checkIfTheyCanTalk(#memberOne, #memberTwo) )")
    @ApiOperation(value = "DELETE_CONVERSATION_MESSAGES", 
    	nickname = "DELETE_CONVERSATION_MESSAGES", notes = "Delete Conversation Messages",
    	response = String.class)
    public ResponseEntity<APIResponse<String>> deleteConversationsMessagesForMembers(
    		@ApiParam(name= "memberOne", value = "Member One Identifier", 
				required = true)
			@Valid @ValidObjectId(message = "{no.valid.object.id}")
				@PathVariable String memberOne,
    		@ApiParam(name= "memberTwo", value = "Member Two Identifier", 
    				required = true)
				@Valid @ValidObjectId(message = "{no.valid.object.id}")
		 		@PathVariable String memberTwo,
		 	@ApiParam(value = "messages", required = false) 
    			@RequestParam(name="messages" , required=false)
					 final ValidList<ObjectId> messagesIds) throws Throwable {

		// Find Conversation
		final ConversationDTO conversationDTO = Optional.ofNullable(conversationService.getConversationForMembers(
					new ObjectId(memberOne), new ObjectId(memberTwo)))
					.orElseThrow(() -> { throw new ConversationNotFoundException(); });
		
		if(messagesIds != null &&
				!messagesIds.isEmpty())
			conversationService.deleteConversationMessages(new ObjectId(conversationDTO.getIdentity()), messagesIds);
		else
			// Delete All Conversation Messages
			conversationService.deleteAllConversationMessages(new ObjectId(conversationDTO.getIdentity()));
		
		// Create and Send response
		return ApiHelper.<String>createAndSendResponse(ConversationResponseEnum.ALL_CONVERSATION_MESSAGES_SUCCESSFULLY_DELETED, 
			HttpStatus.OK, this.messageSourceResolver.resolver("all.conversation.messages.deleted"));
		
    }
	
	
	/**
	 * Add Message
	 * @param memberOne
	 * @param memberTwo
	 * @return
	 */
	@RequestMapping(value = "/members/{memberOne}/{memberTwo}/messages", method = RequestMethod.POST)
	@PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
			+ "&& @authorizationService.checkIfTheyCanTalk(#memberOne, #memberTwo) )")
    @ApiOperation(value = "ADD_MESSAGE", 
    	nickname = "ADD_MESSAGE", notes = "Add Message",
    	response = MessageDTO.class)
    public ResponseEntity<APIResponse<MessageDTO>> addMessage(
    		@ApiParam(name= "memberOne", value = "Member One Identifier", 
				required = true)
			@Valid @ValidObjectId(message = "{no.valid.object.id}")
				@PathVariable String memberOne,
    		@ApiParam(name= "memberTwo", value = "Member Two Identifier", 
    				required = true)
				@Valid @ValidObjectId(message = "{no.valid.object.id}")
		 		@PathVariable String memberTwo,
		 	@ApiParam(value = "message", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody final AddMessageDTO message) throws Throwable {
		
		// Find Conversation
		final ConversationDTO conversationDTO = Optional.ofNullable(conversationService.getConversationForMembers(
							new ObjectId(memberOne), new ObjectId(memberTwo)))
					.orElseThrow(() -> { throw new ConversationNotFoundException(); });
	
		
		// Save Message
		final MessageDTO messageDTO = 
				conversationService.saveMessage(new ObjectId(conversationDTO.getIdentity()),
						message);
				
		// Create and Send response
		return ApiHelper.<MessageDTO>createAndSendResponse(ConversationResponseEnum.MESSAGE_SUCCESSFULLY_CREATED, 
				HttpStatus.OK, messageDTO);
    }
	


	
	/**
	 * 
	 */
	@PostConstruct
	protected void init() {
		Assert.notNull(conversationService, "Conversation Service can not be null");
	}

}
