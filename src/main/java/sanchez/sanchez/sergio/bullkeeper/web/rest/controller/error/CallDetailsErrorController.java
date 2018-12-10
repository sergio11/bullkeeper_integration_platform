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
import sanchez.sanchez.sergio.bullkeeper.exception.CallDetailNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CallDetailsNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.CallDetailResponseCode;

/**
 * Call Details Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CallDetailsErrorController extends BaseController {
	
	/**
	 * Exception Handler for call details not found exception
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(CallDetailsNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCallDetailsNotFoundException(CallDetailsNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CallDetailResponseCode.NO_CALL_DETAILS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("call.details.not.found"));
    }
    
    /**
     * Exception Handler for call detail not found exception
     * @param resourceNotFound
     * @param request
     * @return
     */
    @ExceptionHandler(CallDetailNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCallDetailNotFoundException(CallDetailNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CallDetailResponseCode.NO_CALL_DETAIL_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("call.detail.not.found"));
    }
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
 
}
