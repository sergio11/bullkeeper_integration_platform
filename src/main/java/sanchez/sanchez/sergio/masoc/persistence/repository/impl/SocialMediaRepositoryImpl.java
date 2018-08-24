package sanchez.sanchez.sergio.masoc.persistence.repository.impl;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.masoc.persistence.repository.SocialMediaRepositoryCustom;

/**
 * @author sergio
 */
public class SocialMediaRepositoryImpl implements SocialMediaRepositoryCustom {
    
    private static Logger logger = LoggerFactory.getLogger(SocialMediaRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void setAccessTokenAsInvalid(String accessToken, SocialMediaTypeEnum type) {
    	
       mongoTemplate.updateFirst(
        		new Query(Criteria.where("access_token").is(accessToken)
                    .and("social_media_type").is(type.name())),
        		Update.update("invalid_token", "true"), SocialMediaEntity.class);
    }

	@Override
	public void setScheduledFor(List<ObjectId> ids, Date scheduledFor) {
		Assert.notEmpty(ids, "Ids can not be null");
		Assert.notNull(scheduledFor, "Scheduled For can not be null");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		Update.update("scheduled_for", scheduledFor.getTime()), SocialMediaEntity.class);
	}

	@Override
	public void setScheduledFor(ObjectId id, Date scheduledFor) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(scheduledFor, "Scheduled For can not be null");
		
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("_id").is(id)),
        		Update.update("scheduled_for", scheduledFor.getTime()), SocialMediaEntity.class);
	}

	@Override
	public void setScheduledForAndLastProbing(List<ObjectId> ids, Date scheduledFor, Date lastProbing) {
		Assert.notEmpty(ids, "Ids can not be null");
		Assert.notNull(scheduledFor, "Scheduled For can not be null");
		Assert.notNull(lastProbing, "Last Probing can not be null");

		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("scheduled_for", scheduledFor.getTime()).set("last_probing", lastProbing), SocialMediaEntity.class);
	}

	@Override
	public void setScheduledForAndLastProbing(ObjectId id, Date scheduledFor, Date lastProbing) {
		Assert.notNull(id, "Id can not be null");
		Assert.notNull(scheduledFor, "Scheduled For can not be null");
		Assert.notNull(lastProbing, "Last Probing can not be null");
		
		mongoTemplate.updateFirst(
        		new Query(Criteria.where("_id").is(id)),
        		new Update().set("scheduled_for", scheduledFor.getTime()).set("last_probing", lastProbing), SocialMediaEntity.class);
	}

	@Override
	public SocialMediaTypeEnum getSocialMediaTypeById(ObjectId id) {
		Assert.notNull(id, "Id can not be null");
		
		Query query = new Query(Criteria.where("_id").is(id));
		query.fields().include("social_media_type");
		
		return mongoTemplate.findOne(query, SocialMediaTypeEnum.class);
	
	}   
}
