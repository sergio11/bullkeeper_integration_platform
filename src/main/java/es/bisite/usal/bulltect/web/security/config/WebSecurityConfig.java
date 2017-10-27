package es.bisite.usal.bulltect.web.security.config;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import es.bisite.usal.bulltect.persistence.entity.AuthorityEnum;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.security.handlers.CustomLoginSuccessHandler;
import es.bisite.usal.bulltect.web.security.jwt.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
@Import(value = { CommonSecurityConfig.class, AuthenticationProvidersConfig.class })
public class WebSecurityConfig  {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
    
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;
    
    
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
    
    
    /**
     * Security Configuration for Admin Dashboard
     */
    @Configuration
    @Order(1)
    public class AdminConfiguration extends WebSecurityConfigurerAdapter {
    	
    	
    	@Autowired
    	private PersistentTokenRepository persistentTokenRepository;
    	
    	@Autowired
        @Qualifier("adminAuthenticationProvider")
        private AuthenticationProvider authenticationProvider;
    	
    	
    	@Override
    	protected void configure(AuthenticationManagerBuilder auth) {
    		auth.authenticationProvider(authenticationProvider);
    	}

     
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .requestMatchers()
                    .antMatchers("/backend/**")
                .and()
                .antMatcher("/backend/accounts/**")
                	.anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/backend/admin/users/self/**")
                	.fullyAuthenticated()
                .antMatchers("/backend/admin/**")
                    .hasAuthority(AuthorityEnum.ROLE_ADMIN.name())
                .and()
                .formLogin()
                    .loginPage("/backend/admin/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(customLoginSuccessHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/backend/admin/logout"))
                    .logoutSuccessUrl("/backend/admin/login?logout")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/backend/admin/403")
                .and()
                .csrf()
                	.disable()
                .sessionManagement()
                	.maximumSessions(1)
                	.expiredUrl("/backend/admin/login?expired")
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
				.antMatchers("/api/v1/parents/auth/**", "/api/v1/admin/auth", "/api/v1/children/redirect")
					.permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/parents/", "/api/v1/parents/reset-password")
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
    
    @PostConstruct
    public void init(){
        logger.info("Init Web Security Configuration ...");
    }
}
