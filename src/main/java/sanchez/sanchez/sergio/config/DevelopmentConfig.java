package sanchez.sanchez.sergio.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.DefaultResponseErrorHandler;

import sanchez.sanchez.sergio.fcm.utils.FCMErrorHandler;
import sanchez.sanchez.sergio.rest.interceptor.LoggingRequestInterceptor;

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
	
	@Bean
	@Order(3)
	public ClientHttpRequestInterceptor provideLoggingRequestInterceptor(){
		return new LoggingRequestInterceptor();
	}
	
	@Bean
	public DefaultResponseErrorHandler provideResponseErrorHandler(){
		return new FCMErrorHandler();
	}
	
    @PostConstruct
    protected void init(){
    	logger.debug("Init Development Config ....");
    }
}
