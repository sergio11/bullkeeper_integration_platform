package es.bisite.usal.bulltect.web.rest.controller.error;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.controller.BaseController;
import es.bisite.usal.bulltect.web.rest.exception.SocialMediaNotFoundException;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.SocialMediaResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SocialMediaErrorController extends BaseController {


    @ExceptionHandler(SocialMediaNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSocialMediaNotFoundException(SocialMediaNotFoundException socialMediaNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("social.media.by.son.not.found"));
    }
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
    }
}
