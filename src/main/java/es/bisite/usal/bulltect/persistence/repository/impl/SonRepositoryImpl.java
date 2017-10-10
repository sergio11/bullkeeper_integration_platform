package es.bisite.usal.bulltect.persistence.repository.impl;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.repository.SonRepositoryCustom;

/**
 * @author sergio
 */
public class SonRepositoryImpl implements SonRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(SonRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

	@Override
	public void setProfileImageId(ObjectId id, String profileImageId) {
		Assert.notNull(id, "id can not be null");
		Assert.notNull(profileImageId, "profileImageId can not be null");
		
		logger.debug("Save Image id: " + profileImageId + " for user with id : " + id);
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("profile_image_id", profileImageId), SonEntity.class);
		
	}
}
