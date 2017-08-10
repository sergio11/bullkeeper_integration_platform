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
import sanchez.sanchez.sergio.rest.exception.ParentNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.rest.response.ParentResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
public class ParentErrorController {

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
    
}
