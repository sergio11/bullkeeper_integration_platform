package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = DeviceGroupEntity.COLLECTION_NAME)
public class DeviceGroupEntity {

	public final static String COLLECTION_NAME = "devices_group";
	
	@Id
	private ObjectId id;
	
	@Field("notification_key_name")
	private String notificationKeyName;
	
	@Field("notification_key")
	private String notificationKey;
	
	@Field("create_at")
	private Date createAt = new Date();
	
	@Field("owner")
	private ParentEntity owner;
	
	public DeviceGroupEntity(){}
	
	public DeviceGroupEntity(String notificationKeyName, String notificationKey, ParentEntity owner) {
		this.notificationKeyName = notificationKeyName;
		this.notificationKey = notificationKey;
		this.owner = owner;
	}
	
	
	@PersistenceConstructor
	public DeviceGroupEntity(String notificationKeyName, String notificationKey, Date createAt, ParentEntity owner) {
		super();
		this.notificationKeyName = notificationKeyName;
		this.notificationKey = notificationKey;
		this.createAt = createAt;
		this.owner = owner;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public ParentEntity getOwner() {
		return owner;
	}

	public void setOwner(ParentEntity owner) {
		this.owner = owner;
	}
}
