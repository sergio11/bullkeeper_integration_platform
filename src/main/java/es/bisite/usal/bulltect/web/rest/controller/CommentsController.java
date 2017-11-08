package es.bisite.usal.bulltect.web.rest.controller;



import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.bisite.usal.bulltect.domain.service.ICommentsService;
import es.bisite.usal.bulltect.domain.service.IStatisticsService;
import es.bisite.usal.bulltect.persistence.constraints.ValidObjectId;
import es.bisite.usal.bulltect.util.Utils;
import es.bisite.usal.bulltect.util.ValidList;
import es.bisite.usal.bulltect.web.dto.response.CommentDTO;
import es.bisite.usal.bulltect.web.dto.response.CommentsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.MostActiveFriendsDTO;
import es.bisite.usal.bulltect.web.dto.response.NewFriendsDTO;
import es.bisite.usal.bulltect.web.dto.response.SocialMediaLikesStatisticsDTO;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.exception.CommentNotFoundException;
import es.bisite.usal.bulltect.web.rest.exception.NoActiveFriendsInThisPeriodException;
import es.bisite.usal.bulltect.web.rest.exception.NoCommentsExtractedException;
import es.bisite.usal.bulltect.web.rest.exception.NoCommentsFoundException;
import es.bisite.usal.bulltect.web.rest.exception.NoLikesFoundInThisPeriodException;
import es.bisite.usal.bulltect.web.rest.exception.NoNewFriendsAtThisTimeException;
import es.bisite.usal.bulltect.web.rest.hal.ICommentHAL;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.CommentResponseCode;
import es.bisite.usal.bulltect.web.security.userdetails.CommonUserDetailsAware;
import es.bisite.usal.bulltect.web.security.utils.CurrentUser;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessForAdmin;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessForParent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import springfox.documentation.annotations.ApiIgnore;


@RestController("RestCommentsController")
@Validated
@RequestMapping("/api/v1/comments/")
@Api(tags = "comments", value = "/comments/", description = "Punto de Entrada para el manejo de comentarios y/o opiniones", produces = "application/json")
public class CommentsController extends BaseController implements ICommentHAL {

    private static Logger logger = LoggerFactory.getLogger(CommentsController.class);
    
    private final ICommentsService commentsService;
    private final IStatisticsService statisticsService;

    public CommentsController(ICommentsService commentsService, IStatisticsService statisticsService) {
        this.commentsService = commentsService;
        this.statisticsService = statisticsService;
    }
    
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_COMMENTS", nickname = "GET_ALL_COMMENTS", 
            notes = "Get all Comments", response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<CommentDTO>>>> getAllComments(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<CommentDTO> pagedAssembler) throws Throwable {
        logger.debug("Get all Comments");
        
        Page<CommentDTO> commentsPage = commentsService.findPaginated(pageable);
        
        if(commentsPage.getTotalElements() == 0)
        	throw new NoCommentsFoundException();
        
        return ApiHelper.<PagedResources<Resource<CommentDTO>>>createAndSendResponse(CommentResponseCode.ALL_COMMENTS, 
        		HttpStatus.OK, pagedAssembler.toResource(addLinksToComments((commentsPage))));
       
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_COMMENT_BY_ID", nickname = "GET_COMMENT_BY_ID", notes = "Get Comment By Id",
            response = CommentDTO.class)
    public ResponseEntity<APIResponse<CommentDTO>> getCommentById(
    		@ApiParam(name= "id", value = "Identificador del comentario", required = true)
    			@Valid @ValidObjectId(message = "{comment.id.notvalid}")
    		 		@PathVariable String id) throws Throwable {
        logger.debug("Get Comment with id: " + id);
        return Optional.ofNullable(commentsService.getCommentById(id))
                .map(commentResource -> addLinksToComment(commentResource))
                .map(commentResource -> ApiHelper.<CommentDTO>createAndSendResponse(CommentResponseCode.SINGLE_COMMENT, 
                		HttpStatus.OK, commentResource))
                .orElseThrow(() -> { throw new CommentNotFoundException(); });
    }
    
    
    @RequestMapping(value = {"/comments-extracted"}, method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_COMMENTS_EXTRACTED", nickname = "GET_COMMENTS_EXTRACTED", notes = "Get Comments Extracted",
            response = CommentsStatisticsDTO.class)
    public ResponseEntity<APIResponse<CommentsStatisticsDTO>> getCommentsAnalyzed(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

   	
    	CommentsStatisticsDTO commentsStatistics = statisticsService.getCommentsStatistics(selfParent.getUserId(), identities, from);

    	if(commentsStatistics.getData().isEmpty())
    		throw new NoCommentsExtractedException(from);
    	
        return ApiHelper.<CommentsStatisticsDTO>createAndSendResponse(CommentResponseCode.COMMENTS_EXTRACTED_BY_SON,
                HttpStatus.OK, commentsStatistics);
    }
    
    @RequestMapping(value = {"/social-media-Likes"}, method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "SOCIAL_MEDIA_LIKES", nickname = "SOCIAL_MEDIA_LIKES", notes = "Social Media Likes",
            response = SocialMediaLikesStatisticsDTO.class)
    public ResponseEntity<APIResponse<SocialMediaLikesStatisticsDTO>> getSocialMediaLikes(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

    	
    	SocialMediaLikesStatisticsDTO socialMediaLikes = statisticsService.getSocialMediaLikesStatistics(selfParent.getUserId(), identities, from);

    	if(socialMediaLikes.getData().isEmpty())
    		throw new NoLikesFoundInThisPeriodException(from);
    	
        return ApiHelper.<SocialMediaLikesStatisticsDTO>createAndSendResponse(CommentResponseCode.SOCIAL_MEDIA_LIKES,
                HttpStatus.OK, socialMediaLikes);
    }
    
    
    @RequestMapping(value = {"/most-active-friends"}, method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "MOST_ACTIVE_FRIENDS", nickname = "MOST_ACTIVE_FRIENDS", notes = "Most Active Friends",
            response = MostActiveFriendsDTO.class)
    public ResponseEntity<APIResponse<MostActiveFriendsDTO>> getMostActiveFriends(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

    	
    	MostActiveFriendsDTO mostActiveFriends = statisticsService.getMostActiveFriends(selfParent.getUserId(), identities, from);
    	
    	if(mostActiveFriends.getUsers().isEmpty())
    		throw new NoActiveFriendsInThisPeriodException(from);
    	

        return ApiHelper.<MostActiveFriendsDTO>createAndSendResponse(CommentResponseCode.MOST_ACTIVE_FRIENDS,
                HttpStatus.OK, mostActiveFriends);
    }
    
    
    @RequestMapping(value = {"/new-friends"}, method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "NEW_FRIENDS", nickname = "NEW_FRIENDS", notes = "New Friends",
            response = NewFriendsDTO.class)
    public ResponseEntity<APIResponse<NewFriendsDTO>> getNewFriends(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days limit", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

    	
    	NewFriendsDTO newFriends = statisticsService.getNewFriends(selfParent.getUserId(), identities, from);
    	
    	if(newFriends.getUsers().isEmpty())
    		throw new NoNewFriendsAtThisTimeException(from);

        return ApiHelper.<NewFriendsDTO>createAndSendResponse(CommentResponseCode.NEW_FRIENDS,
                HttpStatus.OK, newFriends);
    }
    
    
}
