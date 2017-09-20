package es.bisite.usal.bulltect.fcm.operations;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FCMNotificationOperation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("to")
    private String notificationKey;
	@JsonProperty("data")
	private Map<String, String> data;
	
	public FCMNotificationOperation(){}
	
	public FCMNotificationOperation(String notificationKey, Map<String, String> data) {
		super();
		this.notificationKey = notificationKey;
		this.data = data;
	}

	public String getNotificationKey() {
		return notificationKey;
	}

	public void setNotificationKey(String notificationKey) {
		this.notificationKey = notificationKey;
	}

	public Map<String, String> getData() {
		return data;
	}
	
	public void setData(Map<String, String> data) {
		this.data = data;
	}
}
