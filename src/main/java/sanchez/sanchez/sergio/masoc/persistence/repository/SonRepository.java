package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;

/**
 *
 * @author sergio
 */
@Repository
public interface SonRepository extends MongoRepository<SonEntity, ObjectId>, SonRepositoryCustom {
	Iterable<SonEntity> findByParentId(ObjectId id);
	Long countByParentId(ObjectId id);
	Long countById(ObjectId id);
	Long countByParentIdAndId(ObjectId parentId, ObjectId id);
    Long countByParentIdAndProfileImage(ObjectId parentId, String profileImageId);
    SonEntity findByProfileImage(String profileImageId);
    List<SonEntity> findAllByResultsSentimentObsolete(Boolean obsolete);
    List<SonEntity> findAllByResultsViolenceObsolete(Boolean obsolete);
    List<SonEntity> findAllByResultsBullyingObsolete(Boolean obsolete);
    List<SonEntity> findAllByResultsDrugsObsolete(Boolean obsolete);
    List<SonEntity> findAllByResultsAdultObsolete(Boolean obsolete);
}
