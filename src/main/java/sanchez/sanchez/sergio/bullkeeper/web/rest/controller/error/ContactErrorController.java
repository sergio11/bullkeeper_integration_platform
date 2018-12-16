package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.exception.ContactNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoContactsFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ContactResponseCode;

/**
 * Contact Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContactErrorController extends BaseController {
	
	/**
	 * Exception Handler for contact not found exception
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(ContactNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleContactNotFoundException(ContactNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ContactResponseCode.CONTACT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("contact.not.found"));
    }
    
    /**
     * Exception Handler for no contacts found exception
     * @param resourceNotFound
     * @param request
     * @return
     */
    @ExceptionHandler(NoContactsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoContactsFoundException(NoContactsFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ContactResponseCode.NO_CONTACTS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.contacts.found"));
    }
    
   
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
 
}
