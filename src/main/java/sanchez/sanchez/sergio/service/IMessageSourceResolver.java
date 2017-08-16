package sanchez.sanchez.sergio.service;

public interface IMessageSourceResolver {
	String resolver(String key);
	String resolver(String key, Object[] params);
}
