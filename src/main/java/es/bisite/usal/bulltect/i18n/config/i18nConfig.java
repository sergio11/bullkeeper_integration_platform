
package es.bisite.usal.bulltect.i18n.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.annotation.PostConstruct;

import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import es.bisite.usal.bulltect.i18n.resolver.SmartLocaleResolver;
import es.bisite.usal.bulltect.i18n.service.I18NService;

@Configuration
public class i18nConfig {
	
	private static Logger logger = LoggerFactory.getLogger(i18nConfig.class);
    
    @Value("classpath:/${i18n.basename}")
    private String baseName;
	
    @Value("${i18n.cache.seconds}")
    private Integer cacheSeconds;
    
    @Value("${i18n.paramname}")
    private String paramName;
    

    @Bean(name="messageSource")
    public MessageSource  provideMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setCacheSeconds(cacheSeconds);
        messageSource.setBasename(baseName);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(true);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean(name="localeChangeInterceptor")
    public LocaleChangeInterceptor provideLocaleChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(paramName);
        return localeChangeInterceptor;
    }
    
    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver(I18NService i18nService) {
        return new SmartLocaleResolver(i18nService);
    }
    
    @Bean(name = "prettyTime")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public PrettyTime prettyTime() {
    	return new PrettyTime(LocaleContextHolder.getLocale());
    }
    
    
    @PostConstruct
    protected void init(){
    	logger.debug("init i18nConfig ...");
    	
    	
    }

}
