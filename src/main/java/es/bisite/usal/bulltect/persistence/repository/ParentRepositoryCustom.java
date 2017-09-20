package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;



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
}
