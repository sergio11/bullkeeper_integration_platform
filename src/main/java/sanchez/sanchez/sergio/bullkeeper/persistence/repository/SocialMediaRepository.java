package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;


/**
 *
 * @author sergio
 */
@Repository
public interface SocialMediaRepository extends MongoRepository<SocialMediaEntity, ObjectId>, SocialMediaRepositoryCustom {
    
	/**
	 * 
	 * @param accessToken
	 * @param type
	 * @return
	 */
	SocialMediaEntity findByAccessTokenAndType(String accessToken, SocialMediaTypeEnum type);
    
	/**
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	SocialMediaEntity findByTypeAndKidId(SocialMediaTypeEnum type, ObjectId id);
    
	/**
	 * 
	 * @param id
	 * @return
	 */
	List<SocialMediaEntity> findByKidId(ObjectId id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
    List<SocialMediaEntity> findByIdAndInvalidTokenTrue(ObjectId id);
    
    /**
     * 
     * @param id
     * @return
     */
    List<SocialMediaEntity> findByIdAndInvalidTokenFalse(ObjectId id);
    
    /**
     * 
     * @param id
     * @return
     */
    Long deleteByKidId(ObjectId id);
    
    /**
     * 
     * @param id
     * @return
     */
    Long deleteById(ObjectId id);
    
    /**
     * 
     * @param id
     * @return
     */
    Long countById(ObjectId id);
    
    /**
     * 
     * @return
     */
    Long countByInvalidTokenFalse();
    
}
