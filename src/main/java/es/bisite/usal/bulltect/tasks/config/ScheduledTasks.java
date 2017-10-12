package es.bisite.usal.bulltect.tasks.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import es.bisite.usal.bulltect.batch.config.BatchConfiguration;
import es.bisite.usal.bulltect.domain.service.IParentsService;
import es.bisite.usal.bulltect.domain.service.IPasswordResetTokenService;
import es.bisite.usal.bulltect.mail.service.IMailClientService;
import es.bisite.usal.bulltect.persistence.entity.EmailEntity;
import es.bisite.usal.bulltect.persistence.repository.EmailRepository;
import io.jsonwebtoken.lang.Assert;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    @Qualifier(BatchConfiguration.NOTIFICATION_JOB)
    private Job notificationJob;

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    private IPasswordResetTokenService passwordResetTokenService;

    @Autowired
    private IParentsService parentService;
    
    @Autowired
    private EmailRepository emailRepository;
    
    @Autowired
    private IMailClientService mailClientService;
    
    @Value("${number.of.emails.to.forward}")
    private Integer numberOfEmailsToForwarding;

    @Scheduled(cron = "${task.push.notification}")
    public void scheduleNotificationJob() {

        String dateParam = new Date().toString();
        logger.debug("Notification Job start at  {}", dateParam);
        JobParameters param
                = new JobParametersBuilder().addString("date", dateParam).toJobParameters();
        JobExecution execution = null;
        try {
            execution = jobLauncher.run(notificationJob, param);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            if (execution != null) {
                logger.debug("Notification Job Finish with Status -> " + execution.getStatus());
            }
        }
    }

    @Scheduled(cron = "${task.delete.expired.tokens}")
    public void deleteExpiredPasswordTokens() {
        logger.debug("Delete Expired Tokens ...");
        passwordResetTokenService.deleteExpiredTokens();
    }

    @Scheduled(cron = "${task.delete.unactivated.accounts}")
    public void deleteUnactivatedAccounts() {
        logger.debug("Delete Unactivated Accounts ...");
        Long total = parentService.deleteUnactivatedAccounts();
        logger.debug(total + " inactive accounts deleted");
    }

    @Scheduled(cron = "${task.cancel.account.deletion.proccess}")
    public void cancelAccountDeletionProcess() {
        logger.debug("Cancel Account Deletion Proccess ...");
        parentService.cancelAllAccountDeletionProcess();
    }
    
    @Scheduled(cron = "${task.unsuccessful.mail.forwarding}")
    public void unsuccessfulMailForwarding() {
        logger.debug("Unsuccessful Mail Forwarding ...");
        List<EmailEntity> emailsToForward = emailRepository.findAllOrderByLastChanceAsc(new PageRequest(0, numberOfEmailsToForwarding));
        for(EmailEntity emailToForward: emailsToForward)
            mailClientService.sendEmail(emailToForward.getSendTo(), 
                    emailToForward.getSubject(), emailToForward.getContent());
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(notificationJob, "Notification Job can not be null");
        Assert.notNull(jobLauncher, "Job Launcher can not be null");
        Assert.notNull(passwordResetTokenService, "Password Reset Token Service can not be null");
        Assert.notNull(emailRepository, "Email Repository can not be null");
        Assert.notNull(mailClientService, "Mail Client Service can not be null");
    }

}
