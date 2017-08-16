package sanchez.sanchez.sergio.service.impl;

import java.util.Locale;

import javax.annotation.PostConstruct;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.service.IMessageSourceResolver;

@Service
public class MessageSourceResolver implements IMessageSourceResolver {
	
	private final MessageSource messageSource;
    
	public MessageSourceResolver(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}

	@Override
	public String resolver(String key) {
		Assert.notNull(key, "key can not be null");
		return messageSource.getMessage(key, new Object[]{}, LocaleContextHolder.getLocale());
	}
	
	@Override
	public String resolver(String key, Object[] params) {
		Assert.notNull(key, "key can not be null");
		Assert.notEmpty(params, "You must provide parameters");
		return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
	}
	
	@PostConstruct
	protected void init(){
		Assert.notNull(messageSource, "Message Source can not be null");
	}
}
