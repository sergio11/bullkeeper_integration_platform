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
	 * Detached
	 */
	@Field("detached")
	private Boolean detached = Boolean.FALSE;
	
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
	 * Carrier Name
	 */
	@Field("carrier_name")
	private String carrierName;
	
	/**
	 * Phone Number
	 */
	@Field("phone_number")
	private String phoneNumber;
	
	/**
	 * Heart Beat
	 */
	@Field("heartbeat")
	private TerminalHeartbeatEntity heartbeat = new TerminalHeartbeatEntity();

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
	 * Terminal Status
	 */
	@Field("status")
	private TerminalStatusEnum status = TerminalStatusEnum.STATE_ON;
	
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
	 * Phone Calls Enabled
	 */
	@Field("phone_calls_enabled")
	private Boolean phoneCallsEnabled = true;
	
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
     * Battery Level
     */
    @Field("battery_level")
    private Integer batteryLevel = 100;
    
    /**
     * Is Battery Charging
     */
    @Field("is_battery_charging")
    private Boolean isBatteryCharging = false;
    
    /**
     * High Accuraccy Location Enabled
     */
    @Field("high_accuraccy_location_enabled")
    private Boolean highAccuraccyLocationEnabled;
    
    /**
     * Apps Overlay Enabled
     */
    @Field("apps_overlay_enabled")
    private Boolean appsOverlayEnabled;
	
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
	 * @param detached
	 * @param sdkVersion
	 * @param manufacturer
	 * @param marketName
	 * @param model
	 * @param codeName
	 * @param deviceName
	 * @param deviceId
	 * @param createAt
	 * @param screenStatus
	 * @param bedTimeEnabled
	 * @param screenEnabled
	 * @param cameraEnabled
	 * @param settingsEnabled
	 * @param phoneCallsEnabled
	 * @param locationPermissionEnabled
	 * @param callsHistoryPermissionEnabled
	 * @param contactsListPermissionEnabled
	 * @param textMessagePermissionEnabled
	 * @param kid
	 * @param funTimeScheduled
	 * @param highAccuraccyLocationEnabled
	 * @param appsOverlayEnabled
	 */
	@PersistenceConstructor
	public TerminalEntity(ObjectId id, String appVersionName, String appVersionCode, String osVersion,
			Boolean detached, String sdkVersion, String manufacturer, String marketName, String model, String codeName,
			String deviceName, String deviceId, final String carrierName, final String phoneNumber, final TerminalHeartbeatEntity heartbeat, Date createAt, 
			ScreenStatusEnum screenStatus, Boolean bedTimeEnabled,
			Boolean screenEnabled, Boolean cameraEnabled, Boolean settingsEnabled,
			final Boolean phoneCallsEnabled, final Boolean locationPermissionEnabled, final Boolean callsHistoryPermissionEnabled,
			final Boolean contactsListPermissionEnabled, 
			final Boolean textMessagePermissionEnabled, final Boolean storagePermissionEnabled,
			final Boolean usageStatsAllowed, final Boolean adminAccessAllowed, 
			KidEntity kid, FunTimeScheduledEntity funTimeScheduled, final Integer batteryLevel,
			final Boolean isBatteryCharging, final TerminalStatusEnum status,
			final Boolean highAccuraccyLocationEnabled, final Boolean appsOverlayEnabled) {
		super();
		this.id = id;
		this.appVersionName = appVersionName;
		this.appVersionCode = appVersionCode;
		this.osVersion = osVersion;
		this.detached = detached;
		this.sdkVersion = sdkVersion;
		this.manufacturer = manufacturer;
		this.marketName = marketName;
		this.model = model;
		this.codeName = codeName;
		this.deviceName = deviceName;
		this.deviceId = deviceId;
		this.carrierName = carrierName;
		this.phoneNumber = phoneNumber;
		this.heartbeat = heartbeat;
		this.createAt = createAt;
		this.screenStatus = screenStatus;
		this.bedTimeEnabled = bedTimeEnabled;
		this.screenEnabled = screenEnabled;
		this.cameraEnabled = cameraEnabled;
		this.settingsEnabled = settingsEnabled;
		this.phoneCallsEnabled = phoneCallsEnabled;
		this.locationPermissionEnabled = locationPermissionEnabled;
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
		this.storagePermissionEnabled = storagePermissionEnabled;
		this.adminAccessAllowed = adminAccessAllowed;
		this.usageStatsAllowed = usageStatsAllowed;
		this.kid = kid;
		this.funTimeScheduled = funTimeScheduled;
		this.batteryLevel = batteryLevel;
		this.isBatteryCharging = isBatteryCharging;
		this.status= status;
		this.highAccuraccyLocationEnabled = highAccuraccyLocationEnabled;
		this.appsOverlayEnabled = appsOverlayEnabled;
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

	
	
	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public TerminalHeartbeatEntity getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(TerminalHeartbeatEntity heartbeat) {
		this.heartbeat = heartbeat;
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

	public TerminalStatusEnum getStatus() {
		return status;
	}

	public void setStatus(TerminalStatusEnum status) {
		this.status = status;
	}

	public Boolean getBedTimeEnabled() {
		return bedTimeEnabled;
	}

	public void setBedTimeEnabled(Boolean bedTimeEnabled) {
		this.bedTimeEnabled = bedTimeEnabled;
	}

	public Boolean getScreenEnabled() {
		return screenEnabled;
	}

	public void setScreenEnabled(Boolean screenEnabled) {
		this.screenEnabled = screenEnabled;
	}

	public Boolean getCameraEnabled() {
		return cameraEnabled;
	}

	public void setCameraEnabled(Boolean cameraEnabled) {
		this.cameraEnabled = cameraEnabled;
	}

	public Boolean getSettingsEnabled() {
		return settingsEnabled;
	}

	public void setSettingsEnabled(Boolean settingsEnabled) {
		this.settingsEnabled = settingsEnabled;
	}

	public Boolean getDetached() {
		return detached;
	}

	public void setDetached(Boolean detached) {
		this.detached = detached;
	}

	public Boolean getPhoneCallsEnabled() {
		return phoneCallsEnabled;
	}

	public void setPhoneCallsEnabled(Boolean phoneCallsEnabled) {
		this.phoneCallsEnabled = phoneCallsEnabled;
	}

	public Boolean getLocationPermissionEnabled() {
		return locationPermissionEnabled;
	}

	public void setLocationPermissionEnabled(Boolean locationPermissionEnabled) {
		this.locationPermissionEnabled = locationPermissionEnabled;
	}

	public Boolean getCallsHistoryPermissionEnabled() {
		return callsHistoryPermissionEnabled;
	}

	public void setCallsHistoryPermissionEnabled(Boolean callsHistoryPermissionEnabled) {
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
	}

	public Boolean getContactsListPermissionEnabled() {
		return contactsListPermissionEnabled;
	}

	public void setContactsListPermissionEnabled(Boolean contactsListPermissionEnabled) {
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
	}

	public Boolean getTextMessagePermissionEnabled() {
		return textMessagePermissionEnabled;
	}

	public void setTextMessagePermissionEnabled(Boolean textMessagePermissionEnabled) {
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
	}

	public Boolean getStoragePermissionEnabled() {
		return storagePermissionEnabled;
	}

	public void setStoragePermissionEnabled(Boolean storagePermissionEnabled) {
		this.storagePermissionEnabled = storagePermissionEnabled;
	}

	public Boolean getUsageStatsAllowed() {
		return usageStatsAllowed;
	}

	public void setUsageStatsAllowed(Boolean usageStatsAllowed) {
		this.usageStatsAllowed = usageStatsAllowed;
	}

	public Boolean getAdminAccessAllowed() {
		return adminAccessAllowed;
	}

	public void setAdminAccessAllowed(Boolean adminAccessAllowed) {
		this.adminAccessAllowed = adminAccessAllowed;
	}

	public Integer getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(Integer batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public Boolean getIsBatteryCharging() {
		return isBatteryCharging;
	}

	public void setIsBatteryCharging(Boolean isBatteryCharging) {
		this.isBatteryCharging = isBatteryCharging;
	}

	public Boolean getHighAccuraccyLocationEnabled() {
		return highAccuraccyLocationEnabled;
	}

	public void setHighAccuraccyLocationEnabled(Boolean highAccuraccyLocationEnabled) {
		this.highAccuraccyLocationEnabled = highAccuraccyLocationEnabled;
	}

	public Boolean getAppsOverlayEnabled() {
		return appsOverlayEnabled;
	}

	public void setAppsOverlayEnabled(Boolean appsOverlayEnabled) {
		this.appsOverlayEnabled = appsOverlayEnabled;
	}

	public KidEntity getKid() {
		return kid;
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
				+ ", osVersion=" + osVersion + ", sdkVersion=" + sdkVersion + ", detached=" + detached
				+ ", manufacturer=" + manufacturer + ", marketName=" + marketName + ", model=" + model + ", codeName="
				+ codeName + ", deviceName=" + deviceName + ", deviceId=" + deviceId + ", carrierName=" + carrierName
				+ ", phoneNumber=" + phoneNumber + ", heartbeat=" + heartbeat + ", createAt=" + createAt
				+ ", screenStatus=" + screenStatus + ", status=" + status + ", bedTimeEnabled=" + bedTimeEnabled
				+ ", screenEnabled=" + screenEnabled + ", cameraEnabled=" + cameraEnabled + ", settingsEnabled="
				+ settingsEnabled + ", locationPermissionEnabled=" + locationPermissionEnabled
				+ ", callsHistoryPermissionEnabled=" + callsHistoryPermissionEnabled
				+ ", contactsListPermissionEnabled=" + contactsListPermissionEnabled + ", textMessagePermissionEnabled="
				+ textMessagePermissionEnabled + ", storagePermissionEnabled=" + storagePermissionEnabled
				+ ", usageStatsAllowed=" + usageStatsAllowed + ", adminAccessAllowed=" + adminAccessAllowed
				+ ", batteryLevel=" + batteryLevel + ", isBatteryCharging=" + isBatteryCharging
				+ ", highAccuraccyLocationEnabled=" + highAccuraccyLocationEnabled + ", appsOverlayEnabled="
				+ appsOverlayEnabled + ", kid=" + kid + ", funTimeScheduled=" + funTimeScheduled + "]";
	}

	
}
