package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserSystemPreferencesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("push_notifications_enabled")
	private Boolean pushNotificationsEnabled;
	
	public UserSystemPreferencesDTO(){}

	public UserSystemPreferencesDTO(Boolean pushNotificationsEnabled) {
		super();
		this.pushNotificationsEnabled = pushNotificationsEnabled;
	}

	public Boolean getPushNotificationsEnabled() {
		return pushNotificationsEnabled;
	}

	public void setPushNotificationsEnabled(Boolean pushNotificationsEnabled) {
		this.pushNotificationsEnabled = pushNotificationsEnabled;
	}

}
