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
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommentsErrorController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(CommentsErrorController.class);
	
    @ExceptionHandler(NoCommentsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsFoundException(NoCommentsFoundException noCommentsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.COMMENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comments.not.found"));
    }
    
    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentNotFoundException(CommentNotFoundException commentNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.COMMENT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comment.not.found"));
    }
    
    
    @ExceptionHandler(NoCommentsExtractedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsExtractedException(NoCommentsExtractedException noCommentsExtractedException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.NO_COMMENTS_EXTRACTED, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.comments.extracted", new Object[] { prettyTime.format(noCommentsExtractedException.getFrom()) }));
    }
    
    
    @ExceptionHandler(NoLikesFoundInThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoLikesFoundInThisPeriodException(NoLikesFoundInThisPeriodException noLikesFoundInThisPeriodException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.NO_LIKES_FOUND_IN_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.likes.found.in.this.period", new Object[] { prettyTime.format(noLikesFoundInThisPeriodException.getFrom()) }));
    }
    
    @ExceptionHandler(NoActiveFriendsInThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoActiveFriendsInThisPeriodException(NoActiveFriendsInThisPeriodException noActiveFriedsInThisPeriodException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.NO_ACTIVE_FRIENDS_IN_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.active.friends.in.this.period", new Object[] { prettyTime.format(noActiveFriedsInThisPeriodException.getFrom()) }));
    }
    
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
