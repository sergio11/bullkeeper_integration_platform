package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.TerminalEntity;

import java.util.List;

/**
 * Terminal Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface TerminalRepository extends MongoRepository<TerminalEntity, ObjectId>, 
	 TerminalRepositoryCustom{

	/**
	 * Find By Kid Id
	 * @param id
	 * @return
	 */
	List<TerminalEntity> findByKidId(final ObjectId id);
	
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
	Long countById(final ObjectId id);
	
	/**
	 * Count By Device Id
	 * @param deviceId
	 * @return
	 */
	Long countByDeviceId(final String deviceId);
	
	/**
	 * Find by id and KId id
	 * @param id
	 * @param kid
	 * @return
	 */
	TerminalEntity findByIdAndKidId(final ObjectId id, final ObjectId kid);
	
	/**
	 * Find By Device Id
	 * @param deviceId
	 * @return
	 */
	TerminalEntity findByDeviceId(final String deviceId);
	
	/**
	 * Find By Device Id
	 * @param deviceId
	 * @param kid
	 * @return
	 */
	TerminalEntity findByDeviceIdAndKidId(final String deviceId, final ObjectId kid);
	
	
}
