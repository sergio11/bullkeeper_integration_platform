package es.bisite.usal.bulltect.web.rest.controller.error;


import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.controller.BaseController;
import es.bisite.usal.bulltect.web.rest.exception.AlertNotFoundException;
import es.bisite.usal.bulltect.web.rest.exception.CommentsBySonNotFoundException;
import es.bisite.usal.bulltect.web.rest.exception.NoAlertsBySonFoundException;
import es.bisite.usal.bulltect.web.rest.exception.NoChildrenFoundException;
import es.bisite.usal.bulltect.web.rest.exception.NoCommunityStatisticsForThisPeriodException;
import es.bisite.usal.bulltect.web.rest.exception.NoDimensionsStatisticsForThisPeriodException;
import es.bisite.usal.bulltect.web.rest.exception.NoSentimentAnalysisStatisticsForThisPeriodException;
import es.bisite.usal.bulltect.web.rest.exception.NoSocialMediaActivityFoundForThisPeriodException;
import es.bisite.usal.bulltect.web.rest.exception.SonNotFoundException;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.ChildrenResponseCode;
import es.bisite.usal.bulltect.web.rest.response.CommentResponseCode;
import io.jsonwebtoken.lang.Assert;

/**
 *
 * @author sergio
 */

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ChildrenErrorController extends BaseController {
	

    @ExceptionHandler(SonNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleSonNotFoundException(SonNotFoundException resourceNotFound, HttpServletRequest request) {
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("son.not.found"));
    }
    
    @ExceptionHandler(CommentsBySonNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleCommentsBySonNotFoundException(CommentsBySonNotFoundException commentsBySonNotFound, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(CommentResponseCode.COMMENTS_BY_CHILD_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("comments.by.son.not.found"));
    }
   
    
    @ExceptionHandler(NoChildrenFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoChildrenFoundException(NoChildrenFoundException noChildrenFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_CHILDREN_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.not.found"));
    }
    
    @ExceptionHandler(NoAlertsBySonFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoAlertsBySonFoundException(NoAlertsBySonFoundException noAlertsBySonFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_ALERTS_BY_SON_FOUNDED, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("alerts.by.son.founded"));
    }
    
    @ExceptionHandler(AlertNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleAlertNotFoundException(AlertNotFoundException alertNotFoundException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.ALERT_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("alert.not.found"));
    }
    
    
    @ExceptionHandler(NoSocialMediaActivityFoundForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSocialMediaActivityFoundForThisPeriodException(NoSocialMediaActivityFoundForThisPeriodException noSocialMediaActivityFoundForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.SOCIAL_MEDIA_ACTIVITY_STATISTICS_NOT_FOUND, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.social.media.activity.statistics.not.found", new Object[] {  prettyTime.format(noSocialMediaActivityFoundForThisPeriodException.getFrom()) } ));
    }
    
    @ExceptionHandler(NoSentimentAnalysisStatisticsForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoSentimentAnalysisStatisticsForThisPeriodException(NoSentimentAnalysisStatisticsForThisPeriodException noSentimentAnalysisStatisticsForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_SENTIMENT_ANALYSIS_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.no.sentiment.analysis.statistics.for.this.period", new Object[] { prettyTime.format(noSentimentAnalysisStatisticsForThisPeriodException.getFrom()) }));
    }
    
    @ExceptionHandler(NoCommunityStatisticsForThisPeriodException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<String>> handleNoCommunityStatisticsForThisPeriodException(NoCommunityStatisticsForThisPeriodException noCommunityStatisticsForThisPeriodException, HttpServletRequest request){
        return ApiHelper.<String>createAndSendErrorResponseWithHeader(ChildrenResponseCode.NO_COMMUNITY_STATISTICS_FOR_THIS_PERIOD, HttpStatus.NOT_FOUND,
        		messageSourceResolver.resolver("children.no.community.statistics.for.this.period", new Object[] { prettyTime.format(noCommunityStatisticsForThisPeriodException.getFrom())} ));
    }
    
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
