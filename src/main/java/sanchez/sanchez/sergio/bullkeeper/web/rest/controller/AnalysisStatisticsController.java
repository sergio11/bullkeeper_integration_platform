package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

import java.util.Date;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Iterables;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAnalysisStatisticsService;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAnalysisStatisticsSummaryFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommentsExtractedException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommunityStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoDimensionsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoLikesFoundInThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSentimentAnalysisStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSocialMediaActivityFoundForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.util.ValidList;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO.CommentsPerDateDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO.CommentsPerSocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SummaryMyKidResultDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.AnalysisStatisticsResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForGuardian;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Analysis Statistics Controller
 * @author ssanchez
 *
 */
@RestController("RestAnalysisStatisticsController")
@Validated
@RequestMapping("/api/v1/analysis-statistics/")
@Api(tags = "analysis-statistics", value = "/analysis-statistics/", 
	description = "Analysis Statistics of comments and/or opinions", produces = "application/json")
public class AnalysisStatisticsController extends BaseController implements ICommentHAL {

    private static Logger logger = LoggerFactory.getLogger(AnalysisStatisticsController.class);

    private final IAnalysisStatisticsService statisticsService;

    /**
     *
     * @param statisticsService
     */
    public AnalysisStatisticsController(final IAnalysisStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }
    

