package sanchez.sanchez.sergio.rest.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import sanchez.sanchez.sergio.config.CommonConfig;
import sanchez.sanchez.sergio.config.i18n.i18nConfig;
import sanchez.sanchez.sergio.config.rest.WebConfig;
import sanchez.sanchez.sergio.config.security.WebSecurityConfig;
import org.springframework.context.annotation.FilterType;

@Configuration
@TestPropertySource(
		  locations = "classpath:application-test.properties")
@Import(value = { EmbeddedMongoAutoConfiguration.class, CommonConfig.class, 
		i18nConfig.class, WebConfig.class, WebSecurityConfig.class })
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "sanchez.sanchez.sergio.config.*")})
public class RestConfig {
	
	private static Logger logger = LoggerFactory.getLogger(RestConfig.class);
	
	@PostConstruct
	protected void init(){
		logger.debug("Init Rest Test Config ....");
	}
}
