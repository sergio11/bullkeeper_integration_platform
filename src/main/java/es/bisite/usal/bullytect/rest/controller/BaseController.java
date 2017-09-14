package es.bisite.usal.bullytect.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import es.bisite.usal.bullytect.service.IMessageSourceResolver;

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
