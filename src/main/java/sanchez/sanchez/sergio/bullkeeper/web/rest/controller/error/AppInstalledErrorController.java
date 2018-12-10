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
import sanchez.sanchez.sergio.bullkeeper.exception.AppInstalledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAppsInstalledFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.AppsResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ChildrenResponseCode;

/**
 * App Installed Error Controller
 * @author sergiosanchezsanchez
 *
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppInstalledErrorController extends BaseController {
	
	/**
	 * Exception Handler for No Apps Installed found exception
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(NoAppsInstalledFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAppsInstalledFoundException(NoAppsInstalledFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(AppsResponseCode.NO_APPS_INSTALLED_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("app.installed.not.found"));
    }
    
    
    /**
	 * Exception Handler for App installed not found
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(AppInstalledNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleAppInstalledNotFoundExceptionException(AppInstalledNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(AppsResponseCode.APP_INSTALLED_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("app.installed.not.found"));
    }
    
    
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
 
}
