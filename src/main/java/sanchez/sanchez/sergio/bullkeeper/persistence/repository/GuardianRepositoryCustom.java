package sanchez.sanchez.sergio.bullkeeper.persistence.repository;


import java.util.List;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PreferencesEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PreferencesEntity.RemoveAlertsEveryEnum;



/**
 *
 * @author sergio
 */
public interface GuardianRepositoryCustom {
	
	/**
	 * 
	 * @param id
	 * @param confirmationToken
	 */
    void setAsNotActiveAndConfirmationToken(ObjectId id, String confirmationToken);
    
    /**
     * 
     * @param id
     * @param newPassword
     */
    void setNewPassword(ObjectId id, String newPassword);
    
    /**
     * 
     * @param confirmationToken
     */
    void setActiveAsTrueAndDeleteConfirmationToken(String confirmationToken);
    
    /**
     * 
     * @param id
     */
    void lockAccount(ObjectId id);
    
    /**
     * 
     * @param id
     */
    void unlockAccount(ObjectId id);
    
    /**
     * 
     * @param fbAccessToken
     * @param fbId
     */
    void setFbAccessTokenByFbId(String fbAccessToken, String fbId);
    
    /**
     * 
     * @param confirmationToken
     */
    void setPendingDeletionAsFalseAndDeleteConfirmationToken(String confirmationToken);
    
    /**
     * 
     */
    void setPendingDeletionAsFalseAndDeleteConfirmationToken();
    
    /**
     * 
     * @param id
     * @param confirmationToken
     */
    void setPendingDeletionAsTrueAndConfirmationTokenById(ObjectId id, String confirmationToken);
    
    /**
     * 
     * @param id
     * @param profileImageId
     */
    void setProfileImageId(ObjectId id, String profileImageId);
    
    /**
     * 
     * @param id
     */
    void setLastAccessToAlerts(ObjectId id);
    
    /**
     * 
     * @param id
     */
    void updateLastLoginAccessAndLastAccessToAlerts(ObjectId id);
    
    /**
     * 
     * @param id
     * @return
     */
    String getGuardianImageIdByUserId(ObjectId id);
    
    /**
     * 
     * @param email
     * @return
     */
    String getFbIdByEmail(String email);
    
    /**
     * 
     * @return
     */
    List<ObjectId> getGuardianIds();
    
    /**
     * 
     * @param removeAlertsEvery
     * @return
     */
    List<ObjectId> getGuardianIdsWithRemoveAlertsEveryAs(
    		RemoveAlertsEveryEnum removeAlertsEvery);
    
    /**
     * 
     * @param id
     * @return
     */
    PreferencesEntity getPreferences(ObjectId id);
    
    /**
     * Search
     * @param text
     * @return
     */
    List<GuardianEntity> search(final String text, final List<ObjectId> exclude);
}
