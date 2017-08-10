package sanchez.sanchez.sergio.rest.controller.error;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import sanchez.sanchez.sergio.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommonErrorResponseCode;

@ControllerAdvice
public class CommonErrorRestController{
	
	private static Logger logger = LoggerFactory.getLogger(CommonErrorRestController.class);
	
	
	@ExceptionHandler(Exception.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleException(
    		Exception exception, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Excepción genérica -> " + exception.getClass().getName());
		return ApiHelper.<String>createAndSendErrorResponse(CommonErrorResponseCode.GENERIC_ERROR, HttpStatus.BAD_REQUEST, exception.getMessage());
    }
 
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
}
