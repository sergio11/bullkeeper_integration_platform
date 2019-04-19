package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;

/**
 *
 * @author sergio
 */
@Repository
public interface GuardianRepository extends MongoRepository<GuardianEntity, ObjectId>, 
	GuardianRepositoryCustom {

	/**
	 * Find By Id
	 * @param id
	 * @return
	 */
	GuardianEntity findById(final ObjectId id);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
    GuardianEntity findOneByEmail(String email);

    /**
     * 
     * @param confirmationToken
     * @return
     */
    GuardianEntity findByConfirmationToken(String confirmationToken);

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
    Long countByEmailAndPendingDeletionFalse(final String email);

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
    GuardianEntity findByFbId(String fbId);
    
    /**
     * 
     * @param googleId
     * @return
     */
    GuardianEntity findByGoogleId(String googleId);

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
    GuardianEntity findByProfileImage(String profileImageId);
    
    /**
     * 
     * @param value
     * @return
     */
    List<GuardianEntity> findByPreferencesPushNotificationsEnabledAndActiveTrueAndLockedFalseAndPendingDeletionFalse(Boolean value);
    
  
    
    
}
