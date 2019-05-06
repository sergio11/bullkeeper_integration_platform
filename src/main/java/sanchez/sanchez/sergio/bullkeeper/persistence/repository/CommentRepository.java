package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 *
 * @author sergio
 */
@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId>, CommentRepositoryCustom {
    
	/**
	 * 
	 * @param kid
	 * @param pageable
	 * @return
	 */
	Page<CommentEntity> findAllByKidId(final ObjectId kid, final Pageable pageable);
	
	/**
	 * 
	 * @param kid
	 * @return
	 */
    List<CommentEntity> findAllByKidId(final ObjectId kid);
    
    /**
     * 
     * @param kid
     * @param pageable
     * @return
     */
    Page<CommentEntity> findAllByKidIdOrderByExtractedAtDesc(final ObjectId kid, final Pageable pageable);
    
    /**
     * 
     * @param kid
     * @return
     */
    List<CommentEntity> findAllByKidIdOrderByExtractedAtDesc(final ObjectId kid);
    
    /**
     * 
     * @param kid
     * @param status
     * @return
     */
    List<CommentEntity> findAllByKidIdAndAnalysisResultsSentimentStatus(final ObjectId kid,
    		final AnalysisStatusEnum status);
    
    /**
     * 
     * @param kid
     * @param status
     * @return
     */
    List<CommentEntity> findAllByKidIdAndAnalysisResultsViolenceStatus(final ObjectId kid, 
    		final AnalysisStatusEnum status);
    
    /**
     * 
     * @param kid
     * @param status
     * @return
     */
    List<CommentEntity> findAllByKidIdAndAnalysisResultsDrugsStatus(final ObjectId kid,
    		final AnalysisStatusEnum status);
    
    /**
     * 
     * @param kid
     * @param status
     * @return
     */
    List<CommentEntity> findAllByKidIdAndAnalysisResultsAdultStatus(final ObjectId kid, 
    		final AnalysisStatusEnum status);
    
    /**
     * 
     * @param kid
     * @param status
     * @return
     */
    List<CommentEntity> findAllByKidIdAndAnalysisResultsBullyingStatus(final ObjectId kid,
    		final AnalysisStatusEnum status);
    
    /**
     * 
     * @param ids
     * @return
     */
    List<CommentEntity> findAllByKidIdIn(final List<ObjectId> ids);
    
    /**
     * 
     * @param ids
     * @return
     */
    List<CommentEntity> findAllByKidIdInOrderByCreatedTimeAsc(final List<ObjectId> ids);
    
    /**
     * 
     * @return
     */
    List<CommentEntity> findAllByOrderByCreatedTimeAsc();
    

    /**
     * 
     * @param ids
     * @return
     */
    Set<CommentEntity> findAllByExternalIdIn(final Set<String> ids);
    
    /**
     * 
     * @param id
     * @return
     */
    Long deleteByKid(final ObjectId id);
    
    /**
     * 
     * @param status
     * @param pageable
     * @return
     */
    Page<CommentEntity> findAllByAnalysisResultsSentimentStatus(final AnalysisStatusEnum status, final Pageable pageable);
  
    /**
   * 
   * @param status
   * @param pageable
   * @return
   */
    Page<CommentEntity> findAllByAnalysisResultsViolenceStatus(final AnalysisStatusEnum status, 
    		final Pageable pageable);
    
    /**
     * 
     * @param status
     * @param pageable
     * @return
     */
    Page<CommentEntity> findAllByAnalysisResultsDrugsStatus(final AnalysisStatusEnum status, final Pageable pageable);
    
    /**
     * 
     * @param status
     * @param pageable
     * @return
     */
    Page<CommentEntity> findAllByAnalysisResultsAdultStatus(final AnalysisStatusEnum status, final Pageable pageable);
 
    /**
     * 
     * @param status
     * @param pageable
     * @return
     */
    Page<CommentEntity> findAllByAnalysisResultsBullyingStatus(final AnalysisStatusEnum status,
    		final Pageable pageable);
    
    /**
     * 
     * @param kid
     * @return
     */
    List<CommentEntity> findByKidId(final ObjectId kid);
    
    /**
     * 
     * @param kid
     * @param from
     * @return
     */
    List<CommentEntity> findByKidIdAndAnalysisResultsSentimentFinishAtGreaterThanEqual(final ObjectId kid, 
    		final Date from);
    
    /**
     * 
     * @param kids
     * @param from
     * @return
     */
    List<CommentEntity> findByKidIdInAndAnalysisResultsSentimentFinishAtGreaterThanEqual(final List<ObjectId> kids, 
    		final Date from);
    
    /**
     * 
     * @param kid
     * @param status
     * @return
     */
    List<CommentEntity> findByKidIdAndAnalysisResultsSentimentStatus(final ObjectId kid, final AnalysisStatusEnum status);
    
    /**
     * 
     * @param kids
     * @param from
     * @return
     */
    List<CommentEntity> findByKidIdAndAnalysisResultsSentimentFinishAtGreaterThanEqual(final List<ObjectId> kids, 
    		final Date from);
    
    /**
     * 
     * @param kid
     * @param from
     * @return
     */
    List<CommentEntity> findByKidIdAndCreatedTimeGreaterThanEqual(final ObjectId kid, 
    		final Date from);
    
    /**
     * 
     * @param ids
     * @param from
     * @return
     */
    List<CommentEntity> findByKidIdInAndCreatedTimeGreaterThanEqual(final List<ObjectId> ids,
    		final Date from);
    
    /**
     * 
     * @param from
     * @return
     */
    List<CommentEntity> findByCreatedTimeGreaterThanEqual(Date from);
    
    /**
     * 
     * @param ids
     * @param from
     * @return
     */
    List<CommentEntity> findByKidIdInAndExtractedAtGreaterThanEqual(final List<ObjectId> ids,
    		final Date from);
    
    /**
     * 
     * @param kid
     * @param from
     * @return
     */
    List<CommentEntity> findByKidAndExtractedAtGreaterThanEqual(final ObjectId kid, 
    		final Date from);
    
    /**
     * 
     * @param kids
     * @param from
     * @return
     */
    List<CommentEntity> findByKidInAndExtractedAtGreaterThanEqual(final List<ObjectId> kids, 
    		final Date from);
    
    /**
     * 
     * @param from
     * @return
     */
    List<CommentEntity> findByExtractedAtGreaterThanEqual(final Date from);
    
    /**
     * 
     * @param from
     * @return
     */
    Long countByAnalysisResultsSentimentFinishAtGreaterThanEqual(final Date from);
    
    /**
     * @param status
     * @return
     */
    Long countByAnalysisResultsSentimentStatus(final AnalysisStatusEnum status);
    
    /**
     * 
     * @param from
     * @return
     */
    Long countByAnalysisResultsViolenceFinishAtGreaterThanEqual(final Date from);
    
    /**
     * @param status
     * @return
     */
    Long countByAnalysisResultsViolenceStatus(final AnalysisStatusEnum status);
    
    /**
     * 
     * @param from
     * @return
     */
    Long countByAnalysisResultsDrugsFinishAtGreaterThanEqual(final Date from);
    
    /**
     * 
     * @param status
     * @return
     */
    Long countByAnalysisResultsDrugsStatus(final AnalysisStatusEnum status);
    
    /**
     * 
     * @param from
     * @return
     */
    Long countByAnalysisResultsAdultFinishAtGreaterThanEqual(final Date from);
    
    /**
     * 
     * @param status
     * @return
     */
    Long countByAnalysisResultsAdultStatus(final AnalysisStatusEnum status);
    
    /**
     * 
     * @param from
     * @return
     */
    Long countByAnalysisResultsBullyingFinishAtGreaterThanEqual(final Date from);
    
    /**
     * 
     * @param status
     * @return
     */
    Long countByAnalysisResultsBullyingStatus(final AnalysisStatusEnum status);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(final ObjectId kid, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdInAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(final List<ObjectId> kids, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsViolenceResult(final ObjectId kid, final Integer result);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(final ObjectId kid, final Date from, final Integer result);
    
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdInAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(final List<ObjectId> kids, final Date from, final Integer result);
    
    
    /**
     * 
     * @param kid
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsDrugsResult(final ObjectId kid, final Integer result);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(final ObjectId kid, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdInAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(final List<ObjectId> kids, 
    		final Date from, final Integer result);
    
    /**
     * @param kid
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsAdultResult(final ObjectId kid, final Integer result);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(final ObjectId kid, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    Long countByKidIdInAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(final List<ObjectId> kids, 
    		final Date from, final Integer result);
    
    /**
     * Count By Kid
     * @param kid
     * @return
     */
    Long countByKidId(final ObjectId kid);
    
    /**
     * 
     * @param kid
     * @param result
     * @return
     */
    Long countByKidIdAndAnalysisResultsBullyingResult(final ObjectId kid, final Integer result);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(final ObjectId kid, 
    		final Date from, final Integer result);
    

    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdInAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(final List<ObjectId> kids, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(final ObjectId kid, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdInAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(final List<ObjectId> kids, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(final ObjectId kid, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdInAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(final List<ObjectId> kids, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kid
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(final ObjectId kid, 
    		final Date from, final Integer result);
    
    /**
     * 
     * @param kids
     * @param from
     * @param result
     * @return
     */
    List<CommentEntity> findByKidIdInAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(final List<ObjectId> kids, 
    		final Date from, final Integer result);
}
