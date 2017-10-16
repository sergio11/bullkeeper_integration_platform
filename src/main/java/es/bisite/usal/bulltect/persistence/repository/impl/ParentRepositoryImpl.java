package es.bisite.usal.bulltect.persistence.repository.impl;


import java.util.Date;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.repository.ParentRepositoryCustom;

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
                new Update().set("password", newPassword).set("last_password_reset_date", new Date()), ParentEntity.class);
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
	
	@Override
	public void setPendingDeletionAsFalseAndDeleteConfirmationToken(String confirmationToken) {
		Assert.notNull(confirmationToken, "confirmationToken can not be null");
		Assert.hasLength(confirmationToken, "confirmationToken can not be empty");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("confirmation_token").is(confirmationToken)), 
				new Update().set("pending_deletion", Boolean.FALSE).set("confirmation_token", ""), ParentEntity.class);
	}

	@Override
	public void setPendingDeletionAsTrueAndConfirmationTokenById(ObjectId id, String confirmationToken) {
		Assert.notNull(id, "id can not be null");
		Assert.notNull(confirmationToken, "confirmationToken can not be null");
		Assert.hasLength(confirmationToken, "confirmationToken can not be empty");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("pending_deletion", Boolean.TRUE).set("confirmation_token", confirmationToken), ParentEntity.class);
	}

	@Override
	public void setPendingDeletionAsFalseAndDeleteConfirmationToken() {
		mongoTemplate.updateMulti(new Query(Criteria.where("pending_deletion").is(Boolean.TRUE)), 
				new Update().set("pending_deletion", Boolean.FALSE).set("confirmation_token", ""), ParentEntity.class);
	}


	@Override
	public void setLastAccessToAlerts(ObjectId id) {
		Assert.notNull(id, "id can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("last_access_to_alerts", new Date()), ParentEntity.class);
	}

	@Override
	public void updateLastLoginAccessAndLastAccessToAlerts(ObjectId id) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("_id").is(id)),
        		new Update().set("last_login_access", new Date()).set("last_access_to_alerts", new Date()), ParentEntity.class);
		
	}
        
        @Override
        public String getProfileImageIdByUserId(ObjectId id) {
            Assert.notNull(id, "Id can not be null");

            Query query = new Query(Criteria.where("_id").is(id));
            query.fields().include("profile_image");

            ParentEntity parentEntity = mongoTemplate.findOne(query, ParentEntity.class);

            return parentEntity.getProfileImage();
        }

    @Override
    public void setProfileImageId(ObjectId id, String profileImageId) {
        Assert.notNull(id, "id can not be null");
        Assert.notNull(profileImageId, "profileImageId can not be null");

        logger.debug("Save Image id: " + profileImageId + " for user with id : " + id);

        mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("profile_image", profileImageId), ParentEntity.class);
    }

	@Override
	public String getFbIdByEmail(String email) {
		
		Assert.notNull(email, "email can not be null");

        Query query = new Query(Criteria.where("email").is(email));
        query.fields().include("fb_id");

        ParentEntity parentEntity = mongoTemplate.findOne(query, ParentEntity.class);

        return parentEntity.getFbId();
	}
}
