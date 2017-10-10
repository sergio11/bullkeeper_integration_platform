package es.bisite.usal.bulltect.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import es.bisite.usal.bulltect.persistence.entity.SocialMediaEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;


/**
 *
 * @author sergio
 */
@Repository
public interface SocialMediaRepository extends MongoRepository<SocialMediaEntity, ObjectId>, SocialMediaRepositoryCustom {
    SocialMediaEntity findByAccessTokenAndType(String accessToken, SocialMediaTypeEnum type);
    SocialMediaEntity findByTypeAndSonEntityId(SocialMediaTypeEnum type, ObjectId id);
    List<SocialMediaEntity> findBySonEntityId(ObjectId id);
    List<SocialMediaEntity> findByIdAndInvalidTokenTrue(ObjectId id);
    List<SocialMediaEntity> findByIdAndInvalidTokenFalse(ObjectId id);
    Long deleteBySonEntityId(ObjectId id);
    Long deleteById(ObjectId id);
    Long countById(ObjectId id);
    Long countByInvalidTokenFalse();
    
}
