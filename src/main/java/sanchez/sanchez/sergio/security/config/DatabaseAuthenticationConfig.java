package sanchez.sanchez.sergio.security.config;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@Configuration
public class DatabaseAuthenticationConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(DatabaseAuthenticationConfig.class);
	
	@Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    
    @PostConstruct
    public void init(){
        logger.info("Init Database Authentication Config ...");
        Assert.state(userDetailsService != null, "A UserDetailsService for DatabaseAuthenticationConfig must be provided");
        Assert.state(passwordEncoder != null, "A PasswordEncoder for DatabaseAuthenticationConfig must be provided");
    }

}
