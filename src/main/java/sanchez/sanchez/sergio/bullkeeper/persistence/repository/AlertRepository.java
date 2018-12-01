package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertDeliveryModeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;

/**
 * @author sergio
 */
@Repository
public interface AlertRepository extends MongoRepository<AlertEntity, ObjectId>,
	AlertRepositoryCustom {

	/**
	 * 
	 * @param id
	 * @param delivered
	 * @param pageable
	 * @return
	 */
    Page<AlertEntity> findByGuardianIdAndDeliveredOrderByCreateAtDesc(final ObjectId id, 
    		final Boolean delivered, final Pageable pageable);

    
    /**
     * 
     * @param level
     * @param id
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertEntity> findByLevelAndGuardianIdAndDeliveredOrderByCreateAtDesc(final AlertLevelEnum level, 
    		final ObjectId id, final Boolean delivered, final Pageable pageable);

    
    /**
     * 
     * @param id
     * @param delivered
     * @return
     */
    List<AlertEntity> findByGuardianIdAndDeliveredOrderByCreateAtDesc(final ObjectId id, final Boolean delivered);

    
    /**
     * 
     * @param id
     * @return
     */
    List<AlertEntity> findByDeliveredTrueAndGuardianIdOrderByCreateAtDesc(final ObjectId id);

    
    /**
     * 
     * @param id
     * @param lastAccessToAlerts
     * @param pageable
     * @return
     */
    List<AlertEntity> findByGuardianIdAndCreateAtGreaterThanEqualOrderByCreateAtDesc(final ObjectId id, final Date lastAccessToAlerts,
    			final Pageable pageable);

    /**
     * 
     * @param id
     * @param lastAccessToAlerts
     * @param levels
     * @param pageable
     * @return
     */
    List<AlertEntity> findByGuardianIdAndCreateAtGreaterThanEqualAndLevelIsInOrderByCreateAtDesc(final ObjectId id, final Date lastAccessToAlerts, 
    			final String[] levels, final Pageable pageable);
    
    
    /**
     * 
     * @param id
     * @param pageable
     * @return
     */
    List<AlertEntity> findByGuardianIdOrderByCreateAtDesc(final ObjectId id, final Pageable pageable);

    
    /**
     * 
     * @param id
     * @param levels
     * @param pageable
     * @return
     */
    List<AlertEntity> findByGuardianIdAndLevelIsInOrderByCreateAtDesc(final ObjectId id, 
    		final String[] levels, final Pageable pageable);

    
    /**
     * 
     * @param id
     * @return
     */
    Integer countByGuardianId(final ObjectId id);

    
    /**
     * 
     * @param id
     * @param lastAccessToAlerts
     * @return
     */
    Integer countByGuardianIdAndCreateAtGreaterThanEqual(final ObjectId id, 
    		final Date lastAccessToAlerts);

    /**
     * 
     * @param id
     * @return
     */
    Long deleteByGuardianId(final ObjectId id);
    
    
    /**
     * 
     * @param id
     * @param level
     * @return
     */
    Long deleteByGuardianIdAndLevel(final ObjectId id, final AlertLevelEnum level);

    
    /**
     * 
     * @param id
     * @return
     */
    List<AlertEntity> findByKidIdOrderByCreateAtDesc(final ObjectId id);

    
    /**
     * 
     * @param id
     * @return
     */
    Long deleteByKidId(final ObjectId id);
    
    
    /**
     * 
     * @param id
     * @param level
     * @return
     */
    Long deleteByKidIdAndLevel(final ObjectId id, final AlertLevelEnum level);

    
    /**
     * 
     * @param id
     * @return
     */
    List<AlertEntity> findByGuardianIdOrderByCreateAtDesc(ObjectId id);
    
    
    /**
     * 
     * @param id
     * @param deliveryMode
     * @param lastAccess
     * @return
     */
    List<AlertEntity> findByGuardianIdAndDeliveredFalseAndDeliveryModeAndCreateAtGreaterThan(final ObjectId id, 
    		final AlertDeliveryModeEnum deliveryMode, final Date lastAccess);
    
    
    /**
     * 
     * @param from
     * @return
     */
    List<AlertEntity> findByCreateAtGreaterThanEqual(Date from);
    
    /**
     * 
     * @param id
     * @param from
     * @return
     */
    List<AlertEntity> findByGuardianIdAndCreateAtGreaterThanEqual(ObjectId id, Date from);
    
    /**
     * 
     * @param guardian
     * @param ids
     * @param from
     * @return
     */
    List<AlertEntity> findByGuardianIdAndKidIdInAndCreateAtGreaterThanEqual(ObjectId guardian, List<ObjectId> ids,
    		Date from);
    
    
    /**
     * 
     * @param id
     * @param from
     * @return
     */
    List<AlertEntity> findByKidIdAndCreateAtGreaterThanEqual(ObjectId kid, Date from);
    
    /**
     * 
     * @param ids
     * @param minimum
     * @return
     */
    Long deleteByGuardianIdInAndCreateAtLessThanEqual(List<ObjectId> ids, Date minimum);
    

}
