package sanchez.sanchez.sergio.bullkeeper.mail.service.impl;


import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.util.Locale;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.mail.properties.MailProperties;
import sanchez.sanchez.sergio.bullkeeper.mail.service.IMailClientService;
import sanchez.sanchez.sergio.bullkeeper.mail.service.IMailContentBuilderService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.EmailEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.EmailTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.EmailRepository;

import java.util.Date;
import java.util.Optional;

/**
 * @author sergio
 */
@Service
public class MailClientServiceImpl implements IMailClientService {

    private Logger logger = LoggerFactory.getLogger(MailClientServiceImpl.class);

    private final IMailContentBuilderService mailContentBuilderService;
    private final JavaMailSender mailSender;
    private final IMessageSourceResolverService messageSourceResolver;
    private final MailProperties mailProperties;
    private final EmailRepository emailRepository;

    /**
     * 
     * @param mailContentBuilderService
     * @param mailSender
     * @param messageSourceResolver
     * @param mailProperties
     * @param emailRepository
     */
    public MailClientServiceImpl(IMailContentBuilderService mailContentBuilderService, JavaMailSender mailSender,
            IMessageSourceResolverService messageSourceResolver, MailProperties mailProperties, EmailRepository emailRepository) {
        this.mailContentBuilderService = mailContentBuilderService;
        this.mailSender = mailSender;
        this.messageSourceResolver = messageSourceResolver;
        this.mailProperties = mailProperties;
        this.emailRepository = emailRepository;
    }

