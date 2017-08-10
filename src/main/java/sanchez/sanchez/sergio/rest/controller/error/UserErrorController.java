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
import sanchez.sanchez.sergio.rest.exception.CommentsByUserNotFoundException;
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.exception.UserNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.rest.response.ChildrenResponseCode;

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
        return ApiHelper.<String>createAndSendErrorResponse(ChildrenResponseCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("user.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
    @ExceptionHandler(CommentsByUserNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentsByUserNotFoundException(CommentsByUserNotFoundException commentsByUserNotFound, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponse(CommentResponseCode.COMMENTS_BY_USER_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("comments.by.user.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
    @ExceptionHandler(SocialMediaNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSocialMediaNotFoundException(SocialMediaNotFoundException socialMediaNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_USER_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("social.media.by.user.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
    
}
