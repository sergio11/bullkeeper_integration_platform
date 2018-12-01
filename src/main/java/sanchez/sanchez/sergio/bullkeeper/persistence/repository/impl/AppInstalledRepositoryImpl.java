package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.Map;
import java.util.Map.Entry;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppInstalledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppRuleEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.AppInstalledRepositoryCustom;

/**
 * App Installed Repository
 * @author sergiosanchezsanchez
 *
 */
public final class AppInstalledRepositoryImpl implements AppInstalledRepositoryCustom {

	/**
	 * Logger
	 */
	private static Logger logger = LoggerFactory.getLogger(AppInstalledRepositoryImpl.class);
    
	/**
	 * Mongo Template
	 */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Update App Rules
     */
	@Override
	public void updateAppRules(final Map<ObjectId, AppRuleEnum> appRules) {
		Assert.notNull(appRules, "App Rules can not be null");
		
		for(final Entry<ObjectId, AppRuleEnum> appRule: appRules.entrySet())
			mongoTemplate.updateFirst(
	                new Query(Criteria.where("_id").is(appRule.getKey())),
	                new Update().set("app_rule", appRule.getValue().name()), AppInstalledEntity.class);
	
	}
	
}
