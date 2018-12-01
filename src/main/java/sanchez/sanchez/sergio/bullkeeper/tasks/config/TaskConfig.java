package sanchez.sanchez.sergio.bullkeeper.tasks.config;

import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class TaskConfig implements SchedulingConfigurer {
		
	private final int POOL_SIZE = 10;

	
	private static final Logger logger = LoggerFactory.getLogger(TaskConfig.class);

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		scheduledTaskRegistrar.setScheduler(taskExecutor());
	}

	
	@Bean(destroyMethod="shutdown")
	public Executor taskExecutor() {
		logger.debug("Create Thread Pool Task Scheduler ...");
		final ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
		threadPoolTaskScheduler.setThreadNamePrefix("task-pool-thread");
		return threadPoolTaskScheduler;
	}
	
	@PostConstruct
	protected void init(){
		logger.debug("Init Task Scheduling Config ....");
	}
}
