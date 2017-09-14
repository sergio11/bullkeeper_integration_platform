package es.bisite.usal.bullytect.dto.response;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceGroupDTO extends ResourceSupport {

	@JsonProperty("identity")
	private String identity;
	@JsonProperty("notification_key_name")
	private String notificationKeyName;
	@JsonProperty("notification_key")
	private String notificationKey;
	@JsonProperty("create_at")
	private String createAt;
	
	public DeviceGroupDTO(){}
	
	public DeviceGroupDTO(String identity, String notificationKeyName, String notificationKey, String createAt) {
		super();
		this.identity = identity;
		this.notificationKeyName = notificationKeyName;
		this.notificationKey = notificationKey;
		this.createAt = createAt;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
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

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

}
