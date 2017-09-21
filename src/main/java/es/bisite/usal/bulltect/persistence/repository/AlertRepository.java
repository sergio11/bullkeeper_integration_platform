package es.bisite.usal.bulltect.persistence.repository;

import java.util.List;

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
	Page<AlertEntity> findByParentIdAndDeliveredOrderByCreateAtDesc(ObjectId id, Boolean delivered, Pageable pageable);
	Page<AlertEntity> findByLevelAndParentIdAndDeliveredOrderByCreateAtDesc(AlertLevelEnum level, ObjectId id, Boolean delivered, Pageable pageable);
	List<AlertEntity> findByParentIdAndDeliveredOrderByCreateAtDesc(ObjectId id, Boolean delivered);
	List<AlertEntity> findByDeliveredTrueAndParentIdOrderByCreateAtDesc(ObjectId id);
}
