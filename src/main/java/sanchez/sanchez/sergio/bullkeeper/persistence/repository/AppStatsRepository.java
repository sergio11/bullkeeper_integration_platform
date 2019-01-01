package sanchez.sanchez.sergio.bullkeeper.persistence.repository;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppStatsEntity;

/**
 * App Stats Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface AppStatsRepository extends MongoRepository<AppStatsEntity, Long> {

	/**
	 * Find By Id
	 * @param id
	 */
	AppStatsEntity findById(final ObjectId id);
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countById(final ObjectId id);
	
	/**
	 * 
	 * @param terminal
	 * @param kidId
	 * @param pageRequest
	 * @return
	 */
	Page<AppStatsEntity> findByTerminalIdAndKidId(final ObjectId terminal, final ObjectId kidId,
			final Pageable pageRequest);
 
	/**
	 * Find One By App Id And Terminal Id And Kid Id
	 * @return
	 */
	AppStatsEntity findOneByAppIdAndTerminalIdAndKidId(
			final ObjectId app, final ObjectId terminal, final ObjectId kid);
	
	
	/**
	 * 
	 * @param kid
	 * @param terminal
	 * @param ids
	 */
	void deleteByKidIdAndTerminalIdAndIdIn(final ObjectId kid, 
			final ObjectId terminal, final List<ObjectId> ids);
	
}
