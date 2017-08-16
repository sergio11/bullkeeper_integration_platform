package sanchez.sanchez.sergio.rest.controller.error;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.CommentNotFoundException;
import sanchez.sanchez.sergio.rest.exception.NoCommentsFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.service.IMessageSourceResolver;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentsErrorController {
	
	private static Logger logger = LoggerFactory.getLogger(CommentsErrorController.class);
	
	private final IMessageSourceResolver messageSourceResolver;

    public CommentsErrorController(IMessageSourceResolver messageSourceResolver) {
        this.messageSourceResolver = messageSourceResolver;
    }

    @ExceptionHandler(NoCommentsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsFoundException(NoCommentsFoundException noCommentsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(CommentResponseCode.COMMENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comments.not.found"));
    }
    
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentNotFoundException(CommentNotFoundException commentNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(CommentResponseCode.COMMENT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comment.not.found"));
    }
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
