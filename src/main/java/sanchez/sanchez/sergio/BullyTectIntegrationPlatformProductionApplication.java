package sanchez.sanchez.sergio;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@Profile("prod")
@EnableHypermediaSupport(type = HAL)
@EnableMongoRepositories( value = { "sanchez.sanchez.sergio.persistence.repository" } )
public class BullyTectIntegrationPlatformProductionApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BullyTectIntegrationPlatformProductionApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BullyTectIntegrationPlatformProductionApplication.class, args);
    }
}
