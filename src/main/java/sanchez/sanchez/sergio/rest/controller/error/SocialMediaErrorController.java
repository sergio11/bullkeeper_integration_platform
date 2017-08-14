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
import sanchez.sanchez.sergio.rest.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.SocialMediaResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
public class SocialMediaErrorController {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public SocialMediaErrorController(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(SocialMediaNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSocialMediaNotFoundException(SocialMediaNotFoundException socialMediaNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD_NOT_FOUND, HttpStatus.NOT_FOUND,
                messageSource.getMessage("social.media.by.son.not.found", new Object[]{}, localeResolver.resolveLocale(request)));
    }
}
