package sanchez.sanchez.sergio.masoc.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class UserSystemPreferencesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("push_notifications_enabled")
	private Boolean pushNotificationsEnabled;
	
	@JsonProperty("remove_alerts_every")
    private String removeAlertsEvery;
	
	public UserSystemPreferencesDTO(){}

	public UserSystemPreferencesDTO(Boolean pushNotificationsEnabled, String removeAlertsEvery) {
		super();
		this.pushNotificationsEnabled = pushNotificationsEnabled;
		this.removeAlertsEvery = removeAlertsEvery;
	}


	public Boolean getPushNotificationsEnabled() {
		return pushNotificationsEnabled;
	}

	public void setPushNotificationsEnabled(Boolean pushNotificationsEnabled) {
		this.pushNotificationsEnabled = pushNotificationsEnabled;
	}

	public String getRemoveAlertsEvery() {
		return removeAlertsEvery;
	}

	public void setRemoveAlertsEvery(String removeAlertsEvery) {
		this.removeAlertsEvery = removeAlertsEvery;
	}
}
