package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.Map;
import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.masoc.persistence.entity.AppRuleEnum;

/**
 *
 * @author sergio
 */
public interface AppInstalledRepositoryCustom {
	
	/**
	 * Update App Rules
	 * @param appRules
	 */
	void updateAppRules(final Map<ObjectId, AppRuleEnum> appRules);

}
