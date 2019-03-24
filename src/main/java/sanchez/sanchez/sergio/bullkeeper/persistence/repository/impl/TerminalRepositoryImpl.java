package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DayScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeDaysEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalHeartbeatEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepositoryCustom;

/**
 * Terminal Repository Impl
 * @author sergiosanchezsanchez
 *
 */
public class TerminalRepositoryImpl implements TerminalRepositoryCustom {
	
	private Logger logger = LoggerFactory.getLogger(TerminalRepositoryImpl.class);
	
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * Save Terminal Status
	 */
	@Override
	public void saveTerminalStatus(final ObjectId terminal, final ObjectId kid,
    		final ScreenStatusEnum screenStatus, final boolean accessFineLocationEnabled,
    		final boolean readContactsEnabled, final boolean readCallLogEnabled,
    		final boolean writeExternalStorageEnabled, final boolean usageStatsAllowed,
    		final boolean adminAccessEnabled, 
    		final int batteryLevel, final boolean isBatteryCharging,
    		final boolean highAccuraccyLocationEnabled,
    		final boolean appsOverlayEnabled) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(screenStatus, "Screen Status can not be null");
		
		 mongoTemplate.updateFirst(
	                new Query(Criteria.where("_id").in(terminal)
	                		.andOperator(Criteria.where("kid").in(kid))),
	                new Update()
	                	.set("screen_status", screenStatus.name())
	                	.set("heartbeat.last_time_notified", new Date())
	                	.set("location_permission_enabled", accessFineLocationEnabled)
	                	.set("call_history_permission_enabled", readCallLogEnabled)
	                	.set("contacts_list_permission_enabled", readContactsEnabled)
	                	.set("storage_permission_enabled", writeExternalStorageEnabled)
	                	.set("usage_stats_allowed", usageStatsAllowed)
	                	.set("admin_access_allowed", adminAccessEnabled)
	                	.set("battery_level", batteryLevel)
	                	.set("is_battery_charging", isBatteryCharging)
	                	.set("high_accuraccy_location_enabled", highAccuraccyLocationEnabled)
	                	.set("apps_overlay_enabled", appsOverlayEnabled)
	                	.set("detached", false)
	                	.set("status", TerminalStatusEnum.STATE_ON.name()), TerminalEntity.class);
		
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
                	.set("screen_enabled", false), TerminalEntity.class);
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
                	.set("screen_enabled", true), TerminalEntity.class);
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
                	.set("camera_enabled", false), TerminalEntity.class);
		
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
                	.set("camera_enabled", true), TerminalEntity.class);
		
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

	/**
	 * Get Fun Time Scheduled
	 */
	@Override
	public FunTimeScheduledEntity getFunTimeScheduled(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		
		Query query =  new Query(Criteria.where("_id").in(terminal)
        		.andOperator(Criteria.where("kid").in(kid)));
        query.fields().include("funtime_scheduled");
        
        // Get Terminal
        TerminalEntity terminalEntity = mongoTemplate.findOne(query, TerminalEntity.class);
		
		// Get Fun Time Scheduled
		return terminalEntity != null ? terminalEntity.getFunTimeScheduled(): null;
	}
	
	
	/**
	 * Save Fun Time Scheduled
	 */
	@Override
	public void saveFunTimeScheduled(final ObjectId kid, final ObjectId terminal, 
			final FunTimeScheduledEntity funTimeScheduledEntity) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(funTimeScheduledEntity, "Fun Time Scheduled can not be null");
		
		// Update First
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
				new Update()
				    .set("funtime_scheduled.enable", funTimeScheduledEntity.getEnabled()) 
				    .set("funtime_scheduled.monday.enable", 
				        	funTimeScheduledEntity.getMonday().getEnabled())
				    .set("funtime_scheduled.monday.total_hours", 
				    		funTimeScheduledEntity.getMonday().getTotalHours())
				    .set("funtime_scheduled.tuesday.enable", 
				    		funTimeScheduledEntity.getTuesday().getEnabled())
				    .set("funtime_scheduled.tuesday.total_hours", 
				    		funTimeScheduledEntity.getTuesday().getTotalHours())
				    .set("funtime_scheduled.wednesday.enable", 
				    		funTimeScheduledEntity.getWednesday().getEnabled())
				    .set("funtime_scheduled.wednesday.total_hours", 
				    		funTimeScheduledEntity.getWednesday().getTotalHours())
				    .set("funtime_scheduled.thursday.enable", 
				        	funTimeScheduledEntity.getThursday().getEnabled())
				    .set("funtime_scheduled.thursday.total_hours", 
				    		funTimeScheduledEntity.getThursday().getTotalHours())
				    .set("funtime_scheduled.friday.enable", 
				    		funTimeScheduledEntity.getFriday().getEnabled())
				    .set("funtime_scheduled.friday.total_hours", 
				        	funTimeScheduledEntity.getFriday().getTotalHours())
				    .set("funtime_scheduled.saturday.enable", 
				        	funTimeScheduledEntity.getSaturday().getEnabled())
				    .set("funtime_scheduled.saturday.total_hours", 
				        	funTimeScheduledEntity.getSaturday().getTotalHours())
				    .set("funtime_scheduled.sunday.enable", 
				        	funTimeScheduledEntity.getSunday().getEnabled())
				    .set("funtime_scheduled.sunday.total_hours", 
				        	funTimeScheduledEntity.getSunday().getTotalHours()),
				    				TerminalEntity.class);
	}

	/**
	 * Get Fun Time Day Scheduled
	 */
	@Override
	public DayScheduledEntity getFunTimeDayScheduled(final ObjectId kid, 
			final ObjectId terminal, final FunTimeDaysEnum day) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(day, "Day can not be null");
		
		Query query =  new Query(Criteria.where("_id").in(terminal)
        		.andOperator(Criteria.where("kid").in(kid)));
        query.fields().include("funtime_scheduled");
        
        // Get Terminal
        TerminalEntity terminalEntity = mongoTemplate.findOne(query, TerminalEntity.class);
        
        // Day Scheduled Entity
        DayScheduledEntity dayScheduledEntity = null;
        
        /**
         * Day Scheduled
         */
        switch(day) {
        	case MONDAY:
        		dayScheduledEntity = terminalEntity.getFunTimeScheduled().getMonday();
        		break;
        	case TUESDAY:
        		dayScheduledEntity = terminalEntity.getFunTimeScheduled().getTuesday();
        		break;
        	case WEDNESDAY:
        		dayScheduledEntity = terminalEntity.getFunTimeScheduled().getWednesday();
        		break;
        	case THURSDAY:
        		dayScheduledEntity = terminalEntity.getFunTimeScheduled().getThursday();
        		break;
        	case FRIDAY:
        		dayScheduledEntity = terminalEntity.getFunTimeScheduled().getFriday();
        		break;
        	case SATURDAY:
        		dayScheduledEntity = terminalEntity.getFunTimeScheduled().getSaturday();
        		break;
        	case SUNDAY:
        		dayScheduledEntity = terminalEntity.getFunTimeScheduled().getSunday();
        		break;	
        }
        
        return dayScheduledEntity;
	}

	/**
	 * Save Day Scheduled
	 */
	@Override
	public void saveDayScheduled(final ObjectId kid, final ObjectId terminal, 
			final FunTimeDaysEnum day, final DayScheduledEntity dayScheduled) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(day, "Day can not be null");
		Assert.notNull(dayScheduled, "Day Scheduled can not be null");
		
		// Update First
		mongoTemplate.updateFirst(
			new Query(Criteria.where("_id").in(terminal)
		                .andOperator(Criteria.where("kid").in(kid))),
			new Update()
				.set(String.format("funtime_scheduled.%s.enable", 
						day.name().toLowerCase()), dayScheduled.getEnabled())
				.set(String.format("funtime_scheduled.%s.total_hours", 
						day.name().toLowerCase()), dayScheduled.getTotalHours()), 
					TerminalEntity.class);
		
	}

	/**
	 * Save Terminal Status
	 * @param status
	 * @param kid
	 * @param status
	 */
	@Override
	public void saveTerminalStatus(final ObjectId terminal, final ObjectId kid, final TerminalStatusEnum status) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(status, "Terminal Status can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
                new Update()
                	.set("status", status.name()), TerminalEntity.class);
		
	}

	/**
	 * Lock Screen
	 * @param kid
	 */
	@Override
	public void lockScreen(final ObjectId kid) {
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateMulti(
                new Query(Criteria.where("kid").in(kid)),
                new Update()
                	.set("screen_enabled", false), TerminalEntity.class);
		
	}

	/**
	 * Unlock Screen
	 * @param kid
	 */
	@Override
	public void unlockScreen(final ObjectId kid) {
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateMulti(
                new Query(Criteria.where("kid").in(kid)),
                new Update()
                	.set("screen_enabled", true), TerminalEntity.class);
		
	}

	/**
	 * Save Terminal Heartbeat Configuration
	 * @param terminal
	 * @param kid
	 * @param alertThresholdInMinutes
	 * @param alertModeEnabled
	 */
	@Override
	public void saveTerminalHeartbeatConfiguration(final ObjectId terminal, final ObjectId kid, 
			final int alertThresholdInMinutes, final boolean alertModeEnabled) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		 mongoTemplate.updateFirst(
	                new Query(Criteria.where("_id").in(terminal)
	                		.andOperator(Criteria.where("kid").in(kid))),
	                new Update()
	                	.set("heartbeat.alert_mode_enabled", alertModeEnabled)
	                	.set("heartbeat.alert_threshold_in_minutes", alertThresholdInMinutes), TerminalEntity.class);
		
	}

	/**
	 * Get Terminal Heartbeat Configration
	 * @param terminal
	 * @param kid
	 */
	@Override
	public TerminalHeartbeatEntity getTerminalHeartbeatConfiguration(final ObjectId terminal, final ObjectId kid) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		final Query query =  new Query(Criteria.where("_id").in(terminal)
        		.andOperator(Criteria.where("kid").in(kid)));
        query.fields().include("heartbeat");
        
        // Get Terminal
        TerminalEntity terminalEntity = mongoTemplate.findOne(query, TerminalEntity.class);
		
		// Get Heartbeat
		return terminalEntity != null ? terminalEntity.getHeartbeat(): null;
	}

	/**
	 * Get Terminals With Heartbeat threshold exceeded
	 */
	@Override
	public List<TerminalEntity> getTerminalsWithHeartbeatAlertThresholdEnabledAndStateOn() {
		
		final Criteria criteria = Criteria
				.where("heartbeat.alert_mode_enabled").is(true)
				.and("status").is(TerminalStatusEnum.STATE_ON.name())
				.and("detached").is(false);
				
        return  mongoTemplate.find(new Query(criteria), TerminalEntity.class);
	}

	/**
	 * Detach
	 * @param kid
	 * @param terminal
	 */
	@Override
	public void detach(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
	
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
				new Update()
				    .set("detached", true)
				    .set("status", TerminalStatusEnum.STATE_OFF.name()),
				    				TerminalEntity.class);
	}

	/**
	 * Enable Phone Calls
	 * @param kid
	 * @param terminal
	 */
	@Override
	public void enablePhoneCalls(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
				new Update()
				    .set("phone_calls_enabled", true),
				    				TerminalEntity.class);
	}

	/**
	 * Disable Phone Calls
	 * @param kid
	 * @parma terminal
	 */
	@Override
	public void disablePhoneCalls(final ObjectId kid, final ObjectId terminal) {
		Assert.notNull(terminal, "Terminal can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(terminal)
                		.andOperator(Criteria.where("kid").in(kid))),
				new Update()
					.set("phone_calls_enabled", false),
				    				TerminalEntity.class);
	}

}
