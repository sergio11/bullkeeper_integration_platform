package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

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
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ICommentsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IScheduledBlockService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ISocialMediaService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IStatisticsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.exception.AlertNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CommentsByKidNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.GuardianNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.KidNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsByKidFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAppsInstalledFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoChildrenFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommunityStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoDimensionsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoKidGuardianFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoScheduledBlockFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSentimentAnalysisStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSocialMediaActivityFoundForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoTerminalsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.TerminalNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.AppInstalledShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ImageShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ScheduledBlockShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SocialMediaShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.util.ValidList;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockStatusDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.IKidHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.SocialMediaResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.UploadFileInfo;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;

import springfox.documentation.annotations.ApiIgnore;
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;


/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@RestController("RestUserController")
@Validated
@RequestMapping("/api/v1/children/")
@Api(tags = "children", value = "/children/",
		description = "Administration of the children / adolescents of the platform", 
produces = "application/json")
public class ChildrenController extends BaseController 
	implements IKidHAL, ICommentHAL, ISocialMediaHAL {

    private static Logger logger = LoggerFactory.getLogger(ChildrenController.class);
    
    private final IKidService kidService;
    private final ICommentsService commentService;
    private final ISocialMediaService socialMediaService;
    private final IUploadFilesService uploadFilesService;
    private final IAlertService alertService;
    private final IStatisticsService statisticsService;
    private final ITerminalService terminalService;
    private final IScheduledBlockService scheduledBlockService;
    
    /**
     * 
     * @param kidService
     * @param commentService
     * @param socialMediaService
     * @param uploadFilesService
     * @param alertService
     * @param statisticsService
     * @param terminalService
     * @param scheduledBlockService
     */
    public ChildrenController(IKidService kidService, ICommentsService commentService, ISocialMediaService socialMediaService,
    		IUploadFilesService uploadFilesService, IAlertService alertService, IStatisticsService statisticsService,
    		final ITerminalService terminalService, final IScheduledBlockService scheduledBlockService) {
        this.kidService = kidService;
        this.commentService = commentService;
        this.socialMediaService = socialMediaService;
        this.uploadFilesService = uploadFilesService;
        this.alertService = alertService;
        this.statisticsService = statisticsService;
        this.terminalService = terminalService;
        this.scheduledBlockService = scheduledBlockService;
    }
    
    /**
     * Get All Children
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_CHILDREN", nickname = "GET_ALL_CHILDREN",
    	notes = "Get all Children", response = PagedResources.class)
    public ResponseEntity<APIResponse<PagedResources<Resource<KidDTO>>>> getAllChildren(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<KidDTO> pagedAssembler) throws Throwable {
        
    	logger.debug("Get all Children");
    	// Get Children Page
    	final Page<KidDTO> childrenPage = kidService.findPaginated(pageable);
        
    	if(childrenPage.getTotalElements() == 0)
        	throw new NoChildrenFoundException();
        // Create And Send Response
    	return ApiHelper.<PagedResources<Resource<KidDTO>>>createAndSendResponse(ChildrenResponseCode.ALL_USERS, 
            		HttpStatus.OK, pagedAssembler.toResource(addLinksToChildren(childrenPage)));
    }
    

    /**
     * Get Kid By Id
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() "
    		+ "|| ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_KID_BY_ID", nickname = "GET_KID_BY_ID", notes = "Get Kid By Id", response = KidDTO.class)
    public ResponseEntity<APIResponse<KidDTO>> getSonById(
    		@ApiParam(name= "id", value = "Kid identified", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
    		 		@PathVariable String id) throws Throwable {
        
    	logger.debug("Get Kid with id: " + id);
        
        return Optional.ofNullable(kidService.getKidById(id))
                .map(sonResource -> addLinksToKid(sonResource))
                .map(sonResource -> ApiHelper.<KidDTO>createAndSendResponse(ChildrenResponseCode.SINGLE_USER, HttpStatus.OK, sonResource))
                .orElseThrow(() -> { throw new KidNotFoundException(); });
    }
    
    /**
     * Delete Kid By Id
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && "
    		+ "@authorizationService.isYourGuardianAndCanEditKidInformation(#id) )")
    @ApiOperation(value = "DELETE_KID_BY_ID", nickname = "DELETE_KID_BY_ID", notes = "Delete KId By Id", 
    		response = KidDTO.class)
    public ResponseEntity<APIResponse<String>> deleteKidById(
    		@ApiParam(name= "id", value = "Kid Identified", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
                                @KidShouldExists(message = "{son.should.be.exists}")
    		 		@PathVariable String id) throws Throwable {
        
        logger.debug("Delete Kid with id: " + id);
        
        
        // Delete Kid By Id
        kidService.deleteById(id);
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_DELETED_SUCCESSFULLY,
                HttpStatus.OK, messageSourceResolver.resolver("son.deleted.successfully"));
    }
    
   
    /**
     * Save Guardians
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/guardians", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && "
    		+ "@authorizationService.isYourGuardianAndCanEditKidInformation(#id) )")
    @ApiOperation(value = "SAVE_GUARDIANS_FOR_KID", nickname = "SAVE_GUARDIANS_FOR_KID", 
    	notes = "Save Guardians for kid", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<KidGuardianDTO>>> saveGuardians(
    		@ApiParam(name= "id", value = "Kid identified", required = true)
    			@Valid @KidShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable String id,
    		@ApiParam(value = "guardians", required = true) 
				@Validated(ICommonSequence.class)
    				@RequestBody final ValidList<SaveGuardianDTO> guardians) throws Throwable {
    	
    	logger.debug("Save Guardians -> " + guardians.size());
    	
    	// Save Guardians
    	final Iterable<KidGuardianDTO> kidGuardiansDTOs = 
    			kidService.save(guardians.getList(), new ObjectId(id));
    	
    	// Create and send response
        return ApiHelper.<Iterable<KidGuardianDTO>>createAndSendResponse(ChildrenResponseCode.CHILD_GUARDIANS_SAVED,
                HttpStatus.OK, kidGuardiansDTOs);
    }
    
    /**
     * Get Guardians
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/guardians", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && "
    		+ "@authorizationService.isYourGuardianAndCanEditKidInformation(#id) )")
    @ApiOperation(value = "GET_GUARDIANS_FOR_KID", nickname = "GET_GUARDIANS_FOR_KID", 
    	notes = "Get Guardians for kid", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<KidGuardianDTO>>> getGuardians(
    		@ApiParam(name= "id", value = "Kid identified", required = true)
    			@Valid @KidShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable String id) throws Throwable {
    	
    	logger.debug("Get Guardians for -> " + id);
    	
    	// Save Guardians
    	final Iterable<KidGuardianDTO> kidGuardiansDTOs = 
    			kidService.getGuardians(new ObjectId(id));
    	
    	if(Iterables.size(kidGuardiansDTOs) == 0)
    		throw new NoKidGuardianFoundException();
    	
    	// Create and send response
        return ApiHelper.<Iterable<KidGuardianDTO>>createAndSendResponse(ChildrenResponseCode.CHILD_GUARDIANS,
                HttpStatus.OK, kidGuardiansDTOs);
    	
    }
    
   
    /**
     * Get Comments By Kid
     * @param id
     * @param author
     * @param from
     * @param socialMedias
     * @param violence
     * @param drugs
     * @param bullying
     * @param adult
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_COMMENTS_BY_KID", nickname = "GET_COMMENTS_BY_KID", 
    	notes = "Get Comments By Kid Id", response = List.class)
    public ResponseEntity<APIResponse<Iterable<CommentDTO>>> getCommentsByKidId(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
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
        
    	logger.debug("Get Comments by kid with id: " + id);
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
        	throw new CommentsByKidNotFoundException();
        
        return ApiHelper.<Iterable<CommentDTO>>createAndSendResponse(CommentResponseCode.ALL_COMMENTS_BY_CHILD, 
        		HttpStatus.OK, comments);
        
    }
    
    /**
     * Upload Profile Image For Kid
     * @param id
     * @param profileImage
     * @param selfGuard
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
    		+ " && @authorizationService.isYourGuardianAndCanEditKidInformation(#id) )")
    @ApiOperation(value = "UPLOAD_PROFILE_IMAGE_FOR_KID", nickname = "UPLOAD_PROFILE_IMAGE_FOR_KID",
    	notes = "Upload Profile Image For Kid")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message= "Profile Image", response = ImageDTO.class),
    	@ApiResponse(code = 500, message= "Upload Failed")
    })
    public ResponseEntity<APIResponse<ImageDTO>> uploadProfileImageForKid(
    		@ApiParam(name = "id", value = "Kid Identifier", required = true)
         	@Valid @ValidObjectId(message = "{son.id.notvalid}")
          		@PathVariable String id,
            @RequestPart("profile_image") MultipartFile profileImage,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuard) throws Throwable {
    	
    	RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
    	ImageDTO imageDTO = uploadFilesService.uploadKidProfileImage(new ObjectId(id), uploadProfileImage);
        return ApiHelper.<ImageDTO>createAndSendResponse(ChildrenResponseCode.PROFILE_IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, imageDTO);

    }
    
    /**
     * Download Kid Profile Image
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/image", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() "
    		+ "|| ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "DOWNLOAD_KID_PROFILE_IMAGE", nickname = "DOWNLOAD_KID_PROFILE_IMAGE",
    	notes = "Download Kid Profile Image")
    public ResponseEntity<byte[]> downloadKidProfileImage(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
       
    	logger.debug("Download Kid Profile Image");
    	// Get Kid By Id
        final KidDTO sonDTO = kidService.getKidById(id);
        // Download Profile Image
        return controllerHelper.downloadProfileImage(sonDTO.getProfileImage());
        
    }
    
    /**
     * Get Social Media By Kid Id
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = { "/{id}/social", "/{id}/social/all"  }, method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_SOCIAL_MEDIA_BY_KID", nickname = "GET_SOCIAL_MEDIA_BY_KID", 
    	notes = "Get Social Madia By Kid", response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getSocialMediaByKidId(
    		@ApiParam(name = "id", value = "Kid Identifier", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
    				@PathVariable String id) throws Throwable {
        
    	logger.debug("Get Social Media by Kid Id " + id);
        // Get Social Media By Kid
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getSocialMediaByKid(id);
        
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        // Create and Send Response
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
    
    /**
     * Delete All Social Media
     * @param socialMedia
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/social/add", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardianAndCanEditKidInformation(#socialMedia.kid) )")
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
    
    /**
     * Update Social Media To SOn
     * @param socialMedia
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/social/update", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && "
    		+ "@authorizationService.isYourGuardianAndCanEditKidInformation(#socialMedia.kid) )")
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
    
    /**
     * Save Social Media To Son
     * @param socialMedia
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/social/save", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() &&"
    		+ " @authorizationService.isYourGuardianAndCanEditKidInformation(#socialMedia.kid) )")
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
    
    /**
     * Get Social Media Statistics Activity
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/statistics/social-activity", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "SOCIAL_MEDIA_ACTIVITY_STATISTICS", nickname = "SOCIAL_MEDIA_ACTIVITY_STATISTICS", 
            notes = "Social Media Activity Statistics", response = SocialMediaActivityStatisticsDTO.class)
    public ResponseEntity<APIResponse<SocialMediaActivityStatisticsDTO>> getSocialMediaStatisticsActivity(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@KidShouldExists(message = "{son.should.be.exists}")
    						@PathVariable String id,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
            	@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        logger.debug("Get Social Media Activity Statistics for Son -> " + id );
        
        final SocialMediaActivityStatisticsDTO socialMediaActivityStatistics = 
        		statisticsService.getSocialMediaActivityStatistics(id, from);
       
        if(socialMediaActivityStatistics.getData().isEmpty())
        	throw new NoSocialMediaActivityFoundForThisPeriodException(from);
        
        // Create and send response
        return ApiHelper.<SocialMediaActivityStatisticsDTO>createAndSendResponse(ChildrenResponseCode.SOCIAL_MEDIA_ACTIVITY_STATISTICS, 
				HttpStatus.OK, socialMediaActivityStatistics);  
    }
    
    /**
     * Get Sentiment Analysis Statistics
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/statistics/sentiment-analysis", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "SENTIMENT_ANALYSIS_STATISTICS", nickname = "SENTIMENT_ANALYSIS_STATISTICS", 
            notes = "Sentiment Analysis Statistics", response = SentimentAnalysisStatisticsDTO.class)
    public ResponseEntity<APIResponse<SentimentAnalysisStatisticsDTO>> getSentimentAnalysisStatistics(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@KidShouldExists(message = "{son.should.be.exists}")
    					@PathVariable String id,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
        		@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        logger.debug("Get Sentiment Analysis Statistics for -> " + id );
        
        SentimentAnalysisStatisticsDTO sentimentAnalysisStatistics = statisticsService.getSentimentAnalysisStatistics(id, from);
      
        if(sentimentAnalysisStatistics.getData().isEmpty())
        	throw new NoSentimentAnalysisStatisticsForThisPeriodException(from);
        // Create and send response
        return ApiHelper.<SentimentAnalysisStatisticsDTO>createAndSendResponse(ChildrenResponseCode.SENTIMENT_ANALYSIS_STATISTICS, 
				HttpStatus.OK, sentimentAnalysisStatistics);  
    }
    
    /**
     * Get Communities Statistics
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/statistics/communities", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "COMMUNITIES_STATISTICS", nickname = "COMMUNITIES_STATISTICS", 
            notes = "Communities Statistics", response = CommunitiesStatisticsDTO.class)
    public ResponseEntity<APIResponse<CommunitiesStatisticsDTO>> getCommunitiesStatistics(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@KidShouldExists(message = "{son.should.be.exists}")
    					@PathVariable String id,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
    			@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
        logger.debug("Get Communities Statistics for -> " + id);
        
        CommunitiesStatisticsDTO communitiesStatistics = statisticsService.getCommunitiesStatistics(id, from);
        
        if(communitiesStatistics.getData().isEmpty())
        	throw new NoCommunityStatisticsForThisPeriodException(from);
        // Create and send response
        return ApiHelper.<CommunitiesStatisticsDTO>createAndSendResponse(ChildrenResponseCode.COMMUNITIES_STATISTICS, 
				HttpStatus.OK, communitiesStatistics);  
    }
    
    /**
     * Get Four Dimensions Statistics
     * @param id
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/statistics/dimensions", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "FOUR_DIMENSIONS_STATISTICS", nickname = "FOUR_DIMENSIONS_STATISTICS", 
            notes = "Four Dimensions Statistics", response = CommunitiesStatisticsDTO.class)
    public ResponseEntity<APIResponse<DimensionsStatisticsDTO>> getFourDimensionsStatistics(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
            		@KidShouldExists(message = "{son.should.be.exists}")
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
    
   
   
    /**
     * Save All Social Media To Son
     * @param id
     * @param socialMedias
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/social/save/all", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardianAndCanEditKidInformation(#id) )")
    @ApiOperation(value = "SAVE_ALL_SOCIAL_MEDIA", nickname = "SAVE_ALL_SOCIAL_MEDIA", notes = "Save Social Media",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> saveAllSocialMediaToSon(
    		@ApiParam(name = "id", value = "Kid Identifier", required = true)
        	@Valid @ValidObjectId(message = "{son.id.notvalid}")
         		@PathVariable String id,
            @ApiParam(value = "socialMedias", required = true) 
				@Validated(ICommonSequence.class) 
    		@RequestBody ValidList<SaveSocialMediaDTO> socialMedias) throws Throwable {
    	
    	// Save Social Media information
    	Iterable<SocialMediaDTO> socialMediaEntitiesSaved = socialMediaService.save(socialMedias.getList(), id);
    	
    	if(Iterables.size(socialMediaEntitiesSaved) == 0)
    		throw new SocialMediaNotFoundException();
    	
    	// Create and send response
    	return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.ALL_SOCIAL_MEDIA_SAVED, 
				HttpStatus.OK, addLinksToSocialMedia(socialMediaEntitiesSaved));    
    }
    
    /**
     * Delete All Social Media
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/social/delete/all", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardianAndCanEditKidInformation(#id) )")
    @ApiOperation(value = "DELETE_ALL_SOCIAL_MEDIA", nickname = "DELETE_ALL_SOCIAL_MEDIA", 
    	notes = "Delete all social media of kid", response = List.class)
    //@OnlyAccessForAdminOrGuardianOfTheKid(son = "#id")
    public ResponseEntity<APIResponse<Long>> deleteAllSocialMedia(
    		@ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
        Long socialMediaEntitiesDeleted = socialMediaService.deleteSocialMediaByKid(id);
        
        return ApiHelper.<Long>createAndSendResponse(
                SocialMediaResponseCode.ALL_USER_SOCIAL_MEDIA_DELETED, HttpStatus.OK,
                socialMediaEntitiesDeleted);
    }
    
    
    /**
     * Delete Social Media
     * @param kid
     * @param social
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/social/delete/{social}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardianAndCanEditKidInformation(#kid) )")
    @ApiOperation(value = "DELETE_SOCIAL_MEDIA", nickname = "DELETE_SOCIAL_MEDIA", 
    	notes = "Delete a single social media", response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<String>> deleteSocialMedia(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String kid,
            @ApiParam(name = "social", value = "Social Media Identifier", required = true)
            	@Validated(ICommonSequence.class) 
    					@ValidObjectId(message = "{social.id.notvalid}")
            			@SocialMediaShouldExists(message = "{social.not.exists}")
             				@PathVariable String social) throws Throwable {
        
        Boolean deleted = socialMediaService.deleteSocialMediaById(social);
        
        return deleted ? ApiHelper.<String>createAndSendResponse(
                SocialMediaResponseCode.USER_SOCIAL_MEDIA_DELETED, HttpStatus.OK, this.messageSourceResolver.resolver("social.media.deleted")) :
                	ApiHelper.<String>createAndSendResponse(
                            SocialMediaResponseCode.USER_SOCIAL_MEDIA_NOT_DELETED, HttpStatus.NOT_FOUND, this.messageSourceResolver.resolver("social.media.not.deleted"));
    }
    
   /**
    * Get Invalid Social Media By Kid Id
    * @param id
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/{id}/social/invalid", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_INVALID_SOCIAL_MEDIA_BY_KID", nickname = "GET_INVALID_SOCIAL_MEDIA_BY_KID",
    	notes = "Get Social Madia By Kid with invalid token", response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getInvalidSocialMediaByKidId(
    		@ApiParam(name = "id", value = "Kid Identifier", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        logger.debug("Get Invalid Social  Media by Kid Id " + id);
        
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getInvalidSocialMediaById(id);
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.INVALID_SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
    
    /**
     * Get Valid Social Media By Kid ID
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/social/valid", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_VALID_SOCIAL_MEDIA_BY_KID", nickname = "GET_VALID_SOCIAL_MEDIA_BY_KID", 
    	notes = "Get Social Madia By Kid with valid token",
            response = SocialMediaDTO.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> getValidSocialMediaByKidId(
    		@ApiParam(name = "id", value = "Kid identifier", required = true)
    			@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
       
    	logger.debug("Get Valid Social  Media by kid Id " + id);
        
        Iterable<SocialMediaDTO> socialMedia = socialMediaService.getValidSocialMediaById(id);
        if(Iterables.size(socialMedia) == 0)
        	throw new SocialMediaNotFoundException();
        return ApiHelper.<Iterable<SocialMediaDTO>>createAndSendResponse(SocialMediaResponseCode.VALID_SOCIAL_MEDIA_BY_CHILD, 
        		HttpStatus.OK, addLinksToSocialMedia(socialMedia));
    }
    
    /**
     * Ger Alerts by kid Id
     * @param id
     * @param count
     * @param from
     * @param levels
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_ALERTS_BY_KID", nickname = "GET_ALERTS_BY_KID", 
    	notes = "Get Alerts By KId Id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getAlertsByKidId(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id,
             @ApiParam(name = "count", value = "Number of alerts", required = false)
    				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from,
			@ApiParam(name = "levels", value = "Alert Levels", required = false)	
				@RequestParam(name="levels" , required=false)
            	AlertLevelEnum[] levels) throws Throwable {
        
    	logger.debug("Get Alerts by Kid with id: " + id);
    	logger.debug("Count -> " + count);
    	logger.debug("From -> " + from);
    	if(levels != null && levels.length > 0)
    		logger.debug("Levels -> " + levels.toString());
        
        Iterable<AlertDTO> alerts = alertService.findKidAlerts(new ObjectId(id), count, from, levels);
        
        if(Iterables.size(alerts) == 0)
            throw new NoAlertsByKidFoundException();

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(ChildrenResponseCode.ALERTS_BY_KID, 
        		HttpStatus.OK, alerts);
        
    }
    
    /**
     * Get Warning Alerts By Kid Id
     * @param id
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/warning", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_WARNING_ALERTS_BY_KID", nickname = "GET_WARNING_ALERTS_BY_KID", 
    	notes = "Get Warning Alerts By Kid Id", response = AlertDTO.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getWarningAlertsByKidId(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id,
             @ApiParam(name = "count", value = "Number of alerts", required = false)
    				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
    	logger.debug("Get WArning Alerts by Kid with id: " + id);
    	logger.debug("Count -> " + count);
    	logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService.findKidWarningAlerts(new ObjectId(id), count, from);
        
        if(Iterables.size(alerts) == 0)
            throw new NoAlertsByKidFoundException();

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(ChildrenResponseCode.WARNING_ALERTS_BY_KID, 
        		HttpStatus.OK, alerts);
        
    }
    
    /**
     * Get Information Alerts By Son Id
     * @param id
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/info", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_INFO_ALERTS_BY_KID", nickname = "GET_INFO_ALERTS_BY_KID",
    notes = "Get Information Alerts By Kid Id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getInformationAlertsByKidId(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id,
             @ApiParam(name = "count", value = "Number of alerts", required = false)
    				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
    	logger.debug("Get Information Alerts by Kid with id: " + id);
    	logger.debug("Count -> " + count);
    	logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService.findInformationKidAlerts(new ObjectId(id), count, from);
        
        if(Iterables.size(alerts) == 0)
            throw new NoAlertsByKidFoundException();

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(ChildrenResponseCode.INFO_ALERTS_BY_KID, 
        		HttpStatus.OK, alerts);
        
    }
    
    /**
     * Get Danger Alerts By Son Id
     * @param id
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/danger", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_DANGER_ALERTS_BY_KID", nickname = "GET_DANGER_ALERTS_BY_KID",
    notes = "Get Danger Alerts By Kid Id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getDangerAlertsByKidId(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id,
             @ApiParam(name = "count", value = "Number of alerts", required = false)
    				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
    	logger.debug("Get Danger Alerts by Kid with id: " + id);
    	logger.debug("Count -> " + count);
    	logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService.findDangerKidAlerts(new ObjectId(id), count, from);
        
        if(Iterables.size(alerts) == 0)
            throw new NoAlertsByKidFoundException();

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(ChildrenResponseCode.DANGER_ALERTS_BY_KID, 
        		HttpStatus.OK, alerts);
        
    }
    
    
    /**
     * Get Success Alerts By Kid Id
     * @param id
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/success", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "GET_SUCCESS_ALERTS_BY_KID", nickname = "GET_SUCCESS_ALERTS_BY_KID",
    notes = "Get Success Alerts By Kid Id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getSuccessAlertsByKidId(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id,
             @ApiParam(name = "count", value = "Number of alerts", required = false)
    				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
            @ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from) throws Throwable {
        
    	logger.debug("Get Success Alerts by Kid with id: " + id);
    	logger.debug("Count -> " + count);
    	logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService.findSuccessKidAlerts(new ObjectId(id), count, from);
        
        if(Iterables.size(alerts) == 0)
            throw new NoAlertsByKidFoundException();

        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(ChildrenResponseCode.SUCCESS_ALERTS_BY_KID, 
        		HttpStatus.OK, alerts);
        
    }
    
    /**
     * Clear Child Warning Alerts
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/warning", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "CLEAR_CHILD_WARNING_ALERTS",
    	nickname = "CLEAR_CHILD_WARNING_ALERTS", notes = "Clear Child Warning Alerts",
            response = Long.class)
    public ResponseEntity<APIResponse<String>> clearChildWarningAlerts(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
    	logger.debug("Clear alerts of son with id: " + id);
        
    	// Clear Kid Alerts By Level
        Long alertsDeleted = alertService.clearKidAlertsByLevel(new ObjectId(id), AlertLevelEnum.WARNING);
       
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_WARNING_ALERTS_CLEANED, 
        		HttpStatus.OK, alertsDeleted.toString());
        
    }
    
    /**
     * Clear Child Info Alerts
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/info", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "CLEAR_CHILD_INFO_ALERTS", nickname = "CLEAR_CHILD_INFO_ALERTS",
    notes = "Clear INfo Child Alerts",
            response = Long.class)
    public ResponseEntity<APIResponse<String>> clearChildInfoAlerts(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
    	logger.debug("Clear Info alerts of kid with id: " + id);
        
        Long alertsDeleted = alertService.clearKidAlertsByLevel(new ObjectId(id), AlertLevelEnum.INFO);
       
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_INFO_ALERTS_CLEANED, 
        		HttpStatus.OK, alertsDeleted.toString());
        
    }
    
    /**
     * Clear Child Danger Alerts
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/danger", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "CLEAR_CHILD_DANGER_ALERTS", nickname = "CLEAR_CHILD_DANGER_ALERTS",
    notes = "Clear INfo Child Alerts",
            response = Long.class)
    public ResponseEntity<APIResponse<String>> clearChildDangerAlerts(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
    	logger.debug("Clear Danger alerts of kid with id: " + id);
        
        Long alertsDeleted = alertService.clearKidAlertsByLevel(new ObjectId(id), AlertLevelEnum.DANGER);
       
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_DANGER_ALERTS_CLEANED, 
        		HttpStatus.OK, alertsDeleted.toString());
        
    }
    
    /**
     * Clear Child Success Alerts
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts/success", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "CLEAR_CHILD_SUCCESS_ALERTS", nickname = "CLEAR_CHILD_SUCCESS_ALERTS",
    notes = "Clear Success Child Alerts",
            response = Long.class)
    public ResponseEntity<APIResponse<String>> clearChildSuccessAlerts(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
    	logger.debug("Clear Success alerts of kid with id: " + id);
        
        Long alertsDeleted = alertService.clearKidAlertsByLevel(new ObjectId(id), AlertLevelEnum.SUCCESS);
       
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_SUCCESS_ALERTS_CLEANED, 
        		HttpStatus.OK, alertsDeleted.toString());
        
    }
    
    /**
     * Clear Child Alerts
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/alerts", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#id) )")
    @ApiOperation(value = "CLEAR_CHILD_ALERTS", nickname = "CLEAR_CHILD_ALERTS", notes = "Clear Child Alerts",
            response = Long.class)
    public ResponseEntity<APIResponse<String>> clearChildAlerts(
            @ApiParam(name = "id", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String id) throws Throwable {
        
    	logger.debug("Clear alerts of kid with id: " + id);
        
        Long alertsDeleted = alertService.clearKidAlerts(new ObjectId(id));
       
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.CHILD_ALERTS_CLEANED, 
        		HttpStatus.OK, alertsDeleted.toString());
        
    }
    
    /**
     * Get Alert By Id
     * @param kid
     * @param alert
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/alerts/{alert}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_ALERT_BY_ID", nickname = "GET_ALERT_BY_ID", notes = "Get alert by id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<AlertDTO>> getAlertById(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String kid,
             @ApiParam(name = "alert", value = "Alert Identfier", required = true)
            	@Valid @ValidObjectId(message = "{alert.id.notvalid}")
             		@PathVariable String alert) throws Throwable {
        
        
        return Optional.ofNullable(alertService.findById(new ObjectId(alert)))
        		.map(alertDTO -> ApiHelper.<AlertDTO>createAndSendResponse(ChildrenResponseCode.GET_ALERT_BY_ID, 
        				HttpStatus.OK, alertDTO))
        		.orElseThrow(() -> { throw new AlertNotFoundException(); }); 
        
    }
    
    /**
     * Delete Alert By Id
     * @param kid
     * @param alert
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/alerts/{alert}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_ALERT_BY_ID", nickname = "DELETE_ALERT_BY_ID", notes = "Delete alert by id",
            response = AlertDTO.class)
    public ResponseEntity<APIResponse<String>> deleteAlertById(
            @ApiParam(name = "kid", value = "Kid Identfier", required = true)
            	@Valid @ValidObjectId(message = "{son.id.notvalid}")
             		@PathVariable String kid,
             @ApiParam(name = "alert", value = "Alert Identifier", required = true)
            	@Valid @ValidObjectId(message = "{alert.id.notvalid}")
             		@PathVariable String alert) throws Throwable {
        
        logger.debug("Delete Alert by id: " + alert);
        
        alertService.deleteById(new ObjectId(alert));
        
        return ApiHelper.<String>createAndSendResponse(
                ChildrenResponseCode.ALERT_BY_ID_DELETED, HttpStatus.OK, messageSourceResolver.resolver("alert.deleted"));
        
    }
    
   
    /**
     * Get All Terminals
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_ALL_TERMINAL", nickname = "GET_ALL_TERMINAL", notes = "Get all Terminals", 
    response = List.class)
    public ResponseEntity<APIResponse<Iterable<TerminalDTO>>> getAllTerminals(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.id.notvalid}")
         		@PathVariable String kid) 
    		throws Throwable {
        
    	logger.debug("Get all Terminals");
    	
    	final Iterable<TerminalDTO> terminalsList = terminalService.getTerminalsByKidId(kid);
        
    	if(Iterables.isEmpty(terminalsList))
        	throw new NoTerminalsFoundException();
    	
        return ApiHelper.<Iterable<TerminalDTO>>createAndSendResponse(ChildrenResponseCode.ALL_TERMINALS, 
            		HttpStatus.OK, terminalsList);
    }
    
    
    /**
     * Get Terminal Detail
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_TERMINAL_DETAIL", nickname = "GET_TERMINAL_DETAIL",
    	notes = "Get Terminal Detail", response = TerminalDetailDTO.class)
    public ResponseEntity<APIResponse<TerminalDetailDTO>> getTerminalDetail(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal	) 
    		throws Throwable {
        
    	logger.debug("Get Terminal Detail");
    	
    	// Get Terminal Detail
    	final TerminalDetailDTO terminalDetailDTO = Optional.ofNullable(terminalService.getTerminalDetail(
    			new ObjectId(kid), terminal))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    
    	
        return ApiHelper.<TerminalDetailDTO>createAndSendResponse(ChildrenResponseCode.TERMINAL_DETAIL, 
            		HttpStatus.OK, terminalDetailDTO);
    }
   
    
    /**
     * Save Terminal
     * @param terminal
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_TERMINAL", nickname = "SAVE_TERMINAL", 
    	notes = "Save Terminal",
            response = TerminalDTO.class)
    public ResponseEntity<APIResponse<TerminalDTO>> saveTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.id.notvalid}")
         		@PathVariable String kid,
            @ApiParam(value = "terminal", required = true) 
				@Validated(ICommonSequence.class) 
            		@RequestBody SaveTerminalDTO saveTerminalDTO) throws Throwable {
    		
        return Optional.ofNullable(terminalService.save(saveTerminalDTO))
        		.map(terminalDTO -> {
        			
        			// Save Alert
        			alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("terminal.saved.title"),
        	    			messageSourceResolver.resolver("terminal.saved.description", 
        	    					new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() } ), 
        	    			new ObjectId(kid), AlertCategoryEnum.TERMINALS);
        			
        			return terminalDTO;
        			
        		})
        		.map(terminalDTO -> ApiHelper.<TerminalDTO>createAndSendResponse(ChildrenResponseCode.TERMINAL_SAVED, 
        				HttpStatus.OK, terminalDTO))
        		.orElseThrow(() -> { throw new TerminalNotFoundException(); });        
    }
    
    
    /**
     * Delete Terminal By Id
     * @param kid
     * @param alert
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_TERMINAL_BY_ID", nickname = "DELETE_TERMINAL_BY_ID", notes = "Delete terminal by id",
            response = String.class)
    public ResponseEntity<APIResponse<String>> deleteTerminalById(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.not.exists}")
             		@PathVariable String kid,
             @ApiParam(name = "terminal", value = "Terminal Identity", required = true)
            	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
             		@PathVariable String terminal) throws Throwable {
        
        logger.debug("Delete Terminal by id: " + terminal);
        
        // Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        // Delete Apps installed by child id an terminal id
        terminalService.deleteAppsInstalledByKidIdAndTerminalId(new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()));
        
        // Delete terminal by id)
        terminalService.deleteById(new ObjectId(terminalDTO.getIdentity()));
       
        
        // Save Alert
    	alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("terminal.deleted.title"),
    			messageSourceResolver.resolver("terminal.deleted.description", 
    					new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() } ), 
    			new ObjectId(kid), AlertCategoryEnum.TERMINALS);
        
        // Create And Send Response
        return ApiHelper.<String>createAndSendResponse(
                ChildrenResponseCode.TERMINAL_BY_ID_DELETED, HttpStatus.OK, messageSourceResolver.resolver("terminal.deleted"));
        
    }
    
    
    /**
     * Get All apps installed in the terminal
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_ALL_APPS_INSTALLED_IN_THE_TERMINAL", nickname = "GET_ALL_APPS_INSTALLED_IN_THE_TERMINAL",
    notes = "Get all apps installed in the terminal", 
    response = List.class)
    public ResponseEntity<APIResponse<Iterable<AppInstalledDTO>>> getAllAppsInstalledInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
        	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         		@PathVariable String terminal) 
    		throws Throwable {
        
    	logger.debug("Get all apps installed in the terminal");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    
    	// Get App installed in the terminal
    	final Iterable<AppInstalledDTO> appInstalledTerminal =
    			terminalService.getAllAppsInstalledInTheTerminal(new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()));
        
    	if(Iterables.isEmpty(appInstalledTerminal))
        	throw new NoAppsInstalledFoundException();
    	
    	// Create And send response
        return ApiHelper.<Iterable<AppInstalledDTO>>createAndSendResponse(ChildrenResponseCode.ALL_APPS_INSTALLED_IN_THE_TERMINAL, 
            		HttpStatus.OK, appInstalledTerminal);
    }
    
    
    /**
     * Save App installed
     * @param id
     * @param socialMedias
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_APPS_INSTALLED_IN_THE_TERMINAL", nickname = "SAVE_APPS_INSTALLED_IN_THE_TERMINAL", 
    	notes = "Save Apps installed in the terminal",
            response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<AppInstalledDTO>>> saveAppsInstalledInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
            	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
             		@PathVariable String terminal,
            @ApiParam(value = "apps", required = true) 
				@Validated(ICommonSequence.class) 
    				@RequestBody ValidList<SaveAppInstalledDTO> appsInstalled) throws Throwable {
    	
    	logger.debug("Save Apps installed int the terminal");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Save App installed in the terminal
    	Iterable<AppInstalledDTO> appInstalledSaved = terminalService.save(appsInstalled.getList());
    	
    	// Save Alert
    	alertService.save(AlertLevelEnum.INFO, messageSourceResolver.resolver("apps.installed.terminal.title", 
    			new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() } ),
    			messageSourceResolver.resolver("apps.installed.terminal.description", 
    					new Object[] { Iterables.size(appInstalledSaved) }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
    	
    	// Create and send response
    	return ApiHelper.<Iterable<AppInstalledDTO>>createAndSendResponse(ChildrenResponseCode.APPS_INSTALLED_SAVED, 
				HttpStatus.OK, appInstalledSaved);    
    }
    
    /**
     * Save App Rules for apps in the terminal
     * @param kid
     * @param terminal
     * @return
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/rules", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_APP_RULES_FOR_APPS_IN_THE_TERMINAL", nickname = "SAVE_APP_RULES_FOR_APPS_IN_THE_TERMINAL", 
    	notes = "Save App Rules",
            response = Iterable.class)
    public ResponseEntity<APIResponse<String>> saveAppRules(
    	@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    		@Valid @KidShouldExists(message = "{son.not.exists}")
 				@PathVariable String kid,
 		@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
    		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     			@PathVariable String terminal,
     	@ApiParam(value = "apps", required = true) 
			@Validated(ICommonSequence.class) 
				@RequestBody ValidList<SaveAppRulesDTO> appRules) throws Throwable {
    	
    	logger.debug("Save App Rules in the terminal -> " + terminal);
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Save App Rules
    	terminalService.saveAppRules(appRules);
    	
    	// Save Alert
    	alertService.save(AlertLevelEnum.INFO, messageSourceResolver.resolver("apps.rules.saved.title"),
    			messageSourceResolver.resolver("apps.rules.saved.description", 
    					new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.APP_RULES_WERE_APPLIED, 
				HttpStatus.OK, messageSourceResolver.resolver("apps.rules.saved"));
    
    }
    
    /**
     * Delete All Apps installed
     * @param kid
     * @param alert
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_ALL_APPS_INSTALLED", nickname = "DELETE_ALL_APPS_INSTALLED", 
    	notes = "Delete all apps installed", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAllAppsIstalled(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.id.notvalid}")
             		@PathVariable String kid,
            @ApiParam(name = "terminal", value = "Terminal id", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable String terminal) throws Throwable {
        
        logger.debug("Delete all apps installed by child identity " + kid + " for terminal " + terminal);
        
        // Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        
        // Delete Apps installed
        terminalService.deleteAppsInstalledByKidIdAndTerminalId(new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()));
      
        // Save Alert
    	alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("apps.installed.all.deleted.title", 
    			new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() } ),
    			messageSourceResolver.resolver("apps.installed.all.deleted.description", 
    					new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.ALL_APPS_INSTALLED_DELETED, HttpStatus.OK, 
        		messageSourceResolver.resolver("all.apps.installed.deleted"));
        
    }
    
    
    /**
     * Delete App installed by id
     * @param kid
     * @param alert
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/{app}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_APP_INSTALLED_BY_ID", nickname = "DELETE_APP_INSTALLED_BY_ID", 
    	notes = "Delete App installed by id", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAppInstalledById(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.id.notvalid}")
             		@PathVariable String kid,
            @ApiParam(name = "terminal", value = "Terminal id", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable String terminal,
         	@ApiParam(name = "app", value = "App id", required = true)
    			@Valid @AppInstalledShouldExists(message = "{app.not.exists}")
     				@PathVariable String app) throws Throwable {
        
        logger.debug("Delete app installed by " + app);
        
        // Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Delete App installed by id
        terminalService.deleteAppInstalledById(new ObjectId(app));
      
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.APP_INSTALLED_DELETED, HttpStatus.OK, 
        		messageSourceResolver.resolver("app.installed.deleted"));
        
    }
    
    
    /**
     * Save a new app installed
     * @param kid
     * @param terminal
     * @param appInstalledDTO
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/add", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ADD_APP_INSTALLED", nickname = "ADD_APP_INSTALLED", 
    	notes = "Add app installed", response = String.class)
    public ResponseEntity<APIResponse<AppInstalledDTO>> addNewAppInstalled(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.id.notvalid}")
             		@PathVariable String kid,
            @ApiParam(name = "terminal", value = "Terminal id", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable String terminal,
         	@ApiParam(value = "app", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody SaveAppInstalledDTO appInstalledDTO) throws Throwable {
        
        logger.debug("Add a new app isntalled for terminal " + terminal);
        
        // Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Save App Installed
        final AppInstalledDTO appInstalled = terminalService.save(appInstalledDTO);
        
        // Save Alert
    	alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("apps.installed.added.title", 
    			new Object[] { appInstalled.getAppName() } ),
    			messageSourceResolver.resolver("apps.installed.added.description", 
    					new Object[] { appInstalled.getAppName(), terminalDTO.getModel(), terminalDTO.getDeviceName() }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
      
        // Create and send response
        return ApiHelper.<AppInstalledDTO>createAndSendResponse(ChildrenResponseCode.NEW_APP_INSTALLED_ADDED, HttpStatus.OK, appInstalled);
        
    }
    
    /**
     * Get All Scheduled Blocks
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_ALL_SCHEDULED_BLOCKS", nickname = "GET_ALL_SCHEDULED_BLOCKS",
    	notes = "Get all scheduled blocks", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<ScheduledBlockDTO>>> getAllScheduledBlocksConfigured(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid) 
    		throws Throwable {
        
    	logger.debug("Get All Scheduled Blocks");
    	
    	// Get Scheduled Block By Kid
    	final Iterable<ScheduledBlockDTO> scheduledBlocksList = 
    			scheduledBlockService.getScheduledBlockByChild(new ObjectId(kid));
    	
    	if(Iterables.isEmpty(scheduledBlocksList))
        	throw new NoScheduledBlockFoundException();
    	
        return ApiHelper.<Iterable<ScheduledBlockDTO>>createAndSendResponse(ChildrenResponseCode.ALL_SCHEDULED_BLOCKS, 
            		HttpStatus.OK, scheduledBlocksList);
    }
    
    /**
     * Get Scheduled Block by id
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks/{block}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_SCHEDULED_BLOCK_BY_ID", nickname = "GET_SCHEDULED_BLOCK_BY_ID",
    	notes = "Get Scheduled Block By Id", response = ScheduledBlockDTO.class)
    public ResponseEntity<APIResponse<ScheduledBlockDTO>> getScheduledBlockById(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
         	@ApiParam(name = "block", value = "Block id", required = true)
        	@Valid @ScheduledBlockShouldExists(message = "{scheduled.block.not.exists}")
         		@PathVariable String block) 
    		throws Throwable {
        
    	logger.debug("Get Scheduled Block By id -> " + block);
    	
    	// Get Scheduled Block By Id
    	final ScheduledBlockDTO scheduledBlockDTO =
    			scheduledBlockService.getScheduledBlockById(new ObjectId(block));
    
    	
        return ApiHelper.<ScheduledBlockDTO>createAndSendResponse(ChildrenResponseCode.SCHEDULED_BLOCK_DETAIL, 
            		HttpStatus.OK, scheduledBlockDTO);
    }
    
    /**
     * Save Scheduled Block
     * @param id
     * @param socialMedias
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_SCHEDULED_BLOCK", nickname = "SAVE_SCHEDULED_BLOCK", 
    	notes = "Save Scheduled Block",
            response = ScheduledBlockDTO.class)
    public ResponseEntity<APIResponse<ScheduledBlockDTO>> saveScheduledBlock(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
            @ApiParam(value = "scheduled_block", required = true) 
				@Validated(ICommonSequence.class) 
    				@RequestBody SaveScheduledBlockDTO saveScheduledBlockDTO) throws Throwable {
    	
    	logger.debug("Save Scheduled Block");
    	
    	// Save Scheduled Block
    	final ScheduledBlockDTO scheduledBlock = scheduledBlockService.save(saveScheduledBlockDTO);
    	    	
    	return ApiHelper.<ScheduledBlockDTO>createAndSendResponse(ChildrenResponseCode.SCHEDULED_BLOCK_SAVED, 
				HttpStatus.OK, scheduledBlock);    
    }
    
    
    /**
     * Save Scheduled Blocks status
     * @param id
     * @param socialMedias
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks/status", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_SCHEDULED_BLOCKS_STATUS", 
    	nickname = "SAVE_SCHEDULED_BLOCKS_STATUS", notes = "Save Scheduled Block Status",
            response = Iterable.class)
    public ResponseEntity<APIResponse<String>> saveScheduledBlocksStatus(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
         	@ApiParam(value = "scheduled_blocks", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody ValidList<SaveScheduledBlockStatusDTO> saveScheduledBlockStatus) throws Throwable {
    	
    	logger.debug("Save Scheduled Block Status DTO");
    	// Save Status
    	scheduledBlockService.saveStatus(saveScheduledBlockStatus);
    	    	
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.SCHEDULED_BLOCK_STATUS_SAVED, 
				HttpStatus.OK, messageSourceResolver.resolver("scheduled.block.status.saved"));    
    }
    
    /**
     * Delete All Scheduled Block
     * @param kid
     * @param alert
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_ALL_SCHEDULED_BLOCK", nickname = "DELETE_ALL_SCHEDULED_BLOCK", 
    	notes = "Delete all scheduled block", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAllScheduledBlock(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.id.notvalid}")
             		@PathVariable String kid) throws Throwable {
        
        logger.debug("Delete all scheduled block for kid " + kid);
        
        // Delete all scheduled block by child id
        scheduledBlockService.deleteByKidId(new ObjectId(kid));
      
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.ALL_SCHEDULED_BLOCK_DELETED, HttpStatus.OK, 
        		messageSourceResolver.resolver("all.scheduled.blocks.deleted"));
        
    }
    
    
    /**
     * Delete Scheduled block by id
     * @param kid
     * @param alert
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks/{block}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_SCHEDULED_BLOCK_BY_ID", nickname = "DELETE_SCHEDULED_BLOCK_BY_ID", 
    	notes = "Delete App installed by id", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteScheduledBlockById(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.id.notvalid}")
             		@PathVariable String kid,
         	@ApiParam(name = "block", value = "Block Id", required = true)
    			@Valid @ScheduledBlockShouldExists(message = "{block.not.exists}")
     				@PathVariable String block) throws Throwable {
        
        logger.debug("Delete Scheduled Block by id ");
        
        // Delete By Child ID
        scheduledBlockService.delete(new ObjectId(block));
      
        return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.SCHEDULED_BLOCK_DELETED, HttpStatus.OK, 
        		messageSourceResolver.resolver("scheduled.block.deleted"));
        
    }
    
    /**
     * Upload Scheduled Block Image
     * @param id
     * @param profileImage
     * @param selfParent
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks/{block}/image", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "UPLOAD_SCHEDULED_BLOCK_IMAGE", nickname = "UPLOAD_SCHEDULED_BLOCK_IMAGE",
    notes = "Upload Scheduled Block Image")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message= "Scheduled Block Image", response = ImageDTO.class),
    	@ApiResponse(code = 500, message= "Upload Failed")
    })
    public ResponseEntity<APIResponse<ImageDTO>> uploadScheduledBlockImage(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         	@Valid @KidShouldExists(message = "{son.should.be.exists}")
          		@PathVariable String kid,
          	@ApiParam(name = "block", value = "Scheduled Block Identifier", required = true)
             	@Valid @ScheduledBlockShouldExists(message = "{scheduled.block.not.exists}")
              		@PathVariable String block,
            @RequestPart MultipartFile scheduledBlockImage,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
    	
    	// Create Request Upload File
    	final RequestUploadFile uploadScheduledBlockImage = new RequestUploadFile(scheduledBlockImage.getBytes(), 
    			scheduledBlockImage.getContentType() != null ? scheduledBlockImage.getContentType() :
    				MediaType.IMAGE_PNG_VALUE, scheduledBlockImage.getOriginalFilename());
    	
    	ImageDTO imageDTO = uploadFilesService.uploadScheduledBlockImage(new ObjectId(kid),
    			new ObjectId(block), uploadScheduledBlockImage);
    	
    	// Return Response
        return ApiHelper.<ImageDTO>createAndSendResponse(ChildrenResponseCode.SCHEDULED_BLOCK_IMAGE_UPLOADED, 
        		HttpStatus.OK, imageDTO);

    }
    
    /**
     * Download Scheduled Block Image
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/scheduled-blocks/{block}/image/{image}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DOWNLOAD_SCHEDULED_BLOCK_IMAGE", nickname = "DOWNLOAD_SCHEDULED_BLOCK_IMAGE",
    notes = "Download Scheduled Block Image")
    public ResponseEntity<byte[]> downloadScheduledBlockImage(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "block", value = "Scheduled Block Identifier", required = true)
             	@Valid @ScheduledBlockShouldExists(message = "{scheduled.block.not.exists}")
              		@PathVariable String block,
            @ApiParam(name = "image", value = "Image Identifier", required = true)
                  		@PathVariable String image
              		) throws Throwable {
        
        final UploadFileInfo uploadFileInfo = uploadFilesService.getImage(image);
        
        return ResponseEntity.ok()
        		.contentLength(uploadFileInfo.getSize())
        		.contentType( uploadFileInfo.getContentType() != null ?  
        				MediaType.parseMediaType(uploadFileInfo.getContentType()) :
        					MediaType.IMAGE_PNG)
        		.body(uploadFileInfo.getContent());
        
    }
    
    
    /**
     * 
     */
    @PostConstruct
    protected void init() {
        Assert.notNull(kidService, "Kid Service cannot be a null");
        Assert.notNull(commentService, "Comment Service cannot be a null");
        Assert.notNull(socialMediaService, "Social Media Service cannot be a null");
        Assert.notNull(uploadFilesService, "Upload Files Service cannot be a null");
 
    }
}