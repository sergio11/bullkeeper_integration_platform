package sanchez.sanchez.sergio.rest.controller.error;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundForParentException;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundForSelfParentException;
import sanchez.sanchez.sergio.rest.exception.NoParentsFoundException;
import sanchez.sanchez.sergio.rest.exception.ParentNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.ParentResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
public class ParentErrorController {
	
	private static Logger logger = LoggerFactory.getLogger(ParentErrorController.class);

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public ParentErrorController(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(ParentNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleParentNotFoundException(ParentNotFoundException parentNotFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.PARENT_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("parent.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
    @ExceptionHandler(NoChildrenFoundForSelfParentException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForSelfParentException(NoChildrenFoundForSelfParentException noChildrenFoundForSelfParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.NO_CHILDREN_FOUND_FOR_SELF_PARENT, HttpStatus.NOT_FOUND,
                messageSource.getMessage("self.parent.not.children.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
    @ExceptionHandler(NoChildrenFoundForParentException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundForParentException(NoChildrenFoundForParentException noChildrenFoundForParentException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.NO_CHILDREN_FOUND_FOR_PARENT, HttpStatus.NOT_FOUND,
                messageSource.getMessage("parent.not.children.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
    @ExceptionHandler(NoParentsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoParentsFoundException(NoParentsFoundException noParentsFoundException, HttpServletRequest request) {
    	return ApiHelper.<String>createAndSendErrorResponse(ParentResponseCode.PARENTS_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("parents.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
}
