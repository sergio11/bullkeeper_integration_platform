package sanchez.sanchez.sergio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
@EnableMongoRepositories
public class SpringIntegrationSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationSampleApplication.class, args);
    }
}
