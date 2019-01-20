package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

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
    private Boolean bedTimeEnabled = true;

    /**
     * Screen Enabled
     */
	@Field("screen_enabled")
    private Boolean screenEnabled = true;

    /**
     * Camera Enabled
     */
	@Field("camera_enabled")
	private Boolean cameraEnabled = true;
	
	/**
     * Settings Enabled
     */
	@Field("settings_enabled")
	private Boolean settingsEnabled = false;
	
	/**
     * Location Permission Enabled
     */
	@Field("location_permission_enabled")
    private Boolean locationPermissionEnabled;

    /**
     * Call History Permission Enabled
     */
	@Field("call_history_permission_enabled")
    private Boolean callsHistoryPermissionEnabled;

    /**
     * Contacts List Permission Enabled
     */
	@Field("contacts_list_permission_enabled")
    private Boolean contactsListPermissionEnabled;

    /**
     * Text Message Permission Enabled
     */
	@Field("text_message_permission_enabled")
    private Boolean textMessagePermissionEnabled;

    /**
     * Storage Permission Enabled
     */
	@Field("storage_permission_enabled")
    private Boolean storagePermissionEnabled;

    /**
     * Usage Stats Allowed
     */
    @Field("usage_stats_allowed")
    private Boolean usageStatsAllowed;

    /**
     * Admin Access Allowed
     */
    @Field("admin_access_allowed")
	private Boolean adminAccessAllowed;
	
	/**
	 * KId
	 */
	@DBRef
	@Field("kid")
	private KidEntity kid;
	
    /**
     * Fun Time
     */
    @Field("funtime_scheduled")
    @CascadeSave
    private FunTimeScheduledEntity funTimeScheduled = new FunTimeScheduledEntity();
	
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
	 * @param screenEnabled
	 * @param cameraEnabled
	 * @param settingsEnabled
	 * @param locationPermissionEnabled
	 * @param callsHistoryPermissionEnabled
	 * @param contactsListPermissionEnabled
	 * @param textMessagePermissionEnabled
	 * @param kid
	 * @param funTimeScheduled
	 */
	@PersistenceConstructor
	public TerminalEntity(ObjectId id, String appVersionName, String appVersionCode, String osVersion,
			String sdkVersion, String manufacturer, String marketName, String model, String codeName,
			String deviceName, String deviceId, Date lastTimeUsed, Date createAt, 
			ScreenStatusEnum screenStatus, Boolean bedTimeEnabled,
			Boolean screenEnabled, Boolean cameraEnabled, Boolean settingsEnabled, 
			final Boolean locationPermissionEnabled, final Boolean callsHistoryPermissionEnabled,
			final Boolean contactsListPermissionEnabled, 
			final Boolean textMessagePermissionEnabled, final Boolean storagePermissionEnabled,
			final Boolean usageStatsAllowed, final Boolean adminAccessAllowed, 
			KidEntity kid, FunTimeScheduledEntity funTimeScheduled) {
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
		this.screenEnabled = screenEnabled;
		this.cameraEnabled = cameraEnabled;
		this.settingsEnabled = settingsEnabled;
		this.locationPermissionEnabled = locationPermissionEnabled;
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
		this.storagePermissionEnabled = storagePermissionEnabled;
		this.adminAccessAllowed = adminAccessAllowed;
		this.usageStatsAllowed = usageStatsAllowed;
		this.kid = kid;
		this.funTimeScheduled = funTimeScheduled;
	}

	public ObjectId getId() {
		return id;
	}

	public String getAppVersionName() {
		return appVersionName;
	}

	public String getAppVersionCode() {
		return appVersionCode;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getSdkVersion() {
		return sdkVersion;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public String getMarketName() {
		return marketName;
	}

	public String getModel() {
		return model;
	}

	public String getCodeName() {
		return codeName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public Date getLastTimeUsed() {
		return lastTimeUsed;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public ScreenStatusEnum getScreenStatus() {
		return screenStatus;
	}

	public Boolean getBedTimeEnabled() {
		return bedTimeEnabled;
	}

	public Boolean getScreenEnabled() {
		return screenEnabled;
	}

	public Boolean getCameraEnabled() {
		return cameraEnabled;
	}

	public Boolean getSettingsEnabled() {
		return settingsEnabled;
	}

	public KidEntity getKid() {
		return kid;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}

	public void setAppVersionCode(String appVersionCode) {
		this.appVersionCode = appVersionCode;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public void setSdkVersion(String sdkVersion) {
		this.sdkVersion = sdkVersion;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setLastTimeUsed(Date lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void setScreenStatus(ScreenStatusEnum screenStatus) {
		this.screenStatus = screenStatus;
	}

	public void setBedTimeEnabled(Boolean bedTimeEnabled) {
		this.bedTimeEnabled = bedTimeEnabled;
	}

	public void setScreenEnabled(Boolean screenEnabled) {
		this.screenEnabled = screenEnabled;
	}

	public void setCameraEnabled(Boolean cameraEnabled) {
		this.cameraEnabled = cameraEnabled;
	}

	public void setSettingsEnabled(Boolean settingsEnabled) {
		this.settingsEnabled = settingsEnabled;
	}
	
	

	public Boolean getLocationPermissionEnabled() {
		return locationPermissionEnabled;
	}

	public Boolean getCallsHistoryPermissionEnabled() {
		return callsHistoryPermissionEnabled;
	}

	public Boolean getContactsListPermissionEnabled() {
		return contactsListPermissionEnabled;
	}

	public Boolean getTextMessagePermissionEnabled() {
		return textMessagePermissionEnabled;
	}

	public Boolean getStoragePermissionEnabled() {
		return storagePermissionEnabled;
	}

	public Boolean getUsageStatsAllowed() {
		return usageStatsAllowed;
	}

	public Boolean getAdminAccessAllowed() {
		return adminAccessAllowed;
	}

	public void setLocationPermissionEnabled(Boolean locationPermissionEnabled) {
		this.locationPermissionEnabled = locationPermissionEnabled;
	}

	public void setCallsHistoryPermissionEnabled(Boolean callsHistoryPermissionEnabled) {
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
	}

	public void setContactsListPermissionEnabled(Boolean contactsListPermissionEnabled) {
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
	}

	public void setTextMessagePermissionEnabled(Boolean textMessagePermissionEnabled) {
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
	}

	public void setStoragePermissionEnabled(Boolean storagePermissionEnabled) {
		this.storagePermissionEnabled = storagePermissionEnabled;
	}

	public void setUsageStatsAllowed(Boolean usageStatsAllowed) {
		this.usageStatsAllowed = usageStatsAllowed;
	}

	public void setAdminAccessAllowed(Boolean adminAccessAllowed) {
		this.adminAccessAllowed = adminAccessAllowed;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}
	
	public FunTimeScheduledEntity getFunTimeScheduled() {
		return funTimeScheduled;
	}

	public void setFunTimeScheduled(FunTimeScheduledEntity funTimeScheduled) {
		this.funTimeScheduled = funTimeScheduled;
	}

	@Override
	public String toString() {
		return "TerminalEntity [id=" + id + ", appVersionName=" + appVersionName + ", appVersionCode=" + appVersionCode
				+ ", osVersion=" + osVersion + ", sdkVersion=" + sdkVersion + ", manufacturer=" + manufacturer
				+ ", marketName=" + marketName + ", model=" + model + ", codeName=" + codeName + ", deviceName="
				+ deviceName + ", deviceId=" + deviceId + ", lastTimeUsed=" + lastTimeUsed + ", createAt=" + createAt
				+ ", screenStatus=" + screenStatus + ", bedTimeEnabled=" + bedTimeEnabled + ", screenEnabled="
				+ screenEnabled + ", cameraEnabled=" + cameraEnabled + ", settingsEnabled=" + settingsEnabled + ", kid="
				+ kid + "]";
	}
}
