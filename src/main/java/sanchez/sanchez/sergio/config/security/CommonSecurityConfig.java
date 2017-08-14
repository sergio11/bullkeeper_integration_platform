package sanchez.sanchez.sergio.config.security;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import sanchez.sanchez.sergio.persistence.repository.RememberMeTokenRepository;
import sanchez.sanchez.sergio.security.CustomPersistentTokenRepositoryImpl;

@Configuration
public class CommonSecurityConfig implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(CommonSecurityConfig.class);

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Bean
	public PersistentTokenRepository persistentTokenRepository(RememberMeTokenRepository rememberMeTokenRepository) {
		return new CustomPersistentTokenRepositoryImpl(rememberMeTokenRepository);
	}
    
    @PostConstruct
    protected void init() {
        logger.info("init Common Security Config ...");
    }
}
