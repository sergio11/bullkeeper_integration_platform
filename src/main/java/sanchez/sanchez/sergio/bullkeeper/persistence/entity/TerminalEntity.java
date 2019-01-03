package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

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
	 * Screen Status
	 */
	@Field("screen_status")
	private ScreenStatusEnum screenStatus = ScreenStatusEnum.STATE_OFF;
	
	/**
     * Bed Time Enabled
     */
	@Field("bed_time_enabled")
    private Boolean bedTimeEnabled;

    /**
     * Lock Screen Enabled
     */
	@Field("lock_screen_enabled")
    private Boolean lockScreenEnabled;

    /**
     * Lock Camera Enabled
     */
	@Field("lock_camera_enabled")
	private Boolean lockCameraEnabled;
	
	/**
     * Settings Enabled
     */
	@Field("settings_enabled")
	private Boolean settingsEnabled;
	
	/**
	 * KId
	 */
	@DBRef
	@Field("kid")
	private KidEntity kid;
	
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
	 * @param screenStatus
	 * @param bedTimeEnabled
	 * @param lockScreenEnabled
	 * @param lockCameraEnabled
	 * @param settingsEnabled
	 * @param kid
	 */
	@PersistenceConstructor
	public TerminalEntity(ObjectId id, String appVersionName, String appVersionCode, String osVersion,
			String sdkVersion, String manufacturer, String marketName, String model, String codeName,
			String deviceName, String deviceId, Date lastTimeUsed, Date createAt, 
			ScreenStatusEnum screenStatus, Boolean bedTimeEnabled,
			Boolean lockScreenEnabled, Boolean lockCameraEnabled, Boolean settingsEnabled, KidEntity kid) {
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
		this.screenStatus = screenStatus;
		this.bedTimeEnabled = bedTimeEnabled;
		this.lockScreenEnabled = lockScreenEnabled;
		this.lockCameraEnabled = lockCameraEnabled;
		this.settingsEnabled = settingsEnabled;
		this.kid = kid;
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
	
	public ScreenStatusEnum getScreenStatus() {
		return screenStatus;
	}

	public void setScreenStatus(ScreenStatusEnum screenStatus) {
		this.screenStatus = screenStatus;
	}

	public Boolean getBedTimeEnabled() {
		return bedTimeEnabled;
	}

	public Boolean getLockScreenEnabled() {
		return lockScreenEnabled;
	}

	public Boolean getLockCameraEnabled() {
		return lockCameraEnabled;
	}

	public void setBedTimeEnabled(Boolean bedTimeEnabled) {
		this.bedTimeEnabled = bedTimeEnabled;
	}

	public void setLockScreenEnabled(Boolean lockScreenEnabled) {
		this.lockScreenEnabled = lockScreenEnabled;
	}

	public void setLockCameraEnabled(Boolean lockCameraEnabled) {
		this.lockCameraEnabled = lockCameraEnabled;
	}
	
	public Boolean getSettingsEnabled() {
		return settingsEnabled;
	}

	public void setSettingsEnabled(Boolean settingsEnabled) {
		this.settingsEnabled = settingsEnabled;
	}

	public KidEntity getKid() {
		return kid;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "TerminalEntity [id=" + id + ", appVersionName=" + appVersionName + ", appVersionCode=" + appVersionCode
				+ ", osVersion=" + osVersion + ", sdkVersion=" + sdkVersion + ", manufacturer=" + manufacturer
				+ ", marketName=" + marketName + ", model=" + model + ", codeName=" + codeName + ", deviceName="
				+ deviceName + ", deviceId=" + deviceId + ", lastTimeUsed=" + lastTimeUsed + ", createAt=" + createAt
				+ ", screenStatus=" + screenStatus + ", bedTimeEnabled=" + bedTimeEnabled + ", lockScreenEnabled="
				+ lockScreenEnabled + ", lockCameraEnabled=" + lockCameraEnabled + ", settingsEnabled="
				+ settingsEnabled + ", kid=" + kid + "]";
	}

	
	
}
