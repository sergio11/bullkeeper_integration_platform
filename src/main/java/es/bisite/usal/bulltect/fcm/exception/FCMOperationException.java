package es.bisite.usal.bulltect.fcm.exception;

import java.util.Map;

public class FCMOperationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> properties;

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
}
