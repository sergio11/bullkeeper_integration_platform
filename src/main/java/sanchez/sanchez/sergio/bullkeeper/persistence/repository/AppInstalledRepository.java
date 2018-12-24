package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;

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
	 * @param kid
	 * @param terminal
	 * @return
	 */
	long countByKidIdAndTerminalId(final ObjectId kid, final ObjectId terminal);
	
	/**
	 * Find All By Terminal Id And Kid Id
	 * @param terminalId
	 * @param kidId
	 * @return
	 */
	Iterable<AppInstalledEntity> findAllByTerminalIdAndKidId(final ObjectId terminalId, 
			final ObjectId kidId);
	
	
	/**
	 * Find All By Terminal Id And Kid Id and contain text
	 * @param terminalId
	 * @param kidId
	 * @return
	 */
	Iterable<AppInstalledEntity> findAllByTerminalIdAndKidIdAndAppNameIgnoreCaseContaining(final ObjectId terminalId, 
			final ObjectId kidId, final String text);
	
	

	/**
	 * Delete By Kid Id and Terminal Id
	 * @param kidId
	 * @param terminalId
	 */
	void deleteByKidIdAndTerminalId(final ObjectId kidId, final ObjectId terminalId);
	
	/**
	 * Delete By Kid Id and Terminal Id and id in
	 * @param kidId
	 * @param terminalId
	 * @param ids
	 */
	void deleteByKidIdAndTerminalIdAndIdIn(final ObjectId kidId, final ObjectId terminalId, final List<ObjectId> ids);
	
	
	/**
	 * Find By Id collection
	 * @param ids
	 * @return
	 */
	Iterable<AppInstalledEntity> findByIdIn(final Iterable<ObjectId> ids);
	
	/**
	 * Find By Package Name
	 * @param packageName
	 * @return
	 */
	AppInstalledEntity findOneByPackageName(final String packageName);

	
}
