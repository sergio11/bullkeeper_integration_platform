package sanchez.sanchez.sergio.masoc.web.rest.controller;

import java.util.Optional;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Iterables;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sanchez.sanchez.sergio.masoc.domain.service.IAlertService;
import sanchez.sanchez.sergio.masoc.domain.service.ICommentsService;
import sanchez.sanchez.sergio.masoc.domain.service.ISocialMediaService;
import sanchez.sanchez.sergio.masoc.domain.service.ISonService;
import sanchez.sanchez.sergio.masoc.domain.service.IStatisticsService;
import sanchez.sanchez.sergio.masoc.exception.AlertNotFoundException;
import sanchez.sanchez.sergio.masoc.exception.CommentsBySonNotFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoAlertsBySonFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoChildrenFoundException;
import sanchez.sanchez.sergio.masoc.exception.NoCommunityStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoDimensionsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoSentimentAnalysisStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.NoSocialMediaActivityFoundForThisPeriodException;
import sanchez.sanchez.sergio.masoc.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.masoc.exception.SonNotFoundException;
import sanchez.sanchez.sergio.masoc.persistence.constraints.SocialMediaShouldExists;
import sanchez.sanchez.sergio.masoc.persistence.constraints.SonShouldExists;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.masoc.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.masoc.util.ValidList;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommentDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SonDTO;
import sanchez.sanchez.sergio.masoc.web.rest.ApiHelper;
import sanchez.sanchez.sergio.masoc.web.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.masoc.web.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.masoc.web.rest.hal.ISonHAL;
import sanchez.sanchez.sergio.masoc.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.masoc.web.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.masoc.web.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.masoc.web.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.masoc.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.masoc.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.masoc.web.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.masoc.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.masoc.web.uploads.service.IUploadFilesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;


