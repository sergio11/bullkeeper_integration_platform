package sanchez.sanchez.sergio.config.batch;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulingConfig {
	
	@Autowired
    private SimpleJobLauncher jobLauncher;

}
