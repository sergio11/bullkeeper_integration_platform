package sanchez.sanchez.sergio.rest.controller.error;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import sanchez.sanchez.sergio.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.controller.BaseController;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommonErrorResponseCode;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class CommonErrorRestController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(CommonErrorRestController.class);
	

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<ValidationErrorDTO>> handleValidationException(MethodArgumentNotValidException ex) {
    	logger.debug("Validation Error");
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        ValidationErrorDTO validationErrorResource = new ValidationErrorDTO();
        for (FieldError fieldError: fieldErrors) {
            validationErrorResource.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ApiHelper.<ValidationErrorDTO>createAndSendErrorResponse(CommonErrorResponseCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST, validationErrorResource);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<List<String>>> handleConstraintViolationException(ConstraintViolationException ex) {
    	List<String> messages = ex.getConstraintViolations().stream()
    			.map(constraintViolation -> constraintViolation.getMessage())
    			.collect(Collectors.toList());
    	return ApiHelper.<List<String>>createAndSendErrorResponse(CommonErrorResponseCode.VALIDATION_ERROR, HttpStatus.BAD_REQUEST, messages);

    }
    
    @ExceptionHandler(LockedException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<String>> handleLockedException(LockedException ex) {
    	return ApiHelper.<String>createAndSendErrorResponse(CommonErrorResponseCode.ACCOUNT_LOCKED, HttpStatus.FORBIDDEN, ex.getMessage());
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
    	return ApiHelper.<String>createAndSendErrorResponse(CommonErrorResponseCode.ACCESS_DENIED, HttpStatus.FORBIDDEN, ex.getMessage());
    }
    
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
    	return ApiHelper.<String>createAndSendErrorResponse(CommonErrorResponseCode.MESSAGE_NOT_READABLE, HttpStatus.BAD_REQUEST, 
    			messageSourceResolver.resolver("message.not.readable"));
    }
    
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleException(
    		Throwable exception, HttpServletRequest request, HttpServletResponse response) {
    	logger.error(exception.getMessage());
    	exception.printStackTrace();
		return ApiHelper.<String>createAndSendErrorResponse(CommonErrorResponseCode.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, 
				messageSourceResolver.resolver("unexpected.error.occurred"));
    }
}
