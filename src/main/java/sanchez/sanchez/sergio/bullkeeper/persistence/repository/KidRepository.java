package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;

/**
 * Kid Repository
 * @author sergio
 */
@Repository
public interface KidRepository extends MongoRepository<KidEntity, ObjectId>, 
		KidRepositoryCustom {


	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Long countById(ObjectId id);
	
    
    /**
     * 
     * @param profileImage
     * @return
     */
    KidEntity findByProfileImage(String profileImage);
    
    /**
     * 
     * @param obsolete
     * @return
     */
    List<KidEntity> findAllByResultsSentimentObsolete(Boolean obsolete);
    
    /**
     * 
     * @param obsolete
     * @return
     */
    List<KidEntity> findAllByResultsViolenceObsolete(Boolean obsolete);
    
    /**
     * 
     * @param obsolete
     * @return
     */
    List<KidEntity> findAllByResultsBullyingObsolete(Boolean obsolete);
    
    /**
     * 
     * @param obsolete
     * @return
     */
    List<KidEntity> findAllByResultsDrugsObsolete(Boolean obsolete);
    
    /**
     * 
     * @param obsolete
     * @return
     */
    List<KidEntity> findAllByResultsAdultObsolete(Boolean obsolete);
}
