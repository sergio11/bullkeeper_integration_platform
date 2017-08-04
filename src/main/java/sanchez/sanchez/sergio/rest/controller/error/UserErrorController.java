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
import sanchez.sanchez.sergio.rest.exception.UserNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.UserResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
public class UserErrorController {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public UserErrorController(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleUserNotFoundException(UserNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendResponse(UserResponseCode.USER_NOT_FOUND,
                messageSource.getMessage("analyst.not.found", new Object[]{}, localeResolver.resolveLocale(request)), HttpStatus.NOT_FOUND);
    }
}
