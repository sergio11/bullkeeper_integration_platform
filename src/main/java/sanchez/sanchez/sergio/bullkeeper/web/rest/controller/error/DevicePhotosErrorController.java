package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;

import javax.annotation.PostConstruct;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sanchez.sanchez.sergio.bullkeeper.exception.DevicePhotoDetailNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoDevicePhotosFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.DevicePhotosResponseCode;

/**
 * Device Photos Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DevicePhotosErrorController  extends BaseController {
	
	
	/**
     * 
     * @param devicePhotoDetailNotFoundException
     * @return
     */
    @ExceptionHandler(DevicePhotoDetailNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleDevicePhotoDetailNotFoundException(final DevicePhotoDetailNotFoundException devicePhotoDetailNotFoundException) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		DevicePhotosResponseCode.DEVICE_PHOTO_DETAIL_NOT_FOUND, 
        		HttpStatus.NOT_FOUND, messageSourceResolver.resolver("device.photo.detail.not.found"));
    }
    
    /**
     * 
     * @param noDevicePhotosFoundException
     * @return
     */
    @ExceptionHandler(NoDevicePhotosFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoDevicePhotosFoundException(final NoDevicePhotosFoundException noDevicePhotosFoundException) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		DevicePhotosResponseCode.NO_DEVICE_PHOTOS_FOUND, 
        		HttpStatus.NOT_FOUND, messageSourceResolver.resolver("no.device.photos.found"));
    }
    

    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
