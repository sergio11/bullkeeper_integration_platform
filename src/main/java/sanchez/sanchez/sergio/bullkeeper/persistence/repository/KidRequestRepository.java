package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidRequestEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.RequestTypeEnum;

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
	Iterable<KidRequestEntity> findAllByKidOrderByRequestAtDesc(final ObjectId kid);
	
	/**
	 * 
	 * @param kid
	 * @param type
	 * @return
	 */
	KidRequestEntity findFirstByKidAndTypeOrderByExpiredAtDesc(final ObjectId kid, final RequestTypeEnum type);
	
	
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
	
	/**
	 * Find By Id And Kid
	 * @param id
	 * @param kid
	 * @return
	 */
	KidRequestEntity findByIdAndKid(final ObjectId id, final ObjectId kid);
	
	/**
	 * Delete By Kid and iD
	 * @param id
	 * @param kid
	 */
	void deleteByIdAndKid(final ObjectId id, final ObjectId kid);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
}

