package es.bisite.usal.bullytect.service;

public interface IMessageSourceResolver {
	String resolver(String key);
	String resolver(String key, Object[] params);
}
