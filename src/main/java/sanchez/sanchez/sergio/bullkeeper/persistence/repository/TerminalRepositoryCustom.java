package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DayScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeDaysEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalHeartbeatEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DeviceStatusEnum;

/**
 * Terminal Repository Custom
 * @author sergiosanchezsanchez
 *
 */
public interface TerminalRepositoryCustom {

	/**
	 * Save Device Status
	 * @param terminal
	 * @param kid
	 * @param status
	 */
	void saveDeviceStatus(final ObjectId terminal, final ObjectId kid, final DeviceStatusEnum status);
	
    /**
     * Save Heartbeat status
     * @param terminal
     * @param kid
     * @param screenStatus
     * @param accessFineLocationEnabled
     * @param readContactsEnabled
     * @param readCallLogEnabled
     * @param writeExternalStorageEnabled
     * @param usageStatsAllowed
     * @param adminAccessEnabled
     * @param batteryLevel
     * @param isBatteryCharging
     * @param highAccuraccyLocationEnabled
     * @param appsOverlayEnabled
     */
    void saveHeartbeatStatus(final ObjectId terminal, final ObjectId kid,
    		final ScreenStatusEnum screenStatus, final boolean accessFineLocationEnabled,
    		final boolean readContactsEnabled, final boolean readCallLogEnabled,
    		final boolean writeExternalStorageEnabled, final boolean usageStatsAllowed,
    		final boolean adminAccessEnabled, final int batteryLevel, 
    		final boolean isBatteryCharging, final boolean highAccuraccyLocationEnabled,
    		final boolean appsOverlayEnabled);
    
    
    /**
     * Save Terminal Heartbeat Configuration
     * @param terminal
     * @param kid
     * @param alertThresholdInMinutes
     * @param alertModeEnabled
     */
    void saveTerminalHeartbeatConfiguration(final ObjectId terminal, final ObjectId kid, 
			final int alertThresholdInMinutes, final boolean alertModeEnabled);
    
    
    /**
     * Set Terminal Status
     * @param terminal
     * @param status
     */
    void setTerminalStatus(final ObjectId terminal, TerminalStatusEnum status);
    
    
    /**
     * Set Terminal Status
     * @param terminalList
     * @param status
     */
    void setTerminalStatus(final List<ObjectId> terminalList, TerminalStatusEnum status);
    
    
    /**
     * Get Terminal Heartbeat Configuration
     * @param terminal
     * @param kid
     * @return
     */
    TerminalHeartbeatEntity getTerminalHeartbeatConfiguration(final ObjectId terminal, final ObjectId kid);
    
    /**
     * Enable Bed Time
     * @param kid
     * @param terminal
     */
    void enableBedTime(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Disable Bed Time
     * @param kid
     * @param terminal
     */
    void disableBedTime(final ObjectId kid, final ObjectId terminal);
    
    
    /**
     * Lock Screen
     * @param kid
     * @param terminal
     */
    void lockScreen(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Lock Screen
     * @param kid
     */
    void lockScreen(final ObjectId kid);
    
    /**
     * Un lock screen
     * @param kid
     * @param terminal
     */
    void unlockScreen(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Unlock Screen
     * @param kid
     */
    void unlockScreen(final ObjectId kid);
    
    /**
     * Lock Camera
     * @param kid
     * @param terminal
     */
    void lockCamera(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Un lock Camera
     * @param kid
     * @param terminal
     */
    void unlockCamera(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Enable Settings
     * @param kid
     * @param terminal
     */
    void enableSettings(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Disable Settings
     * @param kid
     * @param terminal
     */
    void disableSettings(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Get Fun Time Scheduled
     * @param kid
     * @param terminal
     * @return
     */
    FunTimeScheduledEntity getFunTimeScheduled(final ObjectId kid, final ObjectId terminal);
    
    /**
     * Get Fun Time Day Scheduled
     * @param kid
     * @param terminal
     * @param day
     * @return
     */
    DayScheduledEntity getFunTimeDayScheduled(final ObjectId kid, final ObjectId terminal, final FunTimeDaysEnum day);
    
    /**
     * Save Fun Time Scheduled
     * @param kid
     * @param terminal
     * @param funTimeScheduledEntity
     * @return
     */
    void saveFunTimeScheduled(final ObjectId kid, final ObjectId terminal, final FunTimeScheduledEntity funTimeScheduledEntity);
    
    /**
     * 
     * @param kid
     * @param terminal
     * @param day
     * @param dayScheduled
     */
    void saveDayScheduled(final ObjectId kid, final ObjectId terminal, 
    		final FunTimeDaysEnum day, final DayScheduledEntity dayScheduled);
    
    
    /**
     * Get Terminals With Heartbeat Alert Threshold Enabled And Status Active
     * @return
     */
    List<TerminalEntity> getTerminalsWithHeartbeatAlertThresholdEnabledAndStatusActive();
        
    /**
     * Enable Phone Calls
     * @param kid
     * @param terminal
     */
    void enablePhoneCalls(final ObjectId kid, final ObjectId terminal);
    
    
    /**
     * Disable Phone Calls
     * @param kid
     * @param terminal
     */
    void disablePhoneCalls(final ObjectId kid, final ObjectId terminal);
}
