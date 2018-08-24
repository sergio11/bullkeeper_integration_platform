package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.AlertDeliveryModeEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.AlertLevelEnum;

/**
 * @author sergio
 */
@Repository
public interface AlertRepository extends MongoRepository<AlertEntity, ObjectId>, AlertRepositoryCustom {

    Page<AlertEntity> findByParentIdAndDeliveredOrderByCreateAtDesc(ObjectId id, Boolean delivered, Pageable pageable);

    Page<AlertEntity> findByLevelAndParentIdAndDeliveredOrderByCreateAtDesc(AlertLevelEnum level, ObjectId id, Boolean delivered, Pageable pageable);

    List<AlertEntity> findByParentIdAndDeliveredOrderByCreateAtDesc(ObjectId id, Boolean delivered);

    List<AlertEntity> findByDeliveredTrueAndParentIdOrderByCreateAtDesc(ObjectId id);

    List<AlertEntity> findByParentIdAndCreateAtGreaterThanEqualOrderByCreateAtDesc(ObjectId id, Date lastAccessToAlerts, Pageable pageable);

    List<AlertEntity> findByParentIdAndCreateAtGreaterThanEqualAndLevelIsInOrderByCreateAtDesc(ObjectId id, Date lastAccessToAlerts, String[] levels, Pageable pageable);
    
    List<AlertEntity> findByParentIdOrderByCreateAtDesc(ObjectId id, Pageable pageable);

    List<AlertEntity> findByParentIdAndLevelIsInOrderByCreateAtDesc(ObjectId id, String[] levels, Pageable pageable);

    Integer countByParentId(ObjectId id);

    Integer countByParentIdAndCreateAtGreaterThanEqual(ObjectId id, Date lastAccessToAlerts);

    Long deleteByParentId(ObjectId parent);

    List<AlertEntity> findBySonIdOrderByCreateAtDesc(ObjectId id);

    Long deleteBySonId(ObjectId son);

    List<AlertEntity> findByParentIdOrderByCreateAtDesc(ObjectId id);
    
    List<AlertEntity> findByParentIdAndDeliveredFalseAndDeliveryModeAndCreateAtGreaterThan(ObjectId id, AlertDeliveryModeEnum deliveryMode, Date lastAccess);
    
    List<AlertEntity> findByCreateAtGreaterThanEqual(Date from);
    
    List<AlertEntity> findByParentIdAndCreateAtGreaterThanEqual(ObjectId parentId, Date from);
    
    List<AlertEntity> findByParentIdAndSonIdInAndCreateAtGreaterThanEqual(ObjectId parentId, List<ObjectId> ids, Date from);
    
    List<AlertEntity> findBySonIdAndCreateAtGreaterThanEqual(ObjectId sonId, Date from);
    
    Long deleteByParentIdInAndCreateAtLessThanEqual(List<ObjectId> ids, Date minimum);
    

}
