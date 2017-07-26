package sanchez.sanchez.sergio.config;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author sergio
 */
@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
@Profile("prod")
public class ProductionConfig {
    
    private static Logger logger = LoggerFactory.getLogger(ProductionConfig.class);

    @PostConstruct
    protected void init() {
        logger.info("Init Production Config ...");
    }
}
