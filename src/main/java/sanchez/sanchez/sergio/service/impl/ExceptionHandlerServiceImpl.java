package sanchez.sanchez.sergio.service.impl;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.exception.visitor.IExceptionVisitor;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.repository.SocialMediaRepository;
import sanchez.sanchez.sergio.service.IExceptionHandlerService;
import sanchez.sanchez.sergio.util.IVisitable;

/**
 *
 * @author sergio
 */
@Service("errorService")
public class ExceptionHandlerServiceImpl implements IExceptionHandlerService, IExceptionVisitor {

    private static Logger logger = LoggerFactory.getLogger(ExceptionHandlerServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;

    public ExceptionHandlerServiceImpl(SocialMediaRepository socialMediaRepository) {
        this.socialMediaRepository = socialMediaRepository;
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
        socialMediaRepository.setAccessTokenAsInvalid(exception.getAccessToken(), exception.getSocialMediaType());
    }
    
   
    @PostConstruct
    protected void init(){
        Assert.notNull(socialMediaRepository, "The SocialMediaRepository can not be null");
    }

    @Override
    public void visit(GetCommentsProcessException exception) {
        logger.error(exception.getCause().toString());
    }

}
