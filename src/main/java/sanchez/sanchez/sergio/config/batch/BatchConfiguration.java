package sanchez.sanchez.sergio.config.batch;

import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.Mongo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.batch.AlertItemProcesor;
import sanchez.sanchez.sergio.batch.MongoDBItemReader;
import sanchez.sanchez.sergio.fcm.operations.FCMNotificationOperation;
import sanchez.sanchez.sergio.persistence.entity.AlertEntity;

/**
 *
 * @author sergio
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {
	
	private Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
    
    private final String DB_NAME = "test";
    private final String JOB_NAME = "SEND_NOTIFICATION_JOB";
    
    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    public Mongo mongod;
    
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
    
    
    @Bean
    public MongoDBItemReader provideItemReader() {
        Assert.notNull(mongod, "Mongod can not be null");
        MongoDBItemReader reader = new MongoDBItemReader();
        reader.setMongo(mongod);
        reader.setDb(DB_NAME);
        reader.setCollection(AlertEntity.COLLECTION_NAME);
        reader.setQuery("{ delivered: {$ne: true}}");
        reader.setConverter(new Converter<DBObject, Map<String, String>>() {
			@Override
			public Map<String, String> convert(DBObject source) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("level", (String)source.get("level"));
				result.put("payload", (String)source.get("payload"));
				result.put("create_at", (String)source.get("payload"));
				result.put("son", ((DBRef)source.get("son")).getId().toString());
				return result;
			}
        });
        return reader;
    }
    
    @Bean
    public AlertItemProcesor provideProcessor(){
    	return new AlertItemProcesor();
    }
    
    @SuppressWarnings("unchecked")
	@Bean
    public Step provideJobStep1() {
        return stepBuilderFactory.get("jobStep")
                .<Object, FCMNotificationOperation>chunk(1)
                .reader(provideItemReader())
                .processor(provideProcessor())
                .writer(new ItemWriter<FCMNotificationOperation> () {
					@Override
					public void write(List<? extends FCMNotificationOperation> operations) throws Exception {
						logger.debug("Total Operations -> " + operations.size());
					}
                	
                })
                .build();
    }
    
    @Bean
    public Job provideNotificationJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(provideJobStep1())
                .end()
                .build();
    }
    
    
    @Scheduled(cron = "* */5 * * * ?")
    public void launchJob() throws Exception {
    	
    	try {
    		logger.debug("Notification Job Start -> " + new Date());
            JobParameters param = new JobParametersBuilder().addString("JobID",
                    String.valueOf(System.currentTimeMillis())).toJobParameters();

            JobExecution execution = jobLauncher.run(provideNotificationJob(), param);
            logger.debug("Notification Job Finish with Status -> " + execution.getStatus());	
    	} catch (Exception e) {
    		logger.error(e.toString());
    	}
    	
    }

}
