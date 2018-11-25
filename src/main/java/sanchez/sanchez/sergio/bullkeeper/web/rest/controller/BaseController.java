package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.ResourceLoader;

import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ControllerHelper;

/**
 *
 * @author sergio
 */
public abstract class BaseController {
    
    @Autowired
    protected IMessageSourceResolverService messageSourceResolver;
    
    @Autowired
    protected ApplicationEventPublisher applicationEventPublisher;
    
    @Autowired
    protected ResourceLoader resourceLoader;
    
    @Autowired
    protected ControllerHelper controllerHelper;
    
    @Autowired
    protected PrettyTime prettyTime;
    
}
