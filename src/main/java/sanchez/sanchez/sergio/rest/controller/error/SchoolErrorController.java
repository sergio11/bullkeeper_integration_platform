package sanchez.sanchez.sergio.rest.controller.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.SchoolNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.SchoolResponseCode;

@ControllerAdvice
public class SchoolErrorController {
	
	private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    
    
	public SchoolErrorController(MessageSource messageSource, LocaleResolver localeResolver) {
		super();
		this.messageSource = messageSource;
		this.localeResolver = localeResolver;
	}
	
	@ExceptionHandler(SchoolNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSchoolNotFoundException(SchoolNotFoundException schoolNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendResponse(SchoolResponseCode.SCHOOL_NOT_FOUND,
                messageSource.getMessage("school.not.found", new Object[]{}, localeResolver.resolveLocale(request)), HttpStatus.NOT_FOUND);
    }
	
	
    
	
	
    

}
