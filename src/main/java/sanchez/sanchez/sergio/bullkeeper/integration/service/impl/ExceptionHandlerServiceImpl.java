package sanchez.sanchez.sergio.bullkeeper.integration.service.impl;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.bullkeeper.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.bullkeeper.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.bullkeeper.integration.service.IExceptionHandlerService;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.bullkeeper.util.IVisitable;

/**
 *
 * @author sergio
 */
@Service("errorService")
public class ExceptionHandlerServiceImpl implements IExceptionHandlerService, IExceptionVisitor {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;
    private final IAlertService alertService;
    
    /**
     * 
     * @param socialMediaRepository
     * @param alertService
     */
    public ExceptionHandlerServiceImpl(final SocialMediaRepository socialMediaRepository, final IAlertService alertService) {
        this.socialMediaRepository = socialMediaRepository;
        this.alertService = alertService;
    }
    
    /**
     * 
     */
    @Override
    public void handleException(Message<Exception> messageException) {
        Throwable cause = messageException.getPayload().getCause();
        if (cause instanceof IVisitable)
                ((IVisitable<IExceptionVisitor>)cause).accept(this);  
    }

    /**
     * Invalid Access Token Exception Handler
     */
    @Override
    public void visit(InvalidAccessTokenException exception) {
    	Assert.notNull(exception.getAccessToken(), "Access Token can not be null");
    	Assert.hasLength(exception.getAccessToken(), "Access Token can not be an empty string");
    	Assert.notNull(exception.getMessage(), "Message can not be null");
    	Assert.hasLength(exception.getMessage(), "Message can not be an empty string");
    	Assert.notNull(exception.getTarget(), "Target can not be null");
    	logger.debug("Save exception as Alert for: " + exception.getTarget().getFullName());
        socialMediaRepository.setAccessTokenAsInvalid(exception.getAccessToken(), exception.getSocialMediaType());
        alertService.createInvalidAccessTokenAlert(exception.getMessage(), exception.getTarget());
       
    }
    
    /**
     * Get Comments Process Exception Exception Handler
     */
    @Override
    public void visit(GetCommentsProcessException exception) {
        logger.debug("Get Comments Process Exception");
    }
    
   
    @PostConstruct
    protected void init(){
        Assert.notNull(socialMediaRepository, "The SocialMediaRepository can not be null");
        Assert.notNull(alertService, "The Alert Service can not be null");
    }
}
