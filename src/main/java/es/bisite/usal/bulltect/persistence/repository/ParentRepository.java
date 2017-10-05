package es.bisite.usal.bulltect.persistence.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import org.springframework.data.mongodb.repository.Query;

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

    Long deleteByConfirmationToken(String confirmationToken);

    Long deleteByActiveFalse();

    @Query(value = "{ 'email' : ?0 }", fields = "{ 'fb_id' : 1 }")
    String getFbIdByEmail(String email);
}
