package sanchez.sanchez.sergio.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.controller.BaseController;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.AuthenticationResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationErrorController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(AuthenticationErrorController.class);


    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleBadCredentialsException(BadCredentialsException badCredentialsException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(AuthenticationResponseCode.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST, 
        		messageSourceResolver.resolver("bad.credentials"));
    }
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source can not be null");
    }
}
