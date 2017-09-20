package es.bisite.usal.bulltect.persistence.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.UserSystemEntity;


/**
 * @author sergio
 */
@Repository
public interface UserSystemRepository extends MongoRepository<UserSystemEntity, ObjectId>, UserSystemRepositoryCustom {
	UserSystemEntity findOneByEmail(String email);
	Long countByEmail(String email);
}
