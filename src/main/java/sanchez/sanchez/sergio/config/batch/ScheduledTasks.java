package sanchez.sanchez.sergio.config.batch;

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

@Configuration
@EnableScheduling
public class ScheduledTasks {
	
    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@Autowired
	@Qualifier(BatchConfiguration.NOTIFICATION_JOB)
	private Job notificationJob;
	
	@Autowired
	private SimpleJobLauncher jobLauncher;
	
	
	@Scheduled(
		initialDelayString = "${job.notification.scheduling.initial.delay}",
		fixedRateString = "${job.notification.scheduling.fixed.rate}"
	)
    public void scheduleNotificationJob() {
        log.debug("Notification Job start at  {}", dateFormat.format(new Date()));
        JobExecution execution = null;
        try {
            execution = jobLauncher.run(notificationJob, new JobParameters());	
    	} catch (Exception e) {
    		log.error(e.toString());
    	} finally {
    		if(execution != null)
    			log.debug("Notification Job Finish with Status -> " + execution.getStatus());
    	}
    }
	
	@PostConstruct
	protected void init(){
		Assert.notNull(notificationJob, "Notification Job can not be null");
		Assert.notNull(jobLauncher, "Job Launcher can not be null");
	}

}
