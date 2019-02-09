package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidAppRuleType;

/**
 * Save App Model Category
 * @author sergiosanchezsanchez
 *
 */
public class SaveAppModelCategoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Category Key
	 */
	@JsonProperty("cat_key")
	private String catKey;
	
	/**
	 * Name
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * Default Rule
	 */
	@ValidAppRuleType(message = "{app.rule.not.valid}")
	@JsonProperty("default_rule")
	private String defaultRule;
	
	public SaveAppModelCategoryDTO() {}

	/**
	 * 
	 * @param catKey
	 * @param name
	 * @param defaultRule
	 */
	public SaveAppModelCategoryDTO(final String catKey, final String name, 
			final String defaultRule) {
		super();
		this.catKey = catKey;
		this.name = name;
		this.defaultRule = defaultRule;
	}

	public String getCatKey() {
		return catKey;
	}

	public String getName() {
		return name;
	}

	public String getDefaultRule() {
		return defaultRule;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDefaultRule(String defaultRule) {
		this.defaultRule = defaultRule;
	}

	@Override
	public String toString() {
		return "SaveAppModelCategoryDTO [catKey=" + catKey + ", name=" + name + ", defaultRule=" + defaultRule + "]";
	}
	
}
