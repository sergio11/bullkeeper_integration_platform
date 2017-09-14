package es.bisite.usal.bullytect.rest.controller.error;


import javax.annotation.PostConstruct;
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
import es.bisite.usal.bullytect.rest.exception.DeviceGroupCreateFailedException;
import es.bisite.usal.bullytect.rest.exception.NoDevicesIntoTheGroupException;
import es.bisite.usal.bullytect.rest.exception.RemoveDeviceFromGroupFailedException;
import es.bisite.usal.bullytect.rest.exception.UpdateDeviceFailedException;
import es.bisite.usal.bullytect.rest.response.APIResponse;
import es.bisite.usal.bullytect.rest.response.DeviceGroupResponseCode;
import io.jsonwebtoken.lang.Assert;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DeviceGroupErrorController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(DeviceGroupErrorController.class);
	

    @ExceptionHandler(NoDevicesIntoTheGroupException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoDevicesIntoTheGroupException(NoDevicesIntoTheGroupException noDevicesIntoTheGroupException) {
        return ApiHelper.<String>createAndSendErrorResponse(DeviceGroupResponseCode.NO_DEVICES_INTO_GROUP, 
        		HttpStatus.NOT_FOUND, messageSourceResolver.resolver("devices.not.found"));
    }
    
    @ExceptionHandler(DeviceGroupCreateFailedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleDeviceGroupCreateFailedException(DeviceGroupCreateFailedException deviceGroupCreateFailedException) {
        return ApiHelper.<String>createAndSendErrorResponse(DeviceGroupResponseCode.DEVICES_GROUP_CREATE_FAILED, 
        		HttpStatus.INTERNAL_SERVER_ERROR, messageSourceResolver.resolver("devices.group.create.failed"));
    }
    
    @ExceptionHandler(RemoveDeviceFromGroupFailedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleRemoveDeviceFromGroupFailedException(RemoveDeviceFromGroupFailedException removeDeviceFromGroupFailedException) {
        return ApiHelper.<String>createAndSendErrorResponse(DeviceGroupResponseCode.REMOVE_DEVICE_FROM_GROUP_FAILED, 
        		HttpStatus.INTERNAL_SERVER_ERROR, messageSourceResolver.resolver("remove.device.from.group.failed"));
    }
    
    @ExceptionHandler(UpdateDeviceFailedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleUpdateDeviceFailedException(UpdateDeviceFailedException updateDeviceFailedException) {
        return ApiHelper.<String>createAndSendErrorResponse(DeviceGroupResponseCode.UPDATE_DEVICE_FAILED, 
        		HttpStatus.INTERNAL_SERVER_ERROR, messageSourceResolver.resolver("update.device.failed"));
    }
    
    
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source can not be null");
    }
}
