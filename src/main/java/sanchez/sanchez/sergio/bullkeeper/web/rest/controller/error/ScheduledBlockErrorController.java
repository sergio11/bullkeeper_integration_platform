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
import sanchez.sanchez.sergio.bullkeeper.exception.NoScheduledBlockFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.ScheduledBlockNotValidException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ChildrenResponseCode;


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
    protected ResponseEntity<APIResponse<String>> handleNoScheduledBlockFoundException(
    		final NoScheduledBlockFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_SCHEDULED_BLOCKS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("scheduled.block.not.found"));
    }
    
    /**
	 * Exception Handler for Scheduled Block Not Valid Exception
	 * @param exception
	 * @param request
	 * @return
	 */
    @ExceptionHandler(ScheduledBlockNotValidException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleScheduledBlockNotValidException(
    		final ScheduledBlockNotValidException exception, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.SCHEDULED_BLOCK_NOT_VALID, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("scheduled.block.not.valid"));
    }
    
    /**
     * 
     */
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
}
