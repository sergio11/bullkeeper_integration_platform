package sanchez.sanchez.sergio.masoc.i18n.service;

import java.util.Locale;

public interface IMessageSourceResolverService {

    String resolver(String key);
    
    String resolver(String key, Locale locale);

    String resolver(String key, Object[] params);

    String resolver(String key, Object[] params, Locale locale);
}
