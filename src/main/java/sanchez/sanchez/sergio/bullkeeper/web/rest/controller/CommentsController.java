package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;



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

import com.google.common.collect.Iterables;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ICommentsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IStatisticsService;
import sanchez.sanchez.sergio.bullkeeper.exception.CommentNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoActiveFriendsInThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommentsExtractedException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommentsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoLikesFoundInThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoNewFriendsAtThisTimeException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.util.ValidList;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MostActiveFriendsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.NewFriendsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaLikesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForGuardian;

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
@Api(tags = "comments", value = "/comments/", description = "Administration of comments and/or opinions", produces = "application/json")
public class CommentsController extends BaseController implements ICommentHAL {

    private static Logger logger = LoggerFactory.getLogger(CommentsController.class);
    
    private final ICommentsService commentsService;
    private final IStatisticsService statisticsService;

    public CommentsController(ICommentsService commentsService, IStatisticsService statisticsService) {
        this.commentsService = commentsService;
        this.statisticsService = statisticsService;
    }
    
    /**
     * 
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
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

    /**
     * 
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
    
    /**
     * 
     * @param selfGuardian
     * @param identities
     * @param author
     * @param socialMedias
     * @param from
     * @param violence
     * @param drugs
     * @param bullying
     * @param adult
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    @ApiOperation(value = "GET_COMMENTS", nickname = "GET_COMMENTS", 
            notes = "Get Comments", response = PagedResources.class)
    public ResponseEntity<APIResponse<Iterable<CommentDTO>>> getComments(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "children", value = "Children's Identifiers", required = false)
            	@RequestParam(name="children" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "author", value = "Author's identifier", required = false)	
    			@RequestParam(name="author" , required=false)
    				String author,
    	   @ApiParam(name = "social_medias", value = "Social Medias", required = false)	
    			@RequestParam(name="social_media" , required=false) SocialMediaTypeEnum[] socialMedias,
			@ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from,
        	@ApiParam(name = "violence", value = "Result for Violence", required = false)
    			@RequestParam(name = "violence", required = false) ViolenceLevelEnum violence,
    		@ApiParam(name = "drugs", value = "Result for Drugs", required = false)
				@RequestParam(name = "drugs", required = false) DrugsLevelEnum drugs,
			@ApiParam(name = "bullying", value = "Result for bullying", required = false)
				@RequestParam(name = "bullying", required = false) BullyingLevelEnum bullying,
			@ApiParam(name = "adult", value = "Result for adult content", required = false)
				@RequestParam(name = "adult", required = false) AdultLevelEnum adult) throws Throwable {
    	
    	if(identities != null && !identities.isEmpty()) {
    		logger.debug("Children's Identifiers -> " + identities.toString());
    	}
    	
    	logger.debug("Author's identifier -> " + author);
    	if(socialMedias != null && socialMedias.length > 0)
    		for(int i = 0; i < socialMedias.length; i++)
    			logger.debug("Social Media -> " + socialMedias[i].name());
    	logger.debug("Days Ago -> " + from);
    	logger.debug("Violence -> " + violence);
    	logger.debug("Drugs -> " + drugs);
    	logger.debug("Bullying -> " + bullying);
    	logger.debug("Adult -> " + adult);
    	
    	
    	final Iterable<CommentDTO> comments = commentsService.getComments(identities, author, from, socialMedias, violence, drugs, bullying, adult);
    	
    	if(Iterables.size(comments) == 0)
    		throw new NoCommentsFoundException();
        
        
        return ApiHelper.<Iterable<CommentDTO>>createAndSendResponse(CommentResponseCode.FILTERED_COMMENTS, 
        		HttpStatus.OK, comments);
       
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
    public ResponseEntity<APIResponse<CommentsStatisticsDTO>> getCommentsAnalyzed(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

   	
    	CommentsStatisticsDTO commentsStatistics = statisticsService.getCommentsStatistics(selfGuardian.getUserId(), identities, from);

    	if(commentsStatistics.getData().isEmpty())
    		throw new NoCommentsExtractedException(from);
    	
        return ApiHelper.<CommentsStatisticsDTO>createAndSendResponse(CommentResponseCode.COMMENTS_EXTRACTED_BY_SON,
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
    @RequestMapping(value = {"/social-media-Likes"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "SOCIAL_MEDIA_LIKES", nickname = "SOCIAL_MEDIA_LIKES", notes = "Social Media Likes",
            response = SocialMediaLikesStatisticsDTO.class)
    public ResponseEntity<APIResponse<SocialMediaLikesStatisticsDTO>> getSocialMediaLikes(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

    	
    	SocialMediaLikesStatisticsDTO socialMediaLikes = statisticsService.getSocialMediaLikesStatistics(selfGuardian.getUserId(), identities, from);

    	if(socialMediaLikes.getData().isEmpty())
    		throw new NoLikesFoundInThisPeriodException(from);
    	
        return ApiHelper.<SocialMediaLikesStatisticsDTO>createAndSendResponse(CommentResponseCode.SOCIAL_MEDIA_LIKES,
                HttpStatus.OK, socialMediaLikes);
    }
    
    
    /**
     * 
     * @param selfGuardian
     * @param identities
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/most-active-friends"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "MOST_ACTIVE_FRIENDS", nickname = "MOST_ACTIVE_FRIENDS", notes = "Most Active Friends",
            response = MostActiveFriendsDTO.class)
    public ResponseEntity<APIResponse<MostActiveFriendsDTO>> getMostActiveFriends(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

    	
    	MostActiveFriendsDTO mostActiveFriends = statisticsService
    			.getMostActiveFriends(selfGuardian.getUserId(), identities, from);
    	
    	if(mostActiveFriends.getUsers().isEmpty())
    		throw new NoActiveFriendsInThisPeriodException(from);
    	

        return ApiHelper.<MostActiveFriendsDTO>createAndSendResponse(CommentResponseCode.MOST_ACTIVE_FRIENDS,
                HttpStatus.OK, mostActiveFriends);
    }
    
    
    /**
     * 
     * @param selfGuardian
     * @param identities
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/new-friends"}, method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "NEW_FRIENDS", nickname = "NEW_FRIENDS", notes = "New Friends",
            response = NewFriendsDTO.class)
    public ResponseEntity<APIResponse<NewFriendsDTO>> getNewFriends(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
            @ApiParam(name = "identities", value = "Children's Identifiers", required = false)
            	@RequestParam(name="identities" , required=false)
            		ValidList<String> identities,
            @ApiParam(name = "days_ago", value = "Days limit", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {

    	
    	NewFriendsDTO newFriends = statisticsService.getNewFriends(selfGuardian.getUserId(), identities, from);
    	
    	if(newFriends.getUsers().isEmpty())
    		throw new NoNewFriendsAtThisTimeException(from);

        return ApiHelper.<NewFriendsDTO>createAndSendResponse(CommentResponseCode.NEW_FRIENDS,
                HttpStatus.OK, newFriends);
    }
    
}
