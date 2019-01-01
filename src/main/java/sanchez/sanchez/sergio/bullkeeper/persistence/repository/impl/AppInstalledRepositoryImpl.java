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

	/**
	 * Update App Rules
	 * @param id
	 * @param appRules
	 */
	@Override
	public void updateAppRules(final ObjectId id, final AppRuleEnum appRules) {
		Assert.notNull(id, "id can not be null");
		Assert.notNull(appRules, "App Rules can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                new Update().set("app_rule", appRules.name()), AppInstalledEntity.class);
	}

	/**
	 * Get App Rules
	 */
	@Override
	public Iterable<AppInstalledEntity> getAppRules(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		// Create Query
        final Query query = new Query(Criteria.where("kid").is(kid)
        		.andOperator(Criteria.where("terminal").is(terminal)));
        
        query.fields()
        	.include("_id")
        	.include("package_name")
        	.include("app_rule");    
        
        return mongoTemplate.find(query, AppInstalledEntity.class);
	}

	/**
	 * Get App Rules
	 */
	@Override
	public AppInstalledEntity getAppRules(final ObjectId kid, final ObjectId terminal, final ObjectId app) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(app, "App can not be null");
		
		
		// Kid Criteria
		final Criteria kidIdCriteria = Criteria.where("kid").is(kid);
		
		// Terminal Criteria
		final Criteria terminalIdCriteria = Criteria.where("terminal").is(terminal);
		
		//App Criteria
		final Criteria appCriteria = Criteria.where("_id").is(app);
		
		
		// Create Query
        final Query query = new Query(new Criteria().andOperator(
        		kidIdCriteria, terminalIdCriteria, appCriteria));
        
        query.fields()
        	.include("_id")
        	.include("package_name")
        	.include("app_rule");    
        
        return mongoTemplate.findOne(query, AppInstalledEntity.class);
	
	}

	/**
	 * Enable App In The Terminal
	 */
	@Override
	public void enableAppInTheTerminal(final ObjectId kid, 
			final ObjectId terminal, final ObjectId app) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(app, "App can not be null");
		
		// Kid Criteria
		final Criteria kidIdCriteria = 
				Criteria.where("kid").is(kid);
		// Terminal Criteria
		final Criteria terminalIdCriteria = 
				Criteria.where("terminal").is(terminal);
		//App Criteria
		final Criteria appCriteria =
				Criteria.where("_id").is(app);
				
		// Create Query
		final Query query = new Query(new Criteria().andOperator(
		        		kidIdCriteria, terminalIdCriteria, appCriteria));
		
		mongoTemplate.updateFirst(query,
                new Update().set("disabled", false), AppInstalledEntity.class);
	}

	/**
	 * 
	 */
	@Override
	public void disableAppInTheTerminal(final ObjectId kid, 
			final ObjectId terminal, final ObjectId app) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(app, "App can not be null");
		
		// Kid Criteria
		final Criteria kidIdCriteria = 
					Criteria.where("kid").is(kid);
		// Terminal Criteria
		final Criteria terminalIdCriteria = 
					Criteria.where("terminal").is(terminal);
		//App Criteria
		final Criteria appCriteria =
						Criteria.where("_id").is(app);
						
		// Create Query
		final Query query = new Query(new Criteria().andOperator(
				       kidIdCriteria, terminalIdCriteria, appCriteria));
		
		mongoTemplate.updateFirst(query,
                new Update().set("disabled", true), AppInstalledEntity.class);
		
	}
	
}
