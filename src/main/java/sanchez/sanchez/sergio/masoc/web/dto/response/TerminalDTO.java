package sanchez.sanchez.sergio.masoc.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Terminal DTO
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalDTO implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * App Version Name
	 */
	@JsonProperty("app_version_name")
	private String appVersionName;
	
	
	/**
	 * App Version Code
	 */
	@JsonProperty("app_version_code")
	private String appVersionCode;
	
	/**
	 * Os Version
	 */
	@JsonProperty("os_version")
	private String osVersion;
	
	/**
	 * Sdk Version
	 */
	@JsonProperty("sdk_version")
	private String sdkVersion;
	
	/**
	 * Manufacturer
	 */
	@JsonProperty("manufacturer")
	private String manufacturer;
	
	/**
	 * Market Name
	 */
	@JsonProperty("market_name")
	private String marketName;
	
	/**
	 * Model
	 */
	@JsonProperty("model")
	private String model;
	
	/**
	 * Code Name
	 */
	@JsonProperty("code_name")
	private String codeName;
	
	
	/**
	 * Device Name
	 */
	@JsonProperty("device_name")
	private String deviceName;

	
	public TerminalDTO() {}
	
	/**
	 * @param identity
	 * @param appVersionName
	 * @param appVersionCode
	 * @param osVersion
	 * @param sdkVersion
	 * @param manufacturer
	 * @param marketName
	 * @param model
	 * @param codeName
	 * @param deviceName
	 */
	public TerminalDTO(String identity, String appVersionName, String appVersionCode, String osVersion, String sdkVersion,
			String manufacturer, String marketName, String model, String codeName, String deviceName) {
		super();
		this.identity = identity;
		this.appVersionName = appVersionName;
		this.appVersionCode = appVersionCode;
		this.osVersion = osVersion;
		this.sdkVersion = sdkVersion;
		this.manufacturer = manufacturer;
		this.marketName = marketName;
		this.model = model;
		this.codeName = codeName;
		this.deviceName = deviceName;
	}
	
	

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
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


}
