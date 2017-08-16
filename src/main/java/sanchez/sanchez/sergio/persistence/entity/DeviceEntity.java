package sanchez.sanchez.sergio.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = DeviceEntity.COLLECTION_NAME)
public class DeviceEntity {
	
	public final static String COLLECTION_NAME = "devices";
	
	@Id
	private ObjectId id;
	
	@Field("registration_token")
	private String registrationToken;
	
	@Field("device_type")
	private DeviceTypeEnum deviceTypeEnum = DeviceTypeEnum.MOBILE;
	
	@Field("device_group")
	@DBRef
	private DeviceGroupEntity deviceGroup;
	
	@Field("create_at")
	private Date createAt = new Date();
	
	public DeviceEntity(){}
	

	public DeviceEntity(String registrationToken, DeviceGroupEntity deviceGroup) {
		super();
		this.registrationToken = registrationToken;
		this.deviceGroup = deviceGroup;
	}

	public DeviceEntity(String registrationToken, DeviceTypeEnum deviceTypeEnum, DeviceGroupEntity deviceGroup) {
		super();
		this.registrationToken = registrationToken;
		this.deviceTypeEnum = deviceTypeEnum;
		this.deviceGroup = deviceGroup;
	}



	@PersistenceConstructor
	public DeviceEntity(String registrationToken, DeviceTypeEnum deviceTypeEnum, DeviceGroupEntity deviceGroup,
			Date createAt) {
		super();
		this.registrationToken = registrationToken;
		this.deviceTypeEnum = deviceTypeEnum;
		this.deviceGroup = deviceGroup;
		this.createAt = createAt;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}

	public DeviceTypeEnum getDeviceTypeEnum() {
		return deviceTypeEnum;
	}

	public void setDeviceTypeEnum(DeviceTypeEnum deviceTypeEnum) {
		this.deviceTypeEnum = deviceTypeEnum;
	}

	public DeviceGroupEntity getDeviceGroup() {
		return deviceGroup;
	}

	public void setDeviceGroup(DeviceGroupEntity deviceGroup) {
		this.deviceGroup = deviceGroup;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

}
