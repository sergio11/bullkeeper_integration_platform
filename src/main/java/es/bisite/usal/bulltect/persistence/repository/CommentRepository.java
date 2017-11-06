package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import java.util.Date;
import java.util.List;


/**
 *
 * @author sergio
 */
@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, ObjectId>, CommentRepositoryCustom {
    Page<CommentEntity> findAllBySonEntityId(ObjectId userId, Pageable pageable);
    List<CommentEntity> findAllBySonEntityId(ObjectId userId);
    List<CommentEntity> findAllBySonEntityIdIn(List<ObjectId> ids);
    List<CommentEntity> findAllBySonEntityIdInOrderByCreatedTimeAsc(List<ObjectId> ids);
    List<CommentEntity> findAllByOrderByCreatedTimeAsc();
    Long deleteBySonEntity(ObjectId id);
    Page<CommentEntity> findAllByAnalysisResultsSentimentStatus(AnalysisStatusEnum status, Pageable pageable);
    Page<CommentEntity> findAllByAnalysisResultsViolenceStatus(AnalysisStatusEnum status, Pageable pageable);
    Page<CommentEntity> findAllByAnalysisResultsDrugsStatus(AnalysisStatusEnum status, Pageable pageable);
    Page<CommentEntity> findAllByAnalysisResultsAdultStatus(AnalysisStatusEnum status, Pageable pageable);
    Page<CommentEntity> findAllByAnalysisResultsBullyingStatus(AnalysisStatusEnum status, Pageable pageable);
    List<CommentEntity> findBySonEntityId(ObjectId userId);
    List<CommentEntity> findBySonEntityIdAndAnalysisResultsSentimentFinishAtGreaterThanEqual(ObjectId sonId, Date from);
    List<CommentEntity> findBySonEntityIdAndCreatedTimeGreaterThanEqual(ObjectId sonId, Date from);
    List<CommentEntity> findBySonEntityIdInAndCreatedTimeGreaterThanEqual(List<ObjectId> ids, Date from);
    List<CommentEntity> findByCreatedTimeGreaterThanEqual(Date from);
    List<CommentEntity> findBySonEntityIdInAndExtractedAtGreaterThanEqual(List<ObjectId> ids, Date from);
    List<CommentEntity> findByExtractedAtGreaterThanEqual(Date from);
    Long countByAnalysisResultsSentimentFinishAtGreaterThanEqual(Date from);
    Long countByAnalysisResultsViolenceFinishAtGreaterThanEqual(Date from);
    Long countByAnalysisResultsDrugsFinishAtGreaterThanEqual(Date from);
    Long countByAnalysisResultsAdultFinishAtGreaterThanEqual(Date from);
    Long countByAnalysisResultsBullyingFinishAtGreaterThanEqual(Date from);
    Long countBySonEntityIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(ObjectId sonId, Date from, Integer result);
    Long countBySonEntityIdAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(ObjectId sonId, Date from, Integer result);
    Long countBySonEntityIdAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(ObjectId sonId, Date from, Integer result);
    Long countBySonEntityIdAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(ObjectId sonId, Date from, Integer result);
    List<CommentEntity> findBySonEntityIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(ObjectId sonId, Date from, Integer result);
    List<CommentEntity> findBySonEntityIdAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(ObjectId sonId, Date from, Integer result);
    List<CommentEntity> findBySonEntityIdAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(ObjectId sonId, Date from, Integer result);
    List<CommentEntity> findBySonEntityIdAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(ObjectId sonId, Date from, Integer result);
}
