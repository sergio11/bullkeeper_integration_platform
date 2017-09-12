package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;



/**
 *
 * @author sergio
 */
public interface ParentRepositoryCustom {
    void setAsNotActiveAndConfirmationToken(ObjectId id, String confirmationToken);
    void setNewPassword(ObjectId id, String newPassword);
    void setActiveAsTrueAndDeleteConfirmationToken(String confirmationToken);
}
