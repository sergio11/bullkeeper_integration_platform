package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.exception.AlertNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CommentsByKidNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CurrentLocationException;
import sanchez.sanchez.sergio.bullkeeper.exception.KidNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.KidRequestNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsByKidFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoChildrenFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoKidRequestFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoPhoneNumberBlockedFound;
import sanchez.sanchez.sergio.bullkeeper.exception.PhoneNumberAlreadyBlockedException;
import sanchez.sanchez.sergio.bullkeeper.exception.PreviousRequestHasNotExpiredYetException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.CommentResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ChildrenErrorController extends BaseController {
	

	/**
	 * Exception Handler for Kid Not Found exception
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(KidNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleKidNotFoundException(KidNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("son.not.found"));
    }
    
    /**
     * Exception Handler for comments by kid not found exception
     * @param commentsByKidNotFound
     * @param request
     * @return
     */
    @ExceptionHandler(CommentsByKidNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentsByKidNotFoundException(
    		final CommentsByKidNotFoundException commentsByKidNotFound, final HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		CommentResponseCode.COMMENTS_BY_CHILD_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comments.by.son.not.found"));
    }
    
   
   
    /**
     * Exception handler for no children found exception
     * @param noChildrenFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoChildrenFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundException(
    		final NoChildrenFoundException noChildrenFoundException, 
    		final HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.NO_CHILDREN_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.not.found"));
    }
    
    /**
     * Exception handler for no alerts by son found exception
     * @param noAlertsBySonFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoAlertsByKidFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAlertsBySonFoundException(NoAlertsByKidFoundException noAlertsBySonFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.NO_ALERTS_BY_KID_FOUNDED, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("alerts.by.son.founded"));
    }
    
    /**
     * Exception handler for alert not found exception
     * @param alertNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(AlertNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleAlertNotFoundException(AlertNotFoundException alertNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.ALERT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("alert.not.found"));
    }
    
    /**
     * Exception Handler for Current Location Exception
     * @param currentLocationException
     * @param request
     * @return
     */
    @ExceptionHandler(CurrentLocationException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCurrentLocationException(CurrentLocationException currentLocationException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.NO_CURRENT_LOCATION_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.current.location.found"));
    }
    
    /**
     * Exception Handler for No Phone Number Blocked Found Exception
     * @param noPhoneNumberBlockedFound
     * @param request
     * @return
     */
    @ExceptionHandler(NoPhoneNumberBlockedFound.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoPhoneNumberBlockedFound(NoPhoneNumberBlockedFound noPhoneNumberBlockedFound, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.NO_PHONE_NUMBER_BLOCKED_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.phone.number.blocked.found"));
    } 
    
    /**
     * Exception Handler for Phone Number Already Blocked Exception
     * @param phoneNumberAlreadyBlockedException
     * @param request
     * @return
     */
    @ExceptionHandler(PhoneNumberAlreadyBlockedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handlePhoneNumberAlreadyBlockedException(PhoneNumberAlreadyBlockedException phoneNumberAlreadyBlockedException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.PHONE_NUMBER_ALREADY_BLOCKED, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("phone.number.already.blocked.exception"));
    } 
    
    /**
     * Exception Handler for Kid Request Not Found Exception
     * @param kidRequestNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(KidRequestNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleKidRequestNotFoundException(
    		final KidRequestNotFoundException kidRequestNotFoundException, final HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.KID_REQUEST_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("kid.request.not.found.exception"));
    } 
    
    /**
     * Exception Handler for No Kid Request Found Exception
     * @param noKidRequestFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoKidRequestFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoKidRequestFoundException(
    		final NoKidRequestFoundException noKidRequestFoundException, 
    		final HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.NO_KID_REQUEST_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.kid.request.found.exception"));
    } 
    
    
    
    
    /**
     * Exception Handler for Previous Request Has not expired yet exception
     * @param previousRequestHasNotExpiredYetException
     * @param request
     * @return
     */
    @ExceptionHandler(PreviousRequestHasNotExpiredYetException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handlePreviousRequestHasNotExpiredYetException(
    		final PreviousRequestHasNotExpiredYetException previousRequestHasNotExpiredYetException, final HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		ChildrenResponseCode.PREVIOUS_REQUEST_HAS_NOT_EXPIRED, HttpStatus.BAD_REQUEST,
        		messageSourceResolver.resolver("previous.request.has.not.expired.yet.exception"));
    } 
    
   
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
  
}
