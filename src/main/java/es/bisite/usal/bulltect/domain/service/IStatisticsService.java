package es.bisite.usal.bulltect.domain.service;

import es.bisite.usal.bulltect.web.dto.response.CommunitiesStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.DimensionsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SentimentAnalysisStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO;

/**
 *
 * @author sergio
 */
public interface IStatisticsService {
	SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(String idSon, Integer daysLimit);
    SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(String idSon, Integer daysLimit);
    CommunitiesStatisticsDTO getCommunitiesStatistics(String idSon, Integer daysLimit);
    DimensionsStatisticsDTO getDimensionsStatistics(String idSon, Integer daysLimit);
    
}
