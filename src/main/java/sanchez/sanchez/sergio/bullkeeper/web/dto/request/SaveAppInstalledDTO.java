package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import java.util.Arrays;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.AppInstalledShouldExistsIfPresent;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidAppRuleType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class SaveAppInstalledDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@AppInstalledShouldExistsIfPresent(message = "{app.installed.not.exists}")
	@JsonProperty("identity")
	private String identity;
	
	
	/**
	 * Package Name
	 */
	@NotBlank(message = "{app.packagename.notblank}")
    @Size(min = 5, message = "{app.packagename.size}")
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
	@NotBlank(message = "{app.versionname.notblank}")
	@JsonProperty("version_name")
	private String versionName;
	
	/**
	 * Version Code
	 */
	@NotBlank(message = "{app.versioncode.notblank}")
	@JsonProperty("version_code")
	private String versionCode;
	
	/**
	 * App Name
	 */
	@NotBlank(message = "{app.appname.notblank}")
    @Size(min = 2, message = "{app.appname.size}")
	@JsonProperty("app_name")
	private String appName;
	
	/**
	 * App Rule
	 */
	@ValidAppRuleType(message = "{app.rule.not.valid}")
	@JsonProperty("app_rule")
	private String appRule;
	
	
	/**
	 * Icon Encoded String
	 */
	@JsonProperty("icon_encoded_string")
	private String iconEncodedString;
	
	/**
	 * Son ID
	 */
	@ValidObjectId(message = "{son.id.notvalid}")
    @KidShouldExists(message = "{social.son.not.exists}", groups = Extended.class)
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal Id
	 */
	@ValidObjectId(message = "{terminal.id.notvalid}")
    @TerminalShouldExists(message = "{terminal.not.exists}", groups = Extended.class)
	@JsonProperty("terminal_id")
	private String terminalId;
	
	/**
	 * 
	 */
	public SaveAppInstalledDTO() {}

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
	 * @param iconEncodedString
	 * @param kid
	 * @param terminalId
	 */
	public SaveAppInstalledDTO(
			String identity,
			String packageName, long firstInstallTime, long lastUpdateTime, String versionName, String versionCode,
			String appName, String appRule,
			String iconEncodedString,String kid, String terminalId) {
		super();
		this.identity = identity;
		this.packageName = packageName;
		this.firstInstallTime = firstInstallTime;
		this.lastUpdateTime = lastUpdateTime;
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.appName = appName;
		this.appRule = appRule;
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
		return "SaveAppInstalledDTO [identity=" + identity + ", packageName=" + packageName + ", firstInstallTime="
				+ firstInstallTime + ", lastUpdateTime=" + lastUpdateTime + ", versionName=" + versionName
				+ ", versionCode=" + versionCode + ", appName=" + appName + ", appRule=" + appRule
				+ ", iconEncodedString=" + iconEncodedString + ", kid=" + kid + ", terminalId=" + terminalId + "]";
	}


	
}
