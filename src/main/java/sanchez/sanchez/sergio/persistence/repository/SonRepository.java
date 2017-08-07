package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.SonEntity;

/**
 *
 * @author sergio
 */
@Repository
public interface SonRepository extends MongoRepository<SonEntity, ObjectId> {
	Iterable<SonEntity> findByParentId(ObjectId id);
}
