package sanchez.sanchez.sergio.masoc.persistence.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.IterationEntity;

/**
 * @author sergio
 */
@Repository
public interface IterationRepository extends MongoRepository<IterationEntity, ObjectId>, IterationRepositoryCustom {
}
