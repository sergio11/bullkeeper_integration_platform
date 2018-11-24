package sanchez.sanchez.sergio.masoc.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Terminal Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = TerminalEntity.COLLECTION_NAME)
public class TerminalEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "terminals";
	
	/**
	 * Terminal ID
	 */
	@Id
	private ObjectId id;
	
	/**
	 * App Version Name
	 */
	@Field("app_version_name")
	private String appVersionName;
	
	/**
	 * App Version Code
	 */
	@Field("app_version_code")
	private String appVersionCode;
	
	/**
	 * Os Version
	 */
	@Field("os_version")
	private String osVersion;
	
	/**
	 * SDK Version
	 */
	@Field("sdk_version")
	private String sdkVersion;
	
	/**
	 * Manufacturer
	 */
	@Field("manufacturer")
	private String manufacturer;
	
	/**
	 * Market Name
	 */
	@Field("market_name")
	private String marketName;
	
	/**
	 * Model
	 */
	@Field("model")
	private String model;
	
	/**
	 * Code Name
	 */
	@Field("code_name")
	private String codeName;
	
	/**
	 * Device Name
	 */
	@Field("device_name")
	private String deviceName;
	
	/**
	 * Device ID
	 */
	@Field("device_id")
	private String deviceId;
	
	/**
	 * Last Time Used
	 */
	@Field("last_time_used")
	private Date lastTimeUsed = new Date();

	/**
	 * Create At
	 */
	@Field("create_at")
	private Date createAt = new Date();
	
	/**
	 * Son
	 */
	@DBRef
	private SonEntity sonEntity;
	
	public TerminalEntity() {}

	/**
	 * 
	 * @param id
	 * @param appVersionName
	 * @param appVersionCode
	 * @param osVersion
	 * @param sdkVersion
	 * @param manufacturer
	 * @param marketName
	 * @param model
	 * @param codeName
	 * @param deviceName
	 * @param deviceId
	 * @param lastTimeUsed
	 * @param createAt
	 * @param sonEntity
	 */
	@PersistenceConstructor
	public TerminalEntity(ObjectId id, String appVersionName, String appVersionCode, String osVersion,
			String sdkVersion, String manufacturer, String marketName, String model, String codeName,
			String deviceName, String deviceId, Date lastTimeUsed, Date createAt, SonEntity sonEntity) {
		super();
		this.id = id;
		this.appVersionName = appVersionName;
		this.appVersionCode = appVersionCode;
		this.osVersion = osVersion;
		this.sdkVersion = sdkVersion;
		this.manufacturer = manufacturer;
		this.marketName = marketName;
		this.model = model;
		this.codeName = codeName;
		this.deviceName = deviceName;
		this.deviceId = deviceId;
		this.lastTimeUsed = lastTimeUsed;
		this.createAt = createAt;
		this.sonEntity = sonEntity;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getAppVersionName() {
		return appVersionName;
	}

	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}

	public String getAppVersionCode() {
		return appVersionCode;
	}

	public void setAppVersionCode(String appVersionCode) {
		this.appVersionCode = appVersionCode;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	

	public Date getLastTimeUsed() {
		return lastTimeUsed;
	}

	public void setLastTimeUsed(Date lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public SonEntity getSonEntity() {
		return sonEntity;
	}

	public void setSonEntity(SonEntity sonEntity) {
		this.sonEntity = sonEntity;
	}
	
}
