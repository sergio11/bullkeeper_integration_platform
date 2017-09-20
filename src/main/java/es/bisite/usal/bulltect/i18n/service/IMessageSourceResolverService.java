package es.bisite.usal.bulltect.i18n.service;

public interface IMessageSourceResolverService {
	String resolver(String key);
	String resolver(String key, Object[] params);
}
