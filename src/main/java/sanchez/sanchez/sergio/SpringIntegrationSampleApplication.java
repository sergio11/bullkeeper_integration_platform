package sanchez.sanchez.sergio;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType.HAL;


@ComponentScan
@EnableHypermediaSupport(type = HAL)
@EnableMongoRepositories
public class SpringIntegrationSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationSampleApplication.class, args);
    }
}
