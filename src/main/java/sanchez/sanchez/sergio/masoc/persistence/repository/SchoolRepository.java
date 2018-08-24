package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.SchoolEntity;


/**
 *
 * @author sergio
 */
@Repository
public interface SchoolRepository extends MongoRepository<SchoolEntity, ObjectId>, SchoolRepositoryCustom {
	Page<SchoolEntity> findAllByNameLikeIgnoreCase(String name, Pageable pageable);
	List<SchoolEntity> findAllByNameLikeIgnoreCase(String name);
	Long countByName(String name);
}
