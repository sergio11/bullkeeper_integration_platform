package sanchez.sanchez.sergio.persistence.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.entity.UserSystemEntity;

/**
 *
 * @author sergio
 */
@Repository
public interface ParentRepository extends MongoRepository<ParentEntity, ObjectId>, ParentRepositoryCustom{
	UserSystemEntity findOneByEmail(String email);
	ParentEntity findByConfirmationToken(String confirmationToken);
	Long countByEmail(String email);
	Long countByConfirmationToken(String confirmationToken);
	ParentEntity findByFbId(String fbId);
	Long deleteByConfirmationToken(String confirmationToken);
}
