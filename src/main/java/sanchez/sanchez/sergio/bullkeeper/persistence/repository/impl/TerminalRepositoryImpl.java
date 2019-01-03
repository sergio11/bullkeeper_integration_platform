package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepositoryCustom;

/**
 * Terminal Repository Impl
 * @author sergiosanchezsanchez
 *
 */
public class TerminalRepositoryImpl implements TerminalRepositoryCustom {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * Update Screen Status
	 */
	@Override
	public void updateScreenStatus(final ObjectId terminal, final ObjectId kid, 
			final ScreenStatusEnum screenStatus) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(screenStatus, "Screen Status can not be null");
		
		 mongoTemplate.updateFirst(
	                new Query(Criteria.where("_id").in(terminal)
	                		.andOperator(Criteria.where("kid").in(kid))),
	                new Update()
	                	.set("screen_status", screenStatus.name())
	                	.set("last_time_used", new Date()), TerminalEntity.class);
		
	}

	/**
	 * Enable Bed Time
	 */
	@Override
	public void enableBedTime(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
	
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("bed_time_enabled", true), TerminalEntity.class);
		
	}

	/**
	 * Disable Bed Time
	 */
	@Override
	public void disableBedTime(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("bed_time_enabled", false), TerminalEntity.class);
		
	}

	/**
	 * Lock Screen
	 */
	@Override
	public void lockScreen(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("lock_screen_enabled", true), TerminalEntity.class);
	}

	/**
	 * Unlock screen
	 */
	@Override
	public void unlockScreen(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("lock_screen_enabled", false), TerminalEntity.class);
	}

	/**
	 * Lock Screen
	 */
	@Override
	public void lockCamera(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("lock_camera_enabled", true), TerminalEntity.class);
		
	}

	/**
	 * Unlock Camera
	 */
	@Override
	public void unlockCamera(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("lock_camera_enabled", false), TerminalEntity.class);
		
	}

	/**
	 * Enable Settings
	 */
	@Override
	public void enableSettings(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("settings_enabled", true), TerminalEntity.class);
		
	}

	/**
	 * Disable Settings
	 */
	@Override
	public void disableSettings(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("settings_enabled", false), TerminalEntity.class);
		
	}

}
