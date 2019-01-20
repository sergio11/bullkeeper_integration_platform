package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DayScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeDaysEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeScheduledEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ScreenStatusEnum;

/**
 * Terminal Repository Custom
 * @author sergiosanchezsanchez
 *
 */
public interface TerminalRepositoryCustom {

    /**
     * Update Screen Status
     * @param terminal
     * @param kid
     * @param screenStatus
     */
    void saveTerminalStatus(final ObjectId terminal, final ObjectId kid,
    		final ScreenStatusEnum screenStatus, final boolean accessFineLocationEnabled,
    		final boolean readContactsEnabled, final boolean readCallLogEnabled,
    		final boolean writeExternalStorageEnabled, final boolean usageStatsAllowed,
    		final boolean adminAccessEnabled);
    
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
     * Un lock screen
     * @param kid
     * @param terminal
     */
    void unlockScreen(final ObjectId kid, final ObjectId terminal);
    
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
    
 
}
