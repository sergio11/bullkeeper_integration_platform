package sanchez.sanchez.sergio.config.security;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.AuthorityEnum;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.security.handlers.CustomLoginSuccessHandler;
import sanchez.sanchez.sergio.security.jwt.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Import(value = { CommonSecurityConfig.class, DatabaseAuthenticationConfig.class })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
    @Autowired
    private AuthenticationProvider authenticationProvider;
    
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();
        list.add(authenticationProvider);
        return new ProviderManager(list);
    }
    
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
   

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
    
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.authorizeRequests().anyRequest().permitAll();
    }
    
    
    /**
     * Security Configuration for Admin Dashboard
     */
    @Configuration
    @Order(1)
    public class AdminConfiguration extends WebSecurityConfigurerAdapter {
    	
    	@Autowired
    	private PersistentTokenRepository persistentTokenRepository;

        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .requestMatchers()
                    .antMatchers("/admin/**")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/users/self/**")
                	.fullyAuthenticated()
                .antMatchers("/admin/**")
                    .hasAuthority(AuthorityEnum.ROLE_ADMIN.name())
                .and()
                .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(customLoginSuccessHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout"))
                    .logoutSuccessUrl("/admin/login?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/admin/403")
                .and()
                .csrf()
                	.disable()
                .sessionManagement()
                	.maximumSessions(1)
                	.expiredUrl("/admin/login?expired")
                	.and()
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenRepository(persistentTokenRepository)
                    .tokenValiditySeconds(1209600);
        }
    }
    
    @Configuration
    @Order(2)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
    	
    	@Autowired
    	private JwtAuthenticationTokenFilter authenticationTokenFilter;
    
 
        protected void configure(HttpSecurity http) throws Exception {
        	http
        		.requestMatchers()
		            .antMatchers(ApiHelper.BASE_API_ANY_REQUEST)
		        .and()
        	.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**")
					.permitAll()
				.antMatchers(ApiHelper.AUTHENTICATION_ANY_REQUEST)
					.permitAll()
				.anyRequest()
					.authenticated()
			.and()
			.exceptionHandling()
            	.authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));

			// Custom JWT based security filter
        	http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        }
    }
    
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        	.antMatchers(
        			"/documentation/v2/api-docs",
        			"/documentation/swagger-resources/configuration/ui",
        			"/documentation/swagger-resources/configuration/security",
        			"/documentation/swagger-resources",
        			"/documentation/configuration/ui",
        			"/documentation/configuration/security",
        			"/documentation/swagger-ui.html**",
        			"/documentation/webjars/**",
        			"/v2/api-docs",
        			"/swagger-resources/configuration/ui",
        			"/swagger-resources/configuration/security",
        			"/swagger-resources",
        			"/configuration/ui",
        			"/configuration/security",
        			"/swagger-ui.html**",
        			"/webjars/**"
        	);
    }
    
    @PostConstruct
    public void init(){
        logger.info("Init Web Security Configuration ...");
        Assert.state(authenticationProvider != null, "A AuthenticationProvider for WebSecurityConfig must be provided");
    }
}
