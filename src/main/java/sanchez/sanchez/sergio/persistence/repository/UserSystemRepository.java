package sanchez.sanchez.sergio.persistence.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.UserSystemEntity;


/**
 * @author sergio
 */
@Repository
public interface UserSystemRepository extends MongoRepository<UserSystemEntity, ObjectId> {
	Optional<UserSystemEntity> findOneByEmail(String email);
	Long countByEmail(String email);
}
