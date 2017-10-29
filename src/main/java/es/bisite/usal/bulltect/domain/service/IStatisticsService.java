package es.bisite.usal.bulltect.domain.service;

import java.util.List;

import es.bisite.usal.bulltect.web.dto.response.CommentsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.CommunitiesStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.DimensionsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.MostActiveFriendsDTO;
import es.bisite.usal.bulltect.web.dto.response.NewFriendsDTO;
import es.bisite.usal.bulltect.web.dto.response.SentimentAnalysisStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaActivityStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaLikesStatisticsDTO;

/**
 *
 * @author sergio
 */
public interface IStatisticsService {
	SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(String idSon, Integer daysLimit);
    SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(String idSon, Integer daysLimit);
    CommunitiesStatisticsDTO getCommunitiesStatistics(String idSon, Integer daysLimit);
    DimensionsStatisticsDTO getDimensionsStatistics(String idSon, Integer daysLimit);
    CommentsStatisticsDTO getCommentsStatistics(List<String> identities, Integer daysLimit);
    SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(List<String> identities, Integer daysLimit);
    MostActiveFriendsDTO getMostActiveFriends(List<String> identities, Integer daysLimit);
    NewFriendsDTO getNewFriends(List<String> identities, Integer daysLimit);
}
