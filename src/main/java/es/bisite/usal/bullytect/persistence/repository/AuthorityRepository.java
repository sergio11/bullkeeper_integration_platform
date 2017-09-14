package es.bisite.usal.bullytect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bullytect.persistence.entity.AuthorityEntity;
import es.bisite.usal.bullytect.persistence.entity.AuthorityEnum;

/**
 *
 * @author sergio
 */
@Repository
public interface AuthorityRepository extends MongoRepository<AuthorityEntity, ObjectId> {
	
	AuthorityEntity findByType(AuthorityEnum type);
	
}
