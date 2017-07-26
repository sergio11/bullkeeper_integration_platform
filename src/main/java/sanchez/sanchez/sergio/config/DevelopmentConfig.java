package sanchez.sanchez.sergio.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author sergio
 */
@Configuration
@EnableAutoConfiguration
@Profile("dev")
public class DevelopmentConfig {
    
}
