package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppInstalledEntity;

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
	 * 
	 * @param app
	 * @param terminal
	 * @return
	 */
	AppInstalledEntity findByIdAndTerminalId(final ObjectId app, final ObjectId terminal);
	
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
	 * Count By Id and Kid Id
	 * @param id
	 * @param kidId
	 * @return
	 */
	long countByIdAndKidId(final ObjectId id, final ObjectId kidId);
	
	/**
	 * Find All By Terminal Id And Kid Id
	 * @param terminalId
	 * @param kidId
	 * @return
	 */
	Iterable<AppInstalledEntity> findAllByTerminalIdAndKidId(final ObjectId terminalId, 
			final ObjectId kidId);

	/**
	 * Delete By Kid Id and Terminal Id
	 * @param kidId
	 * @param terminalId
	 */
	void deleteByKidIdAndTerminalId(final ObjectId kidId, final ObjectId terminalId);
	
	
	/**
	 * Find By Id collection
	 * @param ids
	 * @return
	 */
	Iterable<AppInstalledEntity> findByIdIn(final Iterable<ObjectId> ids);

	
}
