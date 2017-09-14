package es.bisite.usal.bullytect.rest.controller.error;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import es.bisite.usal.bullytect.rest.ApiHelper;
import es.bisite.usal.bullytect.rest.controller.BaseController;
import es.bisite.usal.bullytect.rest.exception.CommentNotFoundException;
import es.bisite.usal.bullytect.rest.exception.NoCommentsFoundException;
import es.bisite.usal.bullytect.rest.response.APIResponse;
import es.bisite.usal.bullytect.rest.response.CommentResponseCode;
import es.bisite.usal.bullytect.service.IMessageSourceResolver;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentsErrorController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(CommentsErrorController.class);
	
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
