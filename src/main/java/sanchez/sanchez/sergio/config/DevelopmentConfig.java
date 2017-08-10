package sanchez.sanchez.sergio.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author sergio
 */
@Configuration
@EnableAutoConfiguration
@Profile("dev")
@DependsOn("commonConfig")
public class DevelopmentConfig {
	
	private static Logger logger = LoggerFactory.getLogger(DevelopmentConfig.class);
    
    @PostConstruct
    protected void init(){
    	logger.debug("Init Development Config ....");
    }
}
