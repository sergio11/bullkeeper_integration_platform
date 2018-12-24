package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * App Rule DTO
 * @author sergiosanchezsanchez
 *
 */
public final class AppRuleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * App Rule
	 */
	@JsonProperty("app_rule")
	private String appRule;
	
	/**
	 * 
	 */
	public AppRuleDTO() {}

	/**
	 * 
	 * @param identity
	 * @param packageName
	 * @param appRule
	 */
	public AppRuleDTO(final String identity, final String packageName, final String appRule) {
		super();
		this.identity = identity;
		this.packageName = packageName;
		this.appRule = appRule;
	}

	public String getIdentity() {
		return identity;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getAppRule() {
		return appRule;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setAppRule(String appRule) {
		this.appRule = appRule;
	}

	@Override
	public String toString() {
		return "AppRuleDTO [identity=" + identity + ", packageName=" + packageName + ", appRule=" + appRule + "]";
	}
}
