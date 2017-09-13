package sanchez.sanchez.sergio.service.impl;


import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import sanchez.sanchez.sergio.service.IMailContentBuilderService;
import org.thymeleaf.context.Context;
import sanchez.sanchez.sergio.mail.properties.MailProperties;

/**
 * @author sergio
 */
@Service
public class MailContentBuilderServiceImpl implements IMailContentBuilderService {
	
	private Logger logger = LoggerFactory.getLogger(MailContentBuilderServiceImpl.class);
    
    private final MailProperties mailContentProperties;
    private final TemplateEngine templateEngine;
    
    @Autowired
    public MailContentBuilderServiceImpl(MailProperties mailContentProperties, TemplateEngine templateEngine) {
        this.mailContentProperties = mailContentProperties;
        this.templateEngine = templateEngine;
    }

    @Override
    public String buildRegistrationSuccessTemplate(String firstname, String lastname, String confirmationToken) {
        Assert.notNull(mailContentProperties.getRegistrationSuccessTemplate(), "Registration Success Template can not be null");
        Assert.hasLength(mailContentProperties.getRegistrationSuccessTemplate(), "Registration Success Template can not be empty");
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        context.setVariable("activateUrl", 
        		ServletUriComponentsBuilder.fromCurrentContextPath().path("/backend/accounts/activate?token={token}").buildAndExpand(confirmationToken));
        return templateEngine.process(mailContentProperties.getRegistrationSuccessTemplate(), context);
    }

	@Override
	public String buildPasswordResetTemplate(String id, String firstname, String lastname, String token) {
		Assert.notNull(mailContentProperties.getPassWordResetTemplate(), "Password Reset Template can not be null");
        Assert.hasLength(mailContentProperties.getRegistrationSuccessTemplate(), "Password Reset Template can not be empty");
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        context.setVariable("token", 
        		ServletUriComponentsBuilder.fromCurrentContextPath().path("/backend/accounts/resetting?id={id}&token={token}").buildAndExpand(id, token));
        return templateEngine.process(mailContentProperties.getRegistrationSuccessTemplate(), context);
	}

	@Override
	public String buildConfirmPasswordChangeTemplate(String firstname, String lastname) {
		Assert.notNull(mailContentProperties.getConfirmPasswordChangeTemplate(), "Password Change Template can not be null");
        Assert.hasLength(mailContentProperties.getConfirmPasswordChangeTemplate(), "Password Change Template can not be empty");
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        return templateEngine.process(mailContentProperties.getConfirmPasswordChangeTemplate(), context);
	}

	@Override
	public String buildConfirmAccountActivationTemplate(String firstname, String lastname) {
		Assert.notNull(mailContentProperties.getConfirmAccountActivationTemplate(), "Confirm Account Activation Template can not be null");
        Assert.hasLength(mailContentProperties.getConfirmAccountActivationTemplate(), "Confirm Account Activation Template can not be empty");
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        return templateEngine.process(mailContentProperties.getConfirmAccountActivationTemplate(), context);
	}
	
	@Override
	public String buildConfirmRegistrationViaFacebookTemplate(String firstname, String lastname) {
		Assert.notNull(mailContentProperties.getConfirmRegistrationViaFacebookTemplate(), "Confirm Registration via Facebook Template can not be null");
        Assert.hasLength(mailContentProperties.getConfirmRegistrationViaFacebookTemplate(), "Confirm Registration via Facebook Template can not be empty");
        Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        return templateEngine.process(mailContentProperties.getConfirmRegistrationViaFacebookTemplate(), context);
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(mailContentProperties, "Mail Content Properties can not be null");
		Assert.notNull(templateEngine, "Template Engine can not be null");
	}

}
