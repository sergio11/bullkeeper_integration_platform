package sanchez.sanchez.sergio.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;


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
    List<SocialMediaEntity> deleteBySonEntityId(ObjectId id);
    SocialMediaEntity deleteById(ObjectId id);
    Long countById(ObjectId id);
}