    /**
     * Social Media Likes
     * @param selfGuardian
     * @param identities
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/social-media-Likes"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "SOCIAL_MEDIA_LIKES", nickname = "SOCIAL_MEDIA_LIKES", notes = "Social Media Likes",
            response = SocialMediaLikesStatisticsDTO.class)
    public ResponseEntity<APIResponse<SocialMediaLikesStatisticsDTO>> getSocialMediaLikes(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<ObjectId> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

    	
    	SocialMediaLikesStatisticsDTO socialMediaLikes = statisticsService
    			.getSocialMediaLikesStatistics(selfGuardian.getUserId(), identities, from);

    	if(socialMediaLikes.getData().isEmpty())
    		throw new NoLikesFoundInThisPeriodException(from);
    	
        return ApiHelper.<SocialMediaLikesStatisticsDTO>createAndSendResponse(AnalysisStatisticsResponseCode.SOCIAL_MEDIA_LIKES,
                HttpStatus.OK, socialMediaLikes);
    }
    
    /**
     * Get Social Media Statistics Activity
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/social-activity", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "SOCIAL_MEDIA_ACTIVITY_STATISTICS", nickname = "SOCIAL_MEDIA_ACTIVITY_STATISTICS", 
            notes = "Social Media Activity Statistics", response = SocialMediaActivityStatisticsDTO.class)
    public ResponseEntity<APIResponse<SocialMediaActivityStatisticsDTO>> getSocialMediaStatisticsActivity(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "identities", value = "Children's Identifiers", required = false)
        	@RequestParam(name="identities" , required=false)
        		ValidList<ObjectId> identities,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
            	@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        
        final SocialMediaActivityStatisticsDTO socialMediaActivityStatistics = 
        		statisticsService.getSocialMediaActivityStatistics(selfGuardian.getUserId(), identities, from);
       
        if(socialMediaActivityStatistics.getData().isEmpty())
        	throw new NoSocialMediaActivityFoundForThisPeriodException(from);
        
        // Create and send response
        return ApiHelper.<SocialMediaActivityStatisticsDTO>createAndSendResponse(AnalysisStatisticsResponseCode.SOCIAL_MEDIA_ACTIVITY_STATISTICS, 
				HttpStatus.OK, socialMediaActivityStatistics);  
    }
    
    /**
     * Get Sentiment Analysis Statistics
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/sentiment-analysis", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "SENTIMENT_ANALYSIS_STATISTICS", nickname = "SENTIMENT_ANALYSIS_STATISTICS", 
            notes = "Sentiment Analysis Statistics", response = SentimentAnalysisStatisticsDTO.class)
    public ResponseEntity<APIResponse<SentimentAnalysisStatisticsDTO>> getSentimentAnalysisStatistics(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "identities", value = "Children's Identifiers", required = false)
        	@RequestParam(name="identities" , required=false)
        		ValidList<ObjectId> identities,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
     
        SentimentAnalysisStatisticsDTO sentimentAnalysisStatistics = statisticsService
        		.getSentimentAnalysisStatistics(selfGuardian.getUserId(), identities, from);
      
        if(sentimentAnalysisStatistics.getData().isEmpty())
        	throw new NoSentimentAnalysisStatisticsForThisPeriodException(from);
        // Create and send response
        return ApiHelper.<SentimentAnalysisStatisticsDTO>createAndSendResponse(AnalysisStatisticsResponseCode.SENTIMENT_ANALYSIS_STATISTICS, 
				HttpStatus.OK, sentimentAnalysisStatistics);  
    }
    
    /**
     * Get Communities Statistics
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/communities", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "COMMUNITIES_STATISTICS", nickname = "COMMUNITIES_STATISTICS", 
            notes = "Communities Statistics", response = CommunitiesStatisticsDTO.class)
    public ResponseEntity<APIResponse<CommunitiesStatisticsDTO>> getCommunitiesStatistics(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "identities", value = "Children's Identifiers", required = false)
        		@RequestParam(name="identities" , required=false)
        			ValidList<ObjectId> identities,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
    			@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        final CommunitiesStatisticsDTO communitiesStatistics = statisticsService
        		.getCommunitiesStatistics(selfGuardian.getUserId(), identities, from);
        
        if(communitiesStatistics.getData().isEmpty())
        	throw new NoCommunityStatisticsForThisPeriodException(from);
        // Create and send response
        return ApiHelper.<CommunitiesStatisticsDTO>createAndSendResponse(AnalysisStatisticsResponseCode.COMMUNITIES_STATISTICS, 
				HttpStatus.OK, communitiesStatistics);  
    }
    
    /**
     * Get Four Dimensions Statistics
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/dimensions", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "FOUR_DIMENSIONS_STATISTICS", nickname = "FOUR_DIMENSIONS_STATISTICS", 
            notes = "Four Dimensions Statistics", response = DimensionsStatisticsDTO.class)
    public ResponseEntity<APIResponse<DimensionsStatisticsDTO>> getFourDimensionsStatistics(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "identities", value = "Children's Identifiers", required = false)
    			@RequestParam(name="identities" , required=false)
    				ValidList<ObjectId> identities,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
     
        final DimensionsStatisticsDTO fourDimensionsStatistics = statisticsService
        		.getDimensionsStatistics(selfGuardian.getUserId(), identities, from);
     
        if(fourDimensionsStatistics.getData().isEmpty())
        	throw new NoDimensionsStatisticsForThisPeriodException(from);
        
        
        return ApiHelper.<DimensionsStatisticsDTO>createAndSendResponse(AnalysisStatisticsResponseCode.FOUR_DIMENSIONS_STATISTICS, 
				HttpStatus.OK, fourDimensionsStatistics);  
    }
    
    /**
     * 
     * @param selfGuardian
     * @param identities
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/comments-extracted"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_COMMENTS_EXTRACTED", nickname = "GET_COMMENTS_EXTRACTED", notes = "Get Comments Extracted",
            response = CommentsStatisticsDTO.class)
    public ResponseEntity<APIResponse<CommentsStatisticsDTO<CommentsPerDateDTO>>> getCommentsExtracted(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<ObjectId> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

   	
    	CommentsStatisticsDTO<CommentsPerDateDTO> commentsStatistics = statisticsService
    			.getCommentsStatistics(selfGuardian.getUserId(), identities, from);

    	if(commentsStatistics.getData().isEmpty())
    		throw new NoCommentsExtractedException(from);
    	
        return ApiHelper.<CommentsStatisticsDTO<CommentsPerDateDTO>>createAndSendResponse(AnalysisStatisticsResponseCode.COMMENTS_EXTRACTED_BY_KID,
                HttpStatus.OK, commentsStatistics);
    }
    
    
    /**
     * 
     * @param selfGuardian
     * @param identities
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/comments-extracted-by-social-media"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_COMMENTS_EXTRACTED_BY_SOCIAL_MEDIA", nickname = "GET_COMMENTS_EXTRACTED_BY_SOCIAL_MEDIA", 
    	notes = "Get Comments Extracted By Social Media",
            response = CommentsStatisticsDTO.class)
    public ResponseEntity<APIResponse<CommentsStatisticsDTO<CommentsPerSocialMediaDTO>>> getCommentsExtractedBySocialMedia(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<ObjectId> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

   	
    	final CommentsStatisticsDTO<CommentsPerSocialMediaDTO> commentsStatistics = statisticsService
    			.getCommentsExtractedBySocialMediaStatistics(selfGuardian.getUserId(), identities, from);

    	if(commentsStatistics.getData().isEmpty())
    		throw new NoCommentsExtractedException(from);
    	
        return ApiHelper.<CommentsStatisticsDTO<CommentsPerSocialMediaDTO>>createAndSendResponse(AnalysisStatisticsResponseCode.COMMENTS_EXTRACTED_BY_SOCIAL_MEDIA,
                HttpStatus.OK, commentsStatistics);
    }
    
    
    /**
     * 
     * @param selfGuardian
     * @param identities
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/summary"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SUMMARY", nickname = "GET_SUMMARY", 
    	notes = "Get Summary",
            response = CommentsStatisticsDTO.class)
    public ResponseEntity<APIResponse<Iterable<SummaryMyKidResultDTO>>> getSummary(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {

    	final Iterable<SummaryMyKidResultDTO> summaryList = statisticsService
    			.getSummary(selfGuardian.getUserId());

    	if(Iterables.isEmpty(summaryList))
    		throw new NoAnalysisStatisticsSummaryFoundException();
    	
        return ApiHelper.<Iterable<SummaryMyKidResultDTO>>createAndSendResponse(AnalysisStatisticsResponseCode.ANALYSIS_STATISTICS_SUMMARY,
                HttpStatus.OK, summaryList);
    }
    
}
