package es.bisite.usal.bulltect.tasks.config;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import es.bisite.usal.bulltect.domain.service.IParentsService;
import es.bisite.usal.bulltect.domain.service.IPasswordResetTokenService;
import es.bisite.usal.bulltect.mail.service.IMailClientService;
import es.bisite.usal.bulltect.persistence.entity.EmailEntity;
import es.bisite.usal.bulltect.persistence.repository.EmailRepository;
import io.jsonwebtoken.lang.Assert;

@Configuration
@EnableScheduling
public class AccountsTasksConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountsTasksConfig.class);
	
	private final IPasswordResetTokenService passwordResetTokenService;
	private final IParentsService parentService;
	private final EmailRepository emailRepository;
	private final IMailClientService mailClientService;
	
	@Value("${task.account.unsuccessful.mail.forwarding.number.of.emails.to.forward}")
    private Integer numberOfEmailsToForwarding;
	
	@Autowired
	public AccountsTasksConfig(IPasswordResetTokenService passwordResetTokenService, 
			IParentsService parentService, EmailRepository emailRepository, IMailClientService mailClientService){
		this.passwordResetTokenService = passwordResetTokenService;
		this.parentService = parentService;
		this.emailRepository = emailRepository;
		this.mailClientService = mailClientService;
	}
	
	
	@Scheduled(cron = "${task.account.delete.expired.tokens}")
    public void deleteExpiredPasswordTokens() {
        logger.debug("Delete Expired Tokens ...");
        passwordResetTokenService.deleteExpiredTokens();
    }

    @Scheduled(cron = "${task.account.delete.unactivated.accounts}")
    public void deleteUnactivatedAccounts() {
        logger.debug("Delete Unactivated Accounts ...");
        Long total = parentService.deleteUnactivatedAccounts();
        logger.debug(total + " inactive accounts deleted");
    }

    @Scheduled(cron = "${task.account.cancel.account.deletion.proccess}")
    public void cancelAccountDeletionProcess() {
        logger.debug("Cancel Account Deletion Proccess ...");
        parentService.cancelAllAccountDeletionProcess();
    }
    
    @Scheduled(cron = "${task.account.unsuccessful.mail.forwarding}")
    public void unsuccessfulMailForwarding() {
        logger.debug("Unsuccessful Mail Forwarding ...");
        List<EmailEntity> emailsToForward = emailRepository.findAllByOrderByLastChanceAsc(new PageRequest(0, numberOfEmailsToForwarding));
        for(EmailEntity emailToForward: emailsToForward)
            mailClientService.sendEmail(emailToForward.getSendTo(), emailToForward.getType(),
                    emailToForward.getSubject(), emailToForward.getContent());
    }
    
    @PostConstruct
    protected void init() {
        Assert.notNull(passwordResetTokenService, "Password Reset Token Service can not be null");
        Assert.notNull(parentService, "Parent Service can not be null");
        Assert.notNull(emailRepository, "Email Repository can not be null");
        Assert.notNull(mailClientService, "Mail Client Service can not be null");
        logger.debug("AccountsTasksConfig initialized ...");
    }

}
