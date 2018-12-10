package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * App Installed DTO
 * @author sergiosanchezsanchez
 *
 */
public class AppInstalledDTO extends ResourceSupport {
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
    private String identity;
	
	/**
	 * Package Name
	 */
	@JsonProperty("package_name")
	private String packageName;
	
	/**
	 * First Install Time
	 */
	@JsonProperty("first_install_time")
	private long firstInstallTime;
	
	/**
	 * Last Update Time
	 */
	@JsonProperty("last_update_time")
	private long lastUpdateTime;
	
	/**
	 * Version Name
	 */
	@JsonProperty("version_name")
	private String versionName;
	
	/**
	 * Version Code
	 */
	@JsonProperty("version_code")
	private String versionCode;
	
	/**
	 * App Name
	 */
	@JsonProperty("app_name")
	private String appName;
	
	/**
	 * App Rule
	 */
	@JsonProperty("app_rule")
	private String appRule;
	
	/**
	 * Min SDK
	 */
	@JsonProperty("min_sdk")
	private String minSdk;
	
	/**
	 * Permissions
	 */
	@JsonProperty("permissions")
	private String permissions;
	
	/**
	 * Icon Encoded String
	 */
	@JsonProperty("icon_encoded_string")
	private String iconEncodedString;
	
	/**
	 * KId
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal ID
	 */
	@JsonProperty("terminal_id")
	private String terminalId;
	
	/**
	 * 
	 */
	public AppInstalledDTO() {}

	/**
	 * 
	 * @param identity
	 * @param packageName
	 * @param firstInstallTime
	 * @param lastUpdateTime
	 * @param versionName
	 * @param versionCode
	 * @param appName
	 * @param appRule
	 * @param minSdk
	 * @param permissions
	 * @param iconEncodedString
	 * @param kid
	 * @param terminalId
	 */
	public AppInstalledDTO(String identity, String packageName, long firstInstallTime, long lastUpdateTime,
			String versionName, String versionCode, String appName, String appRule, String minSdk, String permissions,
			String iconEncodedString, String kid, String terminalId) {
		super();
		this.identity = identity;
		this.packageName = packageName;
		this.firstInstallTime = firstInstallTime;
		this.lastUpdateTime = lastUpdateTime;
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.appName = appName;
		this.appRule = appRule;
		this.minSdk = minSdk;
		this.permissions = permissions;
		this.iconEncodedString = iconEncodedString;
		this.kid = kid;
		this.terminalId = terminalId;
	}

	public String getIdentity() {
		return identity;
	}

	public String getPackageName() {
		return packageName;
	}

	public long getFirstInstallTime() {
		return firstInstallTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public String getVersionName() {
		return versionName;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppRule() {
		return appRule;
	}

	public String getMinSdk() {
		return minSdk;
	}

	public String getPermissions() {
		return permissions;
	}

	public String getIconEncodedString() {
		return iconEncodedString;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setFirstInstallTime(long firstInstallTime) {
		this.firstInstallTime = firstInstallTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setAppRule(String appRule) {
		this.appRule = appRule;
	}

	public void setMinSdk(String minSdk) {
		this.minSdk = minSdk;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public void setIconEncodedString(String iconEncodedString) {
		this.iconEncodedString = iconEncodedString;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	@Override
	public String toString() {
		return "AppInstalledDTO [identity=" + identity + ", packageName=" + packageName + ", firstInstallTime="
				+ firstInstallTime + ", lastUpdateTime=" + lastUpdateTime + ", versionName=" + versionName
				+ ", versionCode=" + versionCode + ", appName=" + appName + ", appRule=" + appRule + ", minSdk="
				+ minSdk + ", permissions=" + permissions + ", iconEncodedString=" + iconEncodedString + ", kid=" + kid
				+ ", terminalId=" + terminalId + "]";
	}
	
	

}
