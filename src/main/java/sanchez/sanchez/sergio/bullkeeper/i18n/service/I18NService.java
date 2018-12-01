package sanchez.sanchez.sergio.bullkeeper.i18n.service;

import java.util.Locale;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public interface I18NService {
	
	/**
	 * 
	 * @param locale
	 * @return
	 */
	Locale parseLocale(String locale);
	
	/**
	 * 
	 * @param locale
	 * @return
	 */
	boolean isValid(Locale locale);
	
	/**
	 * 
	 * @param locale
	 * @return
	 */
	Locale parseLocaleOrDefault(String locale);
	
	/**
	 * 
	 * @param rangeLocale
	 * @return
	 */
	Locale parseRangeLocaleOrDefault(String rangeLocale);
}
