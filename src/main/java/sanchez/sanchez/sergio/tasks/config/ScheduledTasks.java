package sanchez.sanchez.sergio.tasks.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.batch.config.BatchConfiguration;
import sanchez.sanchez.sergio.service.IParentsService;
import sanchez.sanchez.sergio.service.IPasswordResetTokenService;

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
	
	
	@Scheduled(
		initialDelayString = "${job.notification.scheduling.initial.delay}",
		fixedRateString = "${job.notification.scheduling.fixed.rate}"
	)
    public void scheduleNotificationJob() {
        logger.debug("Notification Job start at  {}", dateFormat.format(new Date()));
        JobExecution execution = null;
        try {
            execution = jobLauncher.run(notificationJob, new JobParameters());	
    	} catch (Exception e) {
    		logger.error(e.toString());
    	} finally {
    		if(execution != null)
    			logger.debug("Notification Job Finish with Status -> " + execution.getStatus());
    	}
    }
	
	@Scheduled(cron = "15 10 * * * ?")
	public void deleteExpiredPasswordTokens(){
		logger.debug("Delete Expired Tokens ...");
		passwordResetTokenService.deleteExpiredTokens();
	}
	
	@Scheduled(cron = "15 10 * * * ?")
	public void deleteUnactivatedAccounts(){
		logger.debug("Delete Unactivated Accounts ...");
		Long total = parentService.deleteUnactivatedAccounts();
		logger.debug( total + " inactive accounts deleted");
	}
	
	@Scheduled(cron = "15 20 * * * ?")
	public void cancelAccountDeletionProcess(){
		logger.debug("Cancel Account Deletion Proccess ...");
		parentService.cancelAllAccountDeletionProcess();
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(notificationJob, "Notification Job can not be null");
		Assert.notNull(jobLauncher, "Job Launcher can not be null");
		Assert.notNull(passwordResetTokenService, "Password Reset Token Service can not be null");
	}

}
