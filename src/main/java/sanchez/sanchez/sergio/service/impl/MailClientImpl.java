package sanchez.sanchez.sergio.service.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.mail.properties.MailProperties;
import sanchez.sanchez.sergio.service.IMailClient;
import sanchez.sanchez.sergio.service.IMailContentBuilderService;
import sanchez.sanchez.sergio.service.IMessageSourceResolver;


/**
 * @author sergio
 */
@Service
public class MailClientImpl implements IMailClient {
    
    private final IMailContentBuilderService mailContentBuilderService;
    private final JavaMailSender mailSender;
    private final IMessageSourceResolver messageSourceResolver;
    private final MailProperties mailProperties;

    public MailClientImpl(IMailContentBuilderService mailContentBuilderService, JavaMailSender mailSender,
            IMessageSourceResolver messageSourceResolver, MailProperties mailProperties) {
        this.mailContentBuilderService = mailContentBuilderService;
        this.mailSender = mailSender;
        this.messageSourceResolver = messageSourceResolver;
        this.mailProperties = mailProperties;
    }
   
    @Override
    public void sendMailForActivateAccount(String email, String firstname, String lastname) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailProperties.getMailFrom());
            messageHelper.setTo(email);
            messageHelper.setSubject(messageSourceResolver.resolver("mail.registration.success.subject.title", new Object[] { firstname + lastname}));
            String content = mailContentBuilderService.buildRegistrationSuccessTemplate(firstname, lastname);
            messageHelper.setText(content, true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            // runtime exception; compiler will not force you to handle it
        }       
    }
    
}
