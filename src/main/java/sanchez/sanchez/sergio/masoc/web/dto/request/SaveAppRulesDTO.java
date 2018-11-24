package sanchez.sanchez.sergio.masoc.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.masoc.persistence.constraints.AppInstalledShouldExists;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ValidAppRuleType;

/**
 * Save App Rules DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveAppRulesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@AppInstalledShouldExists(message = "{app.installed.not.exists}")
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Type
	 */
	@ValidAppRuleType(message = "{app.rule.not.valid}")
	@JsonProperty("type")
	private String type;
	
	
	public SaveAppRulesDTO() {}
	
	/**
	 * Save App Rules DTO
	 * @param identity
	 * @param type
	 */
	public SaveAppRulesDTO(final String identity, final String type) {
		super();
		this.identity = identity;
		this.type = type;
	}

	public String getIdentity() {
		return identity;
	}

	public String getType() {
		return type;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SaveAppRulesDTO [identity=" + identity + ", type=" + type + "]";
	}
}
