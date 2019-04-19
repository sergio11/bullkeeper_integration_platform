package sanchez.sanchez.sergio.bullkeeper.persistence.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
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

import sanchez.sanchez.sergio.bullkeeper.domain.service.impl.SocialMediaServiceImpl;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepositoryCustom;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsByKidDTO;

/**
 *
 * @author sergio
 */
public class CommentRepositoryImpl implements CommentRepositoryCustom {
	
	private static Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    
    /**
     * 
     */
    @Override
    public List<CommentsByKidDTO> getCommentsByKid() {
        
        TypedAggregation<CommentEntity> commentsAggregation = 
               Aggregation.newAggregation(CommentEntity.class,
                Aggregation.group("sonEntity.firstName").count().as("comments"),
                Aggregation.project("sonEntity.firstName").and("comments").previousOperation());
        
        AggregationResults<CommentsByKidDTO> results = mongoTemplate.
             aggregate(commentsAggregation, CommentsByKidDTO.class);

        List<CommentsByKidDTO> commentsBySonResultsList = results.getMappedResults();

        return commentsBySonResultsList;
    }

    /**
     * Start Sentiment Analysis For
     */
	@Override
	public void startSentimentAnalysisFor(final Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.sentiment.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.sentiment.start_at", new Date()), CommentEntity.class);
		
	}

	/**
	 * Start Violence
	 */
	@Override
	public void startViolenceAnalysisFor(final Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.sentiment.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.sentiment.start_at", new Date()), CommentEntity.class);
		
	}

	/**
	 * Start Drugs Analysis
	 */
	@Override
	public void startDrugsAnalysisFor(final Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.drugs.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.drugs.start_at", new Date()), CommentEntity.class);
		
	}

	/**
	 * Start Adult Analysis 
	 */
	@Override
	public void startAdultAnalysisFor(final Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.adult.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.adult.start_at", new Date()), CommentEntity.class);
		
	}

	/**
	 * 
	 */
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

