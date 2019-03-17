package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO.CommentsPerDateDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO.CommentsPerSocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SummaryMyKidResultDTO;

/**
 *
 * @author sergio
 */
public interface IAnalysisStatisticsService {
	
	
	/**
	 * Get Social Media Likes Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 * @return
	 */
    SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(final ObjectId guardian, 
    		final List<ObjectId> kids, final Date from);
	
	 /**
	  * Get Social Media Likes Statistics
	  * @param guardian
	  * @param kid
	  * @param from
	  * @return
	  */
    SocialMediaLikesStatisticsDTO getSocialMediaLikesStatistics(final ObjectId guardian, final ObjectId kid, 
    		final Date from);
    
    /**
     * Get Social Media Activity Statistics
     * @param guardian
     * @param kids
     * @param from
     * @return
     */
	SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(final ObjectId guardian, 
    		final List<ObjectId> kids, final Date from);
    
	
	/**
	 * Get Social Media Activity Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 * @return
	 */
	SocialMediaActivityStatisticsDTO getSocialMediaActivityStatistics(final ObjectId guardian, final ObjectId kid, 
			final Date from);
	
	/**
	 * Get Sentiment Analysis Statistics
	 * @param guardian
	 * @param kids
	 * @param from
	 * @return
	 */
    SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(final ObjectId guardian, 
    		final List<ObjectId> kids, final Date from);
	
	
	/**
	 * Get Sentiment Analysis Statistics
	 * @param guardian
	 * @param kid
	 * @param from
	 * @return
	 */
    SentimentAnalysisStatisticsDTO getSentimentAnalysisStatistics(final ObjectId guardian, final ObjectId kid, 
    		final Date from);
    
    /**
     * Get Communities Statistics
     * @param guardian
     * @param kids
     * @param from
     * @return
     */
    CommunitiesStatisticsDTO getCommunitiesStatistics(final ObjectId guardian, 
    		final List<ObjectId> kids, final Date from);
    
    /**
     * Get Communities Statistics
     * @param guardian
     * @param kid
     * @param from
     * @return
     */
    CommunitiesStatisticsDTO getCommunitiesStatistics(final ObjectId guardian, final ObjectId kid, final Date from);
    
    /**
     * Get Dimensions Statistics
     * @param guardian
     * @param kids
     * @param from
     * @return
     */
    DimensionsStatisticsDTO getDimensionsStatistics(final ObjectId guardian, 
    		final List<ObjectId> kids, final Date from);
    
    /**
     * Get Dimensions Statistics
     * @param guardian
     * @param kid
     * @param from
     * @return
     */
    DimensionsStatisticsDTO getDimensionsStatistics(final ObjectId guardian, final ObjectId kid, final Date from);
    
    /**
     * Get Comments Statistics
     * @parma guardian
     * @param kid
     * @param from
     * @return
     */
    CommentsStatisticsDTO<CommentsPerDateDTO> getCommentsStatistics(final ObjectId guardian, final ObjectId kid, 
    		final Date from);
    
    /**
     * Get Comments Statistics
     * @param guardian
     * @param kids
     * @param from
     * @return
     */
    CommentsStatisticsDTO<CommentsPerDateDTO> getCommentsStatistics(final ObjectId guardian, final List<ObjectId> kids, 
    		final Date from);
    
    /**
     * Get Comments Extracted By Social Media Statistics
     * @param guardian
     * @param kids
     * @param from
     * @return
     */
    CommentsStatisticsDTO<CommentsPerSocialMediaDTO> getCommentsExtractedBySocialMediaStatistics(final ObjectId guardian, final List<ObjectId> kids,
    		final Date from);
    
    /**
     * Get Comments Extracted By Social Media Statistics
     * @param guardian
     * @param kid
     * @param from
     * @return
     */
    CommentsStatisticsDTO<CommentsPerSocialMediaDTO> getCommentsExtractedBySocialMediaStatistics(final ObjectId guardian, final ObjectId kid,
    		final Date from);
    
    /**
     * Get Summary
     * @param guardian
     * @param kids
     * @return
     */
    Iterable<SummaryMyKidResultDTO> getSummary(final ObjectId guardian, final List<ObjectId> kids);
    
    /**
     * Get Summary
     * @param guardian
     * @param kid
     * @return
     */
    SummaryMyKidResultDTO getSummary(final ObjectId guardian, final ObjectId kid);
    
    /**
     * Get Summary
     * @param guardian
     * @return
     */
    Iterable<SummaryMyKidResultDTO> getSummary(final ObjectId guardian);
   
}
