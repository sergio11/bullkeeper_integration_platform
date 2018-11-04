package sanchez.sanchez.sergio.masoc.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.masoc.persistence.entity.TerminalEntity;
import java.util.List;

/**
 * Terminal Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface ITerminalRepository extends MongoRepository<TerminalEntity, ObjectId> {

	/**
	 * Find By Son Entity Id
	 * @param id
	 * @return
	 */
	List<TerminalEntity> findBySonEntityId(final ObjectId id);
	
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
	
}
