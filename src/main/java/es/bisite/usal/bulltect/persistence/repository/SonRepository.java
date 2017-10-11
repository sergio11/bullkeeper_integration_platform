package es.bisite.usal.bulltect.persistence.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.SonEntity;

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
}
