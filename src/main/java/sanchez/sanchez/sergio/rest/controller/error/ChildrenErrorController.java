package sanchez.sanchez.sergio.rest.controller.error;


import javax.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.CommentsBySonNotFoundException;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundException;
import sanchez.sanchez.sergio.rest.exception.SonNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.rest.response.ChildrenResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ChildrenErrorController {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public ChildrenErrorController(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(SonNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSonNotFoundException(SonNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponse(ChildrenResponseCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("son.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
    @ExceptionHandler(CommentsBySonNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentsBySonNotFoundException(CommentsBySonNotFoundException commentsBySonNotFound, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponse(CommentResponseCode.COMMENTS_BY_CHILD_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("comments.by.son.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
   
    
    @ExceptionHandler(NoChildrenFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundException(NoChildrenFoundException noChildrenFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.NO_CHILDREN_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("children.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
  
}
