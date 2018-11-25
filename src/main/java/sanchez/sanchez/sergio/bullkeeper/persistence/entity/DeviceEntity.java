package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

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
	
	/**
	 * Id
	 */
	@Id
	private ObjectId id;
	
	/**
	 * Device ID
	 */
	@Field("device_id")
	private String deviceId;
	
	/**
	 * Registration Token
	 */
	@Field("registration_token")
	private String registrationToken;
	
	/**
	 * Device Type
	 */
	@Field("device_type")
	private DeviceTypeEnum deviceTypeEnum = DeviceTypeEnum.MOBILE;
	
	/**
	 * Device Group
	 */
	@Field("device_group")
	@DBRef
	private DeviceGroupEntity deviceGroup;
	
	/**
	 * Create At
	 */
	@Field("create_at")
	private Date createAt = new Date();
	
	public DeviceEntity(){}
	
	/**
	 * 
	 * @param deviceId
	 * @param registrationToken
	 * @param deviceGroup
	 */
	public DeviceEntity(String deviceId, String registrationToken, DeviceGroupEntity deviceGroup) {
		super();
		this.deviceId = deviceId;
		this.registrationToken = registrationToken;
		this.deviceGroup = deviceGroup;
	}

	/**
	 * 
	 * @param deviceId
	 * @param registrationToken
	 * @param deviceTypeEnum
	 * @param deviceGroup
	 */
	public DeviceEntity(String deviceId, String registrationToken, DeviceTypeEnum deviceTypeEnum, DeviceGroupEntity deviceGroup) {
		super();
		this.deviceId = deviceId;
		this.registrationToken = registrationToken;
		this.deviceTypeEnum = deviceTypeEnum;
		this.deviceGroup = deviceGroup;
	}

	/**
	 * 
	 * @param deviceId
	 * @param registrationToken
	 * @param deviceTypeEnum
	 * @param deviceGroup
	 * @param createAt
	 */
	@PersistenceConstructor
	public DeviceEntity(String deviceId, String registrationToken, DeviceTypeEnum deviceTypeEnum, DeviceGroupEntity deviceGroup,
			Date createAt) {
		super();
		this.deviceId = deviceId;
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
