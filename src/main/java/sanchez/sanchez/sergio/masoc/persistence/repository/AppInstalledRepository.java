package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.masoc.persistence.entity.AppInstalledEntity;

/**
 * App Installed Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface AppInstalledRepository extends MongoRepository<AppInstalledEntity, Long>, AppInstalledRepositoryCustom {

	/**
	 * Find By Id
	 * @param id
	 */
	AppInstalledEntity findById(final ObjectId id);
	
	/**
	 * Delete By Id
	 * @param id
	 */
	void deleteById(final ObjectId id);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * Count By Id and Son Id
	 * @param id
	 * @param sonId
	 * @return
	 */
	long countByIdAndSonId(final ObjectId id, final ObjectId sonId);
	
	/**
	 * Find All By Terminal Id And Son Id
	 * @param terminalId
	 * @param sonId
	 * @return
	 */
	Iterable<AppInstalledEntity> findAllByTerminalIdAndSonId(final ObjectId terminalId, final ObjectId sonId);

	/**
	 * Delete By Son Id and Terminal Id
	 * @param sonId
	 * @param terminalId
	 */
	void deleteBySonIdAndTerminalId(final ObjectId sonId, final ObjectId terminalId);
	
	
	/**
	 * Find By Id collection
	 * @param ids
	 * @return
	 */
	Iterable<AppInstalledEntity> findByIdIn(final Iterable<ObjectId> ids);

	
}
