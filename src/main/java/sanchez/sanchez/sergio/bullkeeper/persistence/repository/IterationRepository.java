package sanchez.sanchez.sergio.bullkeeper.persistence.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.IterationEntity;

/**
 * @author sergio
 */
@Repository
public interface IterationRepository extends MongoRepository<IterationEntity, ObjectId>, IterationRepositoryCustom {
}
