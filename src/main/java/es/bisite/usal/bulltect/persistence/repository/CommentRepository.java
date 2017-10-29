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
    Long deleteBySonEntity(ObjectId id);
    List<CommentEntity> findAllByAnalysisResultsSentimentStatus(AnalysisStatusEnum status);
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
}
