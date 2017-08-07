package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.SchoolEntity;


/**
 *
 * @author sergio
 */
@Repository
public interface SchoolRepository extends MongoRepository<SchoolEntity, ObjectId> {
	Page<SchoolEntity> findAllByName(String name, Pageable pageable);
}
