package sanchez.sanchez.sergio.masoc.domain.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.masoc.web.dto.response.CommentsStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.MostActiveFriendsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.NewFriendsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SocialMediaLikesStatisticsDTO;

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
