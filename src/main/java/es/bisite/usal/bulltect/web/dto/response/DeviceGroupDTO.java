package es.bisite.usal.bulltect.web.dto.response;

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
	@JsonProperty("owner")
	private String owner;
	
	public DeviceGroupDTO(){}
	
	public DeviceGroupDTO(String identity, String notificationKeyName, String notificationKey, String createAt, String owner) {
		super();
		this.identity = identity;
		this.notificationKeyName = notificationKeyName;
		this.notificationKey = notificationKey;
		this.createAt = createAt;
		this.owner = owner;
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "DeviceGroupDTO [identity=" + identity + ", notificationKeyName=" + notificationKeyName
				+ ", notificationKey=" + notificationKey + ", createAt=" + createAt + "]";
	}

}
