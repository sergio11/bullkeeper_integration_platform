package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Iterables;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IAnalysisStatisticsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ISocialMediaService;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisStatusEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO.CommentsPerDateDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO.CommentsPerSocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO.SocialMediaLikesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SummaryMyKidResultDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO.CommunityDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO.DimensionDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO.SentimentDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO.ActivityDTO;

/**
 * Analysis Statistics
 * @author sergio
 */
@Service
public class AnalysisStatisticsServiceImpl implements IAnalysisStatisticsService {
	
	private Logger logger = LoggerFactory.getLogger(AnalysisStatisticsServiceImpl.class);
	
	private final CommentRepository commentRepository;
	private final IMessageSourceResolverService messageSourceResolverService;
	private final PrettyTime pt;
	private final SupervisedChildrenRepository supervisedChildrenRepository;
	private final IKidService kidService;
	private final ISocialMediaService socialMediaService;
	

	/**
	 * 
	 * @param commentRepository
	 * @param messageSourceResolverService
	 * @param pt
	 * @param supervisedChildrenRepository
	 * @param kidService
	 * @param socialMediaService
	 */
	@Autowired
	public AnalysisStatisticsServiceImpl(final CommentRepository commentRepository, 
			final IMessageSourceResolverService messageSourceResolverService, 
			final PrettyTime pt,
			final SupervisedChildrenRepository supervisedChildrenRepository,
			final IKidService kidService, final ISocialMediaService socialMediaService){
		this.commentRepository = commentRepository;
		this.messageSourceResolverService = messageSourceResolverService;
		this.pt = pt;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
		this.kidService = kidService;
		this.socialMediaService = socialMediaService;
	}
	
	
	/**
	 * Get Social Media Likes Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 */
	@Override
	public SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(final ObjectId guardian, final List<ObjectId> kids,
			final Date from) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "kids can not be null");
		Assert.notNull(from, "From can not be null");
		
		
		final List<CommentEntity> commentList = getCommentsByExtractedAtFor(guardian, kids, from);
		
    	List<SocialMediaLikesDTO> socialMediaData = commentList.parallelStream()
    			.collect(Collectors.groupingBy(CommentEntity::getSocialMedia, 
        				Collectors.summingLong(CommentEntity::getLikes)))
        		.entrySet()
        		.stream()
        		.map(socialEntry -> new SocialMediaLikesDTO(socialEntry.getKey().name(), 
        				socialEntry.getValue(), String.format("%d likes", socialEntry.getValue())))
        		.collect(Collectors.toList());
    	
    	final long totalLikes = socialMediaData.stream().collect(Collectors.summingLong(SocialMediaLikesDTO::getLikes));
    	
		return new SocialMediaLikesStatisticsDTO(
				messageSourceResolverService.resolver("statistics.social.likes.title", new Object[] { pt.format(from) }), 
				messageSourceResolverService.resolver("statistics.social.likes.subtitle", new Object[] { commentList.size() }), 
				totalLikes,
				socialMediaData);
	}
	
	/**
	 * Get Social Media Likes Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 */
	@Override
	public SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(final ObjectId guardian, final ObjectId kid, final Date from) {
		return getSocialMediaLikesStatistics(guardian, Arrays.asList(kid), from);
	}

	/**
	 * Get Social Media Activity Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 */
	@Override
	public SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(final ObjectId guardian,
			final List<ObjectId> kids, final Date from) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "Kids can not be null");
		Assert.notNull(from, "From can not be null");
		
		final Map<SocialMediaTypeEnum, Long> socialActivity = commentRepository
    			.findByKidInAndExtractedAtGreaterThanEqual(kids, from)
    			.parallelStream()
    			.collect(Collectors.groupingBy(CommentEntity::getSocialMedia, Collectors.counting()));
    	
    	final Integer totalComments = socialActivity.values().stream().mapToInt(Number::intValue).sum();
    	
    	List<ActivityDTO> socialData = socialActivity
    			.entrySet().stream()
    			.map(socialActivityEntry -> new ActivityDTO(
    					socialActivityEntry.getKey().name(),
    					Math.round(socialActivityEntry.getValue().floatValue()/totalComments.floatValue()*100),
    					Math.round(socialActivityEntry.getValue().floatValue()/totalComments.floatValue()*100) + "%"
    			))
    			.collect(Collectors.toList());
		
		
		return new SocialMediaActivityStatisticsDTO(
				messageSourceResolverService.resolver("statistics.social.activity.title", new Object[] { pt.format(from) }),
				messageSourceResolverService.resolver("statistics.social.activity.subtitle", new Object[] { totalComments }),
				socialData);
		
		
	}

	/**
	 * Get Social Media Activity Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 */
	@Override
	public SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(final ObjectId guardian, 
			final ObjectId kid, final Date from) {
		return getSocialMediaActivityStatistics(guardian, Arrays.asList(kid), from);
	}

	/**
	 * Get Sentiment Analysis Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 */
	@Override
	public SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(final ObjectId guardian, final List<ObjectId> kids,
			final Date from) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "Kids can not be null");
		Assert.notNull(from, "From can not be null");
	
    	
    	final Map<SentimentLevelEnum, Long> sentimentResults = commentRepository
    			.findByKidIdInAndAnalysisResultsSentimentFinishAtGreaterThanEqual(kids, from)
	    		.parallelStream()
	    		.map(comment -> comment.getAnalysisResults().getSentiment())
				.collect(Collectors.groupingBy(
					sentiment -> {
						
						SentimentLevelEnum sentimentLevel; 
						
						if(sentiment.getResult() >= -10 && sentiment.getResult() <= -5) {
							sentimentLevel = SentimentLevelEnum.NEGATIVE;
						} else if(sentiment.getResult() > -5 && sentiment.getResult() <= 5) {
							sentimentLevel = SentimentLevelEnum.NEUTRO;
						} else {
							sentimentLevel = SentimentLevelEnum.POSITIVE;
						}
						
						return sentimentLevel;
						
					},
				Collectors.counting()
			));
    	
    	final Integer totalComments = sentimentResults.values().stream().mapToInt(Number::intValue).sum();
    	
    	List<SentimentDTO> sentimentData = sentimentResults.entrySet()
        		.stream()
        		.map(sentimentEntry -> new SentimentDTO(
        				sentimentEntry.getKey().name(),
        				(long)Math.round(sentimentEntry.getValue().floatValue()/totalComments.floatValue()*100),
        				Math.round(sentimentEntry.getValue().floatValue()/totalComments.floatValue()*100) + "%"))
        		.collect(Collectors.toList());
        
		
        	return new SentimentAnalysisStatisticsDTO(
        			messageSourceResolverService.resolver("statistics.comments.sentiment.title", new Object[] { pt.format(from) }),
        			messageSourceResolverService.resolver("statistics.comments.sentiment.subtitle", new Object[] {  totalComments }), 
        			sentimentData);
	}


	/**
	 * Get Sentiment Analysis Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 */
	@Override
	public SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(final ObjectId guardian, final ObjectId kid, final Date from) {
		return getSentimentAnalysisStatistics(guardian, Arrays.asList(kid), from);
	}

	/**
	 * Get Communities Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 */
	@Override
	public CommunitiesStatisticsDTO getCommunitiesStatistics(final ObjectId guardian,
			final List<ObjectId> kids, final Date from) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "Kids can not be null");
		Assert.notNull(from, "From can not be null");
		
		final List<CommunityDTO> commutitiesData = new ArrayList<CommunityDTO>();
		
		// Violence Comunitity
		Integer totalUserForViolence = commentRepository
			.findByKidIdInAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(kids, from, 1)
			.parallelStream()
			.map(CommentEntity::getAuthor)
			.collect(Collectors.toSet())
			.size();
		
		if(totalUserForViolence > 0)
			commutitiesData.add(new CommunityDTO(AnalysisTypeEnum.VIOLENCE.name(), totalUserForViolence, String.format("%d comentarios", totalUserForViolence)));
		
		// Drugs Comunitity
		
		Integer totalUserForDrugs = commentRepository
				.findByKidIdInAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(kids, from, 1)
				.parallelStream()
				.map(CommentEntity::getAuthor)
				.collect(Collectors.toSet())
				.size();
		
		
		if(totalUserForDrugs > 0)
			commutitiesData.add(new CommunityDTO(AnalysisTypeEnum.DRUGS.name(), totalUserForDrugs, String.format("%d comentarios", totalUserForDrugs)));
		
		// Adult Comunitity
		
		
		Integer totalUserForAdultContent = commentRepository
				.findByKidIdInAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(kids, from, 1)
				.parallelStream()
				.map(CommentEntity::getAuthor)
				.collect(Collectors.toSet())
				.size();
		
		if(totalUserForAdultContent > 0)
			commutitiesData.add(new CommunityDTO(AnalysisTypeEnum.ADULT.name(), totalUserForAdultContent, String.format("%d comentarios", totalUserForAdultContent)));
		
		// Bullying Comunitity
		
		Integer totalUserForBullying = commentRepository
				.findByKidIdInAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(kids, from, 1)
				.parallelStream()
				.map(CommentEntity::getAuthor)
				.collect(Collectors.toSet())
				.size();
		
		if(totalUserForBullying > 0)
			commutitiesData.add(new CommunityDTO(AnalysisTypeEnum.BULLYING.name(), totalUserForBullying, String.format("%d comentarios", totalUserForBullying)));
		
		
		return new CommunitiesStatisticsDTO(
				messageSourceResolverService.resolver("statistics.comments.communities.title", new Object[] { pt.format(from) }), 
				commutitiesData);
	}

	/**
	 * Get Communities Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 */
	@Override
	public CommunitiesStatisticsDTO getCommunitiesStatistics(final ObjectId guardian, final ObjectId kid, final Date from) {
		return getCommunitiesStatistics(guardian, Arrays.asList(kid), from);
	}

	/**
	 * Get Dimensions Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 */
	@Override
	public DimensionsStatisticsDTO getDimensionsStatistics(final ObjectId guardian, final List<ObjectId> kids, 
			final Date from) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "Kids can not be null");
		Assert.notNull(from, "From can not be null");
		
		
		final List<DimensionDTO> dimensionsData = new ArrayList<DimensionDTO>();
		
		
		// Count comments for violence.
		Long totalCommentsViolence = commentRepository
				.countByKidIdInAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(kids, from, 1);
		
		// Count comments for drugs.
	    Long totalCommentsDrugs = commentRepository
	    		.countByKidIdInAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(kids, from, 1);
				
	    // Count comments for adult
	 	Long totalCommentsAdult = commentRepository
	 			.countByKidIdInAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(kids, from, 1);
	 	
	 	// Count comments for bullying
	 	Long totalCommentsBullying = commentRepository
	 			.countByKidIdInAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(kids, from, 1);
	 	
	 	Long totalAnalyzesPerformed = totalCommentsViolence + totalCommentsDrugs + totalCommentsAdult + totalCommentsBullying;
		
		if(totalCommentsViolence > 0)
			dimensionsData.add(new DimensionDTO(AnalysisTypeEnum.VIOLENCE.name(), 
					totalCommentsViolence, String.format("%d/%d", totalCommentsViolence, totalAnalyzesPerformed)));
	
		
		if(totalCommentsDrugs > 0)
			dimensionsData.add(new DimensionDTO(AnalysisTypeEnum.DRUGS.name(), 
					totalCommentsDrugs, String.format("%d/%d", totalCommentsDrugs, totalAnalyzesPerformed)));
		
		
		if(totalCommentsAdult > 0)
			dimensionsData.add(new DimensionDTO(AnalysisTypeEnum.ADULT.name(), 
					totalCommentsAdult, String.format("%d/%d", totalCommentsAdult, totalAnalyzesPerformed)));
		
		
		if(totalCommentsBullying > 0)
			dimensionsData.add(new DimensionDTO(AnalysisTypeEnum.BULLYING.name(), 
					totalCommentsBullying, String.format("%d/%d", totalCommentsBullying, totalAnalyzesPerformed)));
		

		return new DimensionsStatisticsDTO(
				messageSourceResolverService.resolver("statistics.comments.dimensions.title", new Object[] { pt.format(from) }),
				messageSourceResolverService.resolver("statistics.comments.dimensions.subtitle", new Object[] { totalAnalyzesPerformed  }),
				dimensionsData, totalAnalyzesPerformed);
	}

	/**
	 * Get Dimensions Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 */
	@Override
	public DimensionsStatisticsDTO getDimensionsStatistics(final ObjectId guardian, final ObjectId kid, final Date from) {
		return getDimensionsStatistics(guardian, Arrays.asList(kid), from);
	}

	/**
	 * Get Comments Statistics
	 * @param kid
	 * @param from
	 */
	@Override
	public CommentsStatisticsDTO<CommentsPerDateDTO> getCommentsStatistics(final ObjectId guardian, final ObjectId kid, final Date from) {
		return getCommentsStatistics(guardian, Arrays.asList(kid), from);
	}

	/**
	 * Get Comments Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 */
	@Override
	public CommentsStatisticsDTO<CommentsPerDateDTO> getCommentsStatistics(final ObjectId guardian, final List<ObjectId> kids,
			final Date from) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "Kids can not be null");
		Assert.notNull(from, "From can not be null");
		
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	
		final List<CommentEntity> comments = getCommentsByExtractedAtFor(guardian, kids, from);
		final int totalComments = comments.size();
    	
    	List<CommentsStatisticsDTO.CommentsPerDateDTO> commentsData = comments
    		.parallelStream()
    		.collect(Collectors.groupingBy(
    				comment -> dateFormat.format(comment.getExtractedAt()), 
    				Collectors.counting()))
    		.entrySet()
    		.stream()
    		.map(comment -> new CommentsStatisticsDTO.CommentsPerDateDTO(comment.getValue(), comment.getValue().toString(), comment.getKey()))
    		.collect(Collectors.toList());
    	
    	return new CommentsStatisticsDTO<CommentsPerDateDTO>(
    			messageSourceResolverService.resolver("statistics.comments.extracted.per.date.title", new Object[] { pt.format(from) }), 
    			messageSourceResolverService.resolver("statistics..comments.extracted.per.date.title.subtitle", new Object[] { totalComments  }),
    			totalComments,
    			commentsData);
	}
	
	/**
	 * Get Comments Extracted By Social Media Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 */
	@Override
	public CommentsStatisticsDTO<CommentsPerSocialMediaDTO> getCommentsExtractedBySocialMediaStatistics(final ObjectId guardian, 
			final List<ObjectId> kids, final Date from) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "Kids can not be null");
		Assert.notNull(from, "From can not be null");
		
		final List<CommentEntity> comments = getCommentsByExtractedAtFor(guardian, kids, from);
		final int totalCommnents = comments.size();
		
    	List<CommentsStatisticsDTO.CommentsPerSocialMediaDTO> commentsData = comments
    		.parallelStream()
    		.collect(Collectors.groupingBy(
    				comment -> comment.getSocialMedia(), 
    				Collectors.counting()))
    		.entrySet()
    		.stream()
    		.map(comment -> new CommentsStatisticsDTO.CommentsPerSocialMediaDTO(comment.getValue(), comment.getValue().toString(), comment.getKey().name()))
    		.collect(Collectors.toList());
    	
    	return new CommentsStatisticsDTO<CommentsPerSocialMediaDTO>(
    			messageSourceResolverService.resolver("statistics.comments.extracted.by.social.media.title", new Object[] { pt.format(from) }), 
    			messageSourceResolverService.resolver("statistics..comments.extracted.by.social.media.subtitle", new Object[] { totalCommnents  }),
    			totalCommnents,
    			commentsData);
	
	}

	/**
	 * Get Comments Extracted By Social Media Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 */
	@Override
	public CommentsStatisticsDTO<CommentsPerSocialMediaDTO> getCommentsExtractedBySocialMediaStatistics(final ObjectId guardian, final ObjectId kid,
			final Date from) {
		return getCommentsExtractedBySocialMediaStatistics(guardian, Arrays.asList(kid), from);
	}
	
	/**
	 * Get Summary
	 * @param guardian
	 * @param kids
	 */
	@Override
	public Iterable<SummaryMyKidResultDTO> getSummary(final ObjectId guardian, List<ObjectId> kids) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kids, "Kids can not be null");
		final List<SummaryMyKidResultDTO> summaryList = new ArrayList<>();
		for(final ObjectId kid: kids)
			summaryList.add(getSummary(guardian, kid));
		return summaryList;
	}

	/**
	 * Get Summary
	 * @param guardian
	 * @param kid
	 */
	@Override
	public SummaryMyKidResultDTO getSummary(final ObjectId guardian, final ObjectId kid) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		
		final KidDTO kidDTO = kidService.getKidById(kid.toString());
		
		final SummaryMyKidResultDTO summaryMyKidResultDTO = new SummaryMyKidResultDTO();
		summaryMyKidResultDTO.setIdentity(kidDTO.getIdentity());
		summaryMyKidResultDTO.setFirstName(kidDTO.getFirstName());
		summaryMyKidResultDTO.setLastName(kidDTO.getLastName());
		summaryMyKidResultDTO.setAge(kidDTO.getAge());
		summaryMyKidResultDTO.setBirthdate(kidDTO.getBirthdate());
		summaryMyKidResultDTO.setLocation(kidDTO.getCurrentLocation());
		summaryMyKidResultDTO.setProfileImage(kidDTO.getProfileImage());
		summaryMyKidResultDTO.setSchool(kidDTO.getSchool());
		summaryMyKidResultDTO.setSocialMedias(socialMediaService.getSocialMediaByKid(kid.toString()));
		summaryMyKidResultDTO.setTotalDevices(Iterables.size(kidDTO.getTerminals()));
		
		final long totalComments = commentRepository.countByKidId(kid);
		
		summaryMyKidResultDTO.setTotalComments(totalComments);
		
		// Total comments with positive results for dimensions
		final long totalAdultComments = commentRepository.countByKidIdAndAnalysisResultsAdultResult(kid, 1);
		final long totalViolenceComments = commentRepository.countByKidIdAndAnalysisResultsViolenceResult(kid, 1);
		final long totalBullyingComments = commentRepository.countByKidIdAndAnalysisResultsBullyingResult(kid, 1);
		final long totalDrugsCommnets = commentRepository.countByKidIdAndAnalysisResultsDrugsResult(kid, 1);
		
		summaryMyKidResultDTO.setTotalCommentsAnalyzed(totalAdultComments + totalViolenceComments
				+ totalBullyingComments + totalDrugsCommnets);
		
		// Total Adult Comments
		summaryMyKidResultDTO.setTotalCommentsAdultContent(
				totalAdultComments);
		
		// Total Violent Comments
		summaryMyKidResultDTO.setTotalViolentComments(totalViolenceComments);
		
		// Total Bullying Comments
		summaryMyKidResultDTO.setTotalCommentsBullying(totalBullyingComments);
		
		// Total Drugs Comments
		summaryMyKidResultDTO.setTotalCommentsDrugs(totalDrugsCommnets);
		
		final Map<SentimentLevelEnum, Long> sentimentResults = commentRepository
    			.findByKidIdAndAnalysisResultsSentimentStatus(kid, AnalysisStatusEnum.FINISHED)
	    		.parallelStream()
	    		.map(comment -> comment.getAnalysisResults().getSentiment())
				.collect(Collectors.groupingBy(
					sentiment -> {
						SentimentLevelEnum sentimentLevel; 
						if(sentiment.getResult() >= -10 && sentiment.getResult() <= -5) {
							sentimentLevel = SentimentLevelEnum.NEGATIVE;
						} else if(sentiment.getResult() > -5 && sentiment.getResult() <= 5) {
							sentimentLevel = SentimentLevelEnum.NEUTRO;
						} else {
							sentimentLevel = SentimentLevelEnum.POSITIVE;
						}
						
						return sentimentLevel;
						
					},
				Collectors.counting()
			));
		
		// Total Negative Sentiment Comments
		if(sentimentResults.containsKey(SentimentLevelEnum.NEGATIVE)) {
			
			summaryMyKidResultDTO.setTotalCommentsNegativeSentiment(sentimentResults.get(SentimentLevelEnum.NEGATIVE) != null ? 
					sentimentResults.get(SentimentLevelEnum.NEGATIVE) : 0
					);
		} else {
			
			summaryMyKidResultDTO.setTotalCommentsNegativeSentiment(0);
		}
		
		// Total Negative Sentiment Comments
		if(sentimentResults.containsKey(SentimentLevelEnum.NEUTRO)) {
			summaryMyKidResultDTO.setTotalCommentsNeutralSentiment(sentimentResults.get(SentimentLevelEnum.NEUTRO) != null ? 
					sentimentResults.get(SentimentLevelEnum.NEUTRO): 0);
		} else {
			summaryMyKidResultDTO.setTotalCommentsNeutralSentiment(0);
		}
		
		// Total Positive Sentiment Comments
		if(sentimentResults.containsKey(SentimentLevelEnum.POSITIVE)) {
			summaryMyKidResultDTO.setTotalCommentsPositiveSentiment(sentimentResults.get(SentimentLevelEnum.POSITIVE) != null ?
					sentimentResults.get(SentimentLevelEnum.POSITIVE): 0);
			
		} else {
			summaryMyKidResultDTO.setTotalCommentsPositiveSentiment(0);
		}
		
	
		
		return summaryMyKidResultDTO;
		
	}
	
	/**
	 * Get Summary
	 * @param guardian
	 */
	@Override
	public Iterable<SummaryMyKidResultDTO> getSummary(final ObjectId guardian) {
		Assert.notNull(guardian, "Guardian can not be null");
		return getSummary(guardian, supervisedChildrenRepository.findByGuardianId(guardian)
				.stream().map(supervisedChildren -> supervisedChildren.getKid().getId())
				.collect(Collectors.toList()));
	}

	
	/**
	 * Private Methods
	 * ====================
	 */
	
	/**
	 * Get Comments By Extrated At For
	 * @param guardian
	 * @param identities
	 * @param from
	 * @return
	 */
	private List<CommentEntity> getCommentsByExtractedAtFor(final ObjectId guardian, 
			List<ObjectId> identities, final Date from){
		
		logger.debug("Get Comments Extracted From -> " + from);
		if(identities != null && !identities.isEmpty())
			logger.debug("Identities -> " + identities.toString());
		logger.debug("Guardian -> " + guardian.toString());
		

		if(identities == null || identities.isEmpty()) {
			
			identities = 
					supervisedChildrenRepository.findByGuardianId(guardian)
					.stream().map(supervisedChildren -> supervisedChildren.getKid().getId())
					.collect(Collectors.toList());
			
		}
		
		return commentRepository.findByKidIdInAndExtractedAtGreaterThanEqual(identities.stream()
				.collect(Collectors.toList()), from);
	}
	

	
	/**
	 * Init
	 */
	@PostConstruct
    protected void init() {
        Assert.notNull(commentRepository, "Comment Repository cannot be null");
        Assert.notNull(messageSourceResolverService, "Message Source Resolver Service cannot be null");
        Assert.notNull(pt, "Pretty Time cannot be null");
    }
}
