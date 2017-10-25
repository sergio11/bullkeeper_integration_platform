package es.bisite.usal.bulltect.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class PreferencesEntity {

	@Id
    private ObjectId id;
	
	@Field("push_notifications_enabled")
    private Boolean pushNotificationsEnabled = Boolean.TRUE;
	
	public PreferencesEntity(){}

	@PersistenceConstructor
	public PreferencesEntity(ObjectId id, Boolean pushNotificationsEnabled) {
		super();
		this.id = id;
		this.pushNotificationsEnabled = pushNotificationsEnabled;
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
}
