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

import sanchez.sanchez.sergio.bullkeeper.exception.NoGeofenceAlertsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoGeofenceFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.GeofenceResponseCode;

/**
 * Geofence Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GeofenceErrorController  extends BaseController {
	
	
	/**
     * Exception handler for No Geofence Found Exception
     * @param noGeofenceFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoGeofenceFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handlerNoGeofenceFoundException(
    		NoGeofenceFoundException noGeofenceFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		GeofenceResponseCode.NO_GEOFENCES_FOUND_EXCEPTION, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.geofences.found.exception"));
    }
    
    /**
     * Exception handler for No Geofence Alerts Found Exception
     * @param noGeofenceAlertsFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoGeofenceAlertsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handlerNoGeofenceAlertsFoundException(
    		NoGeofenceAlertsFoundException noGeofenceAlertsFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		GeofenceResponseCode.NO_GEOFENCE_ALERTS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.geofences.alerts.found.exception"));
    }
    

    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
