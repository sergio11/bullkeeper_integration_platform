package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AppModelEntity;

/**
 * App Model Repository
 * @author sergiosanchezsanchez
 *
 */
@Repository
public interface AppModelRepository 
	extends MongoRepository<AppModelEntity, String> {}
