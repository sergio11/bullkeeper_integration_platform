package es.bisite.usal.bulltect.web.rest.config;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.HateoasSortHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.bisite.usal.bulltect.fcm.properties.FCMCustomProperties;
import es.bisite.usal.bulltect.fcm.utils.FCMErrorHandler;
import es.bisite.usal.bulltect.web.rest.interceptor.HeaderRequestInterceptor;
import es.bisite.usal.bulltect.web.rest.interceptor.LoggingRequestInterceptor;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;
    
    @Autowired
    private FCMCustomProperties fcmProperties;
    
    @Value("${platform.documentation.path}")
    private String platformDocumentationPath;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // move Swagger UI under /documentation
        registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs?group=bullTect");
        registry.addRedirectViewController("/documentation/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/documentation/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
        registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
        registry.addRedirectViewController("/documentation/configuration/ui", "/configuration/ui");
        registry.addRedirectViewController("/documentation/configuration/security", "/configuration/security");
        registry.addRedirectViewController("/", platformDocumentationPath);
        registry.addViewController("/backend/admin/login").setViewName("login");
        registry.addViewController("/backend/accounts/resetting/password-changed").setViewName("password_changed");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/documentation/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/documentation/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
    }
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(pageableResolver());
        argumentResolvers.add(sortResolver());
        argumentResolvers.add(pagedResourcesAssemblerArgumentResolver());
    }
    
   
    @Bean
    public HateoasSortHandlerMethodArgumentResolver sortResolver() {
        return new HateoasSortHandlerMethodArgumentResolver();
    }

    @Bean
    public HateoasPageableHandlerMethodArgumentResolver pageableResolver() {
        return new HateoasPageableHandlerMethodArgumentResolver(sortResolver());
    }

    @Bean
    public PagedResourcesAssembler<?> pagedResourcesAssembler() {
        return new PagedResourcesAssembler<Object>(pageableResolver(), null);
    }

    @Bean
    public PagedResourcesAssemblerArgumentResolver pagedResourcesAssemblerArgumentResolver() {
        return new PagedResourcesAssemblerArgumentResolver(pageableResolver(), null);
    }
    
    @Bean
    @Order(1)
    public ClientHttpRequestInterceptor provideAuthorizationInterceptor(){
    	return new HeaderRequestInterceptor("Authorization", String.format("key=%s", fcmProperties.getAppServerKey()));
    }
    
    @Bean
    @Order(2)
    public ClientHttpRequestInterceptor provideProjectId(){
    	return new HeaderRequestInterceptor("project_id", fcmProperties.getSenderId());
    }
    
    @Bean(name = "loggingRequestInterceptor")
	@Order(Ordered.LOWEST_PRECEDENCE)
	public ClientHttpRequestInterceptor provideLoggingRequestInterceptor(){
		return new LoggingRequestInterceptor();
	}
    
    @Bean
    public ClientHttpRequestFactory provideClientHttpRequestFactory() {
        int timeout = 5000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
          = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }
    
    @Bean
	public DefaultResponseErrorHandler provideResponseErrorHandler(){
		return new FCMErrorHandler();
	}

    @Bean("fcmRestTemplate")
    public RestTemplate fcmRestTemplate(ObjectMapper objectMapper, List<ClientHttpRequestInterceptor> interceptors,
    		MappingJackson2HttpMessageConverter converter, DefaultResponseErrorHandler responseErrorHandler) {
    	logger.debug("Total interceptors: " + interceptors.size());
        RestTemplate restTemplate = new RestTemplate(Collections.singletonList(converter));
        restTemplate.setInterceptors(interceptors);
        restTemplate.setErrorHandler(responseErrorHandler);
        return restTemplate;
    }
    
    @Bean("analysisRestTemplate")
    public RestTemplate analysisRestTemplate(ObjectMapper objectMapper, 
    		ClientHttpRequestFactory clientHttpRequestFactory, @Qualifier("loggingRequestInterceptor") ClientHttpRequestInterceptor loggingRequestInterceptor) {
    	RestTemplate rest =  new RestTemplate(Collections.singletonList(new FormHttpMessageConverter()));
    	rest.setRequestFactory(clientHttpRequestFactory);
    	rest.setInterceptors(Collections.singletonList(loggingRequestInterceptor));
    	return rest;
    }
    
    @PostConstruct
    protected void init(){
    	logger.debug("init WebConfig ...");
    }
}
