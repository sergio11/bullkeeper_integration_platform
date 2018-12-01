package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidRemoveAlertsEvery;

/**
 * Save User System Preferences DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveUserSystemPreferencesDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Push Notifications Enabled
	 */
	@JsonProperty("push_notifications_enabled")
	private Boolean pushNotificationsEnabled;
	
	/**
	 * Remove Alerts Every
	 */
	@ValidRemoveAlertsEvery(message = "{user.preference.remove.alerts.every.not.valid}")
	@JsonProperty("remove_alerts_every")
	private String removeAlertsEvery;
	
	public SaveUserSystemPreferencesDTO(){}

	public SaveUserSystemPreferencesDTO(Boolean pushNotificationsEnabled, String removeAlertsEvery) {
		super();
		this.pushNotificationsEnabled = pushNotificationsEnabled;
		this.removeAlertsEvery = removeAlertsEvery;
	}

	public Boolean isPushNotificationsEnabled() {
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

	@Override
	public String toString() {
		return "SaveUserSystemPreferencesDTO [pushNotificationsEnabled=" + pushNotificationsEnabled
				+ ", removeAlertsEvery=" + removeAlertsEvery + "]";
	}
}
