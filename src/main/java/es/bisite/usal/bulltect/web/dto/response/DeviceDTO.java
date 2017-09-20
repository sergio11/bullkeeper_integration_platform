package es.bisite.usal.bulltect.web.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceDTO extends ResourceSupport {

	@JsonProperty("device_id")
	private String deviceId;
	@JsonProperty("registration_token")
	private String registrationToken;
	@JsonProperty("type")
	private String type;
	@JsonProperty("create_at")
	private String createAt;
	@JsonProperty("notification_key_name")
	private String notificationKeyName;
	@JsonProperty("notification_key")
	private String notificationKey;
	
	public DeviceDTO(){}
	
	public DeviceDTO(String deviceId, String registrationToken, String type, 
			String createAt, String notificationKeyName, String notificationKey) {
		super();
		this.deviceId = deviceId;
		this.registrationToken = registrationToken;
		this.type = type;
		this.createAt = createAt;
		this.notificationKeyName = notificationKeyName;
		this.notificationKey = notificationKey;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public String getNotificationKeyName() {
		return notificationKeyName;
	}

	public void setNotificationKeyName(String notificationKeyName) {
		this.notificationKeyName = notificationKeyName;
	}

	public String getNotificationKey() {
		return notificationKey;
	}

	public void setNotificationKey(String notificationKey) {
		this.notificationKey = notificationKey;
	}
}
