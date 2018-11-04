package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.masoc.persistence.entity.ParentEntity;

/**
 *
 * @author sergio
 */
@Repository
public interface ParentRepository extends MongoRepository<ParentEntity, ObjectId>, ParentRepositoryCustom {

	/**
	 * 
	 * @param email
	 * @return
	 */
    ParentEntity findOneByEmail(String email);

    /**
     * 
     * @param confirmationToken
     * @return
     */
    ParentEntity findByConfirmationToken(String confirmationToken);

    /**
     * 
     * @param email
     * @return
     */
    Long countByEmail(String email);

    /**
     * 
     * @param email
     * @return
     */
    Long countByEmailAndActiveTrue(String email);

    /**
     * 
     * @param email
     * @return
     */
    Long countByEmailAndLockedFalse(String email);

    /**
     * 
     * @param confirmationToken
     * @return
     */
    Long countByConfirmationToken(String confirmationToken);

    
    /**
     * 
     * @param fbId
     * @return
     */
    ParentEntity findByFbId(String fbId);
    
    /**
     * 
     * @param googleId
     * @return
     */
    ParentEntity findByGoogleId(String googleId);

    /**
     * 
     * @param confirmationToken
     * @return
     */
    Long deleteByConfirmationToken(String confirmationToken);

    /**
     * 
     * @return
     */
    Long deleteByActiveFalse();
    
    /**
     * 
     * @param profileImageId
     * @return
     */
    ParentEntity findByProfileImage(String profileImageId);
    
    /**
     * 
     * @param value
     * @return
     */
    List<ParentEntity> findByPreferencesPushNotificationsEnabledAndActiveTrueAndLockedFalseAndPendingDeletionFalse(Boolean value);
    
    
    
}
