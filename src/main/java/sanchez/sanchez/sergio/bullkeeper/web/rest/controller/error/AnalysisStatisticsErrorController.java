package sanchez.sanchez.sergio.bullkeeper.web.rest.controller.error;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAnalysisStatisticsSummaryFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommentsExtractedException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommunityStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoDimensionsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoLikesFoundInThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSentimentAnalysisStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSocialMediaActivityFoundForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.AnalysisStatisticsResponseCode;

/**
 * Analysis Statistics Error Controller
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AnalysisStatisticsErrorController extends BaseController {
	
    
    /**
     * Exception handler for no social media activity found for this period exception
     * @param noSocialMediaActivityFoundForThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoSocialMediaActivityFoundForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSocialMediaActivityFoundForThisPeriodException(NoSocialMediaActivityFoundForThisPeriodException noSocialMediaActivityFoundForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AnalysisStatisticsResponseCode.SOCIAL_MEDIA_ACTIVITY_STATISTICS_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("social.media.activity.statistics.not.found", new Object[] {  prettyTime.format(noSocialMediaActivityFoundForThisPeriodException.getFrom()) } ));
    }
    
    /**
     * Exception handler no sentiment analysis statistics for this period exception
     * @param noSentimentAnalysisStatisticsForThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoSentimentAnalysisStatisticsForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSentimentAnalysisStatisticsForThisPeriodException(NoSentimentAnalysisStatisticsForThisPeriodException noSentimentAnalysisStatisticsForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AnalysisStatisticsResponseCode.NO_SENTIMENT_ANALYSIS_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.sentiment.analysis.statistics.for.this.period", new Object[] { prettyTime.format(noSentimentAnalysisStatisticsForThisPeriodException.getFrom()) }));
    }
    
    /**
     * Exception handler for no community statistics for this period exception
     * @param noCommunityStatisticsForThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoCommunityStatisticsForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommunityStatisticsForThisPeriodException(NoCommunityStatisticsForThisPeriodException noCommunityStatisticsForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AnalysisStatisticsResponseCode.NO_COMMUNITY_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.community.statistics.for.this.period", new Object[] { prettyTime.format(noCommunityStatisticsForThisPeriodException.getFrom())} ));
    }
    
    /**
     * Exception handler for no dimensions statistics for this period exception
     * @param noDimensionsStatisticsForThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoDimensionsStatisticsForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoDimensionsStatisticsForThisPeriodException(NoDimensionsStatisticsForThisPeriodException noDimensionsStatisticsForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(
        		AnalysisStatisticsResponseCode.NO_DIMENSIONS_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.dimensions.statistics.for.this.period", new Object[] { prettyTime.format(noDimensionsStatisticsForThisPeriodException.getFrom())} ));
    }

    
    /**
     * No Likes found in this period exception
     * @param noLikesFoundInThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoLikesFoundInThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoLikesFoundInThisPeriodException(NoLikesFoundInThisPeriodException noLikesFoundInThisPeriodException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(AnalysisStatisticsResponseCode.NO_LIKES_FOUND_IN_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.likes.found.in.this.period", new Object[] { prettyTime.format(noLikesFoundInThisPeriodException.getFrom()) }));
    }
    
    /**
     * No Comments extracted exception
     * @param noCommentsExtractedException
     * @param request
     * @return
     */
    @ExceptionHandler(NoCommentsExtractedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommentsExtractedException(NoCommentsExtractedException noCommentsExtractedException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(AnalysisStatisticsResponseCode.NO_COMMENTS_EXTRACTED, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.comments.extracted", new Object[] { prettyTime.format(noCommentsExtractedException.getFrom()) }));
    }
    
    /**
     * Handler for No Analysis Statistics Summary Found Exception  
     * @param noAnalysisStatisticsSummaryFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoAnalysisStatisticsSummaryFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAnalysisStatisticsSummaryFoundException(NoAnalysisStatisticsSummaryFoundException noAnalysisStatisticsSummaryFoundException, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(AnalysisStatisticsResponseCode.NO_ANALYSIS_STATISTICS_SUMMARY_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("no.analysis.statistics.summary.found"));
    }
   
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
  
}
