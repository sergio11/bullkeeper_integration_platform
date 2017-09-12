package sanchez.sanchez.sergio.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger logger = LoggerFactory.getLogger(MailClientImpl.class);
    
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
    
    private void sendEmail(String email, String subject, String content) throws MailException {
    	MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailProperties.getMailFrom());
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };
        
        mailSender.send(messagePreparator);
    }
   
    @Override
    public void sendMailForActivateAccount(String email, String firstname, String lastname) {
    	logger.debug("Send Mail for Activate Account");
    	String subject = messageSourceResolver.resolver("mail.registration.success.subject.title", new Object[] { firstname + lastname});
    	String content = mailContentBuilderService.buildRegistrationSuccessTemplate(firstname, lastname);
        try {
        	sendEmail(email, subject, content);
        } catch (MailException e) {
            logger.error(e.toString());
        }       
    }

	@Override
	public void sendMailForResetPassword(String id, String email, String firstname, String lastname, String token) {
		logger.debug("Send Mail for Reset Password");
		String subject = messageSourceResolver.resolver("mail.password.reset.subject.title");
    	String content = mailContentBuilderService.buildPasswordResetTemplate(id, firstname, lastname, token);
        try {
        	sendEmail(email, subject, content);
        } catch (MailException e) {
        	logger.error(e.toString());
        }   
	}

	@Override
	public void sendMailForConfirmPasswordChange(String email, String firstname, String lastname) {
		logger.debug("Send Mail for Confirm Password Change");
		String subject = messageSourceResolver.resolver("mail.confirm.password.change.subject.title", new Object[] { firstname + lastname });
    	String content = mailContentBuilderService.buildConfirmPasswordChangeTemplate(firstname, lastname);
        try {
        	sendEmail(email, subject, content);
        } catch (MailException e) {
        	logger.error(e.toString());
        }  
	}
}
