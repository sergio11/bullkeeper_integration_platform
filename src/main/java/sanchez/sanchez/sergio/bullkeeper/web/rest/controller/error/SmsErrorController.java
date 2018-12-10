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
import sanchez.sanchez.sergio.bullkeeper.exception.NoSmsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SingleSmsNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.SmsResponseCode;

/**
 * SMS  Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SmsErrorController extends BaseController {
	
	/**
	 * Exception Handler for no sms found exception
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(NoSmsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSmsFoundException(NoSmsFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(SmsResponseCode.NO_SMS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("sms.not.found"));
    }
    
    /**
     * Exception Handler for Single Sms Not Found Exception
     * @param resourceNotFound
     * @param request
     * @return
     */
    @ExceptionHandler(SingleSmsNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSingleSmsNotFoundException(SingleSmsNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(SmsResponseCode.SINGLE_SMS_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("single.sms.not.found"));
    }
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
 
}
