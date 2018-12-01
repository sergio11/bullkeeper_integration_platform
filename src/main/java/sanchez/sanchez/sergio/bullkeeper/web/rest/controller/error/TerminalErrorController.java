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

import sanchez.sanchez.sergio.bullkeeper.exception.NoTerminalsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.TerminalNotFoundException;
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
public class TerminalErrorController  extends BaseController {
	
	
	/**
     * Exception handler for no terminals found exception
     * @param noTerminalsFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoTerminalsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handlerNoTerminalsFoundException(
    		NoTerminalsFoundException noTerminalsFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_TERMINALS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.no.terminals.found"));
    }
    
    
    /**
	 * Exception handler for Terminal not found exception
	 * @param socialMediaNotFoundException
	 * @param request
	 * @return
	 */
    @ExceptionHandler(TerminalNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleTerminalNotFoundException(TerminalNotFoundException terminalNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_TERMINAL_FOUND_EXCEPTION, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.no.terminal.found"));
    }

    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
