package sanchez.sanchez.sergio.config.batch;

import com.mongodb.Mongo;
import io.jsonwebtoken.lang.Assert;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sanchez.sanchez.sergio.batch.MongoDBItemReader;
import sanchez.sanchez.sergio.persistence.entity.AlertEntity;

/**
 *
 * @author sergio
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    
    private final String DB_NAME = "test";
    
    private final String JOB_NAME = "SEND_NOTIFICATION_JOB";
    
    @Bean
    public ItemReader provideItemReader(Mongo mongod) {
        Assert.notNull(mongod, "Mongod can not be null");
        MongoDBItemReader reader = new MongoDBItemReader();
        reader.setMongo(mongod);
        reader.setDb(DB_NAME);
        reader.setCollection(AlertEntity.COLLECTION_NAME);
        reader.setQuery("{ delivered: {$ne: true}}");
        return reader;
    }
    
    @Bean
    public Step jobStep(StepBuilderFactory stepBuilderFactory, ItemReader reader) {
        return stepBuilderFactory.get("jobStep")
                .chunk(1)
                .reader(reader)
                .build();
    }
    
    @Bean
    public Job sendNotificationJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(s1)
                .end()
                .build();
    }
}
