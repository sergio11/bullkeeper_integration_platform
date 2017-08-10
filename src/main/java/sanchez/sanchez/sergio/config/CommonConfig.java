package sanchez.sanchez.sergio.config;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@Configuration("commonConfig")
@EnableHypermediaSupport(type = HAL)
@EnableMongoRepositories( value = { "sanchez.sanchez.sergio.persistence.repository" } )
public class CommonConfig {
	
	private static Logger logger = LoggerFactory.getLogger(CommonConfig.class);

	@PostConstruct
    protected void init(){
    	logger.debug("Init Common Config ....");
    }
}
