package sanchez.sanchez.sergio.masoc.i18n.resolver;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import sanchez.sanchez.sergio.masoc.i18n.service.I18NService;

public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {
	
	private final I18NService i18NService;
	
	
	public SmartLocaleResolver(I18NService i18nService) {
		super();
		i18NService = i18nService;
	}


	@Override
    public Locale resolveLocale(HttpServletRequest request) {
		return i18NService.parseRangeLocaleOrDefault(request.getHeader("Accept-Language"));
    }

}
