package es.bisite.usal.bulltect.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.IterationEntity;

/**
 * @author sergio
 */
@Repository
public interface IterationRepository extends MongoRepository<IterationEntity, ObjectId>, IterationRepositoryCustom {
	List<IterationEntity> findByParentId(ObjectId id);
	List<IterationEntity> findByParentIdOrderByFinishDateDesc(ObjectId id, PageRequest pageRequest );
	IterationEntity findFirstByParentIdOrderByFinishDateDesc(ObjectId id);
}
