package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;


import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PreferencesEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PreferencesEntity.RemoveAlertsEveryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepositoryCustom;

/**
 * @author sergio
 */
public class GuardianRepositoryImpl implements GuardianRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(GuardianRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;
    
    
    /**
     * Set As Not Activate And Confirmation Token
     */
    @Override
    public void setAsNotActiveAndConfirmationToken(final ObjectId id, final String confirmationToken) {
        Assert.notNull(confirmationToken, "Confirmation Token can not be null");
        Assert.hasLength(confirmationToken, "Confirmation Token can not be empty");
        
        mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("is_active", Boolean.FALSE).set("confirmation_token", confirmationToken), GuardianEntity.class);
    }

    /**
     * Set New Password
     */
	@Override
	public void setNewPassword(final ObjectId id, final String newPassword) {
		Assert.notNull(id, "Parent Id can not be null");
		Assert.notNull(newPassword, "New Password can not be null");
        Assert.hasLength(newPassword, "New Password can not be empty");
        
        mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("password", newPassword).set("last_password_reset_date", 
                		new Date()), GuardianEntity.class);
	}

	/**
	 * Set Active As true and delete confirmation token
	 */
	@Override
	public void setActiveAsTrueAndDeleteConfirmationToken(final String confirmationToken) {
		Assert.notNull(confirmationToken, "Confirmation Token can not be null");
        Assert.hasLength(confirmationToken, "Confirmation Token can not be empty");
		
        mongoTemplate.updateFirst(
                new Query(Criteria.where("confirmation_token").is(confirmationToken)),
                new Update().set("is_active", Boolean.TRUE).set("confirmation_token", ""),
                GuardianEntity.class);
	}

	/**
	 * Lock Account
	 */
	@Override
	public void lockAccount(final ObjectId id) {
		Assert.notNull(id, "id can not be null");
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("is_locked", Boolean.TRUE), GuardianEntity.class);
	}

	/**
	 * UnLock Account
	 */
	@Override
	public void unlockAccount(final ObjectId id) {
		Assert.notNull(id, "id can not be null");
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("is_locked", Boolean.FALSE), GuardianEntity.class);
	}

	/**
	 * Set FB Access Token By FB id
	 */
	@Override
	public void setFbAccessTokenByFbId(final String fbAccessToken, final String fbId) {
		Assert.notNull(fbAccessToken, "Fb Access Token can not be null");
		Assert.hasLength(fbAccessToken, "Fb Access Token can not be empty");
		Assert.notNull(fbId, "Fb Id can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("fb_id").in(fbId)), 
				new Update().set("fb_access_token", fbAccessToken), GuardianEntity.class);
		
	}
	
	/**
	 * Set Pending Deletion As false and delete confirmation token
	 */
	@Override
	public void setPendingDeletionAsFalseAndDeleteConfirmationToken(final String confirmationToken) {
		Assert.notNull(confirmationToken, "confirmationToken can not be null");
		Assert.hasLength(confirmationToken, "confirmationToken can not be empty");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("confirmation_token").is(confirmationToken)), 
				new Update().set("pending_deletion", Boolean.FALSE).set("confirmation_token", ""), GuardianEntity.class);
	}

	/**
	 * Set Pending Deletion As True and confirmation
	 */
	@Override
	public void setPendingDeletionAsTrueAndConfirmationTokenById(final ObjectId id, 
			final String confirmationToken) {
		Assert.notNull(id, "id can not be null");
		Assert.notNull(confirmationToken, "confirmationToken can not be null");
		Assert.hasLength(confirmationToken, "confirmationToken can not be empty");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("pending_deletion", Boolean.TRUE).set("confirmation_token", confirmationToken), GuardianEntity.class);
	}

	/**
	 * Set Pending Deletion As false and delete confirmation token
	 */
	@Override
	public void setPendingDeletionAsFalseAndDeleteConfirmationToken() {
		mongoTemplate.updateMulti(new Query(Criteria.where("pending_deletion").is(Boolean.TRUE)), 
				new Update().set("pending_deletion", Boolean.FALSE).set("confirmation_token", ""), GuardianEntity.class);
	}

	/**
	 * Set Last Access To Alerts
	 */
	@Override
	public void setLastAccessToAlerts(final ObjectId id) {
		Assert.notNull(id, "id can not be null");
		
		mongoTemplate.updateFirst(
				new Query(Criteria.where("_id").in(id)), 
				new Update().set("last_access_to_alerts", new Date()), GuardianEntity.class);
	}

	/**
	 * 
	 */
	@Override
	public void updateLastLoginAccessAndLastAccessToAlerts(final ObjectId id) {
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("_id").is(id)),
        		new Update().set("last_login_access", new Date()).set("last_access_to_alerts", new Date()), GuardianEntity.class);
	}
    
	/**
	 * Get Guardian Image Id By User Id
	 */
    @Override
    public String getGuardianImageIdByUserId(final ObjectId id) {
    	Assert.notNull(id, "Id can not be null");
    	
    	// Create Query
        final Query query = new Query(Criteria.where("_id").is(id));
        
        query.fields().include("profile_image");     
        final GuardianEntity parentEntity = 
        		mongoTemplate.findOne(query, GuardianEntity.class);
        return parentEntity.getProfileImage();
   }

   /**
    * Set Profile Image Id
    */
   @Override
   public void setProfileImageId(final ObjectId id, final String profileImageId) {
	   Assert.notNull(id, "id can not be null");
       Assert.notNull(profileImageId, "profileImageId can not be null");
       
       logger.debug("Save Image id: " + profileImageId + " for user with id : " + id);

       mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").in(id)),
                new Update().set("profile_image", profileImageId), GuardianEntity.class);
    }

   /**
    * Get FB id by email
    */
   @Override
   public String getFbIdByEmail(final String email) {
	   Assert.notNull(email, "email can not be null");
	   final Query query = new Query(Criteria.where("email").is(email));
       query.fields().include("fb_id");
       GuardianEntity parentEntity = mongoTemplate.findOne(query, GuardianEntity.class);
       return parentEntity.getFbId();
   }

   /**
    * Get Guardians Ids
    */
   @Override
   public List<ObjectId> getGuardianIds() {
	   final Query query = new Query();
	   query.fields().include("_id");
	   return mongoTemplate.find(query, GuardianEntity.class)
        		.stream()
        		.map((guardian) -> guardian.getId())
        		.collect(Collectors.toList());
	}

   /**
    * Get Preferences
    */
   @Override
   public PreferencesEntity getPreferences(final ObjectId id) {
	   Assert.notNull(id, "id can not be null");
	   
	   Query query = new Query(Criteria.where("_id").is(id));
       query.fields().include("preferences");
       GuardianEntity parent =  mongoTemplate.findOne(query, GuardianEntity.class);
       return parent.getPreferences(); 
	}
   
   /**
    * 
    */
   @Override
   public List<ObjectId> getGuardianIdsWithRemoveAlertsEveryAs(
		   final RemoveAlertsEveryEnum removeAlertsEvery) {
		Assert.notNull(removeAlertsEvery, "Remove Alerts Every can not be null");
		
		Query query = new Query(Criteria.where("preferences.remove_alerts_every").is(removeAlertsEvery));
		query.fields().include("_id");
		
		return mongoTemplate.find(query, GuardianEntity.class)
        		.stream()
        		.map((guardian) -> guardian.getId())
        		.collect(Collectors.toList());
		
	}
   
   /**
    * Search
    */
   @Override
   public List<GuardianEntity> search(String text, final List<ObjectId> exclude) {
	   Assert.notNull(text, "Text can not be null");
	   
	   // First Name Criteria
	   final Criteria firstNameCriteria = Criteria.where("first_name").regex(
			   Pattern.compile(text, 
					   Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));
	   
	   // Last Name Criteria
	   final Criteria lastNameCriteria = Criteria.where("last_name").regex(
			   Pattern.compile(text, 
					   Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));
	   
	   // Email Criteria
	   final Criteria emailCriteria = Criteria.where("email").regex(
			   Pattern.compile(text, 
					   Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE));

	   final Criteria criteria = new Criteria();
       criteria.orOperator(firstNameCriteria, lastNameCriteria, emailCriteria)
       		.andOperator(Criteria.where("visible").is(true).and("_id").nin(exclude));
	   
	   return mongoTemplate.find(new Query(criteria), GuardianEntity.class);
   }

}