@RestController("RestUserController")
@Validated
@RequestMapping("/api/v1/children/")
@Api(tags = "children", value = "/children/", description = "Punto de Entrada para el manejo de usuarios analizados", produces = "application/json")
public class ChildrenController extends BaseController implements ISonHAL, ICommentHAL, ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(ChildrenController.class);
    
    private final ISonService sonService;
    private final ICommentsService commentService;
    private final ISocialMediaService socialMediaService;
    private final IUploadFilesService uploadFilesService;
    private final IAlertService alertService;
    private final IStatisticsService statisticsService;
    
    public ChildrenController(ISonService sonService, ICommentsService commentService, ISocialMediaService socialMediaService,
    		IUploadFilesService uploadFilesService, IAlertService alertService, IStatisticsService statisticsService) {
        this.sonService = sonService;
        this.commentService = commentService;
        this.socialMediaService = socialMediaService;
        this.uploadFilesService = uploadFilesService;
        this.alertService = alertService;
        this.statisticsService = statisticsService;
    }
    
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_CHILDREN", nickname = "GET_ALL_CHILDREN", notes = "Get all Children", response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<SonDTO>>>> getAllChildren(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<SonDTO> pagedAssembler) throws Throwable {
        
    	logger.debug("Get all Children");
    	Page<SonDTO> childrenPage = sonService.findPaginated(pageable);
        if(childrenPage.getTotalElements() == 0)
        	throw new NoChildrenFoundException();
        return ApiHelper.<PagedResources<Resource<SonDTO>>>createAndSendResponse(ChildrenResponseCode.ALL_USERS, 
            		HttpStatus.OK, pagedAssembler.toResource(addLinksToChildren((childrenPage))));
    }
    

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_SON_BY_ID", nickname = "GET_SON_BY_ID", notes = "Get Son By Id", response = SonDTO.class)
    public ResponseEntity<APIResponse<SonDTO>> getSonById(
    		@ApiParam(name= "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
    		 		@PathVariable String id) throws Throwable {
        logger.debug("Get User with id: " + id);
        
        return Optional.ofNullable(sonService.getSonById(id))
                .map(sonResource -> addLinksToSon(sonResource))
                .map(sonResource -> ApiHelper.<SonDTO>createAndSendResponse(ChildrenResponseCode.SINGLE_USER, HttpStatus.OK, sonResource))
                .orElseThrow(() -> { throw new SonNotFoundException(); });
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "DELETE_SON_BY_ID", nickname = "DELETE_SON_BY_ID", notes = "Delete Son By Id", response = SonDTO.class)
    public ResponseEntity<APIResponse<String>> deleteSonById(
    		@ApiParam(name= "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
                                @SonShouldExists(message = "{son.should.be.exists}")
    		 		@PathVariable String id) throws Throwable {
        
        logger.debug("Delete User with id: " + id);
        
        sonService.deleteById(id);
        
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_DELETED_SUCCESSFULLY,
                HttpStatus.OK, messageSourceResolver.resolver("son.deleted.successfully"));
    }
    
  
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_COMMENTS_BY_SON", nickname = "GET_COMMENTS_BY_SON", notes = "Get Comments By Son Id",
            response = List.class)
    public ResponseEntity<APIResponse<Iterable<CommentDTO>>> getCommentsBySonId(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id,
            @ApiParam(name = "author", value = "Author's identifier", required = false)	
        			@RequestParam(name="author" , required=false)
        				String author,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
    			@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from,
    		@ApiParam(name = "social_media", value = "Social Medias", required = false)	
				@RequestParam(name="social_media" , required=false)
					SocialMediaTypeEnum[] socialMedias,
	    	@ApiParam(name = "violence", value = "Result for Violence", required = false)
				@RequestParam(name = "violence", required = false) ViolenceLevelEnum violence,
			@ApiParam(name = "drugs", value = "Result for Drugs", required = false)
				@RequestParam(name = "drugs", required = false) DrugsLevelEnum drugs,
			@ApiParam(name = "bullying", value = "Result for bullying", required = false)
				@RequestParam(name = "bullying", required = false) BullyingLevelEnum bullying,
			@ApiParam(name = "adult", value = "Result for adult content", required = false)
				@RequestParam(name = "adult", required = false) AdultLevelEnum adult) throws Throwable {
        
    	logger.debug("Get Comments by user with id: " + id);
    	logger.debug("Author's identifier -> " + author);
    	if(socialMedias != null && socialMedias.length > 0)
    		logger.debug("Social Media count -> " + socialMedias.length + " Medias -> "+ socialMedias.toString());
    	logger.debug("Days Ago -> " + from);
    	logger.debug("Violence -> " + violence);
    	logger.debug("Drugs -> " + drugs);
    	logger.debug("Bullying -> " + bullying);
    	logger.debug("Adult -> " + adult);
        
        Iterable<CommentDTO> comments = commentService.getComments(id, author, from, socialMedias, violence, drugs, bullying, adult);
        
        if(Iterables.size(comments) == 0)
        	throw new CommentsBySonNotFoundException();
        
        return ApiHelper.<Iterable<CommentDTO>>createAndSendResponse(CommentResponseCode.ALL_COMMENTS_BY_CHILD, 
        		HttpStatus.OK, comments);
        
    }
    
    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "UPLOAD_PROFILE_IMAGE_FOR_SON", nickname = "UPLOAD_PROFILE_IMAGE_FOR_SON", notes = "Upload Profile Image For Son")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message= "Profile Image", response = ImageDTO.class),
    	@ApiResponse(code = 500, message= "Upload Failed")
    })
    public ResponseEntity<APIResponse<ImageDTO>> uploadProfileImageForSon(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
         	@Valid @ValidObjectId(message = "{son.id.notvalid}")
          		@PathVariable String id,
            @RequestPart("profile_image") MultipartFile profileImage,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
    	
    	RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
    	ImageDTO imageDTO = uploadFilesService.uploadSonProfileImage(new ObjectId(id), uploadProfileImage);
        return ApiHelper.<ImageDTO>createAndSendResponse(ChildrenResponseCode.PROFILE_IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, imageDTO);

    }
    
    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "DOWNLOAD_SON_PROFILE_IMAGE", nickname = "DOWNLOAD_SON_PROFILE_IMAGE", notes = "Download Son Profile Image")
    public ResponseEntity<byte[]> downloadSonProfileImage(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
    	
    	logger.debug("Download Son Profile Image");
        SonDTO sonDTO = sonService.getSonById(id);
        return controllerHelper.downloadProfileImage(sonDTO.getProfileImage());
        
    }
    
    @RequestMapping(value = { "/{id}/social", "/{id}/social/all"  }, method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_SON", nickname = "GET_SOCIAL_MEDIA_BY_SON", notes = "Get Social Madia By Son",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getSocialMediaBySonId(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
    				@PathVariable String id) throws Throwable {
        logger.debug("Get Social Media by User Id " + id);
        
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getSocialMediaByUser(id);
        
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
    
    @RequestMapping(value = "/social/add", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#socialMedia.son) )")
    @ApiOperation(value = "ADD_SOCIAL_MEDIA", nickname = "ADD_SOCIAL_MEDIA", notes = "Add Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> deleteAllSocialMedia(
            @ApiParam(value = "socialMedia", required = true) 
				@Validated(ICommonSequence.class) @RequestBody SaveSocialMediaDTO socialMedia) throws Throwable {
    		
        return Optional.ofNullable(socialMediaService.create(socialMedia))
        		.map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
        		.map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_ADDED, 
        				HttpStatus.OK, socialMediaResource))
        		.orElseThrow(() -> { throw new SocialMediaNotFoundException(); });        
    }
    
    
    @RequestMapping(value = "/social/update", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "UPDATE_SOCIAL_MEDIA", nickname = "UPDATE_SOCIAL_MEDIA", notes = "Update Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> updateSocialMediaToSon(
            @ApiParam(value = "socialMedia", required = true) 
				@Validated(ICommonSequence.class) @RequestBody SaveSocialMediaDTO socialMedia) throws Throwable {
    		
        return Optional.ofNullable(socialMediaService.update(socialMedia))
        		.map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
        		.map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_UPDATED, 
        				HttpStatus.OK, socialMediaResource))
        		.orElseThrow(() -> { throw new SocialMediaNotFoundException(); });        
    }
    
    @RequestMapping(value = "/social/save", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#socialMedia.son) )")
    @ApiOperation(value = "SAVE_SOCIAL_MEDIA", nickname = "SAVE_SOCIAL_MEDIA", notes = "Save Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<SocialMediaDTO>> saveSocialMediaToSon(
            @ApiParam(value = "socialMedia", required = true) 
				@Validated(ICommonSequence.class) @RequestBody SaveSocialMediaDTO socialMedia) throws Throwable {
    		
        return Optional.ofNullable(socialMediaService.insertOrUpdate(socialMedia))
        		.map(socialMediaResource -> addLinksToSocialMedia(socialMediaResource))
        		.map(socialMediaResource -> ApiHelper.<SocialMediaDTO>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_SAVED, 
        				HttpStatus.OK, socialMediaResource))
        		.orElseThrow(() -> { throw new SocialMediaNotFoundException(); });        
    }
    
    @RequestMapping(value = "/{id}/statistics/social-activity", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "SOCIAL_MEDIA_ACTIVITY_STATISTICS", nickname = "SOCIAL_MEDIA_ACTIVITY_STATISTICS", 
            notes = "Social Media Activity Statistics", response = SocialMediaActivityStatisticsDTO.class)
    public ResponseEntity<APIResponse<SocialMediaActivityStatisticsDTO>> getSocialMediaStatisticsActivity(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@SonShouldExists(message = "{son.should.be.exists}")
    						@PathVariable String id,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
            	@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        logger.debug("Get Social Media Activity Statistics for Son -> " + id );
        
        SocialMediaActivityStatisticsDTO socialMediaActivityStatistics = statisticsService.getSocialMediaActivityStatistics(id, from);
       
        if(socialMediaActivityStatistics.getData().isEmpty())
        	throw new NoSocialMediaActivityFoundForThisPeriodException(from);
        
        
        return ApiHelper.<SocialMediaActivityStatisticsDTO>createAndSendResponse(ChildrenResponseCode.SOCIAL_MEDIA_ACTIVITY_STATISTICS, 
				HttpStatus.OK, socialMediaActivityStatistics);  
    }
    
    
    @RequestMapping(value = "/{id}/statistics/sentiment-analysis", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "SENTIMENT_ANALYSIS_STATISTICS", nickname = "SENTIMENT_ANALYSIS_STATISTICS", 
            notes = "Sentiment Analysis Statistics", response = SentimentAnalysisStatisticsDTO.class)
    public ResponseEntity<APIResponse<SentimentAnalysisStatisticsDTO>> getSentimentAnalysisStatistics(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@SonShouldExists(message = "{son.should.be.exists}")
    					@PathVariable String id,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        logger.debug("Get Sentiment Analysis Statistics for -> " + id );
        
        SentimentAnalysisStatisticsDTO sentimentAnalysisStatistics = statisticsService.getSentimentAnalysisStatistics(id, from);
      
        if(sentimentAnalysisStatistics.getData().isEmpty())
        	throw new NoSentimentAnalysisStatisticsForThisPeriodException(from);
       
        return ApiHelper.<SentimentAnalysisStatisticsDTO>createAndSendResponse(ChildrenResponseCode.SENTIMENT_ANALYSIS_STATISTICS, 
				HttpStatus.OK, sentimentAnalysisStatistics);  
    }
    
    @RequestMapping(value = "/{id}/statistics/communities", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "COMMUNITIES_STATISTICS", nickname = "COMMUNITIES_STATISTICS", 
            notes = "Communities Statistics", response = CommunitiesStatisticsDTO.class)
    public ResponseEntity<APIResponse<CommunitiesStatisticsDTO>> getCommunitiesStatistics(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@SonShouldExists(message = "{son.should.be.exists}")
    					@PathVariable String id,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
    			@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        logger.debug("Get Communities Statistics for -> " + id);
        
        CommunitiesStatisticsDTO communitiesStatistics = statisticsService.getCommunitiesStatistics(id, from);
        
        if(communitiesStatistics.getData().isEmpty())
        	throw new NoCommunityStatisticsForThisPeriodException(from);
     
       
        return ApiHelper.<CommunitiesStatisticsDTO>createAndSendResponse(ChildrenResponseCode.COMMUNITIES_STATISTICS, 
				HttpStatus.OK, communitiesStatistics);  
    }
    
    
    @RequestMapping(value = "/{id}/statistics/dimensions", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "FOUR_DIMENSIONS_STATISTICS", nickname = "FOUR_DIMENSIONS_STATISTICS", 
            notes = "Four Dimensions Statistics", response = CommunitiesStatisticsDTO.class)
    public ResponseEntity<APIResponse<DimensionsStatisticsDTO>> getFourDimensionsStatistics(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@SonShouldExists(message = "{son.should.be.exists}")
    					@PathVariable String id,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        logger.debug("Get Four Dimensions Statistics for -> " + id );
        
        DimensionsStatisticsDTO fourDimensionsStatistics = statisticsService.getDimensionsStatistics(id, from);
     
        if(fourDimensionsStatistics.getData().isEmpty())
        	throw new NoDimensionsStatisticsForThisPeriodException(from);
        
        
        return ApiHelper.<DimensionsStatisticsDTO>createAndSendResponse(ChildrenResponseCode.FOUR_DIMENSIONS_STATISTICS, 
				HttpStatus.OK, fourDimensionsStatistics);  
    }
    
   
   
    
    @RequestMapping(value = "/{id}/social/save/all", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "SAVE_ALL_SOCIAL_MEDIA", nickname = "SAVE_ALL_SOCIAL_MEDIA", notes = "Save Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> saveAllSocialMediaToSon(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
        	@Valid @ValidObjectId(message = "{son.id.notvalid}")
         		@PathVariable String id,
            @ApiParam(value = "socialMedias", required = true) 
				@Validated(ICommonSequence.class) @RequestBody ValidList<SaveSocialMediaDTO> socialMedias) throws Throwable {
    	
    	
    	Iterable<SocialMediaDTO> socialMediaEntitiesSaved = socialMediaService.save(socialMedias.getList(), id);
    	
    	if(Iterables.size(socialMediaEntitiesSaved) == 0)
    		throw new SocialMediaNotFoundException();
    	
    	return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.ALL_SOCIAL_MEDIA_SAVED, 
				HttpStatus.OK, addLinksToSocialMedia(socialMediaEntitiesSaved));    
    }
    
    @RequestMapping(value = "/{id}/social/delete/all", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "DELETE_ALL_SOCIAL_MEDIA", nickname = "DELETE_ALL_SOCIAL_MEDIA", notes = "Delete all social media of user",
            response = List.class)
    //@OnlyAccessForAdminOrParentOfTheSon(son = "#id")
    public ResponseEntity<APIResponse<Long>> deleteAllSocialMedia(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
        Long socialMediaEntitiesDeleted = socialMediaService.deleteSocialMediaByUser(id);
        
        return ApiHelper.<Long>createAndSendResponse(
                SocialMediaResponseCode.ALL_USER_SOCIAL_MEDIA_DELETED, HttpStatus.OK, socialMediaEntitiesDeleted);
    }
    
    @RequestMapping(value = "/{idson}/social/delete/{idsocial}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#idson) )")
    @ApiOperation(value = "DELETE_SOCIAL_MEDIA", nickname = "DELETE_SOCIAL_MEDIA", notes = "Delete a single social media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<String>> deleteSocialMedia(
    		@ApiParam(name = "idson", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String idson,
            @ApiParam(name = "idsocial", value = "Identificador del Medio Social", required = true)
            	@Validated(ICommonSequence.class) @ValidObjectId(message = "{social.id.notvalid}")
            		@SocialMediaShouldExists(message = "{social.not.exists}")
             			@PathVariable String idsocial) throws Throwable {
        
        Boolean deleted = socialMediaService.deleteSocialMediaById(idsocial);
        
        return deleted ? ApiHelper.<String>createAndSendResponse(
                SocialMediaResponseCode.USER_SOCIAL_MEDIA_DELETED, HttpStatus.OK, this.messageSourceResolver.resolver("social.media.deleted")) :
                	ApiHelper.<String>createAndSendResponse(
                            SocialMediaResponseCode.USER_SOCIAL_MEDIA_NOT_DELETED, HttpStatus.NOT_FOUND, this.messageSourceResolver.resolver("social.media.not.deleted"));
    }
    
   
    @RequestMapping(value = "/{id}/social/invalid", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_INVALID_SOCIAL_MEDIA_BY_SON", nickname = "GET_INVALID_SOCIAL_MEDIA_BY_SON", notes = "Get Social Madia By Son with invalid token",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getInvalidSocialMediaBySonId(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        logger.debug("Get Invalid Social  Media by User Id " + id);
        
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getInvalidSocialMediaById(id);
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.INVALID_SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
    
    
    @RequestMapping(value = "/{id}/social/valid", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_VALID_SOCIAL_MEDIA_BY_SON", nickname = "GET_VALID_SOCIAL_MEDIA_BY_SON", notes = "Get Social Madia By Son with valid token",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getValidSocialMediaBySonId(
    		@ApiParam(name = "id", value = "Identificador del hijo", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
       
    	logger.debug("Get Valid Social  Media by User Id " + id);
        
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getValidSocialMediaById(id);
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.VALID_SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
    
    
    @RequestMapping(value = "/{id}/alerts", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "GET_ALERTS_BY_SON", nickname = "GET_ALERTS_BY_SON", notes = "Get Alerts By Son Id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getAlertsBySonId(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id,
             @ApiParam(name = "count", value = "Number of alerts", required = false)
    				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from,
			@ApiParam(name = "levels", value = "Alert Levels", required = false)	
				@RequestParam(name="levels" , required=false)
            	AlertLevelEnum[] levels) throws Throwable {
        
    	logger.debug("Get Alerts by Son with id: " + id);
    	logger.debug("Count -> " + count);
    	logger.debug("From -> " + from);
    	if(levels != null && levels.length > 0)
    		logger.debug("Levels -> " + levels.toString());
        
        Iterable<AlertDTO> alerts = alertService.findSonAlerts(new ObjectId(id), count, from, levels);
        
        if(Iterables.size(alerts) == 0)
            throw new NoAlertsBySonFoundException();

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(ChildrenResponseCode.ALERTS_BY_SON, 
        		HttpStatus.OK, alerts);
        
    }
    
    @RequestMapping(value = "/{id}/alerts", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#id) )")
    @ApiOperation(value = "CLEAR_CHILD_ALERTS", nickname = "CLEAR_CHILD_ALERTS", notes = "Clear Child Alerts",
            response = Long.class)
    public ResponseEntity<APIResponse<String>> clearChildAlerts(
            @ApiParam(name = "id", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
    	logger.debug("Clear alerts of son with id: " + id);
        
        Long alertsDeleted = alertService.clearChildAlerts(new ObjectId(id));
       
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_ALERTS_CLEANED, 
        		HttpStatus.OK, alertsDeleted.toString());
        
    }
    
    @RequestMapping(value = "/{son}/alerts/{alert}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#son) )")
    @ApiOperation(value = "GET_ALERT_BY_ID", nickname = "GET_ALERT_BY_ID", notes = "Get alert by id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<AlertDTO>> getAlertById(
            @ApiParam(name = "son", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String son,
             @ApiParam(name = "son", value = "Identificador de la alerta", required = true)
            	@Valid @ValidObjectId(message = "{alert.id.notvalid}")
             		@PathVariable String alert) throws Throwable {
        
        
        return Optional.ofNullable(alertService.findById(new ObjectId(alert)))
        		.map(alertDTO -> ApiHelper.<AlertDTO>createAndSendResponse(ChildrenResponseCode.GET_ALERT_BY_ID, 
        				HttpStatus.OK, alertDTO))
        		.orElseThrow(() -> { throw new AlertNotFoundException(); }); 
        
    }
    
    
    @RequestMapping(value = "/{son}/alerts/{alert}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isYourSon(#son) )")
    @ApiOperation(value = "GET_ALERT_BY_ID", nickname = "GET_ALERT_BY_ID", notes = "Get alert by id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<String>> deleteAlertById(
            @ApiParam(name = "son", value = "Identificador del hijo", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String son,
             @ApiParam(name = "son", value = "Identificador de la alerta", required = true)
            	@Valid @ValidObjectId(message = "{alert.id.notvalid}")
             		@PathVariable String alert) throws Throwable {
        
        logger.debug("Delete Alert by id: " + alert);
        
        alertService.deleteById(new ObjectId(alert));
        
        return ApiHelper.<String>createAndSendResponse(
                ChildrenResponseCode.ALERT_BY_ID_DELETED, HttpStatus.OK, messageSourceResolver.resolver("alert.deleted"));
        
    }
    
   
    
    @PostConstruct
    protected void init() {
        Assert.notNull(sonService, "Son Service cannot be a null");
        Assert.notNull(commentService, "Comment Service cannot be a null");
        Assert.notNull(socialMediaService, "Social Media Service cannot be a null");
        Assert.notNull(uploadFilesService, "Upload Files Service cannot be a null");
 
    }
}
