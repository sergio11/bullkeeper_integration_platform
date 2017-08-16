package sanchez.sanchez.sergio.rest.controller.error;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.NoSchoolsFoundException;
import sanchez.sanchez.sergio.rest.exception.SchoolNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.SchoolResponseCode;
import sanchez.sanchez.sergio.service.IMessageSourceResolver;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SchoolErrorController {
	
	private final IMessageSourceResolver messageSourceResolver;
    
	public SchoolErrorController(IMessageSourceResolver messageSourceResolver) {
		super();
		this.messageSourceResolver = messageSourceResolver;
	}
	
	@ExceptionHandler(SchoolNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSchoolNotFoundException(SchoolNotFoundException schoolNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(SchoolResponseCode.SCHOOL_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("school.not.found"));
    }
	
	@ExceptionHandler(NoSchoolsFoundException.class)
	@ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSchoolsFoundException(NoSchoolsFoundException noSchoolsFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(SchoolResponseCode.NO_SCHOOLS_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("schools.not.found"));
    }
	
	@PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }

}
