package sanchez.sanchez.sergio.masoc.web.rest.controller.error;

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

import sanchez.sanchez.sergio.masoc.exception.AdminNotFoundException;
import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.AdminResponseCode;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AdminErrorController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminErrorController.class);
	
	@ExceptionHandler(AdminNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleAdminNotFoundException(AdminNotFoundException adminNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(AdminResponseCode.ADMIN_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("admin.not.found"));
    }
    

}
