package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sanchez.sanchez.sergio.bullkeeper.exception.ConversationMemberNotExistsException;
import sanchez.sanchez.sergio.bullkeeper.exception.ConversationNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.MessageNotAllowedException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoConversationFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoMessagesFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ConversationResponseEnum;



/**
 * Conversation Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConversationErrorController extends BaseController {
	
	
	private static Logger logger = LoggerFactory.getLogger(ConversationErrorController.class);

	
	/**
     * 
     * @param noConversationFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoConversationFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoConversationFoundException(NoConversationFoundException noConversationFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ConversationResponseEnum.NO_CONVERSATIONS_FOUND, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("no.conversations.found"));
    }
    
    /**
     * 
     * @param conversationNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(ConversationNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleConversationNotFoundException(ConversationNotFoundException conversationNotFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ConversationResponseEnum.CONVERSATION_NOT_FOUND_EXCEPTION, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("conversation.not.found"));
    }
    
    /**
     * 
     * @param noMessagesFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoMessagesFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoMessagesFoundException(NoMessagesFoundException noMessagesFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ConversationResponseEnum.NO_MESSAGES_FOUND, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("no.messages.found.exception"));
    }
    
    /**
     * 
     * @param messageNotAllowedException
     * @param request
     * @return
     */
    @ExceptionHandler(MessageNotAllowedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleMessageNotAllowedException(MessageNotAllowedException messageNotAllowedException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ConversationResponseEnum.MESSAGE_NOT_ALLOWED, 
    			HttpStatus.BAD_REQUEST, messageSourceResolver.resolver("message.not.allowed"));
    }
    
    /**
     * @param conversationMemberNotExistsException
     * @param request
     * @return
     */
    @ExceptionHandler(ConversationMemberNotExistsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleConversationMemberNotExistsException(ConversationMemberNotExistsException conversationMemberNotExistsException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ConversationResponseEnum.CONVERSATION_MEMBER_NOT_FOUND, 
    			HttpStatus.NOT_FOUND, messageSourceResolver.resolver("conversation.member.not.exists"));
    }
    
}
