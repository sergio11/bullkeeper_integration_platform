package es.bisite.usal.bulltect.persistence.repository.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.DrugsLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.ViolenceLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.AdultLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisStatusEnum;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.persistence.entity.BullyingLevelEnum;
import es.bisite.usal.bulltect.persistence.repository.CommentRepositoryCustom;
import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;

/**
 *
 * @author sergio
 */
public class CommentRepositoryImpl implements CommentRepositoryCustom {
	
	private static Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CommentsBySonDTO> getCommentsBySon() {
        
        TypedAggregation<CommentEntity> commentsAggregation = 
               Aggregation.newAggregation(CommentEntity.class,
                Aggregation.group("sonEntity.firstName").count().as("comments"),
                Aggregation.project("sonEntity.firstName").and("comments").previousOperation());
        
        AggregationResults<CommentsBySonDTO> results = mongoTemplate.
             aggregate(commentsAggregation, CommentsBySonDTO.class);

        List<CommentsBySonDTO> commentsBySonResultsList = results.getMappedResults();

        return commentsBySonResultsList;
    }

	@Override
	public void startSentimentAnalysisFor(Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.sentiment.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.sentiment.start_at", new Date()), CommentEntity.class);
		
	}

	@Override
	public void startViolenceAnalysisFor(Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.sentiment.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.sentiment.start_at", new Date()), CommentEntity.class);
		
	}

	@Override
	public void startDrugsAnalysisFor(Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.drugs.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.drugs.start_at", new Date()), CommentEntity.class);
		
	}

	@Override
	public void startAdultAnalysisFor(Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.adult.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.adult.start_at", new Date()), CommentEntity.class);
		
	}

