package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;

/**
 * @author sergio
 */
@Repository
public interface AlertRepository extends MongoRepository<AlertEntity, ObjectId>, AlertRepositoryCustom {
	Page<AlertEntity> findByParentIdOrderByCreateAtDesc(ObjectId id, Pageable pageable);
	Page<AlertEntity> findByLevelAndParentIdOrderByCreateAtDesc(AlertLevelEnum level, ObjectId id, Pageable pageable);
}
