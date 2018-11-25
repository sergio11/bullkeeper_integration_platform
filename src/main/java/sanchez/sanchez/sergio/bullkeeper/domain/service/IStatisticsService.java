package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MostActiveFriendsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.NewFriendsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO;

/**
 *
 * @author sergio
 */
public interface IStatisticsService {
	
	/**
	 * 
	 * @param id
	 * @param from
	 * @return
	 */
	SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(final String id, 
			final Date from);
	
	/**
	 * 
	 * @param id
	 * @param from
	 * @return
	 */
    SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(final String id, 
    		final Date from);
    
    /**
     * 
     * @param id
     * @param from
     * @return
     */
    CommunitiesStatisticsDTO getCommunitiesStatistics(String id, Date from);
    
    /**
     * 
     * @param id
     * @param from
     * @return
     */
    DimensionsStatisticsDTO getDimensionsStatistics(String id, Date from);
    
    /**
     * 
     * @param id
     * @param identities
     * @param from
     * @return
     */
    CommentsStatisticsDTO getCommentsStatistics(final ObjectId id, final List<String> identities, 
    		final Date from);
    
    /**
     * 
     * @param id
     * @param identities
     * @param from
     * @return
     */
    SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(final ObjectId id, 
    		final List<String> identities, final Date from);
    
    /**
     * 
     * @param id
     * @param identities
     * @param from
     * @return
     */
    MostActiveFriendsDTO getMostActiveFriends(final ObjectId id, final List<String> identities,
    		final Date from);
    
    
    /**
     * 
     * @param id
     * @param identities
     * @param from
     * @return
     */
    NewFriendsDTO getNewFriends(final ObjectId id, final List<String> identities, 
    		final Date from);
}
