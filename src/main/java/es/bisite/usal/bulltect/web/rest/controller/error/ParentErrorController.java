package es.bisite.usal.bulltect.web.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.controller.BaseController;
import es.bisite.usal.bulltect.web.rest.exception.GetInformationFromFacebookException;
import es.bisite.usal.bulltect.web.rest.exception.InvalidFacebookIdException;
import es.bisite.usal.bulltect.web.rest.exception.NoChildrenFoundForParentException;
import es.bisite.usal.bulltect.web.rest.exception.NoChildrenFoundForSelfParentException;
import es.bisite.usal.bulltect.web.rest.exception.NoCommentsBySonFoundForLastIterationException;
import es.bisite.usal.bulltect.web.rest.exception.NoIterationsFoundForSelfParentException;
import es.bisite.usal.bulltect.web.rest.exception.NoParentsFoundException;
import es.bisite.usal.bulltect.web.rest.exception.ParentNotFoundException;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.ParentResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ParentErrorController extends BaseController{
	
    private static Logger logger = LoggerFactory.getLogger(ParentErrorController.class);
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleBadCredentialsException(BadCredentialsException badCredentialsException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST, 
        		messageSourceResolver.resolver("bad.credentials"));
    }
    
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleBadCredentialsException(UsernameNotFoundException usernameNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.USERNAME_NOT_FOUND, HttpStatus.BAD_REQUEST, 
        		messageSourceResolver.resolver("username.not.found"));
    }
   
    
    @ExceptionHandler(DisabledException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleDisabledException(DisabledException disabledException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.ACCOUNT_DISABLED, HttpStatus.FORBIDDEN, 
        		messageSourceResolver.resolver("account.disabled"));
    }

    @ExceptionHandler(ParentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleParentNotFoundException(ParentNotFoundException parentNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.PARENT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("parent.not.found"));
    }
    
    @ExceptionHandler(NoChildrenFoundForSelfParentException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForSelfParentException(NoChildrenFoundForSelfParentException noChildrenFoundForSelfParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.NO_CHILDREN_FOUND_FOR_SELF_PARENT, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("self.parent.not.children.found"));
    }
    
    @ExceptionHandler(NoChildrenFoundForParentException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForParentException(NoChildrenFoundForParentException noChildrenFoundForParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.NO_CHILDREN_FOUND_FOR_PARENT, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parent.not.children.found"));
    }
    
    @ExceptionHandler(NoParentsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoParentsFoundException(NoParentsFoundException noParentsFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.PARENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parents.not.found"));
    }
    
    @ExceptionHandler(GetInformationFromFacebookException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleGetInformationFromFacebookException(GetInformationFromFacebookException getInformationFromFacebookException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.PARENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parent.get.information.from.facebook.failed"));
    }
    
    @ExceptionHandler(InvalidFacebookIdException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleInvalidFacebookIdException(InvalidFacebookIdException invalidFacebookIdException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.INVALID_FACEBOOK_ID, HttpStatus.BAD_REQUEST,
    			messageSourceResolver.resolver("parent.invalid.facebook.id"));
    }
    
    @ExceptionHandler(NoIterationsFoundForSelfParentException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoIterationsFoundForSelfParentException(NoIterationsFoundForSelfParentException noIterationsFoundForSelfParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.NO_ITERATIONS_FOUND_FOR_SELF_PARENT, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parent.no.iterations.found"));
    }
    
    @ExceptionHandler(NoCommentsBySonFoundForLastIterationException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsBySonFoundForLastIterationException(NoCommentsBySonFoundForLastIterationException noCommentsBySonFoundForLastIterationException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.NO_COMMENTS_BY_SON_FOUND_FOR_LAST_ITERATION, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("no.comments.by.son.found.for.last.iteration"));
    }
    
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