	/**
	 * Start Bullying Analysis For
	 */
	@Override
	public void startBullyingAnalysisFor(Collection<ObjectId> ids) {
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set("analysis_results.bullying.status", AnalysisStatusEnum.IN_PROGRESS)
        		.set("analysis_results.bullying.start_at", new Date()), CommentEntity.class);
		
	}

	/**
	 * Up date Analysis Status For
	 */
	@Override
	public void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum status,
			final Collection<ObjectId> ids) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(status, "status can not be null");
		Assert.notNull(ids, "Ids can not be null");
		Assert.notEmpty(ids, "Ids can not be empty");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria.where("_id").in(ids)),
        		new Update().set(String.format("analysis_results.%s.status", type.name().toLowerCase()), status)
        		.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), new Date()), CommentEntity.class);
		
	}

	/**
	 * 
	 */
	@Override
	public void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum from, 
			final AnalysisStatusEnum to,
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
	
	/**
	 * 
	 */
	@Override
	public void updateAnalysisStatusFor(final AnalysisTypeEnum type, final AnalysisStatusEnum from, 
			final AnalysisStatusEnum to) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(from, "from status can not be null");
		Assert.notNull(to, "to status can not be null");
		
		mongoTemplate.updateMulti(
        		new Query(Criteria
        				.where(String.format("analysis_results.%s.status", type.name().toLowerCase())).is(from)),
        		new Update().set(String.format("analysis_results.%s.status", type.name().toLowerCase()), to)
        		.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), new Date()), CommentEntity.class);
		
	}

	/**
	 * 
	 */
	@Override
	public void cancelAnalyzesThatAreTakingMoreThanNMinutes(final AnalysisTypeEnum type, 
			final Integer minutes) {
		Assert.notNull(type, "Type can not be null");
		Assert.notNull(minutes, "minutes can not be null");
		Assert.isTrue(minutes > 0, "minutes should be grather than 0");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -minutes);
		
		logger.debug("date let -> " + calendar.getTime());
		
		mongoTemplate.updateMulti(
				new Query(Criteria
						.where(String.format("analysis_results.%s.status", 
								type.name().toLowerCase())).is(AnalysisStatusEnum.IN_PROGRESS)
						.and(String.format("analysis_results.%s.start_at", 
								type.name().toLowerCase())).lte(calendar.getTime())),
				new Update().set(String.format("analysis_results.%s.status",
						type.name().toLowerCase()), AnalysisStatusEnum.PENDING)
				.set(String.format("analysis_results.%s.start_at", type.name().toLowerCase()), null), CommentEntity.class);
		
		
	}

	/**
	 * 
	 */
	@Override
	public Date getExtractedAtOfTheLastCommentBySocialMediaAndKidId(final SocialMediaTypeEnum socialMedia, 
			final ObjectId kid) {
		Assert.notNull(socialMedia, "Social Media can not be null");
		Assert.notNull(kid, "Kid id can not be null");
		
		logger.debug("Kid Id -> " + kid);
		logger.debug("Social Media -> " + socialMedia);
		
		Query query = new Query(
				Criteria.where("social_media").is(socialMedia));
        query.fields().include("extracted_at");
        query.with(new Sort(Sort.Direction.DESC,"extracted_at"));
     
        CommentEntity lastComment =  mongoTemplate.findOne(query, CommentEntity.class);
        return lastComment != null ? lastComment.getExtractedAt() : null;
	}

	/**
	 * 
	 */
	@Override
	public List<CommentEntity> getComments(final ObjectId kid, final String author, 
			final Date from, final SocialMediaTypeEnum[] socialMedias,
			ViolenceLevelEnum violence, DrugsLevelEnum drugs, BullyingLevelEnum bullying, AdultLevelEnum adult,
			final SentimentLevelEnum sentiment) {
		Assert.notNull(kid, "Id son can not be null");
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
		
		
		final Criteria commonCriteria = Criteria.where("kid.$id")
				.is(kid).and("extracted_at").gte(from);
		
	
		// Social Media Filter
		if(socialMedias != null && socialMedias.length > 0) {
			final String[] socialMediaNames = new String[socialMedias.length];
			for(int i = 0; i < socialMedias.length; i++) {
				socialMediaNames[i] = socialMedias[i].name();
			}
			commonCriteria.and("social_media").in(Arrays.asList(socialMediaNames));
		}
		
		// Author Filter
		if(author != null)
			commonCriteria.and("author.external_id").is(author);

		final List<Criteria> dimensionCriterias = new ArrayList<>(); 
		
		// Violence Filter
		if(violence != null && !violence.equals(ViolenceLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.violence.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.violence.result").is(violence.ordinal()));
		// Drugs Filter
		if(drugs != null && !drugs.equals(DrugsLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.drugs.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.drugs.result").is(drugs.ordinal()));
		// Bullying filter
		if(bullying != null && !bullying.equals(BullyingLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.bullying.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.bullying.result").is(bullying.ordinal()));
		
		// Adult Content filter
		if(adult != null && !adult.equals(AdultLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.adult.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.adult.result").is(adult.ordinal()));
	
		if(sentiment != null && !sentiment.equals(SentimentLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.sentiment.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.sentiment.result").is(sentiment.ordinal()));
		

		if(!dimensionCriterias.isEmpty())
			commonCriteria.andOperator(dimensionCriterias.toArray(new Criteria[dimensionCriterias.size()]));
		
		final Query query = new Query(commonCriteria);
		
        query.with(new Sort(Sort.Direction.DESC, "extracted_at"));
        return  mongoTemplate.find(query, CommentEntity.class);
		
	}

	/**
	 * Get Comments
	 */
	@Override
	public List<CommentEntity> getComments(final List<ObjectId> identities, final String author,
			final Date from, final SocialMediaTypeEnum[] socialMedias, final ViolenceLevelEnum violence,
			final DrugsLevelEnum drugs, final BullyingLevelEnum bullying, final AdultLevelEnum adult,
			final SentimentLevelEnum sentiment) {
		
		Assert.notNull(from, "From can not be null");
		Assert.isTrue(from.before(new Date()), "From must be a date before the current one");
		
		
		final Criteria commonCriteria = Criteria.where("extracted_at").gte(from);
	
		// children filter
		if(identities != null && !identities.isEmpty())
			commonCriteria.and("kid.$id").in(identities);
		
		// Social Media Filter
		if(socialMedias != null && socialMedias.length > 0) { 
			final String[] socialMediaNames = new String[socialMedias.length];
			for(int i = 0; i < socialMedias.length; i++) {
				socialMediaNames[i] = socialMedias[i].name();
			}
			commonCriteria.and("social_media").in(Arrays.asList(socialMedias));
		}
		
		
		// Author Filter
		if(author != null)
			commonCriteria.and("author.external_id").is(author);
		
		
		List<Criteria> dimensionCriterias = new ArrayList<>(); 
		
		
		// Violence Filter
		if(violence != null && !violence.equals(ViolenceLevelEnum.UNKNOWN))
			dimensionCriterias.add((Criteria
					.where("analysis_results.violence.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.violence.result").is(violence.ordinal())));
		// Drugs Filter
		if(drugs != null && !drugs.equals(DrugsLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.drugs.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.drugs.result").is(drugs.ordinal()));
		// Bullying filter
		if(bullying != null && !bullying.equals(BullyingLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.bullying.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.bullying.result").is(bullying.ordinal()));
		// Adult Content filter
		if(adult != null && !adult.equals(AdultLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.adult.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.adult.result").is(adult.ordinal()));
		
		// Sentiment Filter
		if(sentiment != null && !sentiment.equals(SentimentLevelEnum.UNKNOWN))
			dimensionCriterias.add(Criteria
					.where("analysis_results.sentiment.status").is(AnalysisStatusEnum.FINISHED.name())
					.and("analysis_results.sentiment.result").is(sentiment.ordinal()));
	
		if(!dimensionCriterias.isEmpty())
			commonCriteria.andOperator(dimensionCriterias.toArray(new Criteria[dimensionCriterias.size()]));

		final Query query = new Query(commonCriteria);

        query.with(new Sort(Sort.Direction.DESC, "extracted_at"));
        return mongoTemplate.find(query, CommentEntity.class);
	}

    
}
