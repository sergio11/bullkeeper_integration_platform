package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.IterationEntity;

/**
 * @author sergio
 */
@Repository
public interface IterationRepository extends MongoRepository<IterationEntity, ObjectId>, IterationRepositoryCustom {}
