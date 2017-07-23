package sanchez.sanchez.sergio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;

/**
 *
 * @author sergio
 */
@Configuration
@IntegrationComponentScan
public class InfrastructureConfiguration {
    
    /**
     * 
     * MongoDbMessageSource is an instance of MessageSource which returns a Message with a payload 
     * which is the result of execution of a Query
     */
    @Bean
    @Autowired
    public MessageSource<Object> mongoMessageSource(MongoDbFactory mongo) {
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongo, new LiteralExpression("{'processed' : false}"));
        messageSource.setExpectSingleResult(false);
        //messageSource.setEntityClass(Product.class);
        messageSource.setCollectionNameExpression(new LiteralExpression("users"));
        return messageSource;
    }
    
}
