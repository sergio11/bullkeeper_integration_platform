package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.Map;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppInstalledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppRuleEnum;

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
	
	
	/**
	 * Update App Rules
	 * @param id
	 * @param appRules
	 */
	void updateAppRules(final ObjectId id, final AppRuleEnum appRules);
	
	/**
	 * Get App Rules
	 * @param kid
	 * @param terminal
	 * @return
	 */
	Iterable<AppInstalledEntity> getAppRules(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * Get App Rules
	 * @param kid
	 * @param terminal
	 * @param app
	 * @return
	 */
	AppInstalledEntity getAppRules(final ObjectId kid, final ObjectId terminal, final ObjectId app);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param app
	 */
	void enableAppInTheTerminal(final ObjectId kid, final ObjectId terminal, 
			final ObjectId app);
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param app
	 */
	void disableAppInTheTerminal(final ObjectId kid, final ObjectId terminal, 
			final ObjectId app);
	
	

}
