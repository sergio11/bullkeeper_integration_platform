package es.bisite.usal.bulltect.web.rest.controller.error;

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

import es.bisite.usal.bulltect.exception.NoSchoolsFoundException;
import es.bisite.usal.bulltect.exception.SchoolNotFoundException;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.controller.BaseController;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.SchoolResponseCode;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SchoolErrorController extends BaseController {
	
    @ExceptionHandler(SchoolNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSchoolNotFoundException(SchoolNotFoundException schoolNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(SchoolResponseCode.SCHOOL_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("school.not.found"));
    }
	
	@ExceptionHandler(NoSchoolsFoundException.class)
	@ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSchoolsFoundException(NoSchoolsFoundException noSchoolsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(SchoolResponseCode.NO_SCHOOLS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("schools.not.found"));
    }
	
	@PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }

}
