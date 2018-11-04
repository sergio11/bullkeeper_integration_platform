package sanchez.sanchez.sergio.masoc.web.dto.response;

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
	 * Son ID
	 */
	@JsonProperty("son_id")
	private String sonId;
	
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
	 * @param sonId
	 * @param terminalId
	 */
	public AppInstalledDTO(String identity, String packageName, long firstInstallTime, long lastUpdateTime,
			String versionName, String versionCode, String appName, String appRule, String sonId,
			String terminalId) {
		super();
		this.identity = identity;
		this.packageName = packageName;
		this.firstInstallTime = firstInstallTime;
		this.lastUpdateTime = lastUpdateTime;
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.appName = appName;
		this.appRule = appRule;
		this.sonId = sonId;
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

	public String getSonId() {
		return sonId;
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

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	

}
