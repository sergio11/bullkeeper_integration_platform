package es.bisite.usal.bulltect.domain.service.impl;

import es.bisite.usal.bulltect.domain.service.IStatisticsService;
import es.bisite.usal.bulltect.web.dto.response.CommunitiesStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.CommunitiesStatisticsDTO.CommunityDTO;
import es.bisite.usal.bulltect.web.dto.response.DimensionsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.DimensionsStatisticsDTO.DimensionDTO;
import es.bisite.usal.bulltect.web.dto.response.SentimentAnalysisStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SentimentAnalysisStatisticsDTO.SentimentDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO.ActivityDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
    
}
