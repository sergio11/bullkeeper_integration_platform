package sanchez.sanchez.sergio.masoc.web.rest.controller.error;


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

import sanchez.sanchez.sergio.masoc.exception.CommentNotFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoActiveFriendsInThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoCommentsExtractedException;
import sanchez.sanchez.sergio.masoc.exception.NoCommentsFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoLikesFoundInThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoNewFriendsAtThisTimeException;
import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.CommentResponseCode;

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
    
    /**
     * No Comments extracted exception
     * @param noCommentsExtractedException
     * @param request
     * @return
     */
    @ExceptionHandler(NoCommentsExtractedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsExtractedException(NoCommentsExtractedException noCommentsExtractedException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.NO_COMMENTS_EXTRACTED, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.comments.extracted", new Object[] { prettyTime.format(noCommentsExtractedException.getFrom()) }));
    }
    
    /**
     * No Likes found in this period exception
     * @param noLikesFoundInThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoLikesFoundInThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoLikesFoundInThisPeriodException(NoLikesFoundInThisPeriodException noLikesFoundInThisPeriodException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.NO_LIKES_FOUND_IN_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.likes.found.in.this.period", new Object[] { prettyTime.format(noLikesFoundInThisPeriodException.getFrom()) }));
    }
    
    /**
     * Exception Handler for No Active friends in this period exception
     * @param noActiveFriedsInThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoActiveFriendsInThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoActiveFriendsInThisPeriodException(NoActiveFriendsInThisPeriodException noActiveFriedsInThisPeriodException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.NO_ACTIVE_FRIENDS_IN_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.active.friends.in.this.period", new Object[] { prettyTime.format(noActiveFriedsInThisPeriodException.getFrom()) }));
    }
    
    /**
     * Exception Handler for no new friends at this time exception
     * @param noNewFriendsAtThisTimeException
     * @param request
     * @return
     */
    @ExceptionHandler(NoNewFriendsAtThisTimeException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoNewFriendsAtThisTimeException(NoNewFriendsAtThisTimeException noNewFriendsAtThisTimeException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.NO_NEW_FRIENDS_AT_THIS_TIME, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.active.friends.in.this.period", new Object[] { prettyTime.format(noNewFriendsAtThisTimeException.getFrom()) }));
    }
   
  
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
