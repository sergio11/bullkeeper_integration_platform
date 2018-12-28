package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * App Installed Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = AppInstalledEntity.COLLECTION_NAME)
public class AppInstalledEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "app_installed";
	
	/**
	 * App id
	 */
	@Id
	private ObjectId id;
	
	/**
	 * Package Name
	 */
	@Field("package_name")
    private String packageName;
	
	/**
	 * First Install Time
	 */
	@Field("first_install_time")
    private Long firstInstallTime;
	
	/**
	 * Last Update Time
	 */
	@Field("last_update_time")
    private Long lastUpdateTime;
	
	/**
	 * Version Name
	 */
	@Field("version_name")
    private String versionName;
	
	/**
	 * Version Code
	 */
	@Field("version_code")
    private String versionCode;
	
	/**
	 * App Name
	 */
	@Field("app_name")
    private String appName;
	
	
	/**
	 * App Rule
	 */
	@Field("app_rule")
	private AppRuleEnum appRuleEnum = AppRuleEnum.NEVER_ALLOWED;
	

	/**
	 * Icon Encoded String
	 */
	@Field("icon_encoded_string")
	private String iconEncodedString;
	
	/**
	 * Kid
	 */
	@Field("kid")
    @DBRef
    private KidEntity kid;
	
	/**
	 * Terminal
	 */
	@Field("terminal")
	@DBRef
	private TerminalEntity terminal;
	
	/**
	 * 
	 */
	public AppInstalledEntity() {}

	/**
	 * 
	 * @param id
	 * @param packageName
	 * @param firstInstallTime
	 * @param lastUpdateTime
	 * @param versionName
	 * @param versionCode
	 * @param appName
	 * @param appRuleEnum
	 * @param minSdk
	 * @param permissions
	 * @param iconEncodedString
	 * @param kid
	 * @param terminal
	 */
	@PersistenceConstructor
	public AppInstalledEntity(ObjectId id, String packageName, Long firstInstallTime, Long lastUpdateTime,
			String versionName, String versionCode, String appName, AppRuleEnum appRuleEnum, String iconEncodedString, KidEntity kid, TerminalEntity terminal) {
		super();
		this.id = id;
		this.packageName = packageName;
		this.firstInstallTime = firstInstallTime;
		this.lastUpdateTime = lastUpdateTime;
		this.versionName = versionName;
		this.versionCode = versionCode;
		this.appName = appName;
		this.appRuleEnum = appRuleEnum;
		this.iconEncodedString = iconEncodedString;
		this.kid = kid;
		this.terminal = terminal;
	}

	public ObjectId getId() {
		return id;
	}

	public String getPackageName() {
		return packageName;
	}

	public Long getFirstInstallTime() {
		return firstInstallTime;
	}

	public Long getLastUpdateTime() {
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

	public AppRuleEnum getAppRuleEnum() {
		return appRuleEnum;
	}

	public String getIconEncodedString() {
		return iconEncodedString;
	}

	public KidEntity getKid() {
		return kid;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setFirstInstallTime(Long firstInstallTime) {
		this.firstInstallTime = firstInstallTime;
	}

	public void setLastUpdateTime(Long lastUpdateTime) {
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

	public void setAppRuleEnum(AppRuleEnum appRuleEnum) {
		this.appRuleEnum = appRuleEnum;
	}

	public void setIconEncodedString(String iconEncodedString) {
		this.iconEncodedString = iconEncodedString;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "AppInstalledEntity [id=" + id + ", packageName=" + packageName + ", firstInstallTime="
				+ firstInstallTime + ", lastUpdateTime=" + lastUpdateTime + ", versionName=" + versionName
				+ ", versionCode=" + versionCode + ", appName=" + appName + ", appRuleEnum=" + appRuleEnum
				+ ", iconEncodedString=" + iconEncodedString + ", kid=" + kid + ", terminal=" + terminal + "]";
	}

	
	
}
