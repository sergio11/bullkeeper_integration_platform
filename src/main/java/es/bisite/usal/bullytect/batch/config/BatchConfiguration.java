package es.bisite.usal.bullytect.batch.config;

import com.mongodb.DBObject;
import com.mongodb.DBRef;
import com.mongodb.Mongo;
import es.bisite.usal.bullytect.batch.AlertItemProcessor;
import es.bisite.usal.bullytect.batch.MongoDBItemReader;
import es.bisite.usal.bullytect.batch.SendNotificationsWriter;
import es.bisite.usal.bullytect.exception.NoDeviceGroupForUserException;
import es.bisite.usal.bullytect.fcm.operations.FCMNotificationOperation;
import es.bisite.usal.bullytect.persistence.entity.AlertEntity;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.bson.types.ObjectId;
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
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

/**
 *
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
    public Mongo mongod;
    
    @Autowired
    private AlertItemProcessor itemProcessor;
    
    @Autowired
    private SendNotificationsWriter sendNotificationsWriter;
    
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
        reader.setDb(dbName);
        reader.setCollection(AlertEntity.COLLECTION_NAME);
        reader.setQuery("{ delivered: { $ne: true }, delivery_mode: { $eq: 'PUSH_NOTIFICATION' } }");
        reader.setConverter(new Converter<DBObject, Map<String, String>>() {
			@Override
			public Map<String, String> convert(DBObject source) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("id", ((ObjectId)source.get("_id")).toString());
				result.put("level", (String)source.get("level"));
				result.put("payload", (String)source.get("payload"));
				Date createAt = (Date)source.get("create_at");
				if(createAt != null)
					result.put("create_at", createAt.toString());
				result.put("parent", ((DBRef)source.get("parent")).getId().toString());
				return result;
			}
        });
        return reader;
    }
    

    @SuppressWarnings("unchecked")
	@Bean
    public Step provideJobStep1() {
        return stepBuilderFactory.get("jobStep")
                .<Object, FCMNotificationOperation>chunk(10)
                .reader(provideItemReader())
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
