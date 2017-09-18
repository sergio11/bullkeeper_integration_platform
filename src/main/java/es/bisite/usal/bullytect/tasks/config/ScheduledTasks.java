package es.bisite.usal.bullytect.tasks.config;

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

import es.bisite.usal.bullytect.batch.config.BatchConfiguration;
import es.bisite.usal.bullytect.service.IParentsService;
import es.bisite.usal.bullytect.service.IPasswordResetTokenService;
import io.jsonwebtoken.lang.Assert;

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
	
	
	@Scheduled(cron = "${task.push.notification}")
    public void scheduleNotificationJob() {
		
		String dateParam = new Date().toString();
        logger.debug("Notification Job start at  {}", dateParam);
        JobParameters param =
        		  new JobParametersBuilder().addString("date", dateParam).toJobParameters();
        JobExecution execution = null;
        try {
            execution = jobLauncher.run(notificationJob, param);	
    	} catch (Exception e) {
    		logger.error(e.toString());
    	} finally {
    		if(execution != null)
    			logger.debug("Notification Job Finish with Status -> " + execution.getStatus());
    	}
    }
	
	@Scheduled(cron = "${task.delete.expired.tokens}")
	public void deleteExpiredPasswordTokens(){
		logger.debug("Delete Expired Tokens ...");
		passwordResetTokenService.deleteExpiredTokens();
	}
	
	@Scheduled(cron = "${task.delete.unactivated.accounts}")
	public void deleteUnactivatedAccounts(){
		logger.debug("Delete Unactivated Accounts ...");
		Long total = parentService.deleteUnactivatedAccounts();
		logger.debug( total + " inactive accounts deleted");
	}
	
	@Scheduled(cron = "${task.cancel.account.deletion.proccess}")
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