	@Override
	public void startAnalysisFor(final AnalysisTypeEnum type, final Collection<ObjectId> ids) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set(String.format("analysis_results.%s.status", type.name().toLowerCase()), AnalysisStatusEnum.IN_PROGRESS)
        		.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), new Date()), CommentEntity.class);
		
		
	}

	@Override
	public void startBullyingAnalysisFor(Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.bullying.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.bullying.start_at", new Date()), CommentEntity.class);
		
	}

	@Override
	public void updateAnalysisStatusFor(AnalysisTypeEnum type, AnalysisStatusEnum status, Collection<ObjectId> ids) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(status, "status can not be null");
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set(String.format("analysis_results.%s.status", type.name().toLowerCase()), status)
        		.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), new Date()), CommentEntity.class);
		
	}

	@Override
	public void updateAnalysisStatusFor(AnalysisTypeEnum type, AnalysisStatusEnum from, AnalysisStatusEnum to,
			Collection<ObjectId> ids) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(from, "from status can not be null");
		Assert.notNull(to, "to status can not be null");
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)
        				.and(String.format("analysis_results.%s.status", type.name().toLowerCase())).is(from)),
        		new Update().set(String.format("analysis_results.%s.status", type.name().toLowerCase()), to)
        		.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), new Date()), CommentEntity.class);
		
	}
	
	@Override
	public void updateAnalysisStatusFor(AnalysisTypeEnum type, AnalysisStatusEnum from, AnalysisStatusEnum to) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(from, "from status can not be null");
		Assert.notNull(to, "to status can not be null");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria
        				.where(String.format("analysis_results.%s.status", type.name().toLowerCase())).is(from)),
        		new Update().set(String.format("analysis_results.%s.status", type.name().toLowerCase()), to)
        		.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), new Date()), CommentEntity.class);
		
	}

	@Override
	public void cancelAnalyzesThatAreTakingMoreThanNHours(AnalysisTypeEnum type, Integer hours) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(hours, "hours can not be null");
		Assert.isTrue(hours > 0, "hours should be grather than 0");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -hours);
		
		logger.debug("date let -> " + calendar.getTime());
		
		mongoTemplate.updateMulti(
				new Query(Criteria
						.where(String.format("analysis_results.%s.status", type.name().toLowerCase())).is(AnalysisStatusEnum.IN_PROGRESS)
						.and(String.format("analysis_results.%s.start_at", type.name().toLowerCase())).lte(calendar.getTime())),
				new Update().set(String.format("analysis_results.%s.status", type.name().toLowerCase()), AnalysisStatusEnum.PENDING)
				.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), null), CommentEntity.class);
		
		
	}

	@Override
	public Date getExtractedAtOfTheLastCommentBySocialMediaAndSonId(SocialMediaTypeEnum socialMedia, ObjectId sonId) {
		Assert.notNull(socialMedia, "Social Media can not be null");
		Assert.notNull(sonId, "Son id can not be null");
		
		logger.debug("Son Id -> " + sonId);
		logger.debug("Social Media -> " + socialMedia);
		
		Query query = new Query(
				Criteria.where("social_media").is(socialMedia));
        query.fields().include("extracted_at");
        query.with(new Sort(Sort.Direction.DESC,"extracted_at"));
     
        CommentEntity lastComment =  mongoTemplate.findOne(query, CommentEntity.class);
        return lastComment != null ? lastComment.getExtractedAt() : null;
	}

	@Override
	public List<CommentEntity> getComments(String idSon, String author, Date from, SocialMediaTypeEnum socialMedia,
			ViolenceLevelEnum violence, DrugsLevelEnum drugs, BullyingLevelEnum bullying, AdultLevelEnum adult) {
		Assert.notNull(idSon, "Id son can not be null");
		Assert.hasLength(idSon, "Id son can not be empty");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
		
		
		final Query query = new Query(Criteria.where("target").is(idSon).and("extracted_at").gte(from));
		// Social Media Filter
		if(socialMedia != null)
			query.addCriteria(Criteria.where("social_media").is(socialMedia));
		
		// Author Filter
		if(author != null)
			query.addCriteria(Criteria.where("author.external_id").is(author));
			
		// Violence Filter
		if(violence != null && !violence.equals(ViolenceLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.violence.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.violence.result").is(violence.ordinal()));
		// Drugs Filter
		if(drugs != null && !drugs.equals(DrugsLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.drugs.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.drugs.result").is(drugs.ordinal()));
		// Bullying filter
		if(bullying != null && !bullying.equals(BullyingLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.bullying.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.bullying.result").is(bullying.ordinal()));
		// Adult Content filter
		if(adult != null && !adult.equals(AdultLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.adult.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.adult.result").is(adult.ordinal()));

		
        query.with(new Sort(Sort.Direction.DESC, "extracted_at"));
        return mongoTemplate.find(query, CommentEntity.class);
		
	}

	@Override
	public List<CommentEntity> getComments(List<String> identities, String author, Date from,
			SocialMediaTypeEnum socialMedia, ViolenceLevelEnum violence, DrugsLevelEnum drugs,
			BullyingLevelEnum bullying, AdultLevelEnum adult) {
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
		
		
		final Query query = new Query(Criteria.where("extracted_at").gte(from));
		
		// children filter
		if(identities != null && !identities.isEmpty())
			query.addCriteria(Criteria.where("target").in(identities));
		
		// Social Media Filter
		if(socialMedia != null)
			query.addCriteria(Criteria.where("social_media").is(socialMedia));
		
		// Author Filter
		if(author != null)
			query.addCriteria(Criteria.where("author.external_id").is(author));
			
		// Violence Filter
		if(violence != null && !violence.equals(ViolenceLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.violence.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.violence.result").is(violence.ordinal()));
		// Drugs Filter
		if(drugs != null && !drugs.equals(DrugsLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.drugs.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.drugs.result").is(drugs.ordinal()));
		// Bullying filter
		if(bullying != null && !bullying.equals(BullyingLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.bullying.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.bullying.result").is(bullying.ordinal()));
		// Adult Content filter
		if(adult != null && !adult.equals(AdultLevelEnum.UNKNOWN))
			query.addCriteria(Criteria
					.where("analysis_results.adult.status").is(AnalysisStatusEnum.FINISHED)
					.and("analysis_results.adult.result").is(adult.ordinal()));

		
        query.with(new Sort(Sort.Direction.DESC, "extracted_at"));
        return mongoTemplate.find(query, CommentEntity.class);
	}

    
}
