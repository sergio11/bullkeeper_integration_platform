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

	/**
	 * 
	 * @param id
	 * @param delivered
	 * @param pageable
	 * @return
	 */
    Page<AlertEntity> findByParentIdAndDeliveredOrderByCreateAtDesc(ObjectId id, Boolean delivered, Pageable pageable);

    
    /**
     * 
     * @param level
     * @param id
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertEntity> findByLevelAndParentIdAndDeliveredOrderByCreateAtDesc(AlertLevelEnum level, ObjectId id, Boolean delivered, Pageable pageable);

    
    /**
     * 
     * @param id
     * @param delivered
     * @return
     */
    List<AlertEntity> findByParentIdAndDeliveredOrderByCreateAtDesc(ObjectId id, Boolean delivered);

    
    /**
     * 
     * @param id
     * @return
     */
    List<AlertEntity> findByDeliveredTrueAndParentIdOrderByCreateAtDesc(ObjectId id);

    
    /**
     * 
     * @param id
     * @param lastAccessToAlerts
     * @param pageable
     * @return
     */
    List<AlertEntity> findByParentIdAndCreateAtGreaterThanEqualOrderByCreateAtDesc(ObjectId id, Date lastAccessToAlerts, Pageable pageable);

    /**
     * 
     * @param id
     * @param lastAccessToAlerts
     * @param levels
     * @param pageable
     * @return
     */
    List<AlertEntity> findByParentIdAndCreateAtGreaterThanEqualAndLevelIsInOrderByCreateAtDesc(ObjectId id, Date lastAccessToAlerts, String[] levels, Pageable pageable);
    
    
    /**
     * 
     * @param id
     * @param pageable
     * @return
     */
    List<AlertEntity> findByParentIdOrderByCreateAtDesc(ObjectId id, Pageable pageable);

    
    /**
     * 
     * @param id
     * @param levels
     * @param pageable
     * @return
     */
    List<AlertEntity> findByParentIdAndLevelIsInOrderByCreateAtDesc(ObjectId id, String[] levels, Pageable pageable);

    
    /**
     * 
     * @param id
     * @return
     */
    Integer countByParentId(ObjectId id);

    
    /**
     * 
     * @param id
     * @param lastAccessToAlerts
     * @return
     */
    Integer countByParentIdAndCreateAtGreaterThanEqual(ObjectId id, Date lastAccessToAlerts);

    /**
     * 
     * @param parent
     * @return
     */
    Long deleteByParentId(ObjectId parent);
    
    
    /**
     * 
     * @param parent
     * @param level
     * @return
     */
    Long deleteByParentIdAndLevel(ObjectId parent, final AlertLevelEnum level);

    
    /**
     * 
     * @param id
     * @return
     */
    List<AlertEntity> findBySonIdOrderByCreateAtDesc(ObjectId id);

    
    /**
     * 
     * @param son
     * @return
     */
    Long deleteBySonId(ObjectId son);
    
    
    /**
     * 
     * @param son
     * @param level
     * @return
     */
    Long deleteBySonIdAndLevel(ObjectId son, AlertLevelEnum level);

    
    /**
     * 
     * @param id
     * @return
     */
    List<AlertEntity> findByParentIdOrderByCreateAtDesc(ObjectId id);
    
    
    /**
     * 
     * @param id
     * @param deliveryMode
     * @param lastAccess
     * @return
     */
    List<AlertEntity> findByParentIdAndDeliveredFalseAndDeliveryModeAndCreateAtGreaterThan(ObjectId id, AlertDeliveryModeEnum deliveryMode, Date lastAccess);
    
    
    /**
     * 
     * @param from
     * @return
     */
    List<AlertEntity> findByCreateAtGreaterThanEqual(Date from);
    
    /**
     * 
     * @param parentId
     * @param from
     * @return
     */
    List<AlertEntity> findByParentIdAndCreateAtGreaterThanEqual(ObjectId parentId, Date from);
    
    /**
     * 
     * @param parentId
     * @param ids
     * @param from
     * @return
     */
    List<AlertEntity> findByParentIdAndSonIdInAndCreateAtGreaterThanEqual(ObjectId parentId, List<ObjectId> ids, Date from);
    
    
    /**
     * 
     * @param sonId
     * @param from
     * @return
     */
    List<AlertEntity> findBySonIdAndCreateAtGreaterThanEqual(ObjectId sonId, Date from);
    
    /**
     * 
     * @param ids
     * @param minimum
     * @return
     */
    Long deleteByParentIdInAndCreateAtLessThanEqual(List<ObjectId> ids, Date minimum);
    

}
