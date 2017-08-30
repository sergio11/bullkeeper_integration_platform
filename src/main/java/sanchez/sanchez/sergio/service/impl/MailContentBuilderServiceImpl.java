package sanchez.sanchez.sergio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import sanchez.sanchez.sergio.service.IMailContentBuilderService;
import org.thymeleaf.context.Context;
import sanchez.sanchez.sergio.mail.properties.MailProperties;

/**
 * @author sergio
 */
@Service
public class MailContentBuilderServiceImpl implements IMailContentBuilderService {
    
    private final MailProperties mailContentProperties;
    private final TemplateEngine templateEngine;
    
    @Autowired
    public MailContentBuilderServiceImpl(MailProperties mailContentProperties, TemplateEngine templateEngine) {
        this.mailContentProperties = mailContentProperties;
        this.templateEngine = templateEngine;
    }

    @Override
    public String buildRegistrationSuccessTemplate(String firstname, String lastname) {
        Assert.notNull(mailContentProperties.getRegistrationSuccessTemplate(), "Registration Success Template can not be null");
        Assert.hasLength(mailContentProperties.getRegistrationSuccessTemplate(), "Registration Success Template can not be empty");
        Context context = new Context();
        context.setVariable("firstname", firstname);
        context.setVariable("lastname", lastname);
        return templateEngine.process(mailContentProperties.getRegistrationSuccessTemplate(), context);
    }
    
}
