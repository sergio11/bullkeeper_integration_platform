package es.bisite.usal.bulltect.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class SaveUserSystemPreferencesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("push_notifications_enabled")
	private Boolean pushNotificationsEnabled;
	
	public SaveUserSystemPreferencesDTO(){}

	public SaveUserSystemPreferencesDTO(Boolean pushNotificationsEnabled) {
		super();
		this.pushNotificationsEnabled = pushNotificationsEnabled;
	}

	public Boolean isPushNotificationsEnabled() {
		return pushNotificationsEnabled;
	}

	public void setPushNotificationsEnabled(Boolean pushNotificationsEnabled) {
		this.pushNotificationsEnabled = pushNotificationsEnabled;
	}

}
