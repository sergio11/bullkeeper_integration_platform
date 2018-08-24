package sanchez.sanchez.sergio.masoc.web.rest.controller.error;

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

import sanchez.sanchez.sergio.masoc.exception.EmailAlreadyExistsException;
import sanchez.sanchez.sergio.masoc.exception.GetInformationFromFacebookException;
import sanchez.sanchez.sergio.masoc.exception.GetInformationFromGoogleException;
import sanchez.sanchez.sergio.masoc.exception.InvalidFacebookIdException;
import sanchez.sanchez.sergio.masoc.exception.NoChildrenFoundForParentException;
import sanchez.sanchez.sergio.masoc.exception.NoChildrenFoundForSelfParentException;
import sanchez.sanchez.sergio.masoc.exception.NoParentsFoundException;
import sanchez.sanchez.sergio.masoc.exception.ParentNotFoundException;
import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.ParentResponseCode;

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
    
    @ExceptionHandler(GetInformationFromGoogleException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleGetInformationFromGoogleException(GetInformationFromGoogleException getInformationFromGoogleException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.PARENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parent.get.information.from.google.failed"));
    }
    
    
    @ExceptionHandler(InvalidFacebookIdException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleInvalidFacebookIdException(InvalidFacebookIdException invalidFacebookIdException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.INVALID_FACEBOOK_ID, HttpStatus.BAD_REQUEST,
    			messageSourceResolver.resolver("parent.invalid.facebook.id"));
    }
    
    
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleEmailAlreadyExistsException(EmailAlreadyExistsException emailAlreadyExistsException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponseWithHeader(ParentResponseCode.EMAIL_ALREADY_EXISTS, HttpStatus.NOT_FOUND,
    			messageSourceResolver.resolver("parent.email.already.exists"));
    }
    
    
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
