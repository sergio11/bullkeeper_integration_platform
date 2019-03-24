package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Terminal DTO
 * @author sergiosanchezsanchez
 *
 */
public class TerminalDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	protected String identity;
	
	/**
	 * Status
	 */
	@JsonProperty("status")
	protected String status;
	
	/**
	 * App Version Name
	 */
	@JsonProperty("app_version_name")
	protected String appVersionName;
	
	
	/**
	 * App Version Code
	 */
	@JsonProperty("app_version_code")
	protected String appVersionCode;
	
	/**
	 * Os Version
	 */
	@JsonProperty("os_version")
	protected String osVersion;
	
	/**
	 * Sdk Version
	 */
	@JsonProperty("sdk_version")
	protected String sdkVersion;
	
	/**
	 * Detached
	 */
	@JsonProperty("detached")
	protected Boolean detached;
	
	/**
	 * Manufacturer
	 */
	@JsonProperty("manufacturer")
	protected String manufacturer;
	
	/**
	 * Market Name
	 */
	@JsonProperty("market_name")
	protected String marketName;
	
	/**
	 * Model
	 */
	@JsonProperty("model")
	protected String model;
	
	/**
	 * Code Name
	 */
	@JsonProperty("code_name")
	protected String codeName;
	
	
	/**
	 * Device Name
	 */
	@JsonProperty("device_name")
	protected String deviceName;
	
	/**
	 * Device Id
	 */
	@JsonProperty("device_id")
	protected String deviceId;
	
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	protected String kid;
	
	/**
	 * Bed Time Enabled
	 */
	@JsonProperty("bed_time_enabled")
    private Boolean bedTimeEnabled;

    /**
     * Screen Enabled
     */
	@JsonProperty("screen_enabled")
    private Boolean screenEnabled;

    /**
     * Camera Enabled
     */
	@JsonProperty("camera_enabled")
	private Boolean cameraEnabled;
	
	/**
	 * Settings Enabled
	 */
	@JsonProperty("settings_enabled")
	private Boolean settingsEnabled;
	
	/**
	 * Phone Calls Enabled
	 */
	@JsonProperty("phone_calls_enabled")
	private Boolean phoneCallsEnabled;
	
	/**
	 * Battery Level
	 */
	@JsonProperty("battery_level")
	private Integer batteryLevel;
	
	/**
	 * Is Battery Charging
	 */
	@JsonProperty("is_battery_charging")
	private Boolean isBatteryCharging;
	
	/**
	 * HeartBeat
	 */
	@JsonProperty("heartbeat")
	private TerminalHeartbeatDTO heartbeat;
	
	/**
	 * Carrier Name
	 */
	@JsonProperty("carrier_name")
	private String carrierName;
	
	/**
	 * Phone Number
	 */
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	public TerminalDTO() {}

	/**
	 * 
	 * @param identity
	 * @param status
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
	 * @param kid
	 * @param bedTimeEnabled
	 * @param screenEnabled
	 * @param cameraEnabled
	 * @param settingsEnabled
	 * @param phoneCallsEnabled
	 * @param batteryLevel
	 * @param isBatteryCharging
	 * @param heartbeat
	 * @param carrierName
	 * @param phoneNumber
	 */
	public TerminalDTO(String identity, String status, String appVersionName, String appVersionCode, String osVersion,
			Boolean detached, String sdkVersion, String manufacturer, String marketName, String model, String codeName, String deviceName,
			String deviceId, String kid, Boolean bedTimeEnabled, Boolean screenEnabled, Boolean cameraEnabled,
			Boolean settingsEnabled, Boolean phoneCallsEnabled, Integer batteryLevel, Boolean isBatteryCharging, TerminalHeartbeatDTO heartbeat,
			String carrierName, String phoneNumber) {
		super();
		this.identity = identity;
		this.status = status;
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
		this.kid = kid;
		this.bedTimeEnabled = bedTimeEnabled;
		this.screenEnabled = screenEnabled;
		this.cameraEnabled = cameraEnabled;
		this.settingsEnabled = settingsEnabled;
		this.phoneCallsEnabled = phoneCallsEnabled;
		this.batteryLevel = batteryLevel;
		this.isBatteryCharging = isBatteryCharging;
		this.heartbeat = heartbeat;
		this.carrierName = carrierName;
		this.phoneNumber = phoneNumber;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Boolean getDetached() {
		return detached;
	}

	public void setDetached(Boolean detached) {
		this.detached = detached;
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

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
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

	public Boolean getPhoneCallsEnabled() {
		return phoneCallsEnabled;
	}

	public void setPhoneCallsEnabled(Boolean phoneCallsEnabled) {
		this.phoneCallsEnabled = phoneCallsEnabled;
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

	public TerminalHeartbeatDTO getHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(TerminalHeartbeatDTO heartbeat) {
		this.heartbeat = heartbeat;
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

	@Override
	public String toString() {
		return "TerminalDTO [identity=" + identity + ", status=" + status + ", appVersionName=" + appVersionName
				+ ", appVersionCode=" + appVersionCode + ", osVersion=" + osVersion + ", sdkVersion=" + sdkVersion
				+ ", detached=" + detached + ", manufacturer=" + manufacturer + ", marketName=" + marketName
				+ ", model=" + model + ", codeName=" + codeName + ", deviceName=" + deviceName + ", deviceId="
				+ deviceId + ", kid=" + kid + ", bedTimeEnabled=" + bedTimeEnabled + ", screenEnabled=" + screenEnabled
				+ ", cameraEnabled=" + cameraEnabled + ", settingsEnabled=" + settingsEnabled + ", phoneCallsEnabled="
				+ phoneCallsEnabled + ", batteryLevel=" + batteryLevel + ", isBatteryCharging=" + isBatteryCharging
				+ ", heartbeat=" + heartbeat + ", carrierName=" + carrierName + ", phoneNumber=" + phoneNumber + "]";
	}
}
