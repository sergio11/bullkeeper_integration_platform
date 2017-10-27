package es.bisite.usal.bulltect.tasks.config;


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
import io.jsonwebtoken.lang.Assert;

@Configuration
@EnableScheduling
public class AlertsTasksConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(AlertsTasksConfig.class);
	
    private final Job notificationJob;
    private final SimpleJobLauncher jobLauncher;
    
    @Autowired
    public AlertsTasksConfig(@Qualifier(BatchConfiguration.NOTIFICATION_JOB) Job notificationJob, 
    		SimpleJobLauncher jobLauncher){
    	
    	this.notificationJob = notificationJob;
    	this.jobLauncher = jobLauncher;
    	
    }
   
	
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
	
	@PostConstruct
    protected void init() {
		Assert.notNull(notificationJob, "Notification job can not be null");
        Assert.notNull(jobLauncher, "Job Launcher can not be null");
        logger.debug("AlertsTasksConfig initialized ...");
	}

}
