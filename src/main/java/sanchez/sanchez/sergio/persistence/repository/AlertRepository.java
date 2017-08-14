package sanchez.sanchez.sergio.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.persistence.entity.AlertLevelEnum;


/**
 * @author sergio
 */
@Repository
public interface AlertRepository extends MongoRepository<AlertEntity, ObjectId> {
	Page<AlertEntity> findBySonParentId(ObjectId id, Pageable pageable);
	Page<AlertEntity> findBySonParentIdAndLevel(ObjectId id, AlertLevelEnum level, Pageable pageable);
}
