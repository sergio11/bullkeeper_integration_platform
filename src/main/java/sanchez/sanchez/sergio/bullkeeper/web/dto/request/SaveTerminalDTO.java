package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;

/**
 * @author sergiosanchezsanchez
 */
public final class SaveTerminalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * App Version Name
	 */
	@NotBlank(message = "{terminal.app.version.name.not.blank}")
	@JsonProperty("app_version_name")
	private String appVersionName;
	
	/**
	 * App Version Code
	 */
	@NotBlank(message = "{terminal.app.version.code.not.blank}")
	@JsonProperty("app_version_code")
	private String appVersionCode;
	
	/**
	 * Os Version
	 */
	@NotBlank(message = "{terminal.os.version.not.blank}")
	@JsonProperty("os_version")
	private String osVersion;
	
	/**
	 * Sdk Version
	 */
	@NotBlank(message = "{terminal.sdk.version.not.blank}")
	@JsonProperty("sdk_version")
	private String sdkVersion;
	
	/**
	 * Manufacturer
	 */
	@NotBlank(message = "{terminal.manufacturer.not.blank}")
	@JsonProperty("manufacturer")
	private String manufacturer;
	
	/**
	 * Market Name
	 */
	@NotBlank(message = "{terminal.market.name.not.blank}")
	@JsonProperty("market_name")
	private String marketName;
	
	/**
	 * Model
	 */
	@NotBlank(message = "{terminal.model.not.blank}")
	@JsonProperty("model")
	private String model;
	
	/**
	 * Code Name
	 */
	@NotBlank(message = "{terminal.code.name.not.blank}")
	@JsonProperty("code_name")
	private String codeName;
	
	/**
	 * Device Name
	 */
	@NotBlank(message = "{terminal.device.name.not.blank}")
	@JsonProperty("device_name")
	private String deviceName;
	
	/**
	 * Device ID
	 */
	@NotBlank(message = "{terminal.device.id.not.blank}")
	@JsonProperty("device_id")
	private String deviceId;
	
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
	
	/**
	 * Son Should Exists
	 */
	@KidShouldExists(message = "{terminal.son.should.exists}")
	@JsonProperty("kid")
	private String kid;
	
	
	public SaveTerminalDTO() {}

	/**
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
	 * @param carrierName
	 * @param phoneNumber
	 * @param kid
	 */
	public SaveTerminalDTO(String appVersionName, String appVersionCode, String osVersion, String sdkVersion,
			String manufacturer, String marketName, String model, String codeName, String deviceName,
			String deviceId, String carrierName, String phoneNumber, String kid) {
		super();
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
		this.carrierName = carrierName;
		this.phoneNumber= phoneNumber;
		this.kid = kid;
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

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "SaveTerminalDTO [appVersionName=" + appVersionName + ", appVersionCode=" + appVersionCode
				+ ", osVersion=" + osVersion + ", sdkVersion=" + sdkVersion + ", manufacturer=" + manufacturer
				+ ", marketName=" + marketName + ", model=" + model + ", codeName=" + codeName + ", deviceName="
				+ deviceName + ", deviceId=" + deviceId + ", carrierName=" + carrierName + ", phoneNumber="
				+ phoneNumber + ", kid=" + kid + "]";
	}

	

}
