package es.bisite.usal.bullytect.rest.controller.error;

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

import es.bisite.usal.bullytect.rest.ApiHelper;
import es.bisite.usal.bullytect.rest.controller.BaseController;
import es.bisite.usal.bullytect.rest.exception.CreateAlertFailedException;
import es.bisite.usal.bullytect.rest.exception.NoAlertsFoundException;
import es.bisite.usal.bullytect.rest.response.APIResponse;
import es.bisite.usal.bullytect.rest.response.AlertResponseCode;
import io.jsonwebtoken.lang.Assert;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AlertsErrorController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(AlertsErrorController.class);
	

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
