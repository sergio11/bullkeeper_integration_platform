package sanchez.sanchez.sergio.bullkeeper.tasks.impl.alerts;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.batch.config.BatchConfiguration;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PreferencesEntity.RemoveAlertsEveryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AlertRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.util.Utils;

/**
 * Alerts Tasks
 * @author ssanchez
 *
 */
@Component
public class AlertsTasks {
	
	private static final Logger logger = LoggerFactory.getLogger(AlertsTasks.class);
	
    private final Job notificationJob;
    private final SimpleJobLauncher jobLauncher;
    private final GuardianRepository parentRepository;
    private final AlertRepository alertRepository;
    
    /**
     * Alerts Tasks
     * @param notificationJob
     * @param jobLauncher
     * @param parentRepository
     * @param alertRepository
     */
    @Autowired
    public AlertsTasks(@Qualifier(BatchConfiguration.NOTIFICATION_JOB) Job notificationJob, 
    		SimpleJobLauncher jobLauncher, GuardianRepository parentRepository, AlertRepository alertRepository){
    	
    	this.notificationJob = notificationJob;
    	this.jobLauncher = jobLauncher;
    	this.parentRepository = parentRepository;
    	this.alertRepository = alertRepository;
    }
   
	/**
	 * Sending Push Notifications
	 */
	@Scheduled(cron = "${task.alerts.sending.push.notifications}")
    public void sendingPushNotifications() {

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
	
	/**
	 * Delete Alerts Every Hour
	 */
	@Scheduled(cron = "0 0 * * * *")
	public void deleteAlertsEveryHour() {
		alertRepository.deleteByGuardianIdInAndCreateAtLessThanEqual(parentRepository.getGuardianIdsWithRemoveAlertsEveryAs(RemoveAlertsEveryEnum.LAST_HOUR), 
				Utils.getDateNHoursAgo(1));
	}
	
	/**
	 * Delete Alerts Every Day
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void deleteAlertsEveryDay() {
		alertRepository.deleteByGuardianIdInAndCreateAtLessThanEqual(parentRepository.getGuardianIdsWithRemoveAlertsEveryAs(RemoveAlertsEveryEnum.LAST_DAY), 
				Utils.getDateNDaysAgo(1));
	}
	
	/**
	 * Delete Alerts Every Month
	 */
	@Scheduled(cron = "0 0 0 1 1/1 *")
	public void deleteAlertsEveryMonth() {
		alertRepository.deleteByGuardianIdInAndCreateAtLessThanEqual(parentRepository.getGuardianIdsWithRemoveAlertsEveryAs(RemoveAlertsEveryEnum.LAST_MONTH), 
				Utils.getDateNMonthAgo(1));
	}
	
	
	@PostConstruct
    protected void init() {
		Assert.notNull(notificationJob, "Notification job can not be null");
        Assert.notNull(jobLauncher, "Job Launcher can not be null");
        Assert.notNull(parentRepository, "Parent Repository can not be null");
        Assert.notNull(alertRepository, "Alert Repository can not be null");
        logger.debug("init Alerts Tasks  ...");
	}

}
