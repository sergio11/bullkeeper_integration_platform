package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppModelCategoryEntity;

/**
 * App Model Category Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface AppModelCategoryRepository 
	extends MongoRepository<AppModelCategoryEntity, String> {
	
	/**
	 * Count By Id
	 * @param id
	 * @return
	 */
	long countByCatKey(final String id);
}
