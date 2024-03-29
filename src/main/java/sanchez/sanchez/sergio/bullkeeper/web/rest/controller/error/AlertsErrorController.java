package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;

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
import sanchez.sanchez.sergio.bullkeeper.exception.CreateAlertFailedException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoNewAlertsFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.AlertResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AlertsErrorController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(AlertsErrorController.class);
	

    /**
     * 
     * @param noAlertsFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoAlertsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAlertsFoundException(NoAlertsFoundException noAlertsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AlertResponseCode.NO_ALERTS_FOUND, HttpStatus.NOT_FOUND, 
        		messageSourceResolver.resolver("alerts.not.found"));
    }
    
    /**
     * 
     * @param createAlertFailedException
     * @param request
     * @return
     */
    @ExceptionHandler(CreateAlertFailedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCreateAlertFailedException(CreateAlertFailedException createAlertFailedException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AlertResponseCode.CREATE_ALERT_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, 
        		messageSourceResolver.resolver("alert.create.failed"));
    }
    
    /**
     * 
     * @param noNewAlertsFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoNewAlertsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoNewAlertsFoundException(NoNewAlertsFoundException noNewAlertsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AlertResponseCode.NO_NEW_ALERTS_FOUND, HttpStatus.NOT_FOUND, 
        		messageSourceResolver.resolver("no.new.alerts.found"));
    }
    
    /**
     * 
     * @param noAlertsStatisticsForThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoAlertsStatisticsForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAlertsStatisticsForThisPeriodException(NoAlertsStatisticsForThisPeriodException noAlertsStatisticsForThisPeriodException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AlertResponseCode.NO_ALERTS_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND, 
        		messageSourceResolver.resolver("no.alerts.statistics.for.this.period", new Object [] { prettyTime.format(noAlertsStatisticsForThisPeriodException.getFrom()) }));
    }
    
    /**
     * 
     */
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source can not be null");
    }
}
