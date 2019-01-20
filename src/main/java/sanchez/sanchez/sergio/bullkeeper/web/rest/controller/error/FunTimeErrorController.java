package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import sanchez.sanchez.sergio.bullkeeper.exception.FunTimeDayScheduledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.FunTimeScheduledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ChildrenResponseCode;

/**
 * Terminal Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FunTimeErrorController  extends BaseController {
	
	
	/**
     * Exception handler for a Fun Time Sheduled Not Found Exception
     * @param funTimeScheduledNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(FunTimeScheduledNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handlerFunTimeScheduledNotFoundException(
    		FunTimeScheduledNotFoundException funTimeScheduledNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.FUN_TIME_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.fun.time.scheduled.found"));
    }
 
    /**
     * Exception handler for a Fun Time Day Sheduled Not Found Exception
     * @param funTimeScheduledNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(FunTimeDayScheduledNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handlerFunTimeDayScheduledNotFoundException(
    		FunTimeDayScheduledNotFoundException funTimeDayScheduledNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.FUN_TIME_DAY_SCHEDULED_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("fun.time.day.scheduled.not.found"));
    }
    

    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
