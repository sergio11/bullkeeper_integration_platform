package sanchez.sanchez.sergio.persistence.repository.impl;


import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.persistence.repository.ParentRepositoryCustom;

/**
 * @author sergio
 */
public class ParentRepositoryImpl implements ParentRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(ParentRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Override
    public void setAsNotActiveAndConfirmationToken(ObjectId id, String confirmationToken) {
        Assert.notNull(confirmationToken, "Confirmation Token can not be null");
        Assert.hasLength(confirmationToken, "Confirmation Token can not be empty");
        
        mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("is_active", Boolean.FALSE).set("confirmation_token", confirmationToken), ParentEntity.class);
    }

	@Override
	public void setNewPassword(ObjectId id, String newPassword) {
		Assert.notNull(id, "Parent Id can not be null");
		Assert.notNull(newPassword, "New Password can not be null");
        Assert.hasLength(newPassword, "New Password can not be empty");
        
        mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("password", newPassword), ParentEntity.class);
	}

	@Override
	public void setActiveAsTrueAndDeleteConfirmationToken(String confirmationToken) {
		Assert.notNull(confirmationToken, "Confirmation Token can not be null");
        Assert.hasLength(confirmationToken, "Confirmation Token can not be empty");
		
        mongoTemplate.updateFirst(
                new Query(Criteria.where("confirmation_token").is(confirmationToken)),
                new Update().set("is_active", Boolean.TRUE).set("confirmation_token", ""), ParentEntity.class);
	}

	@Override
	public void lockAccount(ObjectId id) {
		Assert.notNull(id, "id can not be null");
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("is_locked", Boolean.TRUE), ParentEntity.class);
	}

	@Override
	public void unlockAccount(ObjectId id) {
		Assert.notNull(id, "id can not be null");
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("is_locked", Boolean.FALSE), ParentEntity.class);
	}

	@Override
	public void setFbAccessTokenByFbId(String fbAccessToken, String fbId) {
		Assert.notNull(fbAccessToken, "Fb Access Token can not be null");
		Assert.hasLength(fbAccessToken, "Fb Access Token can not be empty");
		Assert.notNull(fbId, "Fb Id can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("fb_id").in(fbId)), 
				new Update().set("fb_access_token", fbAccessToken), ParentEntity.class);
		
	}
}
