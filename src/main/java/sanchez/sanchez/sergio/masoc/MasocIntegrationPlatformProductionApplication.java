package sanchez.sanchez.sergio.masoc;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@Profile("prod")
@EnableHypermediaSupport(type = HAL)
@EnableMongoRepositories( value = { "sanchez.sanchez.sergio.masoc.persistence.repository" } )
@PropertySource({ "classpath:application.properties", "classpath:application-prod.properties"})
public class MasocIntegrationPlatformProductionApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MasocIntegrationPlatformProductionApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MasocIntegrationPlatformProductionApplication.class, args);
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
