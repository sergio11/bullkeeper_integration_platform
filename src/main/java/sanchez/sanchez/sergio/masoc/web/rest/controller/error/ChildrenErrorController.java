package sanchez.sanchez.sergio.masoc.web.rest.controller.error;


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
import sanchez.sanchez.sergio.masoc.exception.AlertNotFoundException;
import sanchez.sanchez.sergio.masoc.exception.CommentsBySonNotFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoAlertsBySonFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoChildrenFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoCommunityStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoDimensionsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoSentimentAnalysisStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoSocialMediaActivityFoundForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoTerminalsFoundException;
import sanchez.sanchez.sergio.masoc.exception.SonNotFoundException;
import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.controller.BaseController;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.masoc.web.rest.response.CommentResponseCode;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ChildrenErrorController extends BaseController {
	

	/**
	 * Exception Handler for Son Not Found exception
	 * @param resourceNotFound
	 * @param request
	 * @return
	 */
    @ExceptionHandler(SonNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSonNotFoundException(SonNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("son.not.found"));
    }
    
    /**
     * Exception Handler for comments by son not found exception
     * @param commentsBySonNotFound
     * @param request
     * @return
     */
    @ExceptionHandler(CommentsBySonNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentsBySonNotFoundException(CommentsBySonNotFoundException commentsBySonNotFound, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.COMMENTS_BY_CHILD_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comments.by.son.not.found"));
    }
    
   
   
    /**
     * Exception handler for no children found exception
     * @param noChildrenFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoChildrenFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundException(NoChildrenFoundException noChildrenFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_CHILDREN_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.not.found"));
    }
    
    /**
     * Exception handler for no alerts by son found exception
     * @param noAlertsBySonFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(NoAlertsBySonFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAlertsBySonFoundException(NoAlertsBySonFoundException noAlertsBySonFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_ALERTS_BY_SON_FOUNDED, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("alerts.by.son.founded"));
    }
    
    /**
     * Exception handler for alert not found exception
     * @param alertNotFoundException
     * @param request
     * @return
     */
    @ExceptionHandler(AlertNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleAlertNotFoundException(AlertNotFoundException alertNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.ALERT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("alert.not.found"));
    }
    
    /**
     * Exception handler for no social media activity found for this period exception
     * @param noSocialMediaActivityFoundForThisPeriodException
     * @param request
     * @return
     */
    @ExceptionHandler(NoSocialMediaActivityFoundForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSocialMediaActivityFoundForThisPeriodException(NoSocialMediaActivityFoundForThisPeriodException noSocialMediaActivityFoundForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.SOCIAL_MEDIA_ACTIVITY_STATISTICS_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.social.media.activity.statistics.not.found", new Object[] {  prettyTime.format(noSocialMediaActivityFoundForThisPeriodException.getFrom()) } ));
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
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_SENTIMENT_ANALYSIS_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.no.sentiment.analysis.statistics.for.this.period", new Object[] { prettyTime.format(noSentimentAnalysisStatisticsForThisPeriodException.getFrom()) }));
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
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_COMMUNITY_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.no.community.statistics.for.this.period", new Object[] { prettyTime.format(noCommunityStatisticsForThisPeriodException.getFrom())} ));
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
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_DIMENSIONS_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.no.dimensions.statistics.for.this.period", new Object[] { prettyTime.format(noDimensionsStatisticsForThisPeriodException.getFrom())} ));
    }
    
 
    
    @PostConstruct
    protected void init(){
    	Assert.notNull(messageSourceResolver, "Message Source Resolver can not be a null");
    }
  
}
