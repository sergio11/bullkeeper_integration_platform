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
	 * Settings ENabled
	 */
	@JsonProperty("settings_enabled")
	private Boolean settingsEnabled;
	
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
	
	public TerminalDTO() {}

	
	/**
	 * 
	 * @param identity
	 * @param status
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
	 * @param kid
	 * @param bedTimeEnabled
	 * @param screenEnabled
	 * @param cameraEnabled
	 * @param settingsEnabled
	 * @param batteryLevel
	 * @param isBatteryCharging
	 */
	public TerminalDTO(String identity, String status, String appVersionName, String appVersionCode, String osVersion,
			String sdkVersion, String manufacturer, String marketName, String model, String codeName, String deviceName,
			String deviceId, String kid, Boolean bedTimeEnabled, Boolean screenEnabled, Boolean cameraEnabled,
			Boolean settingsEnabled, Integer batteryLevel, Boolean isBatteryCharging) {
		super();
		this.identity = identity;
		this.status = status;
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
		this.kid = kid;
		this.bedTimeEnabled = bedTimeEnabled;
		this.screenEnabled = screenEnabled;
		this.cameraEnabled = cameraEnabled;
		this.settingsEnabled = settingsEnabled;
		this.batteryLevel = batteryLevel;
		this.isBatteryCharging = isBatteryCharging;
	}



	public String getIdentity() {
		return identity;
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

	public String getKid() {
		return kid;
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

	public void setIdentity(String identity) {
		this.identity = identity;
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

	public void setKid(String kid) {
		this.kid = kid;
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

	@Override
	public String toString() {
		return "TerminalDTO [identity=" + identity + ", appVersionName=" + appVersionName + ", appVersionCode="
				+ appVersionCode + ", osVersion=" + osVersion + ", sdkVersion=" + sdkVersion + ", manufacturer="
				+ manufacturer + ", marketName=" + marketName + ", model=" + model + ", codeName=" + codeName
				+ ", deviceName=" + deviceName + ", deviceId=" + deviceId + ", kid=" + kid + ", bedTimeEnabled="
				+ bedTimeEnabled + ", screenEnabled=" + screenEnabled + ", cameraEnabled=" + cameraEnabled
				+ ", settingsEnabled=" + settingsEnabled + ", batteryLevel=" + batteryLevel + ", isBatteryCharging="
				+ isBatteryCharging + "]";
	}

	
}
