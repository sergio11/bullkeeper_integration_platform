package sanchez.sanchez.sergio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class SpringIntegrationSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationSampleApplication.class, args);
	}
}
