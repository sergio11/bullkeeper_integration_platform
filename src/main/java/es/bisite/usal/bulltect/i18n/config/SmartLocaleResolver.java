package es.bisite.usal.bulltect.i18n.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

public class SmartLocaleResolver extends AcceptHeaderLocaleResolver {
	
	List<Locale> LOCALES = Arrays.asList(new Locale("en"),
            new Locale("es"),
            new Locale("es-US"),
            new Locale("fr"));
	
	@Override
    public Locale resolveLocale(HttpServletRequest request) {
            if (StringUtils.isBlank(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
            }
            List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
            Locale locale = Locale.lookup(list, LOCALES);
            return locale ;
    }

}
