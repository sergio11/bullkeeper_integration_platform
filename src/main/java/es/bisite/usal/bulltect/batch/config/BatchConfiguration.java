package es.bisite.usal.bulltect.batch.config;

import es.bisite.usal.bulltect.batch.AlertItemProcessor;
import es.bisite.usal.bulltect.batch.AlertItemReader;
import es.bisite.usal.bulltect.batch.SendNotificationsWriter;
import es.bisite.usal.bulltect.batch.models.AlertsByParent;
import es.bisite.usal.bulltect.batch.models.FCMNotificationWrapper;
import es.bisite.usal.bulltect.exception.NoDeviceGroupForUserException;
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

/**
 * @author sergio
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	public final static String NOTIFICATION_JOB = "notification_job";
	
	private Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
    
	@Value("${spring.data.mongodb.database}")
	private String dbName;
	
    private final String JOB_NAME = "SEND_NOTIFICATION_JOB";
    
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    
    @Autowired
    private AlertItemProcessor itemProcessor;
    
    @Autowired
    private SendNotificationsWriter sendNotificationsWriter;
    
    @Autowired
    private AlertItemReader alertItemReader;
    
    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public MapJobRepositoryFactoryBean mapJobRepositoryFactory(
            ResourcelessTransactionManager txManager) throws Exception {
        
        MapJobRepositoryFactoryBean factory = new 
                MapJobRepositoryFactoryBean(txManager);
        
        factory.afterPropertiesSet();
        
        return factory;
    }

    @Bean
    public JobRepository jobRepository(
            MapJobRepositoryFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }
    
    @SuppressWarnings("unchecked")
	@Bean
    public Step provideJobStep1() {
        return stepBuilderFactory.get("jobStep")
                .<AlertsByParent, FCMNotificationWrapper>chunk(1)
                .reader(alertItemReader)
                .processor(itemProcessor)
                .writer(sendNotificationsWriter)
                .faultTolerant()
                .skip(NoDeviceGroupForUserException.class)
                .skipLimit(100000)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
    
    @Bean(name = NOTIFICATION_JOB)
    public Job provideNotificationJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(provideJobStep1())
                .end()
                .build();
    }

}
