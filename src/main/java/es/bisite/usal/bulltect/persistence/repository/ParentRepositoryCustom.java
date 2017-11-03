package es.bisite.usal.bulltect.persistence.repository;


import java.util.List;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.persistence.entity.PreferencesEntity;
import es.bisite.usal.bulltect.persistence.entity.PreferencesEntity.RemoveAlertsEveryEnum;



/**
 *
 * @author sergio
 */
public interface ParentRepositoryCustom {
    void setAsNotActiveAndConfirmationToken(ObjectId id, String confirmationToken);
    void setNewPassword(ObjectId id, String newPassword);
    void setActiveAsTrueAndDeleteConfirmationToken(String confirmationToken);
    void lockAccount(ObjectId id);
    void unlockAccount(ObjectId id);
    void setFbAccessTokenByFbId(String fbAccessToken, String fbId);
    void setPendingDeletionAsFalseAndDeleteConfirmationToken(String confirmationToken);
    void setPendingDeletionAsFalseAndDeleteConfirmationToken();
    void setPendingDeletionAsTrueAndConfirmationTokenById(ObjectId id, String confirmationToken);
    void setProfileImageId(ObjectId id, String profileImageId);
    void setLastAccessToAlerts(ObjectId id);
    void updateLastLoginAccessAndLastAccessToAlerts(ObjectId id);
    String getProfileImageIdByUserId(ObjectId id);
    String getFbIdByEmail(String email);
    List<ObjectId> getParentIds();
    List<ObjectId> getParentIdsWithRemoveAlertsEveryAs(RemoveAlertsEveryEnum removeAlertsEvery);
    PreferencesEntity getPreferences(ObjectId id);
}
