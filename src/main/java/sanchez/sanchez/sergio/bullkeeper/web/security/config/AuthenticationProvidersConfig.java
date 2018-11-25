package sanchez.sanchez.sergio.bullkeeper.web.security.config;

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

/**
 * Authentication Provide Config
 * @author sergiosanchezsanchez
 *
 */
@Configuration
public class AuthenticationProvidersConfig {
	
	private Logger logger = LoggerFactory.getLogger(AuthenticationProvidersConfig.class);
	
    /**
     * Password Encoder
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    /**
     * 
     * @param adminDetails
     * @return
     */
    @Bean("adminAuthenticationProvider")
    public AuthenticationProvider adminAuthenticationProvider(
    		@Qualifier("AdminDetailsService") UserDetailsService adminDetails) {
    	Assert.notNull(adminDetails, "A AdminDetailsService for AuthenticationProviderConfig must be provided");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(adminDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    
    /**
     * Provide Guardians Authentication
     * @param guardianDetails
     * @return
     */
    @Bean("guardiansAuthenticationProvider")
    public AuthenticationProvider guardiansAuthenticationProvider(
    		@Qualifier("GuardiansDetailsService") final UserDetailsService guardianDetails) {
    	Assert.notNull(guardianDetails, "A GuardiansDetailsService for AuthenticationProviderConfig must be provided");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(guardianDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }
    
    /**
     * 
     * @param guardiansAuthenticationProvider
     * @return
     * @throws Exception
     */
    @Bean("GuardiansAuthenticationManager")
    public AuthenticationManager guardiansAuthenticationManager(
    		@Qualifier("guardiansAuthenticationProvider") 
    		final AuthenticationProvider guardiansAuthenticationProvider) throws Exception {
    	return new ProviderManager(Arrays.asList(guardiansAuthenticationProvider));
    }
    
  
    
    @PostConstruct
    public void init(){
        logger.info("Init Database Authentication Config ...");
        Assert.state(passwordEncoder != null, "A PasswordEncoder for DatabaseAuthenticationConfig must be provided");
    }

}
