package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
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
    void updateScreenStatus(final ObjectId terminal, final ObjectId kid, final ScreenStatusEnum screenStatus);
    
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
 
}
