package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Preferences Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class PreferencesEntity {
	
	public enum RemoveAlertsEveryEnum { NEVER,  LAST_HOUR, LAST_DAY, LAST_MONTH }

	/**
	 * Id
	 */
	@Id
    private ObjectId id;
	
	/**
	 * Push Notifications Enabled
	 */
	@Field("push_notifications_enabled")
    private Boolean pushNotificationsEnabled = Boolean.TRUE;
	
	/**
	 * Remove Alerts Every
	 */
	@Field("remove_alerts_every")
    private RemoveAlertsEveryEnum removeAlertsEvery = RemoveAlertsEveryEnum.NEVER;
	
	public PreferencesEntity(){}

	@PersistenceConstructor
	public PreferencesEntity(ObjectId id, Boolean pushNotificationsEnabled, RemoveAlertsEveryEnum removeAlertsEvery) {
		super();
		this.id = id;
		this.pushNotificationsEnabled = pushNotificationsEnabled;
		this.removeAlertsEvery = removeAlertsEvery;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Boolean getPushNotificationsEnabled() {
		return pushNotificationsEnabled;
	}

	public void setPushNotificationsEnabled(Boolean pushNotificationsEnabled) {
		this.pushNotificationsEnabled = pushNotificationsEnabled;
	}

	public RemoveAlertsEveryEnum getRemoveAlertsEvery() {
		return removeAlertsEvery;
	}

	public void setRemoveAlertsEvery(RemoveAlertsEveryEnum removeAlertsEvery) {
		this.removeAlertsEvery = removeAlertsEvery;
	}
}
