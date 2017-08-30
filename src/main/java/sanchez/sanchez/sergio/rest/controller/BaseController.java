package sanchez.sanchez.sergio.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import sanchez.sanchez.sergio.service.IMessageSourceResolver;

/**
 *
 * @author sergio
 */
public abstract class BaseController {
    
    @Autowired
    protected IMessageSourceResolver messageSourceResolver;
    
    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;
    
}
