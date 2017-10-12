package es.bisite.usal.bulltect.mail.service.impl;


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
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.mail.properties.MailProperties;
import es.bisite.usal.bulltect.mail.service.IMailClientService;
import es.bisite.usal.bulltect.mail.service.IMailContentBuilderService;
import es.bisite.usal.bulltect.persistence.entity.EmailEntity;
import es.bisite.usal.bulltect.persistence.repository.EmailRepository;
import io.jsonwebtoken.lang.Assert;
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

    public MailClientServiceImpl(IMailContentBuilderService mailContentBuilderService, JavaMailSender mailSender,
            IMessageSourceResolverService messageSourceResolver, MailProperties mailProperties, EmailRepository emailRepository) {
        this.mailContentBuilderService = mailContentBuilderService;
        this.mailSender = mailSender;
        this.messageSourceResolver = messageSourceResolver;
        this.mailProperties = mailProperties;
        this.emailRepository = emailRepository;
    }

    @Override
    public void sendEmail(String email, String subject, String content) {
        Assert.notNull(email, "Email can not be null");
        Assert.hasLength(email, "Email can be empty");
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
                    .orElse(new EmailEntity(email, subject, content, md5String, new Date(), e.getMessage()));
            emailRepository.save(emailEntity);
        }
    }

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
        sendEmail(email, subject, content);
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
        sendEmail(email, subject, content);
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
        sendEmail(email, subject, content);
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
        sendEmail(email, subject, content);
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
        sendEmail(email, subject, content);
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
        sendEmail(email, subject, content);
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(mailContentBuilderService, "Mail Content Builder Service can not be null");
        Assert.notNull(mailSender, "Mail Sender can not be null");
        Assert.notNull(messageSourceResolver, "Message Source Resolver can not be null");
        Assert.notNull(mailProperties, "Mail Properties can not be null");
    }

}
