package sanchez.sanchez.sergio.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.CreateAlertFailedException;
import sanchez.sanchez.sergio.rest.exception.NoAlertsFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.AlertResponseCode;
import sanchez.sanchez.sergio.service.IMessageSourceResolver;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AlertsErrorController {
	
	private static Logger logger = LoggerFactory.getLogger(AlertsErrorController.class);
	
	private final IMessageSourceResolver messageSourceResolver;

    public AlertsErrorController(IMessageSourceResolver messageSourceResolver) {
    	this.messageSourceResolver = messageSourceResolver;
    }

    @ExceptionHandler(NoAlertsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAlertsFoundException(NoAlertsFoundException noAlertsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(AlertResponseCode.NO_ALERTS_FOUND, HttpStatus.NOT_FOUND, 
        		messageSourceResolver.resolver("alerts.not.found"));
    }
    
    @ExceptionHandler(CreateAlertFailedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCreateAlertFailedException(CreateAlertFailedException createAlertFailedException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(AlertResponseCode.CREATE_ALERT_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, 
        		messageSourceResolver.resolver("alert.create.failed"));
    }
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source can not be null");
    }
}
