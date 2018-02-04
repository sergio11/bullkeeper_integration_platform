package es.bisite.usal.bulltect.domain.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

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
	SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(String idSon, Date from);
    SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(String idSon, Date from);
    CommunitiesStatisticsDTO getCommunitiesStatistics(String idSon, Date from);
    DimensionsStatisticsDTO getDimensionsStatistics(String idSon, Date from);
    CommentsStatisticsDTO getCommentsStatistics(ObjectId parentId, List<String> identities, Date from);
    SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(ObjectId parentId, List<String> identities, Date from);
    MostActiveFriendsDTO getMostActiveFriends(ObjectId parentId, List<String> identities, Date from);
    NewFriendsDTO getNewFriends(ObjectId parentId, List<String> identities, Date from);
}
