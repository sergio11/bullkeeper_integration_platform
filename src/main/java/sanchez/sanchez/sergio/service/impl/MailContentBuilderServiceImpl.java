package sanchez.sanchez.sergio.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;
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
        Context context = new Context();
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        context.setVariable("activateUrl", 
        		UriComponentsBuilder.fromUriString("/backend/accounts/activate?token={token}").buildAndExpand(confirmationToken));
        return templateEngine.process(mailContentProperties.getRegistrationSuccessTemplate(), context);
    }

	@Override
	public String buildPasswordResetTemplate(String id, String firstname, String lastname, String token) {
		Assert.notNull(mailContentProperties.getPassWordResetTemplate(), "Password Reset Template can not be null");
        Assert.hasLength(mailContentProperties.getRegistrationSuccessTemplate(), "Password Reset Template can not be empty");
        Context context = new Context();
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        context.setVariable("token", 
        		UriComponentsBuilder.fromUriString("/backend/accounts/resetting?id={id}&token={token}").buildAndExpand(id, token));
        return templateEngine.process(mailContentProperties.getRegistrationSuccessTemplate(), context);
	}

	@Override
	public String buildConfirmPasswordChangeTemplate(String firstname, String lastname) {
		Assert.notNull(mailContentProperties.getConfirmPasswordChangeTemplate(), "Password Change Template can not be null");
        Assert.hasLength(mailContentProperties.getConfirmPasswordChangeTemplate(), "Password Change Template can not be empty");
        Context context = new Context();
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        return templateEngine.process(mailContentProperties.getConfirmPasswordChangeTemplate(), context);
	}
    
}
