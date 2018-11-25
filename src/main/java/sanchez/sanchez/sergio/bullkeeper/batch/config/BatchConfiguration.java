package sanchez.sanchez.sergio.bullkeeper.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sanchez.sanchez.sergio.bullkeeper.batch.AlertItemProcessor;
import sanchez.sanchez.sergio.bullkeeper.batch.AlertItemReader;
import sanchez.sanchez.sergio.bullkeeper.batch.SendNotificationsWriter;
import sanchez.sanchez.sergio.bullkeeper.batch.models.AlertsByGuardian;
import sanchez.sanchez.sergio.bullkeeper.batch.models.FCMNotificationWrapper;
import sanchez.sanchez.sergio.bullkeeper.exception.NoDeviceGroupForUserException;

/**
 * Batch Configuration
 * @author sergiosanchezsanchez
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	public final static String NOTIFICATION_JOB = "notification_job";
	
	private Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
    
	/**
	 * DB Name
	 */
	@Value("${spring.data.mongodb.database}")
	private String dbName;
	
	/**
	 * Job Name
	 */
    private final String JOB_NAME = "SEND_NOTIFICATION_JOB";
    
    
    /**
     * Job Builder
     */
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    /**
     * Step Builder
     */
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    /**
     * Item Processor
     */
    @Autowired
    private AlertItemProcessor itemProcessor;
    
    /**
     * Send Notification Writer
     */
    @Autowired
    private SendNotificationsWriter sendNotificationsWriter;
    
    
    /**
     * Alert Item Reader
     */
    @Autowired
    private AlertItemReader alertItemReader;
    
    
    /**
     * Transaction Manager
     * @return
     */
    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    /**
     * Map Job Repository Factory
     * @param txManager
     * @return
     * @throws Exception
     */
    @Bean
    public MapJobRepositoryFactoryBean mapJobRepositoryFactory(
            ResourcelessTransactionManager txManager) throws Exception {
        
        MapJobRepositoryFactoryBean factory = new 
                MapJobRepositoryFactoryBean(txManager);
        
        factory.afterPropertiesSet();
        
        return factory;
    }

    /**
     * Job Repository
     * @param factory
     * @return
     * @throws Exception
     */
    @Bean
    public JobRepository jobRepository(
            MapJobRepositoryFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    /**
     * Job Launcher
     * @param jobRepository
     * @return
     */
    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }
    
    
    /**
     * Provide Job Steps
     * @return
     */
    @SuppressWarnings("unchecked")
	@Bean
    public Step provideJobStep1() {
        return stepBuilderFactory.get("jobStep")
                .<AlertsByGuardian, FCMNotificationWrapper>chunk(1)
                .reader(alertItemReader)
                .processor(itemProcessor)
                .writer(sendNotificationsWriter)
                .faultTolerant()
                .skip(IllegalArgumentException.class)
                .skip(NoDeviceGroupForUserException.class)
                .skipLimit(100000)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
    
    
    /**
     * Provide Notification Job
     * @return
     */
    @Bean(name = NOTIFICATION_JOB)
    public Job provideNotificationJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(provideJobStep1())
                .end()
                .build();
    }

}
