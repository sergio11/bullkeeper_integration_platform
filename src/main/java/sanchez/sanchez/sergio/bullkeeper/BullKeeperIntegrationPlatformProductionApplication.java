package sanchez.sanchez.sergio.bullkeeper;

import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@Profile("prod")
@EnableHypermediaSupport(type = HAL)
@PropertySource({ "classpath:application.properties", "classpath:application-prod.properties"})
public class BullKeeperIntegrationPlatformProductionApplication extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BullKeeperIntegrationPlatformProductionApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BullKeeperIntegrationPlatformProductionApplication.class, args);
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
