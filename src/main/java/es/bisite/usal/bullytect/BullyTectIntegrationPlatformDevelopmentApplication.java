package es.bisite.usal.bullytect;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import es.bisite.usal.bullytect.rest.interceptor.LoggingRequestInterceptor;

@SpringBootApplication
@Profile("dev")
@EnableHypermediaSupport(type = HAL)
@EnableMongoRepositories( value = { "es.bisite.usal.bullytect.persistence.repository" } )
public class BullyTectIntegrationPlatformDevelopmentApplication {
	
	@Bean
	@Order(3)
	public ClientHttpRequestInterceptor provideLoggingRequestInterceptor(){
		return new LoggingRequestInterceptor();
	}
	
    public static void main(String[] args) {
        SpringApplication.run(BullyTectIntegrationPlatformDevelopmentApplication.class, args);
    }
}
