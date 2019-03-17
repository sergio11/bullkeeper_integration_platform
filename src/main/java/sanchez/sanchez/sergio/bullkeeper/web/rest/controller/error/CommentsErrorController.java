package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;


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
import sanchez.sanchez.sergio.bullkeeper.exception.CommentNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommentsFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.CommentResponseCode;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentsErrorController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(CommentsErrorController.class);
	
    /**
     * Exception Handler for No Comments Found Exception
     * @param noCommentsFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoCommentsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsFoundException(NoCommentsFoundException noCommentsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.COMMENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comments.not.found"));
    }
    
    /**
     * Exception Handler for Comment Not found exception
     * @param commentNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentNotFoundException(CommentNotFoundException commentNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.COMMENT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comment.not.found"));
    }
   
   
  
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
