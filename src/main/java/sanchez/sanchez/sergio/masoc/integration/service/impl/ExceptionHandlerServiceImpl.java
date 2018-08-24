package sanchez.sanchez.sergio.masoc.integration.service.impl;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.masoc.domain.service.IAlertService;
import sanchez.sanchez.sergio.masoc.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.masoc.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.masoc.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.masoc.integration.service.IExceptionHandlerService;
import sanchez.sanchez.sergio.masoc.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.masoc.util.IVisitable;

/**
 *
 * @author sergio
 */
@Service("errorService")
public class ExceptionHandlerServiceImpl implements IExceptionHandlerService, IExceptionVisitor {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;
    private final IAlertService alertService;
    

    public ExceptionHandlerServiceImpl(SocialMediaRepository socialMediaRepository, IAlertService alertService) {
        this.socialMediaRepository = socialMediaRepository;
        this.alertService = alertService;
    }
    
    @Override
    public void handleException(Message<Exception> messageException) {
        Throwable cause = messageException.getPayload().getCause();
        if (cause instanceof IVisitable)
                ((IVisitable<IExceptionVisitor>)cause).accept(this);
        else {
            //logger.error(cause.fillInStackTrace().toString());
        }   
    }

    @Override
    public void visit(InvalidAccessTokenException exception) {
    	Assert.notNull(exception.getAccessToken(), "Access Token can not be null");
    	Assert.hasLength(exception.getAccessToken(), "Access Token can not be an empty string");
    	Assert.notNull(exception.getMessage(), "Message can not be null");
    	Assert.hasLength(exception.getMessage(), "Message can not be an empty string");
    	Assert.notNull(exception.getTarget(), "Target can not be null");
    	logger.debug("Save exception as Alert for: " + exception.getTarget().getFullName());
        socialMediaRepository.setAccessTokenAsInvalid(exception.getAccessToken(), exception.getSocialMediaType());
        alertService.createInvalidAccessTokenAlert(exception.getMessage(), 
                exception.getTarget().getParent(), exception.getTarget());
       
    }
    
    @Override
    public void visit(GetCommentsProcessException exception) {
        logger.error(exception.getMessage());
    }
    
   
    @PostConstruct
    protected void init(){
        Assert.notNull(socialMediaRepository, "The SocialMediaRepository can not be null");
        Assert.notNull(alertService, "The Alert Service can not be null");
    }
}
