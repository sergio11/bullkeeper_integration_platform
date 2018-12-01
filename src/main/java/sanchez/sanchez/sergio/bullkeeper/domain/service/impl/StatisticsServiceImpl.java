package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import sanchez.sanchez.sergio.bullkeeper.domain.service.IStatisticsService;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SentimentLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.CommentRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MostActiveFriendsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.NewFriendsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO.CommunityDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO.DimensionDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO.SentimentDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO.ActivityDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO.SocialMediaLikesDTO;

/**
 * @author sergio
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
	
	private Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
	
	private final CommentRepository commentRepository;
	private final IMessageSourceResolverService messageSourceResolverService;
	private final PrettyTime pt;
	private final SupervisedChildrenRepository supervisedChildrenRepository;
	

	/**
	 * 
	 * @param commentRepository
	 * @param messageSourceResolverService
	 * @param pt
	 * @param supervisedChildrenRepository
	 */
	@Autowired
	public StatisticsServiceImpl(final CommentRepository commentRepository, 
			final IMessageSourceResolverService messageSourceResolverService, 
			final PrettyTime pt,
			final SupervisedChildrenRepository supervisedChildrenRepository){
		this.commentRepository = commentRepository;
		this.messageSourceResolverService = messageSourceResolverService;
		this.pt = pt;
		this.supervisedChildrenRepository = supervisedChildrenRepository;
	}

	/**
	 * 	
	 * @param identities
	 * @param from
	 * @return
	 */
	private List<CommentEntity> getCommentsByCreateAtFor(List<String> identities, Date from){
		
		return (identities == null || identities.isEmpty() ? 
    			commentRepository.findByCreatedTimeGreaterThanEqual(from) :
    				commentRepository.findByKidIdInAndCreatedTimeGreaterThanEqual(identities.stream()
    						.map(id -> new ObjectId(id)).collect(Collectors.toList()), from));
	}
	
	
	/**
	 * 
	 * @param guardian
	 * @param identities
	 * @param from
	 * @return
	 */
	private List<CommentEntity> getCommentsByExtractedAtFor(final ObjectId guardian, 
			List<String> identities, Date from){
		
		logger.debug("Get Comments Extracted From -> " + from);
		if(identities != null && !identities.isEmpty())
			logger.debug("Identities -> " + identities.toString());
		logger.debug("Guardian -> " + guardian.toString());
		

		if(identities == null || identities.isEmpty()) {
			
			identities = 
					supervisedChildrenRepository.findByGuardianId(guardian)
					.stream().map(supervisedChildren -> supervisedChildren.getKid().getId().toString())
					.collect(Collectors.toList());
			
		}
		
		return commentRepository.findByKidIdInAndExtractedAtGreaterThanEqual(identities.stream()
				.map(id -> new ObjectId(id)).collect(Collectors.toList()), from);
	}

	/**
	 * 
	 */
	@Override
	public SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(String kid, Date from) {
		Assert.notNull(kid, "kid can not be null");
		Assert.isTrue(ObjectId.isValid(kid), "Kid is not valid ObjectId");
		Assert.notNull(from, "From can not be null");
		
    	// Agrupamos los comentarios del hijo con id "idSon" posteriores a "from" por medio social.
    	Map<SocialMediaTypeEnum, Long> socialActivity = commentRepository
    			.findByKidAndExtractedAtGreaterThanEqual(new ObjectId(kid), from)
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
	 * 
	 */
	@Override
	public SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(String kid, Date from) {
		Assert.notNull(kid, "kid can not be null");
		Assert.isTrue(ObjectId.isValid(kid), "Id Son is not valid ObjectId");
		Assert.notNull(from, "From can not be null");
	
    	
    	Map<SentimentLevelEnum, Long> sentimentResults = commentRepository.findByKidIdAndAnalysisResultsSentimentFinishAtGreaterThanEqual(new ObjectId(kid), from)
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

	/***
	 * 
	 */
	@Override
	public CommunitiesStatisticsDTO getCommunitiesStatistics(String kid, Date from) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.isTrue(ObjectId.isValid(kid), "Id Son is not valid ObjectId");
		Assert.notNull(from, "From can not be null");
		
		final List<CommunityDTO> commutitiesData = new ArrayList<CommunityDTO>();
		
		// Violence Comunitity
		Integer totalUserForViolence = commentRepository
			.findByKidIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(new ObjectId(kid), from, 1)
			.parallelStream()
			.map(CommentEntity::getAuthor)
			.collect(Collectors.toSet())
			.size();
		
		if(totalUserForViolence > 0)
			commutitiesData.add(new CommunityDTO(AnalysisTypeEnum.VIOLENCE.name(), totalUserForViolence, String.format("%d comentarios", totalUserForViolence)));
		
		// Drugs Comunitity
		
		Integer totalUserForDrugs = commentRepository
				.findByKidIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(new ObjectId(kid), from, 1)
				.parallelStream()
				.map(CommentEntity::getAuthor)
				.collect(Collectors.toSet())
				.size();
		
		
		if(totalUserForDrugs > 0)
			commutitiesData.add(new CommunityDTO(AnalysisTypeEnum.DRUGS.name(), totalUserForDrugs, String.format("%d comentarios", totalUserForDrugs)));
		
		// Adult Comunitity
		
		
		Integer totalUserForAdultContent = commentRepository
				.findByKidIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(new ObjectId(kid), from, 1)
				.parallelStream()
				.map(CommentEntity::getAuthor)
				.collect(Collectors.toSet())
				.size();
		
		if(totalUserForAdultContent > 0)
			commutitiesData.add(new CommunityDTO(AnalysisTypeEnum.ADULT.name(), totalUserForAdultContent, String.format("%d comentarios", totalUserForAdultContent)));
		
		// Bullying Comunitity
		
		Integer totalUserForBullying = commentRepository
				.findByKidIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(new ObjectId(kid), from, 1)
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
	 * 
	 */
	@Override
	public DimensionsStatisticsDTO getDimensionsStatistics(String kid, Date from) {
		Assert.notNull(kid, "Kid can not be null");
		Assert.isTrue(ObjectId.isValid(kid), "KId is not valid ObjectId");
		Assert.notNull(from, "From can not be null");
		
		
		final List<DimensionDTO> dimensionsData = new ArrayList<DimensionDTO>();
		
		
		// Count comments for violence.
		Long totalCommentsViolence = commentRepository
				.countByKidIdAndAnalysisResultsViolenceFinishAtGreaterThanEqualAndAnalysisResultsViolenceResult(new ObjectId(kid), from, 1);
		
		// Count comments for drugs.
	    Long totalCommentsDrugs = commentRepository
	    		.countByKidIdAndAnalysisResultsDrugsFinishAtGreaterThanEqualAndAnalysisResultsDrugsResult(new ObjectId(kid), from, 1);
				
	    // Count comments for adult
	 	Long totalCommentsAdult = commentRepository
	 			.countByKidIdAndAnalysisResultsAdultFinishAtGreaterThanEqualAndAnalysisResultsAdultResult(new ObjectId(kid), from, 1);
	 	
	 	// Count comments for bullying
	 	Long totalCommentsBullying = commentRepository
	 			.countByKidIdAndAnalysisResultsBullyingFinishAtGreaterThanEqualAndAnalysisResultsBullyingResult(new ObjectId(kid), from, 1);
	 	
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
				dimensionsData);
		
	}

	/**
	 * 
	 */
	@Override
	public CommentsStatisticsDTO getCommentsStatistics(ObjectId guardian, List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
    	
    	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	List<CommentsStatisticsDTO.CommentsPerDateDTO> commentsData = getCommentsByExtractedAtFor(guardian, identities, from)
    		.parallelStream()
    		.map(comment -> {
    			
    			logger.debug(comment.toString());
    			return comment;
    		})
    		.collect(Collectors.groupingBy(
    				comment -> dateFormat.format(comment.getExtractedAt()), 
    				Collectors.counting()))
    		.entrySet()
    		.stream()
    		.map(comment -> new CommentsStatisticsDTO.CommentsPerDateDTO(comment.getKey(), comment.getValue(), comment.getValue().toString()))
    		.collect(Collectors.toList());
    	
    	return new CommentsStatisticsDTO(
    			messageSourceResolverService.resolver("statistics.comments.obtained.title", new Object[] { pt.format(from) }), 
    			commentsData);
    	
	}

	/**
	 * 
	 */
	@Override
	public SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(ObjectId guardian, List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
    	
    	List<SocialMediaLikesDTO> socialMediaData = getCommentsByExtractedAtFor(guardian, identities, from).parallelStream()
        		.collect(Collectors.groupingBy(CommentEntity::getSocialMedia, 
        				Collectors.summingLong(CommentEntity::getLikes)))
        		.entrySet()
        		.stream()
        		.map(socialEntry -> new SocialMediaLikesDTO(socialEntry.getKey().name(), 
        				socialEntry.getValue(), String.format("%d likes", socialEntry.getValue())))
        		.collect(Collectors.toList());
    	
   
		return new SocialMediaLikesStatisticsDTO(
				messageSourceResolverService.resolver("statistics.social.likes.title", new Object[] { pt.format(from) }), 
				messageSourceResolverService.resolver("statistics.social.likes.subtitle", new Object[] { 1 }), 
				socialMediaData);
		
	}

	/**
	 * 
	 */
	@Override
	public MostActiveFriendsDTO getMostActiveFriends(ObjectId guardian, List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
		
    	
    	List<MostActiveFriendsDTO.UserDTO> mostActiveFriends = getCommentsByExtractedAtFor(guardian, identities, from).parallelStream()
        		.collect(Collectors.groupingBy(CommentEntity::getSocialMedia,
        				Collectors.groupingBy(CommentEntity::getAuthor, 
        						Collectors.counting())))
        		.entrySet()
        		.stream()
        		.flatMap(mostActiveFriendEntry -> {
        			final Integer totalComments = mostActiveFriendEntry.getValue().values().stream().mapToInt(Number::intValue).sum();
        			return mostActiveFriendEntry.getValue()
        					.entrySet().parallelStream().sorted((f1, f2) -> Long.compare(f2.getValue(), f1.getValue())).map(activeFriend -> new MostActiveFriendsDTO.UserDTO(
        							activeFriend.getKey().getExternalId(), activeFriend.getKey().getName(), activeFriend.getKey().getImage(), mostActiveFriendEntry.getKey(), (long)Math.round(activeFriend.getValue().floatValue()/totalComments.floatValue()*100) ,
        							Math.round(activeFriend.getValue().floatValue()/totalComments.floatValue()*100) + "%"));
        		}).collect(Collectors.toList());
    	
    	
    	
    	return new MostActiveFriendsDTO(
    			messageSourceResolverService.resolver("statistics.most.active.friends", new Object[] { pt.format(from) })
    			, mostActiveFriends);
	
	}

	/**
	 * 
	 */
	@Override
	public NewFriendsDTO getNewFriends(ObjectId guardian, List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
		
		if(identities == null || identities.isEmpty())
			
			identities = 
					supervisedChildrenRepository.findByGuardianId(guardian)
					.stream().map(supervisedChildren -> supervisedChildren.getKid().getId().toString())
					.collect(Collectors.toList());
		
	
		
		List<NewFriendsDTO.UserDTO> newFriends =  
    				commentRepository.findAllByKidIdInOrderByCreatedTimeAsc(identities.stream()
    						.map(id -> new ObjectId(id)).collect(Collectors.toList()))
		 .parallelStream()
		 .collect(Collectors.toMap(CommentEntity::getAuthor , CommentEntity::getExtractedAt, (oldExtractedAt, newExtractedAt) -> oldExtractedAt))
		 .entrySet()
		 .parallelStream()
		 .filter(newFriendEntry -> newFriendEntry.getValue().after(from))
		 .map(newFriendEntry -> new NewFriendsDTO.UserDTO(
				 newFriendEntry.getKey().getExternalId(),
				 newFriendEntry.getKey().getName(), 
				 newFriendEntry.getKey().getImage(),
				 pt.format(newFriendEntry.getValue())))
		 .collect(Collectors.toList());
		
		return new NewFriendsDTO(
				messageSourceResolverService.resolver("statistics.most.new.friends", new Object[] { pt.format(from) }), newFriends);
	}
	
	@PostConstruct
    protected void init() {
        Assert.notNull(commentRepository, "Comment Repository cannot be null");
        Assert.notNull(messageSourceResolverService, "Message Source Resolver Service cannot be null");
        Assert.notNull(pt, "Pretty Time cannot be null");
    }
    
}
