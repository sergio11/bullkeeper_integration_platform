package sanchez.sanchez.sergio.security.config;

import java.io.Serializable;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

@Configuration
public class AuthenticationProvidersConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(AuthenticationProvidersConfig.class);
	
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Bean("adminAuthenticationProvider")
    public AuthenticationProvider adminAuthenticationProvider(
    		@Qualifier("AdminDetailsService") UserDetailsService adminDetails) {
    	Assert.notNull(adminDetails, "A AdminDetailsService for AuthenticationProviderConfig must be provided");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(adminDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    
    @Bean("parentsAuthenticationProvider")
    public AuthenticationProvider parentsAuthenticationProvider(
    		@Qualifier("ParentsDetailsService") UserDetailsService parentDetails) {
    	Assert.notNull(parentDetails, "A ParentsDetailsService for AuthenticationProviderConfig must be provided");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(parentDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    
    @Bean("ParentsAuthenticationManager")
    public AuthenticationManager parentsAuthenticationManager(
    		@Qualifier("parentsAuthenticationProvider") AuthenticationProvider parentsAuthenticationProvider) throws Exception {
    	return new ProviderManager(Arrays.asList(parentsAuthenticationProvider));
    }
    
    /*@Bean("AdminAuthenticationManager")
    public AuthenticationManager adminAuthenticationManager(
    		@Qualifier("adminAuthenticationProvider") AuthenticationProvider adminAuthenticationProvider) throws Exception {
    	return new ProviderManager(Arrays.asList(adminAuthenticationProvider));
    }*/

    
    
    @PostConstruct
    public void init(){
        logger.info("Init Database Authentication Config ...");
        Assert.state(passwordEncoder != null, "A PasswordEncoder for DatabaseAuthenticationConfig must be provided");
    }

}
