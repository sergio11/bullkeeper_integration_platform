package sanchez.sanchez.sergio.masoc.i18n.service;

import java.util.Locale;

public interface I18NService {
	Locale parseLocale(String locale);
	boolean isValid(Locale locale);
	Locale parseLocaleOrDefault(String locale);
	Locale parseRangeLocaleOrDefault(String rangeLocale);
}
