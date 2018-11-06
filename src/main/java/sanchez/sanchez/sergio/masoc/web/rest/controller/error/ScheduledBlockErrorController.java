package sanchez.sanchez.sergio.masoc.web.rest.controller.error;

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
import sanchez.sanchez.sergio.masoc.exception.NoScheduledBlockFoundException;
import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.ChildrenResponseCode;


/**
 * Scheduled Block Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ScheduledBlockErrorController extends BaseController {

	
	/**
	 * Exception Handler for No Scheduled Block Found Exception
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(NoScheduledBlockFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoScheduledBlockFoundException(NoScheduledBlockFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_SCHEDULED_BLOCKS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("scheduled.block.not.found"));
    }
    
    /**
     * 
     */
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
}
