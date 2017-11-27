package es.bisite.usal.bulltect.persistence.repository;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.ParentEntity;

/**
 *
 * @author sergio
 */
@Repository
public interface ParentRepository extends MongoRepository<ParentEntity, ObjectId>, ParentRepositoryCustom {

    ParentEntity findOneByEmail(String email);

    ParentEntity findByConfirmationToken(String confirmationToken);

    Long countByEmail(String email);

    Long countByEmailAndActiveTrue(String email);

    Long countByEmailAndLockedFalse(String email);

    Long countByConfirmationToken(String confirmationToken);

    ParentEntity findByFbId(String fbId);
    
    ParentEntity findByGoogleId(String googleId);

    Long deleteByConfirmationToken(String confirmationToken);

    Long deleteByActiveFalse();
    
    ParentEntity findByProfileImage(String profileImageId);
    
    List<ParentEntity> findByPreferencesPushNotificationsEnabledAndActiveTrueAndLockedFalseAndPendingDeletionFalse(Boolean value);
    
    
    
}