    /**
     * 
     */
    @Override
    public void sendEmail(String email, EmailTypeEnum type, String subject, String content) {
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can be empty");
        Assert.notNull(type, "Type can not be null");
        Assert.notNull(subject, "Subject can not be null");
        Assert.hasLength(subject, "Subject can not be empty");
        Assert.notNull(content, "Content can not be null");
        Assert.hasLength(content, "Content can not be empty");
        
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailProperties.getMailFrom());
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
        };
        try { 
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            HashFunction md = Hashing.md5();
            HashCode code = md.hashBytes( email.concat(subject).concat(content).getBytes() );
            String md5String = code.toString();
            final EmailEntity emailEntity = Optional
                    .ofNullable(emailRepository.findByMd5(md5String))
                    .map((emailEntitySaved) -> {
                        emailEntitySaved.setLastChance(new Date());
                        emailEntitySaved.setError(e.getMessage());
                        return emailEntitySaved;
                    })
                    .orElse(new EmailEntity(email, subject, content, md5String, new Date(), e.getMessage(), type));
            emailRepository.save(emailEntity);
        }
    }

    /**
     * 
     */
    @Override
    public void sendMailForActivateAccount(String email, String firstname, String lastname, String confirmationToken, Locale locale) {
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can not be empty");
        Assert.notNull(firstname, "Firstname can not be null");
        Assert.hasLength(firstname, "Firstname can not be empty");
        Assert.notNull(lastname, "Lastname can not be null");
        Assert.hasLength(lastname, "Lastname can not be empty");
        Assert.notNull(confirmationToken, "Confirmation token can not be null");
        Assert.hasLength(confirmationToken, "Confirmation token can not be empty");
        Assert.notNull(locale, "Locale can not be null");
        
        logger.debug("Send Mail for Activate Account");
        String subject = messageSourceResolver.resolver("mail.registration.success.subject.title", new Object[]{firstname, lastname});
        String content = mailContentBuilderService.buildRegistrationSuccessTemplate(firstname, lastname, confirmationToken, locale);
        sendEmail(email, EmailTypeEnum.ACTIVATE_ACCOUNT, subject, content);
    }

    @Override
    public void sendMailForResetPassword(String id, String email, String firstname, String lastname, String token, Locale locale) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can not be empty");
        Assert.notNull(firstname, "Firstname can not be null");
        Assert.hasLength(firstname, "Firstname can not be empty");
        Assert.notNull(lastname, "Lastname can not be null");
        Assert.hasLength(lastname, "Lastname can not be empty");
        Assert.notNull(token, "token can not be null");
        Assert.hasLength(token, "token can not be empty");
        Assert.notNull(locale, "Locale can not be null");
        
        logger.debug("Send Mail for Reset Password");
        String subject = messageSourceResolver.resolver("mail.password.reset.subject.title");
        String content = mailContentBuilderService.buildPasswordResetTemplate(id, firstname, lastname, token, locale);
        sendEmail(email, EmailTypeEnum.RESET_PASSWORD, subject, content);
    }

    @Override
    public void sendMailForConfirmPasswordChange(String email, String firstname, String lastname, Locale locale) {
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can not be empty");
        Assert.notNull(firstname, "Firstname can not be null");
        Assert.hasLength(firstname, "Firstname can not be empty");
        Assert.notNull(lastname, "Lastname can not be null");
        Assert.hasLength(lastname, "Lastname can not be empty");
        Assert.notNull(locale, "Locale can not be null");
        
        logger.debug("Send Mail for Confirm Password Change");
        String subject = messageSourceResolver.resolver("mail.confirm.password.change.subject.title", new Object[]{firstname + lastname});
        String content = mailContentBuilderService.buildConfirmPasswordChangeTemplate(firstname, lastname, locale);
        sendEmail(email, EmailTypeEnum.CONFIRM_PASSWORD_CHANGE, subject, content);
    }

    @Override
    public void sendMailForConfirmAccountActivation(String email, String firstname, String lastname, Locale locale) {
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can not be empty");
        Assert.notNull(firstname, "Firstname can not be null");
        Assert.hasLength(firstname, "Firstname can not be empty");
        Assert.notNull(lastname, "Lastname can not be null");
        Assert.hasLength(lastname, "Lastname can not be empty");
        Assert.notNull(locale, "Locale can not be null");
        
        logger.debug("Send Mail for Confirm Account Activation");
        String subject = messageSourceResolver.resolver("mail.confirm.account.activation.subject.title", new Object[]{firstname, lastname});
        String content = mailContentBuilderService.buildConfirmAccountActivationTemplate(firstname, lastname, locale);
        sendEmail(email, EmailTypeEnum.CONFIRM_ACCOUNT_ACTIVATION ,subject, content);
    }

    @Override
    public void sendMailForConfirmRegistrationViaFacebook(String email, String firstname, String lastname, Locale locale) {
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can not be empty");
        Assert.notNull(firstname, "Firstname can not be null");
        Assert.hasLength(firstname, "Firstname can not be empty");
        Assert.notNull(lastname, "Lastname can not be null");
        Assert.hasLength(lastname, "Lastname can not be empty");
        Assert.notNull(locale, "Locale can not be null");
        
        logger.debug("Send Mail for Confirm Registration via Facebook");
        String subject = messageSourceResolver.resolver("mail.registration.success.subject.title", new Object[]{firstname, lastname});
        String content = mailContentBuilderService.buildConfirmRegistrationViaFacebookTemplate(firstname, lastname, locale);
        sendEmail(email, EmailTypeEnum.CONFIRM_REGISTRATION_VIA_FACEBOOK , subject, content);
    }
    
    @Override
	public void sendMailForConfirmRegistrationViaGoogle(String email, String firstname, String lastname, Locale locale) {
    	Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can not be empty");
        Assert.notNull(firstname, "Firstname can not be null");
        Assert.hasLength(firstname, "Firstname can not be empty");
        Assert.notNull(lastname, "Lastname can not be null");
        Assert.hasLength(lastname, "Lastname can not be empty");
        Assert.notNull(locale, "Locale can not be null");
        
        logger.debug("Send Mail for Confirm Registration via Google");
        String subject = messageSourceResolver.resolver("mail.registration.success.subject.title", new Object[]{firstname, lastname});
        String content = mailContentBuilderService.buildConfirmRegistrationViaGoogleTemplate(firstname, lastname, locale);
        sendEmail(email, EmailTypeEnum.CONFIRM_REGISTRATION_VIA_GOOGLE , subject, content);
		
	}

    @Override
    public void sendMailForCompleteAccountDeletionProcess(String email, String firstname, String lastname, String confirmationToken, Locale locale) {
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can not be empty");
        Assert.notNull(firstname, "Firstname can not be null");
        Assert.hasLength(firstname, "Firstname can not be empty");
        Assert.notNull(lastname, "Lastname can not be null");
        Assert.hasLength(lastname, "Lastname can not be empty");
        Assert.notNull(locale, "Locale can not be null");
        
        logger.debug("Send Mail for Complete Account Deletion Process");
        String subject = messageSourceResolver.resolver("mail.complete.account.deletion.process.subject.title", new Object[]{firstname, lastname});
        String content = mailContentBuilderService.buildCompleteAccountDeletionProcessTemplate(firstname, lastname, confirmationToken, locale);
        sendEmail(email, EmailTypeEnum.COMPLETE_ACCOUNT_DELETION, subject, content);
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(mailContentBuilderService, "Mail Content Builder Service can not be null");
        Assert.notNull(mailSender, "Mail Sender can not be null");
        Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
        Assert.notNull(mailProperties, "Mail Properties can not be null");
    }

}
