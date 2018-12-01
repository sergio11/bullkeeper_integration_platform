package sanchez.sanchez.sergio.bullkeeper;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import sanchez.sanchez.sergio.bullkeeper.web.rest.interceptor.LoggingRequestInterceptor;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@SpringBootApplication
@Profile("dev")
@EnableHypermediaSupport(type = HAL)
public class BullKeeperIntegrationPlatformDevelopmentApplication {
	
	
    public static void main(String[] args) {
        SpringApplication.run(BullKeeperIntegrationPlatformDevelopmentApplication.class, args);
    }
}
