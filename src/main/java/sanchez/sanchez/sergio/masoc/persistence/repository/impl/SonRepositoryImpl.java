package sanchez.sanchez.sergio.masoc.persistence.repository.impl;


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

import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepositoryCustom;

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
                new Update().set("profile_image", profileImageId), SonEntity.class);

    }

    @Override
    public String getProfileImageIdByUserId(ObjectId id) {
        Assert.notNull(id, "Id can not be null");

        Query query = new Query(Criteria.where("_id").is(id));
        query.fields().include("profile_image");
        
        SonEntity sonEntity = mongoTemplate.findOne(query, SonEntity.class);
        
        return sonEntity.getProfileImage();
    }

	@Override
	public void updateSentimentResultsFor(ObjectId id, long totalPositive, long totalNegative, long totalNeutro) {
		Assert.notNull(id, "Id can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                new Update()
                	.set("results.sentiment.date", new Date())
                	.set("results.sentiment.obsolete", Boolean.FALSE)
                	.set("results.sentiment.total_positive", totalPositive)
                	.set("results.sentiment.total_negative", totalNegative)
                	.set("results.sentiment.total_neutro", totalNeutro), SonEntity.class);
		
	}

	@Override
	public void updateAdultResultsFor(ObjectId id, long totalCommentsAdultContent, long totalCommentsNoAdultContent) {
		Assert.notNull(id, "Id can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                new Update()
                	.set("results.adult.date", new Date())
                	.set("results.adult.obsolete", Boolean.FALSE)
                	.set("results.adult.total_comments_adult_content", totalCommentsAdultContent)
                	.set("results.adult.total_comments_noadult_content", totalCommentsNoAdultContent), SonEntity.class);
		
	}

	@Override
	public void updateViolenceResultsFor(ObjectId id, long totalViolentComments, long totalNonViolentComments) {
		Assert.notNull(id, "Id can not be null");
	
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                new Update()
                	.set("results.violence.date", new Date())
                	.set("results.violence.obsolete", Boolean.FALSE)
                	.set("results.violence.total_violent_comments", totalViolentComments)
                	.set("results.violence.total_nonviolent_comments", totalNonViolentComments), SonEntity.class);
	}

	@Override
	public void updateBullyingResultsFor(ObjectId id, long totalCommentsBullying, long totalCommentsNoBullying) {
		Assert.notNull(id, "Id can not be null");
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                new Update()
                	.set("results.bullying.date", new Date())
            		.set("results.bullying.obsolete", Boolean.FALSE)
                	.set("results.bullying.total_comments_bullying", totalCommentsBullying)
                	.set("results.bullying.total_comments_nobullying", totalCommentsNoBullying), SonEntity.class);
	}

	@Override
	public void updateDrugsResultsFor(ObjectId id, long totalCommentsDrugs, long totalCommentsNoDrugs) {
		Assert.notNull(id, "Id can not be null");
		
		
		mongoTemplate.updateFirst(
                new Query(Criteria.where("_id").is(id)),
                new Update()
                	.set("results.drugs.date", new Date())
                	.set("results.drugs.obsolete", Boolean.FALSE)
                	.set("results.drugs.total_comments_drugs", totalCommentsDrugs)
                	.set("results.drugs.total_comments_nodrugs", totalCommentsNoDrugs), SonEntity.class);
	}

}
