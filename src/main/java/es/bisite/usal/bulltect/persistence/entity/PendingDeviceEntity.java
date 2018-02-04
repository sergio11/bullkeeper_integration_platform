package es.bisite.usal.bulltect.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = PendingDeviceEntity.COLLECTION_NAME)
public class PendingDeviceEntity {
	
	public final static String COLLECTION_NAME = "pending_devices";
	
	@Id
	private ObjectId id;
	
	@Field("device_id")
	private String deviceId;
	
	@Field("registration_token")
	private String registrationToken;
	
	@Field("owner")
	private ObjectId owner;
	
	@Field("failed_attempts")
	private Integer failedAttempts = 1;
	
	@Field("last_time_tried")
	private Date lastTimeTried = new Date();


	public PendingDeviceEntity(String deviceId, String registrationToken, ObjectId owner) {
		super();
		this.deviceId = deviceId;
		this.registrationToken = registrationToken;
		this.owner = owner;
	}

	@PersistenceConstructor
	public PendingDeviceEntity(ObjectId id, String deviceId, String registrationToken, ObjectId owner, Integer failedAttempts) {
		super();
		this.id = id;
		this.deviceId = deviceId;
		this.registrationToken = registrationToken;
		this.owner = owner;
		this.failedAttempts = failedAttempts;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public ObjectId getOwner() {
		return owner;
	}

	public void setOwner(ObjectId owner) {
		this.owner = owner;
	}

	public Integer getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(Integer failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	public Date getLastTimeTried() {
		return lastTimeTried;
	}

	public void setLastTimeTried(Date lastTimeTried) {
		this.lastTimeTried = lastTimeTried;
	}

}
