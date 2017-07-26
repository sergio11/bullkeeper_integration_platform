package sanchez.sanchez.sergio;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@ComponentScan
@EnableMongoRepositories
public class SpringIntegrationSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationSampleApplication.class, args);
    }
}
