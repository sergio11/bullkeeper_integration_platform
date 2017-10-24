package es.bisite.usal.bulltect.domain.service.impl;

import es.bisite.usal.bulltect.domain.service.IStatisticsService;
import es.bisite.usal.bulltect.web.dto.response.CommentsAnalyzedStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.CommentsAnalyzedStatisticsDTO.CommentAnalyzedDTO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import es.bisite.usal.bulltect.web.dto.response.MostActiveFriendsDTO;

/**
 *
 * @author sergio
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {


	@Override
	public SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(String idSon, Integer daysLimit) {
		
		List<ActivityDTO> socialData = Arrays.asList(
				new ActivityDTO("FACEBOOK", 34, "34%"),
				new ActivityDTO("INSTAGRAM", 34, "34%"),
				new ActivityDTO("YOUTUBE", 34, "34%")
		);
		
		return new SocialMediaActivityStatisticsDTO("Social Media Activity", socialData);
	}

	@Override
	public SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(String idSon, Integer daysLimit) {
		
		List<SentimentDTO> sentimentData = new ArrayList<>();
		sentimentData.add(new SentimentDTO("POSITIVE", 50.0f, "50%"));
		sentimentData.add(new SentimentDTO("NEGATIVE", 45.0f, "45%"));
		sentimentData.add(new SentimentDTO("NEUTRO", 5.0f, "5%"));
		
		return new SentimentAnalysisStatisticsDTO("Sentiment Analysis", sentimentData);
	}

	@Override
	public CommunitiesStatisticsDTO getCommunitiesStatistics(String idSon, Integer daysLimit) {
		
		List<CommunityDTO> commutitiesData = Arrays.asList(
				new CommunityDTO("SEX", 50, "50"),
				new CommunityDTO("VIOLENCE", 4, "4"),
				new CommunityDTO("DRUGS", 23, "23"),
				new CommunityDTO("BULLING", 23, "23")
		);
		
		return new CommunitiesStatisticsDTO("Communities Data", commutitiesData);
	}

	@Override
	public DimensionsStatisticsDTO getDimensionsStatistics(String idSon, Integer daysLimit) {
		
		List<DimensionDTO> dimensionsData = Arrays.asList(
				new DimensionDTO("SEX", 6, "6"),
				new DimensionDTO("VIOLENCE", 2, "2"),
				new DimensionDTO("DRUGS", 6, "6"),
				new DimensionDTO("BULLING", 4, "4")
		);
		
		return new DimensionsStatisticsDTO("Dimensions Data", dimensionsData);
		
	}

	@Override
	public CommentsAnalyzedStatisticsDTO getCommentsStatistics(List<String> identities, Integer daysLimit) {
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<CommentAnalyzedDTO> commentsData = Arrays.asList(
				new CommentAnalyzedDTO(dateFormat.format(new Date()), 45, "45"),
				new CommentAnalyzedDTO(dateFormat.format(new Date()), 36, "36"),
				new CommentAnalyzedDTO(dateFormat.format(new Date()), 67, "67"),
				new CommentAnalyzedDTO(dateFormat.format(new Date()), 45, "45")
		);
		
		return new CommentsAnalyzedStatisticsDTO("Comments Analyzed", commentsData);
		
		
	}

	@Override
	public SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(List<String> identities, Integer daysLimit) {
		
		
		List<SocialMediaLikesDTO> socialMediaData = Arrays.asList(
				new SocialMediaLikesDTO("FACEBOOK", 34, "34 Likes"),
				new SocialMediaLikesDTO("INSTAGRAM", 21, "21 Likes"),
				new SocialMediaLikesDTO("YOUTUBE", 67, "67 Likes")
		);
		
		
		return new SocialMediaLikesStatisticsDTO("Social Media Likes", socialMediaData);
		
	}

	@Override
	public MostActiveFriendsDTO getMostActiveFriends(List<String> identities, Integer daysLimit) {
		
		
		List<MostActiveFriendsDTO.UserDTO> mostActiveFriends = Arrays.asList(
				new MostActiveFriendsDTO.UserDTO("Usuario 1", 34),
				new MostActiveFriendsDTO.UserDTO("Usuario 1", 35),
				new MostActiveFriendsDTO.UserDTO("Usuario 1", 78)
		);
		
		
		return new MostActiveFriendsDTO("Most Active Friends", mostActiveFriends);
		
	
	}

	@Override
	public NewFriendsDTO getNewFriends(List<String> identities, Integer daysLimit) {
		
		List<NewFriendsDTO.UserDTO> newFriends = Arrays.asList(
				new NewFriendsDTO.UserDTO("Usuario 1", 34),
				new NewFriendsDTO.UserDTO("Usuario 1", 35),
				new NewFriendsDTO.UserDTO("Usuario 1", 78)
		);
		
		
		return new NewFriendsDTO("New Friends", newFriends);
	}
    
}
