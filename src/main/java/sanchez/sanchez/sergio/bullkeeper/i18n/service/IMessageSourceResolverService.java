package sanchez.sanchez.sergio.bullkeeper.i18n.service;

import java.util.Locale;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public interface IMessageSourceResolverService {

	/**
	 * 
	 * @param key
	 * @return
	 */
    String resolver(String key);
    
    /**
     * 
     * @param key
     * @param locale
     * @return
     */
    String resolver(String key, Locale locale);

    /**
     * 
     * @param key
     * @param params
     * @return
     */
    String resolver(String key, Object[] params);

    /**
     * 
     * @param key
     * @param params
     * @param locale
     * @return
     */
    String resolver(String key, Object[] params, Locale locale);
}
