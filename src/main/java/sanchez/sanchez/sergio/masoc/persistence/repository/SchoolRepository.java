package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.SchoolEntity;


/**
 * School Repository
 * @author sergio
 */
@Repository
public interface SchoolRepository extends MongoRepository<SchoolEntity, ObjectId>, SchoolRepositoryCustom {
	
	/**
	 * Find all by name like ignore case
	 * @param name
	 * @param pageable
	 * @return
	 */
	Page<SchoolEntity> findAllByNameLikeIgnoreCase(final String name, final Pageable pageable);
	
	/**
	 * Find all by name like ignore case
	 * @param name
	 * @return
	 */
	List<SchoolEntity> findAllByNameLikeIgnoreCase(final String name);
	
	/**
	 * Count By Name Ignore Case
	 * @param name
	 * @return
	 */
	Long countByNameIgnoreCase(final String name);
	
	/**
	 * Count By Email
	 * @param email
	 * @return
	 */
	Long countByEmail(final String email);
}
