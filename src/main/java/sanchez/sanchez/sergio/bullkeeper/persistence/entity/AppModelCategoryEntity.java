package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * App Model Category Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = AppModelCategoryEntity.COLLECTION_NAME)
public final class AppModelCategoryEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "app_model_categories";
	
	/**
	 * Cat Key
	 */
	@Id
	@Field("cat_key")
	private String catKey;
	
	/**
	 * Category
	 */
	
	@Field("name")
	private String name;
	
	/**
	 * Default Rule
	 */
	@Field("default_rule")
	private AppRuleEnum defaultRule;
	
	/**
	 * 
	 */
	public AppModelCategoryEntity() {}
	
	
	/**
	 * 
	 * @param catKey
	 * @param name
	 * @param defaultRule
	 */
	public AppModelCategoryEntity(final String catKey, final String name,
			final AppRuleEnum defaultRule) {
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

	public AppRuleEnum getDefaultRule() {
		return defaultRule;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDefaultRule(AppRuleEnum defaultRule) {
		this.defaultRule = defaultRule;
	}


	@Override
	public String toString() {
		return "AppModelCategoryEntity [catKey=" + catKey + ", name=" + name + ", defaultRule=" + defaultRule + "]";
	}
}
