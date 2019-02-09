package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * App Model Category DTO
 * @author sergiosanchezsanchez
 *
 */
public final class AppModelCategoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Cat Key
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
	@JsonProperty("default_rule")
	private String defaultRule;

	public AppModelCategoryDTO() {}
	
	/**
	 * 
	 * @param catKey
	 * @param name
	 * @param defaultRule
	 */
	public AppModelCategoryDTO(String catKey, String name, String defaultRule) {
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
		return "AppModelCategoryDTO [catKey=" + catKey + ", name=" + name + ", defaultRule=" + defaultRule + "]";
	}
	
	

}
