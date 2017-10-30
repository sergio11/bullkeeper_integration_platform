package es.bisite.usal.bulltect.domain.service.impl;

import es.bisite.usal.bulltect.domain.service.IStatisticsService;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SentimentLevelEnum;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.persistence.repository.CommentRepository;
import es.bisite.usal.bulltect.web.dto.response.CommentsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.CommunitiesStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.CommunitiesStatisticsDTO.CommunityDTO;
import es.bisite.usal.bulltect.web.dto.response.DimensionsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.DimensionsStatisticsDTO.DimensionDTO;
import es.bisite.usal.bulltect.web.dto.response.MostActiveFriendsDTO;
import es.bisite.usal.bulltect.web.dto.response.NewFriendsDTO;
import es.bisite.usal.bulltect.web.dto.response.SentimentAnalysisStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SentimentAnalysisStatisticsDTO.SentimentDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO.ActivityDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaLikesStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaLikesStatisticsDTO.SocialMediaLikesDTO;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.bson.types.ObjectId;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sergio
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
	
	private final CommentRepository commentRepository;
	private final IMessageSourceResolverService messageSourceResolverService;
	private final PrettyTime pt = new PrettyTime(LocaleContextHolder.getLocale());
	

	@Autowired
	public StatisticsServiceImpl(CommentRepository commentRepository, IMessageSourceResolverService messageSourceResolverService){
		this.commentRepository = commentRepository;
		this.messageSourceResolverService = messageSourceResolverService;
	}

	
	private List<CommentEntity> getCommentsByCreateAtFor(List<String> identities, Date from){
		
		return (identities == null || identities.isEmpty() ? 
    			commentRepository.findByCreatedTimeGreaterThanEqual(from) :
    				commentRepository.findBySonEntityIdInAndCreatedTimeGreaterThanEqual(identities.stream()
    						.map(id -> new ObjectId(id)).collect(Collectors.toList()), from));
	}
	
	private List<CommentEntity> getCommentsByExtractedAtFor(List<String> identities, Date from){
		
		return (identities == null || identities.isEmpty() ? 
    			commentRepository.findByExtractedAtGreaterThanEqual(from) :
    				commentRepository.findBySonEntityIdInAndExtractedAtGreaterThanEqual(identities.stream()
    						.map(id -> new ObjectId(id)).collect(Collectors.toList()), from));
	}

	@Override
	public SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(String idSon, Date from) {
		Assert.notNull(idSon, "Id Son can not be null");
		Assert.isTrue(ObjectId.isValid(idSon), "Id Son is not valid ObjectId");
		Assert.notNull(from, "From can not be null");
		
    	
    	Map<SocialMediaTypeEnum, Long> socialActivity = commentRepository
    			.findBySonEntityIdAndCreatedTimeGreaterThanEqual(new ObjectId(idSon), from)
    			.parallelStream()
    			.collect(Collectors.groupingBy(CommentEntity::getSocialMedia, Collectors.counting()));
    	
    	final Integer totalComments = socialActivity.values().stream().mapToInt(Number::intValue).sum();
    	
    	List<ActivityDTO> socialData = socialActivity
    			.entrySet().stream()
    			.map(socialActivityEntry -> new ActivityDTO(
    					socialActivityEntry.getKey(),
    					Math.round(socialActivityEntry.getValue().floatValue()/totalComments.floatValue()*100),
        				String.format("%.0f%%", Math.round(socialActivityEntry.getValue().floatValue()/totalComments.floatValue()*100)
    			)))
    			.collect(Collectors.toList());
		
		
		return new SocialMediaActivityStatisticsDTO(
				messageSourceResolverService.resolver("statistics.social.activity.title", new Object[] { pt.format(from) }), 
				socialData);
	}

	@Override
	public SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(String idSon, Date from) {
		Assert.notNull(idSon, "Id Son can not be null");
		Assert.isTrue(ObjectId.isValid(idSon), "Id Son is not valid ObjectId");
		Assert.notNull(from, "From can not be null");
	
    	
    	Map<SentimentLevelEnum, Long> sentimentResults = commentRepository.findBySonEntityIdAndAnalysisResultsSentimentFinishAtGreaterThanEqual(new ObjectId(idSon), from)
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
        				sentimentEntry.getKey(),
        				(long)Math.round(sentimentEntry.getValue().floatValue()/totalComments.floatValue()*100),
        				String.format("%.0f%%", Math.round(sentimentEntry.getValue().floatValue()/totalComments.floatValue()*100))))
        		.collect(Collectors.toList());
        
		
        	return new SentimentAnalysisStatisticsDTO(
        			messageSourceResolverService.resolver("statistics.comments.sentiment.title", new Object[] { pt.format(from) }), 
        			sentimentData);
	}

	@Override
	public CommunitiesStatisticsDTO getCommunitiesStatistics(String idSon, Date from) {
		
		List<CommunityDTO> commutitiesData = Arrays.asList(
				new CommunityDTO("SEX", 50, "50"),
				new CommunityDTO("VIOLENCE", 4, "4"),
				new CommunityDTO("DRUGS", 23, "23"),
				new CommunityDTO("BULLING", 23, "23")
		);
		
		return new CommunitiesStatisticsDTO("Communities Data", commutitiesData);
	}

	@Override
	public DimensionsStatisticsDTO getDimensionsStatistics(String idSon, Date from) {
		
		List<DimensionDTO> dimensionsData = Arrays.asList(
				new DimensionDTO("SEX", 6, "6"),
				new DimensionDTO("VIOLENCE", 2, "2"),
				new DimensionDTO("DRUGS", 6, "6"),
				new DimensionDTO("BULLING", 4, "4")
		);
		
		return new DimensionsStatisticsDTO("Dimensions Data", dimensionsData);
		
	}

	@Override
	public CommentsStatisticsDTO getCommentsStatistics(List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
    	
    	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	List<CommentsStatisticsDTO.CommentsPerDateDTO> commentsData = getCommentsByExtractedAtFor(identities, from)
    		.parallelStream()
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

	@Override
	public SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
    	
    	List<SocialMediaLikesDTO> socialMediaData = getCommentsByExtractedAtFor(identities, from).parallelStream()
        		.collect(Collectors.groupingBy(CommentEntity::getSocialMedia, 
        				Collectors.summingLong(CommentEntity::getLikes)))
        		.entrySet()
        		.stream()
        		.map(socialEntry -> new SocialMediaLikesDTO(socialEntry.getKey(), 
        				socialEntry.getValue(), String.format("%d likes", socialEntry.getValue())))
        		.collect(Collectors.toList());
    	
    
		
		return new SocialMediaLikesStatisticsDTO(
				messageSourceResolverService.resolver("statistics.social.likes.title", new Object[] { pt.format(from) }), 
				socialMediaData);
		
	}

	@Override
	public MostActiveFriendsDTO getMostActiveFriends(List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
		
    	
    	List<MostActiveFriendsDTO.UserDTO> mostActiveFriends = getCommentsByExtractedAtFor(identities, from).parallelStream()
        		.collect(Collectors.groupingBy(CommentEntity::getSocialMedia,
        				Collectors.groupingBy(CommentEntity::getAuthor, 
        						Collectors.counting())))
        		.entrySet()
        		.stream()
        		.flatMap(mostActiveFriendEntry -> {
        			final Integer totalComments = mostActiveFriendEntry.getValue().values().stream().mapToInt(Number::intValue).sum();
        			return mostActiveFriendEntry.getValue()
        					.entrySet().parallelStream().sorted((f1, f2) -> Long.compare(f2.getValue(), f1.getValue())).map(activeFriend -> new MostActiveFriendsDTO.UserDTO(
        							activeFriend.getKey().getName(), activeFriend.getKey().getImage(), mostActiveFriendEntry.getKey(), (long)Math.round(activeFriend.getValue().floatValue()/totalComments.floatValue()*100) ,
        							Math.round(activeFriend.getValue().floatValue()/totalComments.floatValue()*100) + "%"));
        		}).collect(Collectors.toList());
    	
    	
    	
    	return new MostActiveFriendsDTO(
    			messageSourceResolverService.resolver("statistics.most.active.friends", new Object[] { pt.format(from) })
    			, mostActiveFriends);
	
	}

	@Override
	public NewFriendsDTO getNewFriends(List<String> identities, Date from) {
		Assert.notNull(from, "From can not be null");
		
		List<NewFriendsDTO.UserDTO> newFriends = Arrays.asList(
				new NewFriendsDTO.UserDTO("Usuario 1", 34),
				new NewFriendsDTO.UserDTO("Usuario 1", 35),
				new NewFriendsDTO.UserDTO("Usuario 1", 78)
		);
		
		
		return new NewFriendsDTO("New Friends", newFriends);
	}
	
	@PostConstruct
    protected void init() {
        Assert.notNull(commentRepository, "Comment Repository cannot be null");
        Assert.notNull(messageSourceResolverService, "Message Source Resolver Service cannot be null");
    }
    
}
