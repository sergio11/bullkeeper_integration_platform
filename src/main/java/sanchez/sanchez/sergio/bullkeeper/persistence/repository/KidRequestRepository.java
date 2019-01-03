package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidRequestEntity;

/**
 * Kid Request Entity
 * @author sergio
 */
@Repository
public interface KidRequestRepository extends MongoRepository<KidRequestEntity, ObjectId> {

	/**
	 * Find All By Kid
	 * @param kid
	 * @return
	 */
	Iterable<KidRequestEntity> findAllByKid(final ObjectId kid);
	
	/**
	 * Delete All By Kid
	 * @param kid
	 */
	void deleteAllByKid(final ObjectId kid);
	
	/**
	 * Delete All By Kid And Id In
	 * @param kid
	 * @param ids
	 */
	void deleteAllByKidAndIdIn(final ObjectId kid, final List<ObjectId> ids);
	
	
}

