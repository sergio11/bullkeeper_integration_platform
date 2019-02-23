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
import sanchez.sanchez.sergio.bullkeeper.domain.service.IGeofenceService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IScheduledBlockService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ISocialMediaService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IStatisticsService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITerminalService;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppDisabledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppEnabledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppRulesListSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.AppRulesSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.NewAppInstalledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.apps.UninstallAppEvent;
import sanchez.sanchez.sergio.bullkeeper.events.funtime.DayScheduledSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.funtime.SaveFunTimeScheduledEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.AllGeofencesDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofenceAddedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofenceDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.geofences.GeofencesDeletedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.location.CurrentLocationUpdateEvent;
import sanchez.sanchez.sergio.bullkeeper.events.phonenumbers.AddPhoneNumberBlockedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.phonenumbers.DeleteAllPhoneNumberBlockedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.phonenumbers.DeletePhoneNumberBlockedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.request.KidRequestCreatedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.DeleteAllScheduledBlockEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.DeleteScheduledBlockEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.ScheduledBlockImageChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.ScheduledBlockSavedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.scheduledblock.ScheduledBlockStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalBedTimeStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalCameraStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalScreenStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.events.terminal.TerminalSettingsStatusChangedEvent;
import sanchez.sanchez.sergio.bullkeeper.exception.AlertNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.AppInstalledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.AppStatsNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CallDetailNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CallDetailsNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CommentsByKidNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.ContactNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.CurrentLocationException;
import sanchez.sanchez.sergio.bullkeeper.exception.FunTimeDayScheduledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.FunTimeScheduledNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.KidNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.KidRequestNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsByKidFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAppStatsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAppsInstalledFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoChildrenFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoCommunityStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoContactsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoDimensionsStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoGeofenceAlertsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoGeofenceFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoKidGuardianFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoKidRequestFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoPhoneNumberBlockedFound;
import sanchez.sanchez.sergio.bullkeeper.exception.NoScheduledBlockFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSentimentAnalysisStatisticsForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSmsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSocialMediaActivityFoundForThisPeriodException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoTerminalsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SingleSmsNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SocialMediaNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.TerminalNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.AppInstalledShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.CallDetailShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ContactShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.DayNameValidator;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GeofenceShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidRequestShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.PhoneNumberBlockedShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ScheduledBlockShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SmsShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SocialMediaShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AdultLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.BullyingLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.DrugsLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.FunTimeDaysEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ViolenceLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.util.ValidList;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddKidRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddPhoneNumberBlockedDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppRulesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveAppStatsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveCallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveDayScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveFunTimeScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveLocationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockStatusDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSmsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.TerminalHeartbeatDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppInstalledInTerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppRuleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AppStatsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommentDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CommunitiesStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DayScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DimensionsStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.FunTimeScheduledDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.LocationDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PhoneNumberBlockedDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SentimentAnalysisStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SmsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaActivityStatisticsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SocialMediaDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.TerminalDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ICommentHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.IKidHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.ISocialMediaHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.AppsResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.CallDetailResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ChildrenResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.CommentResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.ContactResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.GeofenceResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.SmsResponseCode;
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
		description = "Administration of the children/adolescents of the platform", 
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
    private final IGeofenceService geofenceService;
    
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
     * @param geofenceService
     */
    public ChildrenController(IKidService kidService, ICommentsService commentService, ISocialMediaService socialMediaService,
    		IUploadFilesService uploadFilesService, IAlertService alertService, IStatisticsService statisticsService,
    		final ITerminalService terminalService, 
    		final IScheduledBlockService scheduledBlockService,
    		final IGeofenceService geofenceService) {
        this.kidService = kidService;
        this.commentService = commentService;
        this.socialMediaService = socialMediaService;
        this.uploadFilesService = uploadFilesService;
        this.alertService = alertService;
        this.statisticsService = statisticsService;
        this.terminalService = terminalService;
        this.scheduledBlockService = scheduledBlockService;
        this.geofenceService = geofenceService;
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
    @ApiOperation(value = "GET_KID_BY_ID", nickname = "GET_KID_BY_ID", notes = "Get Kid By Id", 
    response = KidDTO.class)
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
    		response = String.class)
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
    		@ApiParam(name="guadians", value = "guardians", required = true) 
				@Validated(ICommonSequence.class)
    				@RequestBody final ValidList<SaveGuardianDTO> guardians) throws Throwable {
    	
    	logger.debug("Save Guardians -> " + guardians.size());
    	
    	// Save Guardians
    	final Iterable<KidGuardianDTO> kidGuardiansDTOs = 
    			kidService.save(guardians.getList(), new ObjectId(id));
    	
    	for(final KidGuardianDTO kidGuardian: kidGuardiansDTOs) {
    		
    		if(!kidGuardian.isConfirmed())
    			// Save Alert
    			alertService.save(AlertLevelEnum.INFO, messageSourceResolver.resolver("supervised.children.invitation.title"),
        			messageSourceResolver.resolver("supervised.children.invitation.description"), 
        			new ObjectId(kidGuardian.getKid().getIdentity()), 
        			new ObjectId(kidGuardian.getGuardian().getIdentity()),  AlertCategoryEnum.INFORMATION_KID);
    		
    	}
    	
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
     * Get Confirmed Guardians
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/guardians/confirmed", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && "
    		+ "@authorizationService.isYourGuardianAndCanEditKidInformation(#id) )")
    @ApiOperation(value = "GET_CONFIRMED_GUARDIANS_FOR_KID", nickname = "GET_CONFIRMED_GUARDIANS_FOR_KID", 
    	notes = "Get Confirmed Guardians for kid", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<KidGuardianDTO>>> getConfirmedGuardians(
    		@ApiParam(name= "id", value = "Kid identified", required = true)
    			@Valid @KidShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable String id) throws Throwable {
    	
    	logger.debug("Get Guardians for -> " + id);
    	
    	// Save Guardians
    	final Iterable<KidGuardianDTO> kidGuardiansDTOs = 
    			kidService.getConfirmedGuardians(new ObjectId(id));
    	
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
    	notes = "Get Social Madia By Kid", response = Iterable.class)
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
            @ApiParam(name="socialMedia", value = "socialMedia", required = true) 
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
            @ApiParam(name="socialMedia", value = "socialMedia", required = true) 
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
            @ApiParam(name="socialMedia", value = "socialMedia", required = true) 
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
            notes = "Four Dimensions Statistics", response = DimensionsStatisticsDTO.class)
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
            response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<SocialMediaDTO>>> saveAllSocialMediaToSon(
    		@ApiParam(name = "id", value = "Kid Identifier", required = true)
        	@Valid @ValidObjectId(message = "{son.id.notvalid}")
         		@PathVariable String id,
            @ApiParam(name="socialMedias", value = "Social Medias", required = true) 
				@Validated(ICommonSequence.class) 
    		@RequestBody ValidList<SaveSocialMediaDTO> socialMedias) throws Throwable {
    	
    	// Save Social Media information
    	Iterable<SocialMediaDTO> socialMediaEntitiesSaved = socialMediaService.save(socialMedias.getList(), id);
   
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
    	notes = "Delete all social media of kid", response = Long.class)
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
    	notes = "Delete a single social media", response = String.class)
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
    	notes = "Get Social Madia By Kid with invalid token", response = Iterable.class)
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
            response = Iterable.class)
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
            response = Iterable.class)
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
    	notes = "Get Warning Alerts By Kid Id", response = Iterable.class)
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
            response = Iterable.class)
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
            response = Iterable.class)
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
            response = Iterable.class)
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
            response = String.class)
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
    notes = "Clear Info Child Alerts",
            response = String.class)
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
    notes = "Clear Info Child Alerts",
            response = String.class)
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
            response = String.class)
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
            response = String.class)
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
            response = String.class)
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
    response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<TerminalDTO>>> getAllTerminals(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.id.notvalid}")
         		@PathVariable String kid) 
    		throws Throwable {
        
    	logger.debug("Get all Terminals");
    	
    	final Iterable<TerminalDTO> terminalsList = terminalService.getTerminalsByKidId(kid);
        
    	if(Iterables.isEmpty(terminalsList))
        	throw new NoTerminalsFoundException();
    	
    	// Create and send response
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
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
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
            @ApiParam(name="terminal", value = "Terminal to save", required = true) 
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
        terminalService.deleteApps(new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()));
        
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
     * Enable Bed Time
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/bedtime/enable", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ENABLE_BED_TIME_IN_THE_TERMINAL", nickname = "ENABLE_BED_TIME_IN_THE_TERMINAL",
    	notes = "Enable Bed Time in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> enableBedTimeInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Enable Bed Time
        terminalService.enableBedTimeInTheTerminal(new ObjectId(terminalDTO.getKid()),
        		new ObjectId(terminalDTO.getIdentity()));
        
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalBedTimeStatusChangedEvent(
    				this, kid, terminal, true));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.BED_TIME_ENABLED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("bed.time.enabled.successfully"));
    }
    
    /**
     * Disable Bed Time
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/bedtime/disable", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DISABLE_BED_TIME_IN_THE_TERMINAL", nickname = "DISABLE_BED_TIME_IN_THE_TERMINAL",
    	notes = "Disable Bed Time in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> disableBedTimeInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Disabled Bed Time
        terminalService.disableBedTimeInTheTerminal(new ObjectId(terminalDTO.getKid()), 
        		new ObjectId(terminalDTO.getIdentity()));
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalBedTimeStatusChangedEvent(
    				this, kid, terminal, false));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.BED_TIME_DISABLED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("bed.time.disabled.successfully"));
    }
    
    /**
     * Enable Settings in the terminal
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/settings/enable", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ENABLE_SETTINGS_IN_THE_TERMINAL", 
    nickname = "ENABLE_SETTINGS_IN_THE_TERMINAL",
    	notes = "Enable Settings in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> enableSettingsInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Enable Settings
        terminalService.enableSettingsInTheTerminal(new ObjectId(terminalDTO.getKid()),
        		new ObjectId(terminalDTO.getIdentity()));
  
     // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalSettingsStatusChangedEvent(
    				this, kid, terminal, true));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.SETTINGS_ENABLE_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("settings.enabled.successfully"));
    }
    
    /**
     * Disable Bed Time
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/settings/disable", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DISABLE_SETTINGS_IN_THE_TERMINAL", nickname = "DISABLE_SETTINGS_IN_THE_TERMINAL",
    	notes = "Disable Settings in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> disableSettingsInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
       
        // Disabled Settings
        terminalService.disableSettingsInTheTerminal(new ObjectId(terminalDTO.getKid()), 
        		new ObjectId(terminalDTO.getIdentity()));
      
     // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalSettingsStatusChangedEvent(
    				this, kid, terminal, false));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.SETTINGS_DISABLE_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("settings.disabled.successfully"));
    }
    
    /**
     * Lock Screen
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/screen/lock", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "LOCK_SCREEN_IN_THE_TERMINAL", nickname = "LOCK_SCREEN_IN_THE_TERMINAL",
    	notes = "Lock Screen in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> lockScreenInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Lock Screen
        terminalService.lockScreenInTheTerminal(new ObjectId(terminalDTO.getKid()), 
        		new ObjectId(terminalDTO.getIdentity()));
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalScreenStatusChangedEvent(
    				this, kid, terminal, false));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.LOCK_SCREEN_ENABLED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("lock.screen.enabled.successfully"));
    }
    
    /**
     * Unlock Screen
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/screen/unlock", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "UNLOCK_SCREEN_IN_THE_TERMINAL", 
    		nickname = "UNLOCK_SCREEN_IN_THE_TERMINAL",
    		notes = "Unlock Screen in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> unLockScreenInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Unlock screen
        terminalService.unlockScreenInTheTerminal(new ObjectId(terminalDTO.getKid()), 
        		new ObjectId(terminalDTO.getIdentity()));
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalScreenStatusChangedEvent(
    				this, kid, terminal, true));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.LOCK_SCREEN_DISABLED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("lock.screen.disabled.successfully"));
    }
    
    /**
     * Lock Camera
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/camera/lock", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "LOCK_CAMERA_IN_THE_TERMINAL", nickname = "LOCK_CAMERA_IN_THE_TERMINAL",
    	notes = "Lock Camera in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> lockCameraInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Lock Camera
        terminalService.lockCameraInTheTerminal(new ObjectId(terminalDTO.getKid()), 
        		new ObjectId(terminalDTO.getIdentity()));
       
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalCameraStatusChangedEvent(
    				this, kid, terminal, false));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.LOCK_CAMERA_ENABLED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("lock.camera.enabled.successfully"));
    }
    
    /**
     * Unlock Camera
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/camera/unlock", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "UNLOCK_CAMERA_IN_THE_TERMINAL", 
    		nickname = "UNLOCK_CAMERA_IN_THE_TERMINAL",
    		notes = "Unlock Camera in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> unLockCameraInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Unlock Camera
        terminalService.unlockCameraInTheTerminal(new ObjectId(terminalDTO.getKid()), new ObjectId(terminalDTO.getIdentity()));
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new TerminalCameraStatusChangedEvent(
    				this, kid, terminal, true));
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		ChildrenResponseCode.LOCK_CAMERA_DISABLED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("lock.camera.disabled.successfully"));
    }
    
  
    
    /**
     * Get SMS from Terminal
     * @param pageable
     * @param pagedAssembler
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/sms", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_SMS_FROM_TERMINAL", nickname = "GET_SMS_FROM_TERMINAL",
    	notes = "Get Sms From Terminal", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<SmsDTO>>> getSmsFromTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
        
    	logger.debug("Get Sms From Terminal");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        // Get SMS from terminal
        final Iterable<SmsDTO> smsDTOs = terminalService.getSmsList(
        		new ObjectId(terminalDTO.getKid()), new ObjectId(terminalDTO.getIdentity()));
        
        // Check results
        if(Iterables.size(smsDTOs) == 0)
        	throw new NoSmsFoundException();
    	
        // Create and send response
        return ApiHelper.<Iterable<SmsDTO>>createAndSendResponse(SmsResponseCode.ALL_SMS_FROM_TERMINAL, 
            		HttpStatus.OK, smsDTOs);
    	
    }
    
  
    
    /**
     * Get SMS Detail
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/sms/{sms}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_SMS_DETAIL", nickname = "GET_SMS_DETAIL",
    	notes = "Get Sms From Terminal", response = SmsDTO.class)
    public ResponseEntity<APIResponse<SmsDTO>> getSmsDetail(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal,
     		@ApiParam(name = "sms", value = "Sms Identifier", required = true)
				@Valid @SmsShouldExists(message = "{sms.id.notvalid}")
 					@PathVariable String sms) 
    		throws Throwable {
        
    	logger.debug("Get Sms From Terminal");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        
        // Get Sms Detail
        final SmsDTO smsDto = Optional.ofNullable(terminalService.getSmsDetail(
        		new ObjectId(terminalDTO.getKid()),
        		new ObjectId(terminalDTO.getIdentity()), 
        		new ObjectId(sms)))
    			 .orElseThrow(() -> { throw new SingleSmsNotFoundException(); });
    	
        // Create and send response
        return ApiHelper.<SmsDTO>createAndSendResponse(SmsResponseCode.SINGLE_SMS_DETAIL, 
            		HttpStatus.OK, smsDto);
    	
    }
    
    
    /**
     * Delete SMS from Terminal
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/sms", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_SMS_FROM_TERMINAL", nickname = "DELETE_SMS_FROM_TERMINAL",
    	notes = "Delete Sms From Terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteSmsFromTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
        
    	logger.debug("Delete Sms From Terminal");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        // Delete All Sms
        terminalService.deleteAllSms(new ObjectId(terminalDTO.getKid()),
        		new ObjectId(terminalDTO.getIdentity()));
    	
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(SmsResponseCode.ALL_SMS_FROM_TERMINAL_DELETED, 
            		HttpStatus.OK, messageSourceResolver.resolver("all.sms.deleted"));
    	
    }
    
    /**
     * Delete SMS from Terminal
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/sms/delete", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_SMS_FROM_TERMINAL", nickname = "DELETE_SMS_FROM_TERMINAL",
    	notes = "Delete Sms From Terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteSmsFromTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal,
     		@ApiParam(name="ids", value = "SMS ids", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody ValidList<ObjectId> smsList) 
    		throws Throwable {
        
    	logger.debug("Delete Sms From Terminal");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        // Delete Sms
        terminalService.deleteSms(new ObjectId(terminalDTO.getKid()),
        		new ObjectId(terminalDTO.getIdentity()), smsList);
    	
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(SmsResponseCode.SMS_FROM_TERMINAL_DELETED, 
            		HttpStatus.OK, messageSourceResolver.resolver("sms.deleted"));
    	
    }
    
    /**
     * Delete SMS
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/sms/{sms}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_SINGLE_SMS", nickname = "DELETE_SINGLE_SMS",
    	notes = "Delete Single SMS", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteSingleSms(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal,
     		@ApiParam(name = "sms", value = "Sms Identifier", required = true)
				@Valid @SmsShouldExists(message = "{sms.id.notvalid}")
 					@PathVariable String sms) 
    		throws Throwable {
        
    	logger.debug("Delete Single SMS");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Delete SMS
        terminalService.deleteSms(new ObjectId(terminalDTO.getKid()),
        		new ObjectId(terminalDTO.getIdentity()),
        		new ObjectId(sms));
    	
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(SmsResponseCode.SINGLE_SMS_DELETED, 
            		HttpStatus.OK, messageSourceResolver.resolver("single.sms.deleted"));
    }
    
    /**
     * Save SMS
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/sms", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
    		+ " && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_SMS_FROM_TERMINAL", 
    	nickname = "SAVE_SMS_FROM_TERMINAL", 
    			notes = "Save Sms from terminal",
    				response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<SmsDTO>>> saveSmsFromTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
            	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
             		@PathVariable String terminal,
            @ApiParam(name="sms", value = "sms list", required = true) 
 				@Validated(ICommonSequence.class) 
    				@RequestBody ValidList<SaveSmsDTO> sms) throws Throwable {
    	
    	logger.debug("Save SMS from terminal");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        // Save Sms
        final Iterable<SmsDTO> smsSavedList = terminalService.saveSms(sms);
    	
        // Create and send response
        return ApiHelper.<Iterable<SmsDTO>>createAndSendResponse(SmsResponseCode.SMS_SAVED_SUCCESSFULLY, 
           		HttpStatus.OK, smsSavedList);
    	
    }
    
    /**
     * Save SMS
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/sms/add", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
    		+ " && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ADD_SMS_FROM_TERMINAL", 
    	nickname = "ADD_SMS_FROM_TERMINAL", 
    			notes = "Add Sms from terminal",
    				response = Iterable.class)
    public ResponseEntity<APIResponse<SmsDTO>> addSmsFromTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
            	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
             		@PathVariable String terminal,
            @ApiParam(name="sms", value = "SMS to save", required = true) 
 				@Validated(ICommonSequence.class) 
    				@RequestBody SaveSmsDTO sms) throws Throwable {
    	
    	logger.debug("Save SMS from terminal");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        // Save SMS
        final SmsDTO smsDTO = terminalService.saveSms(sms);
    	
    	// Create and send response
        return ApiHelper.<SmsDTO>createAndSendResponse(SmsResponseCode.SMS_SAVED_SUCCESSFULLY, 
           		HttpStatus.OK, smsDTO);
    	
    }
    
    /**
     * Get Calls
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/calls", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_CALLS_FROM_TERMINAL", nickname = "GET_CALLS_FROM_TERMINAL",
    	notes = "Get Calls From Terminal", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<CallDetailDTO>>> getCallsFromTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal) 
    		throws Throwable {
    	
    	logger.debug("Get Calls from terminal");
    	
    	// Get Terminal
        final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
        
        // Get call details
        final Iterable<CallDetailDTO> callDetailDTOs = terminalService.getDetailOfCalls(
        		new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()));
        
        // Check results
        if(Iterables.size(callDetailDTOs) == 0)
        	throw new CallDetailsNotFoundException();
    	
        // Create and send response
        return ApiHelper.<Iterable<CallDetailDTO>>createAndSendResponse(CallDetailResponseCode.ALL_CALL_DETAILS_FROM_TERMINAL, 
            		HttpStatus.OK, callDetailDTOs);
    }
    
    
    /**
     * Get Call Detail
     * @param kid
     * @param terminal
     * @param call
     * @return
     * @throws Throwable
     */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/calls/{call}", method = RequestMethod.GET)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
   		+ "&& @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "GET_CALL_DETAIL", nickname = "GET_CALL_DETAIL",
   	notes = "Get Call Detail", response = CallDetailDTO.class)
   public ResponseEntity<APIResponse<CallDetailDTO>> getCallDetail(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       		@Valid @KidShouldExists(message = "{son.id.notvalid}")
        			@PathVariable String kid,
        	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
   			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
    				@PathVariable String terminal,
    		@ApiParam(name = "call", value = "Call Identifier", required = true)
				@Valid @CallDetailShouldExists(message = "{call.id.notvalid}")
					@PathVariable String call) 
   		throws Throwable {
	   
	   logger.debug("Get Call Detail");
	   
	   // Get Terminal
       final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
   			new ObjectId(terminal), new ObjectId(kid)))
   			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
       
   	
       // Get Call Detail
       final CallDetailDTO calDetailDTO = Optional.ofNullable(terminalService.getDetailOfTheCall(
   			new ObjectId(terminalDTO.getKid()), 
   			new ObjectId(terminalDTO.getIdentity()), 
   			new ObjectId(call)))
   			 .orElseThrow(() -> { throw new CallDetailNotFoundException(); });
	   
       
       // Create and send response
       return ApiHelper.<CallDetailDTO>createAndSendResponse(CallDetailResponseCode.SINGLE_CALL_DETAIL, 
           		HttpStatus.OK, calDetailDTO);
   }
   
   
   /**
    * Delete all calls detail from Terminal
    * @return
    * @throws Throwable
    */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/calls", method = RequestMethod.DELETE)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
   		+ "&& @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "DELETE_CALL_DETAILS_FROM_TERMINAL", nickname = "DELETE_CALL_DETAILS_FROM_TERMINAL",
   	notes = "Delete Call Details From Terminal", response = String.class)
   public ResponseEntity<APIResponse<String>> deleteCallDetailsFromTerminal(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       		@Valid @KidShouldExists(message = "{son.id.notvalid}")
        			@PathVariable String kid,
        	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
   			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
    				@PathVariable String terminal) 
   		throws Throwable {
       
	   logger.debug("Delete all call details from terminal");
	   
	   // Get Terminal
       final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
   			new ObjectId(terminal), new ObjectId(kid)))
   			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
       
       // Delete all call details
       terminalService.deleteAllCallDetails(
    		   new ObjectId(terminalDTO.getKid()), 
    		   new ObjectId(terminalDTO.getIdentity()));
   	
	   // Create and send response
       return ApiHelper.<String>createAndSendResponse(CallDetailResponseCode.ALL_CALL_DETAILS_DELETED, 
          		HttpStatus.OK, messageSourceResolver.resolver("all.call.details.deleted"));
	   
   }
   
   /**
    * Delete calls detail from Terminal
    * @return
    * @throws Throwable
    */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/calls/delete", method = RequestMethod.POST)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
   		+ "&& @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "DELETE_CALL_DETAILS_FROM_TERMINAL", nickname = "DELETE_CALL_DETAILS_FROM_TERMINAL",
   	notes = "Delete Call Details From Terminal", response = String.class)
   public ResponseEntity<APIResponse<String>> deleteCallDetailsFromTerminal(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       		@Valid @KidShouldExists(message = "{son.id.notvalid}")
        		@PathVariable String kid,
        @ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
   			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
    			@PathVariable String terminal,
    	@ApiParam(name="ids", value = "Call ids", required = true) 
   			@Validated(ICommonSequence.class) 
				@RequestBody ValidList<ObjectId> callsList) 
   		throws Throwable {
       
	   logger.debug("Delete all call details from terminal");
	   
	   // Get Terminal
       final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
   			new ObjectId(terminal), new ObjectId(kid)))
   			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
       
       // Delete call details
       terminalService.deleteCallDetail(
    		   new ObjectId(terminalDTO.getKid()), 
    		   new ObjectId(terminalDTO.getIdentity()),
    		   callsList);
   	
	   // Create and send response
       return ApiHelper.<String>createAndSendResponse(CallDetailResponseCode.CALL_DETAILS_DELETED, 
          		HttpStatus.OK, messageSourceResolver.resolver("call.details.deleted"));
	   
   }
   
   
   /**
    * Delete Call Detail
    * @param kid
    * @param terminal
    * @param call
    * @return
    * @throws Throwable
    */
  @RequestMapping(value = "/{kid}/terminal/{terminal}/calls/{call}", method = RequestMethod.DELETE)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
  		+ "&& @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "DELETE_SINGLE_CALL_DETAIL", nickname = "DELETE_SINGLE_CALL_DETAIL",
  	notes = "Delete Single Call Detail", response = String.class)
  public ResponseEntity<APIResponse<String>> deleteSingleCallDetail(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      		@Valid @KidShouldExists(message = "{son.id.notvalid}")
       			@PathVariable String kid,
       	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
  			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
   				@PathVariable String terminal,
   		@ApiParam(name = "call", value = "Call Identifier", required = true)
				@Valid @CallDetailShouldExists(message = "{call.id.notvalid}")
					@PathVariable String call) 
  		throws Throwable {
	  
	  logger.debug("Delete single call detail from terminal");
	   
	   // Get Terminal
      final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
  			new ObjectId(terminal), new ObjectId(kid)))
  			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
      
      // Delete call detail
      terminalService.deleteCallDetail(
   		   new ObjectId(terminalDTO.getKid()), 
   		   new ObjectId(terminalDTO.getIdentity()),
   		   new ObjectId(call));
  	
	   // Create and send response
      return ApiHelper.<String>createAndSendResponse(CallDetailResponseCode.SINGLE_CALL_DETAIL_DELETED, 
         		HttpStatus.OK, messageSourceResolver.resolver("single.call.details.deleted"));
  }
   
   
   /**
    * Save Call Details from terminal 
    * @return
    * @throws Throwable
    */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/calls", method = RequestMethod.POST)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
   		+ " && @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "SAVE_CALL_DETAILS_FROM_TERMINAL", 
   	nickname = "SAVE_CALL_DETAILS_FROM_TERMINAL", 
   			notes = "Save call details from terminal",
   				response = Iterable.class)
   public ResponseEntity<APIResponse<Iterable<CallDetailDTO>>> saveCallDetailsFromTerminal(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       	@Valid @KidShouldExists(message = "{son.not.exists}")
        		@PathVariable String kid,
        	@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
           	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
            		@PathVariable String terminal,
           @ApiParam(name="calls", value = "calls", required = true) 
				@Validated(ICommonSequence.class) 
   				@RequestBody ValidList<SaveCallDetailDTO> calls) throws Throwable {
   	
   	logger.debug("Save calls details from terminal");
   	
   	// Get Terminal
    final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
			new ObjectId(terminal), new ObjectId(kid)))
			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    
    // Save Calls
    final Iterable<CallDetailDTO> callsSaved = terminalService.saveCalls(calls);
    
    // Create and send response
    return ApiHelper.<Iterable<CallDetailDTO>>createAndSendResponse(CallDetailResponseCode.CALLS_SAVED, 
       		HttpStatus.OK, callsSaved);
   }
   
   /**
    * Save Calls
    * @return
    * @throws Throwable
    */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/calls/add", method = RequestMethod.POST)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
   		+ " && @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "ADD_CALL_DETAILS_FROM_TERMINAL", 
   	nickname = "ADD_CALL_DETAILS_FROM_TERMINAL", 
   			notes = "Add Call Detail from terminal",
   				response = Iterable.class)
   public ResponseEntity<APIResponse<CallDetailDTO>> addCallDetailsFromTerminal(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       	@Valid @KidShouldExists(message = "{son.not.exists}")
        		@PathVariable String kid,
        	@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
           	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
            		@PathVariable String terminal,
           @ApiParam(name="call", value = "call", required = true) 
				@Validated(ICommonSequence.class) 
   				@RequestBody SaveCallDetailDTO callDetail) throws Throwable {
	   
	   logger.debug("Add Call Detail From Terminal");
	   
	   // Save Call
	   final CallDetailDTO callDetailDTO = terminalService.saveCall(callDetail);
	  
	    // Create and send response
	    return ApiHelper.<CallDetailDTO>createAndSendResponse(CallDetailResponseCode.CALL_SAVED, 
	       		HttpStatus.OK, callDetailDTO);
   }
   
   
   /**
    * Get Calls
    * @throws Throwable
    */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/contacts", method = RequestMethod.GET)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
   		+ "&& @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "GET_ALL_CONTACTS_FROM_TERMINAL", nickname = "GET_ALL_CONTACTS_FROM_TERMINAL",
   	notes = "Get Calls From Terminal", response = Iterable.class)
   public ResponseEntity<APIResponse<Iterable<ContactDTO>>> getAllContactsFromTerminal(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       		@Valid @KidShouldExists(message = "{son.id.notvalid}")
        		@PathVariable final String kid,
        @ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
   			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
    			@PathVariable final String terminal,
    	@ApiParam(name="text", value = "text", required = false) 
   			@RequestParam(value = "text", required = false) 
   				final String text) 
   		throws Throwable {
   	
   		logger.debug("Get All Contacts from terminal");
   	
   		// Get Terminal
       final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
   			new ObjectId(terminal), new ObjectId(kid)))
   			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
       
       // Get all contacts 
       final Iterable<ContactDTO> contactDTOs = terminalService.getContacts(
       		new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()), text);
       
       // Check results
       if(Iterables.size(contactDTOs) == 0)
       		throw new NoContactsFoundException();
   	
       // Create and send response
       return ApiHelper.<Iterable<ContactDTO>>createAndSendResponse(ContactResponseCode.ALL_CONTACTS, 
           		HttpStatus.OK, contactDTOs);
   }
   
   
   /**
    * Get Contact Detail
    * @param kid
    * @param terminal
    * @param contact
    * @return
    * @throws Throwable
    */
  @RequestMapping(value = "/{kid}/terminal/{terminal}/contacts/{contact}", method = RequestMethod.GET)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
  		+ "&& @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "GET_CONTACT_DETAIL", nickname = "GET_CONTACT_DETAIL",
  	notes = "Get Contact Detail", response = ContactDTO.class)
  public ResponseEntity<APIResponse<ContactDTO>> getContactDetail(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      		@Valid @KidShouldExists(message = "{son.id.notvalid}")
       			@PathVariable String kid,
       	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
  			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
   				@PathVariable String terminal,
   		@ApiParam(name = "contact", value = "Contact Identifier", required = true)
				@Valid @ContactShouldExists(message = "{contact.id.notvalid}")
					@PathVariable String contact) 
  		throws Throwable {
	   
	   logger.debug("Get Contact Detail");
	   
	   // Get Terminal
      final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
  			new ObjectId(terminal), new ObjectId(kid)))
  			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
      
  	
      // Get Contact Detail
      final ContactDTO contactDTO = Optional.ofNullable(terminalService.getContactByIdAndTerminalIdAndKidId(
    		new ObjectId(contact),
    		new ObjectId(terminalDTO.getIdentity()), 
  			new ObjectId(terminalDTO.getKid())))
  			 .orElseThrow(() -> { throw new ContactNotFoundException(); });
	   
      
      // Create and send response
      return ApiHelper.<ContactDTO>createAndSendResponse(ContactResponseCode.SINGLE_CONTACT_DETAIL, 
          		HttpStatus.OK, contactDTO);
  }
  
  
  /**
   * Delete all contacts from Terminal
   * @return
   * @throws Throwable
   */
  @RequestMapping(value = "/{kid}/terminal/{terminal}/contacts", method = RequestMethod.DELETE)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
  		+ "&& @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "DELETE_ALL_CONTACTS_FROM_TERMINAL", 
  	nickname = "DELETE_ALL_CONTACTS_FROM_TERMINAL",
  	notes = "Delete All Contacts From Terminal", response = String.class)
  public ResponseEntity<APIResponse<String>> deleteAllContactsFromTerminal(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      		@Valid @KidShouldExists(message = "{son.id.notvalid}")
       			@PathVariable String kid,
       	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
  			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
   				@PathVariable String terminal) 
  		throws Throwable {
      
	   logger.debug("Delete all contacts from terminal");
	   
	   // Get Terminal
      final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
  			new ObjectId(terminal), new ObjectId(kid)))
  			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
      
      // Delete all contacts
      terminalService.deleteAllContacts(
   		   new ObjectId(terminalDTO.getKid()), 
   		   new ObjectId(terminalDTO.getIdentity()));
  	
	   // Create and send response
      return ApiHelper.<String>createAndSendResponse(ContactResponseCode.ALL_CONTACTS_DELETED, 
         		HttpStatus.OK, messageSourceResolver.resolver("all.contacts.deleted"));
	   
  }
  
  
  /**
   * Delete contacts from Terminal
   * @return
   * @throws Throwable
   */
  @RequestMapping(value = "/{kid}/terminal/{terminal}/contacts/delete", method = RequestMethod.POST)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
  		+ "&& @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "DELETE_CONTACTS_FROM_TERMINAL", 
  	nickname = "DELETE_CONTACTS_FROM_TERMINAL",
  	notes = "Delete Contacts From Terminal", response = String.class)
  public ResponseEntity<APIResponse<String>> deleteContactsFromTerminal(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      		@Valid @KidShouldExists(message = "{son.id.notvalid}")
       			@PathVariable String kid,
       	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
  			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
   				@PathVariable String terminal,
   		@ApiParam(name="ids", value = "Contacts ids", required = true) 
			@Validated(ICommonSequence.class) 
				@RequestBody ValidList<ObjectId> contactsList) 
  		throws Throwable {
      
	   logger.debug("Delete all contacts from terminal");
	   
	   // Get Terminal
      final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
  			new ObjectId(terminal), new ObjectId(kid)))
  			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
      
      // Delete all contacts
      terminalService.deleteContacts(
   		   new ObjectId(terminalDTO.getKid()), 
   		   new ObjectId(terminalDTO.getIdentity()),
   		   contactsList);
  	
	   // Create and send response
      return ApiHelper.<String>createAndSendResponse(ContactResponseCode.CONTACTS_DELETED, 
         		HttpStatus.OK, messageSourceResolver.resolver("contacts.deleted"));
	   
  }
  
  
  /**
   * Delete Call Detail
   * @param kid
   * @param terminal
   * @param call
   * @return
   * @throws Throwable
   */
 @RequestMapping(value = "/{kid}/terminal/{terminal}/contacts/{contact}", method = RequestMethod.DELETE)
 @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
 		+ "&& @authorizationService.isYourGuardian(#kid) )")
 @ApiOperation(value = "DELETE_SINGLE_CONTACT", nickname = "DELETE_SINGLE_CONTACT",
 	notes = "Delete Single Contact", response = String.class)
 public ResponseEntity<APIResponse<String>> deleteSingleContact(
 		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
     		@Valid @KidShouldExists(message = "{son.id.notvalid}")
      			@PathVariable String kid,
      	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
 			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
  				@PathVariable String terminal,
  		@ApiParam(name = "contact", value = "Contact Identifier", required = true)
				@Valid @ContactShouldExists(message = "{contact.id.notvalid}")
					@PathVariable String contact) 
 		throws Throwable {
	  
	 logger.debug("Delete single contact from terminal");
	   
	   // Get Terminal
     final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
 			new ObjectId(terminal), new ObjectId(kid)))
 			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
     
     // Delete Contact
     terminalService.deleteContact(
  		   new ObjectId(terminalDTO.getKid()), 
  		   new ObjectId(terminalDTO.getIdentity()),
  		   new ObjectId(contact));
 	
	   // Create and send response
     return ApiHelper.<String>createAndSendResponse(ContactResponseCode.SINGLE_CONTACT_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("single.contact.deleted"));
 }
  
  
  /**
   * Save Contacts from terminal 
   * @return
   * @throws Throwable
   */
  @RequestMapping(value = "/{kid}/terminal/{terminal}/contacts", method = RequestMethod.POST)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
  		+ " && @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "SAVE_CONTACTS_FROM_TERMINAL", 
  	nickname = "SAVE_CONTACTS_FROM_TERMINAL", 
  			notes = "Save Contacts from terminal",
  				response = Iterable.class)
  public ResponseEntity<APIResponse<Iterable<ContactDTO>>> saveContactsFromTerminal(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      		@Valid @KidShouldExists(message = "{son.not.exists}")
       			@PathVariable String kid,
       	@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
          	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
           		@PathVariable String terminal,
        @ApiParam(name="contacts", value = "Contacts to save", required = true) 
			@Validated(ICommonSequence.class) 
  				@RequestBody ValidList<SaveContactDTO> contacts) throws Throwable {
  	
  	logger.debug("Save contacts from terminal");
  	
  	// Get Terminal
   final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
			new ObjectId(terminal), new ObjectId(kid)))
			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
   
   // Save Contacts
   final Iterable<ContactDTO> contactsSaved = terminalService.saveContacts(contacts);
   
   // Create and send response
   return ApiHelper.<Iterable<ContactDTO>>createAndSendResponse(ContactResponseCode.CONTACTS_SAVED, 
      		HttpStatus.OK, contactsSaved);
  }
  
  /**
   * Save Calls
   * @return
   * @throws Throwable
   */
  @RequestMapping(value = "/{kid}/terminal/{terminal}/contacts/add", method = RequestMethod.POST)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole()"
  		+ " && @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "ADD_CONTACT_FROM_TERMINAL", 
  	nickname = "ADD_CONTACT_FROM_TERMINAL", 
  			notes = "Add Contact from terminal",
  				response = ContactDTO.class)
  public ResponseEntity<APIResponse<ContactDTO>> addContactFromTerminal(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      	@Valid @KidShouldExists(message = "{son.not.exists}")
       		@PathVariable String kid,
       	@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
          	@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
           		@PathVariable String terminal,
          @ApiParam(name="contact", value = "Contact to save", required = true) 
				@Validated(ICommonSequence.class) 
  				@RequestBody SaveContactDTO contact) throws Throwable {
	   
	   logger.debug("Add Contact From Terminal");
	   
	   // Get Terminal
	   final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
				new ObjectId(terminal), new ObjectId(kid)))
				 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
	   
	   // Save Contact
	   final ContactDTO contactDTO = terminalService.saveContact(contact);
	  
	    // Create and send response
	    return ApiHelper.<ContactDTO>createAndSendResponse(ContactResponseCode.CONTACT_SAVED, 
	       		HttpStatus.OK, contactDTO);
  }
  
  /**
   * Get All apps installed in the terminal
   * @param kid
   * @param terminal
   * @return
   * @throws Throwable
   */
  @RequestMapping(value = "/{kid}/terminal/apps", method = RequestMethod.GET)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "GET_ALL_APPS_INSTALLED_FOR_KID", nickname = "GET_ALL_APPS_INSTALLED_FOR_KID",
  notes = "Get all apps installed for kid", 
  response = List.class)
  public ResponseEntity<APIResponse<Iterable<AppInstalledInTerminalDTO>>> getAllAppsInstalledForKid(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      		@Valid @KidShouldExists(message = "{son.not.exists}")
       			@PathVariable final String kid,
       	@ApiParam(name="text", value = "text", required = false) 
 				@RequestParam(value = "text", required = false) 
 					final String text) 
  		throws Throwable {
      
  	logger.debug("Get all apps installed for kid");

  
  	// Get App installed for kid
  	final Iterable<AppInstalledInTerminalDTO> appInstalledForKid =
  			terminalService.getAllAppsInstalledByKid(
  					new ObjectId(kid), text);
      
  	if(Iterables.isEmpty(appInstalledForKid))
      	throw new NoAppsInstalledFoundException();
  	
  	// Create And send response
      return ApiHelper.<Iterable<AppInstalledInTerminalDTO>>createAndSendResponse(AppsResponseCode.ALL_APPS_INSTALLED_BY_KID, 
          		HttpStatus.OK, appInstalledForKid);
  }
    
    
    /**
     * Get All apps installed in the terminal
     * @param kid
     * @param terminal
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
         			@PathVariable final String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable final String terminal,
         	@ApiParam(name="text", value = "text", required = false) 
   				@RequestParam(value = "text", required = false) 
   					final String text) 
    		throws Throwable {
        
    	logger.debug("Get all apps installed in the terminal");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    
    	// Get App installed in the terminal
    	final Iterable<AppInstalledDTO> appInstalledTerminal =
    			terminalService.getAllAppsInstalledInTheTerminal(
    					new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()), text);
        
    	if(Iterables.isEmpty(appInstalledTerminal))
        	throw new NoAppsInstalledFoundException();
    	
    	// Create And send response
        return ApiHelper.<Iterable<AppInstalledDTO>>createAndSendResponse(AppsResponseCode.ALL_APPS_INSTALLED_IN_THE_TERMINAL, 
            		HttpStatus.OK, appInstalledTerminal);
    }
    
    /**
     * Get App Detail
     * @param kid
     * @param terminal
     * @param app
     * @return
     * @throws Throwable
     */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/{app}", method = RequestMethod.GET)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
   		+ "&& @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "GET_APP_DETAIL", nickname = "GET_APP_DETAIL",
   	notes = "Get Call Detail", response = AppInstalledDetailDTO.class)
   public ResponseEntity<APIResponse<AppInstalledDetailDTO>> getAppDetail(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       		@Valid @KidShouldExists(message = "{son.id.notvalid}")
        			@PathVariable String kid,
        	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
   			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
    				@PathVariable String terminal,
    		@ApiParam(name = "app", value = "App Identifier", required = true)
				@Valid @AppInstalledShouldExists(message = "{app.id.notvalid}")
					@PathVariable String app) 
   		throws Throwable {
	   
	   logger.debug("Get App Detail with id -> " + app);
	   
	   	// Get Terminal
   		final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
   			new ObjectId(terminal), new ObjectId(kid)))
   			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
   		
   		// Get App Installed
   		final AppInstalledDetailDTO appInstalledDTO = 
   				Optional.ofNullable(terminalService.getAppInstalledDetail(new ObjectId(app), 
   						new ObjectId(terminalDTO.getIdentity())))
   				.orElseThrow(() -> { throw new AppInstalledNotFoundException(); });
   		
   		// Create And send response
        return ApiHelper.<AppInstalledDetailDTO>createAndSendResponse(AppsResponseCode.APP_INSTALLED_DETAIL, 
            		HttpStatus.OK, appInstalledDTO);
	   
   }
    
    
    /**
     * Save App installed
     * @param kid
     * @param terminal
     * @param appsInstalled
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
            @ApiParam(name="apps", value = "apps", required = true) 
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
    	return ApiHelper.<Iterable<AppInstalledDTO>>createAndSendResponse(AppsResponseCode.APPS_INSTALLED_SAVED, 
				HttpStatus.OK, appInstalledSaved);    
    }
    
    /**
     * Save App Rules for apps in the terminal
     * @param kid
     * @param terminal
     * @return
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/{app}/rules", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "SAVE_APP_RULES_FOR_APP_IN_THE_TERMINAL", 
    		nickname = "SAVE_APP_RULES_FOR_APP_IN_THE_TERMINAL", 
    	notes = "Save App Rules",
            response = String.class)
    public ResponseEntity<APIResponse<String>> saveAppRules(
    	@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    		@Valid @KidShouldExists(message = "{son.not.exists}")
 				@PathVariable String kid,
 		@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
    		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     			@PathVariable String terminal,
     	@ApiParam(name = "app", value = "App Identifier", required = true)
			@Valid @AppInstalledShouldExists(message = "{app.id.notvalid}")
				@PathVariable String app,
     	@ApiParam(name="rules", value = "rules", required = true) 
			@Validated(ICommonSequence.class) 
				@RequestBody SaveAppRulesDTO appRules) throws Throwable {
    	
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
    	
    	// Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new AppRulesSavedEvent(
    				this, kid, terminal, app, appRules.getType()));
    	
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(AppsResponseCode.APP_RULES_WERE_APPLIED, 
				HttpStatus.OK, messageSourceResolver.resolver("apps.rules.saved"));
    
    }
    
    /**
     * Save App Rules for a specific app in the terminal
     * @param kid
     * @param terminal
     * @return
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/rules", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_APP_RULES_FOR_APPS_IN_THE_TERMINAL", nickname = "SAVE_APP_RULES_FOR_APPS_IN_THE_TERMINAL", 
    	notes = "Save App Rules",
            response = String.class)
    public ResponseEntity<APIResponse<String>> saveAppRules(
    	@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    		@Valid @KidShouldExists(message = "{son.not.exists}")
 				@PathVariable String kid,
 		@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
    		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     			@PathVariable String terminal,
     	@ApiParam(name="rules", value = "rules", required = true) 
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
    	
    	// Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new AppRulesListSavedEvent(
    				this, kid, terminal, appRules));
    	
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(AppsResponseCode.APP_RULES_WERE_APPLIED, 
				HttpStatus.OK, messageSourceResolver.resolver("apps.rules.saved"));
    
    }
    
    /**
     * Get App Rules for specific app in the terminal
     * @param kid
     * @param terminal
     * @param app
     * @return
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/{app}/rules", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "GET_APP_RULES_FOR_APPS_IN_THE_TERMINAL", nickname = "GET_APP_RULES_FOR_APPS_IN_THE_TERMINAL", 
    		notes = "Get App Rules", response = AppRuleDTO.class)
    public ResponseEntity<APIResponse<AppRuleDTO>> getAppRules(
    	@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    		@Valid @KidShouldExists(message = "{son.not.exists}")
 				@PathVariable final String kid,
 		@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
    		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     			@PathVariable final String terminal,
     	@ApiParam(name = "app", value = "App Identifier", required = true)
			@Valid @AppInstalledShouldExists(message = "{app.id.notvalid}")
				@PathVariable final String app) throws Throwable {
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Get App Rules
    	final AppRuleDTO appRules = terminalService.getAppRules(
    			new ObjectId(terminalDTO.getKid()),
    			new ObjectId(terminalDTO.getIdentity()),
    			new ObjectId(app));
    	
    	// Create and send response
    	return ApiHelper.<AppRuleDTO>createAndSendResponse(AppsResponseCode.APP_RULES_DETAIL, 
				HttpStatus.OK, appRules);
    
    }
    
    
    /**
     * Get App Rules for specific app in the terminal
     * @param kid
     * @param terminal
     * @return
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/rules", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "GET_APP_RULES_FOR_APPS_IN_THE_TERMINAL", nickname = "GET_APP_RULES_FOR_APPS_IN_THE_TERMINAL", 
    		notes = "Get App Rules", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<AppRuleDTO>>> getAppRules(
    	@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    		@Valid @KidShouldExists(message = "{son.not.exists}")
 				@PathVariable String kid,
 		@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
    		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     			@PathVariable String terminal) throws Throwable {
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Get App Rules
    	final Iterable<AppRuleDTO> appRules = terminalService.getAppRules(
    			new ObjectId(terminalDTO.getKid()),
    			new ObjectId(terminalDTO.getIdentity()));
    	
    	// Create and send response
    	return ApiHelper.<Iterable<AppRuleDTO>>createAndSendResponse(AppsResponseCode.ALL_APP_RULES, 
				HttpStatus.OK, appRules);
    
    }
    
    /**
     * Disable App In The Terminal
     * @param kid
     * @param terminal
     * @param app
     * @return
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/{app}/disabled", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DISABLE_APP_IN_THE_TERMINAL", nickname = "DISABLE_APP_IN_THE_TERMINAL", 
    	notes = "Disable App In The Terminal",
            response = String.class)
    public ResponseEntity<APIResponse<String>> disableAppInTheTerminal(
    	@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    		@Valid @KidShouldExists(message = "{son.not.exists}")
 				@PathVariable String kid,
 		@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
    		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     			@PathVariable String terminal,
     	@ApiParam(name = "app", value = "App Identifier", required = true)
    		@Valid @AppInstalledShouldExists(message = "{app.id.notvalid}")
    			@PathVariable final String app) throws Throwable {
    	
    	logger.debug("Disable App In The Terminal");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Disabled App In The Terminal
    	terminalService.disableAppInTheTerminal(
    			new ObjectId(kid), new ObjectId(terminal), 
    			new ObjectId(app));

    	// Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new AppDisabledEvent(
    				this, terminalDTO.getKid(), terminalDTO.getIdentity(), app));
    	
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(AppsResponseCode.APP_DISABLED_SUCCESSFULLY, 
				HttpStatus.OK, messageSourceResolver.resolver("apps.disabled.successfully"));
    
    }
    
    
    /**
     * Enable App In The Terminal
     * @param kid
     * @param terminal
     * @param app
     * @return
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/{app}/enable", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ENABLE_APP_IN_THE_TERMINAL", nickname = "ENABLE_APP_IN_THE_TERMINAL", 
    	notes = "Enable App In The Terminal",
            response = String.class)
    public ResponseEntity<APIResponse<String>> enableAppInTheTerminal(
    	@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    		@Valid @KidShouldExists(message = "{son.not.exists}")
 				@PathVariable String kid,
 		@ApiParam(name = "terminal", value = "Terminal Identity", required = true)
    		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     			@PathVariable String terminal,
     	@ApiParam(name = "app", value = "App Identifier", required = true)
    		@Valid @AppInstalledShouldExists(message = "{app.id.notvalid}")
    			@PathVariable final String app) throws Throwable {
    	
    	logger.debug("Enable App In The Terminal");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Enable App In The Terminal
    	terminalService.enableAppInTheTerminal(
    			new ObjectId(kid), new ObjectId(terminal), 
    			new ObjectId(app));

    	// Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new AppEnabledEvent(this, terminalDTO.getKid(), 
    				terminalDTO.getIdentity(), app));
    	
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(AppsResponseCode.APP_ENABLED_SUCCESSFULLY, 
				HttpStatus.OK, messageSourceResolver.resolver("app.enabled.successfully"));
    
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
        terminalService.deleteApps(new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()));
      
        // Save Alert
    	alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("apps.installed.all.deleted.title", 
    			new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() } ),
    			messageSourceResolver.resolver("apps.installed.all.deleted.description", 
    					new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(AppsResponseCode.ALL_APPS_INSTALLED_DELETED, HttpStatus.OK, 
        		messageSourceResolver.resolver("all.apps.installed.deleted"));
        
    }
    
    
    /**
     * Delete Apps installed
     * @param kid
     * @param terminal
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/delete", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_APPS_INSTALLED", nickname = "DELETE_APPS_INSTALLED", 
    	notes = "Delete apps installed", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAppsIstalled(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.id.notvalid}")
             		@PathVariable String kid,
            @ApiParam(name = "terminal", value = "Terminal id", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable String terminal,
         	@ApiParam(name="ids", value = "ids", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody ValidList<ObjectId> appsList) throws Throwable {
        
        logger.debug("Delete all apps installed by child identity " + kid + " for terminal " + terminal);
        
        // Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    
        
        // Delete Apps installed
        terminalService
        	.deleteApps(new ObjectId(kid), new ObjectId(terminalDTO.getIdentity()), appsList);
      
        // Save Alert
    	alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("apps.installed.all.deleted.title", 
    			new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() } ),
    			messageSourceResolver.resolver("apps.installed.all.deleted.description", 
    					new Object[] { terminalDTO.getModel(), terminalDTO.getDeviceName() }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
        
        // Create and send response
        return ApiHelper.<String>createAndSendResponse(AppsResponseCode.APPS_INSTALLED_DELETED,
        		HttpStatus.OK, messageSourceResolver.resolver("apps.installed.deleted"));
        
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
    	
    	// Get App Installed
   		final AppInstalledDTO appInstalledDTO = 
   				Optional.ofNullable(terminalService.getAppInstalled(new ObjectId(app), 
   						new ObjectId(terminalDTO.getIdentity())))
   				.orElseThrow(() -> { throw new AppInstalledNotFoundException(); });
    	
        // Delete App installed by id
        terminalService.deleteAppInstalledById(new ObjectId(appInstalledDTO.getIdentity()));
        
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new UninstallAppEvent(this, terminalDTO.getKid(), 
    				terminalDTO.getIdentity(), appInstalledDTO));
        
        // Save Alert
    	alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("apps.installed.remove.title", 
    			new Object[] { appInstalledDTO.getAppName() } ),
    			messageSourceResolver.resolver("apps.installed.remove.description", 
    					new Object[] { appInstalledDTO.getAppName(), terminalDTO.getModel(), terminalDTO.getDeviceName() }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
      
        return ApiHelper.<String>createAndSendResponse(AppsResponseCode.APP_INSTALLED_DELETED, HttpStatus.OK, 
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
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ADD_APP_INSTALLED", nickname = "ADD_APP_INSTALLED", 
    	notes = "Add app installed", response = AppInstalledDTO.class)
    public ResponseEntity<APIResponse<AppInstalledDTO>> addNewAppInstalled(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{kid.id.notvalid}")
             		@PathVariable String kid,
            @ApiParam(name = "terminal", value = "Terminal id", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable String terminal,
         	@ApiParam(name="app", value = "app", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody SaveAppInstalledDTO appInstalledDTO) throws Throwable {
        
        logger.debug("Add a new app isntalled for terminal " + terminal);
        
        // Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
        // Save App Installed
        final AppInstalledDTO appInstalled = terminalService.save(appInstalledDTO);
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new NewAppInstalledEvent(this, terminalDTO.getKid(), 
    				terminalDTO.getIdentity(), appInstalled));
        
        // Save Alert
    	alertService.save(AlertLevelEnum.WARNING, messageSourceResolver.resolver("apps.installed.added.title", 
    			new Object[] { appInstalled.getAppName() } ),
    			messageSourceResolver.resolver("apps.installed.added.description", 
    					new Object[] { appInstalled.getAppName(), terminalDTO.getModel(), terminalDTO.getDeviceName() }), 
    			new ObjectId(kid), AlertCategoryEnum.APPS_INSTALLED);
      
        // Create and send response
        return ApiHelper.<AppInstalledDTO>createAndSendResponse(AppsResponseCode.NEW_APP_INSTALLED_ADDED, 
        		HttpStatus.OK, appInstalled);
        
    }
    
    /**
     * Get Stats for all apps installed in the terminal
     * @param kid
     * @param terminal
     * @param total
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/stats", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_STATS_FOR_ALL_APPS_INSTALLED_IN_THE_TERMINAL", 
    	nickname = "GET_STATS_FOR_ALL_APPS_INSTALLED_IN_THE_TERMINAL",
    		notes = "Get Stats For all apps installed in the terminal", 
    			response = AppStatsDTO.class)
    public ResponseEntity<APIResponse<Iterable<AppStatsDTO>>> getStatsForAllAppsInstalledInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.not.exists}")
         			@PathVariable final String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable final String terminal,
         	@ApiParam(name="total", value = "total", required = false) 
   				@RequestParam(value = "total", required = false, defaultValue = "10") 
   					final Integer total) 
    		throws Throwable {
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Get Stats For App Installed
    	Iterable<AppStatsDTO> appStats = terminalService.getStatsForAppInstalled(new ObjectId(terminalDTO.getKid()), 
    			new ObjectId(terminalDTO.getIdentity()), total);
    	
    	if(Iterables.size(appStats) == 0)
    		throw new NoAppStatsFoundException();
    	
    	// Create and send response
        return ApiHelper.<Iterable<AppStatsDTO>>createAndSendResponse(
        		AppsResponseCode.STATS_FOR_APPS_INSTALLED_IN_THE_TERMINAL, HttpStatus.OK, 
        		appStats);
    
    }
    
    /**
     * Delete App Stats
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/stats/delete", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_STATS_FOR_APPS_INSTALLED_IN_THE_TERMINAL", 
    		nickname = "DELETE_STATS_FOR_APPS_INSTALLED_IN_THE_TERMINAL",
    	notes = "Delete Stats for apps installed in the terminal", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteStatsForAppsInstalledInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.id.notvalid}")
         			@PathVariable String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.id.notvalid}")
     				@PathVariable String terminal,
     		@ApiParam(name="ids", value = "ids", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody ValidList<ObjectId> ids) 
    		throws Throwable {
        
    	logger.debug("Delete Stats For Apps Installed In The Terminal");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Delete Stats For Apps Installed
    	this.terminalService
    		.deleteStatsForAppsInstalled(new ObjectId(terminalDTO.getKid()), 
    				new ObjectId(terminalDTO.getIdentity()), ids);
    	
    	// Create and send response
        return ApiHelper.<String>createAndSendResponse(
        		AppsResponseCode.STATS_FOR_APPS_INSTALLED_DELETED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("stats.for.apps.installed.deleted.successfully"));
    	
    }
    
    
    /**
     * Get stats for a specific app installed in the terminal
     * @param kid
     * @param terminal
     * @param app
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/{app}/stats", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_STATS_FOR_A_SPECIFIC_APP_INSTALLED_IN_THE_TERMINAL", 
    	nickname = "GET_STATS_FOR_A_SPECIFIC_APP_INSTALLED_IN_THE_TERMINAL", 
    	notes = "Get stats for a specific app installed in the terminal", 
    	response = AppStatsDTO.class)
    public ResponseEntity<APIResponse<AppStatsDTO>> getStatsForASpecificAppInstalledInTheTerminal(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
    			@Valid @KidShouldExists(message = "{son.not.exists}")
     				@PathVariable final String kid,
     		@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
     				@PathVariable final String terminal,
     		@ApiParam(name = "app", value = "App id", required = true)
				@Valid @AppInstalledShouldExists(message = "{app.not.exists}")
 					@PathVariable String app) throws Throwable {
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Get App Stats
    	final AppStatsDTO appStatsDTO = Optional.ofNullable(terminalService.getStatsForApp(
    			new ObjectId(terminalDTO.getIdentity()), new ObjectId(terminalDTO.getKid()),
    					new ObjectId(app)))
    			 .orElseThrow(() -> { throw new AppStatsNotFoundException(); });
    
    	
    	// Create and send response
        return ApiHelper.<AppStatsDTO>createAndSendResponse(
        		AppsResponseCode.APP_STATS_NOT_FOUND, HttpStatus.OK, 
        		appStatsDTO);
    
    }
    
    /**
     * Save Stats for all apps installed in the terminal
     * @param kid
     * @param terminal
     * @param stats
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/apps/stats", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "SAVE_STATS_FOR_ALL_APPS_INSTALLED_IN_THE_TERMINAL", 
    		nickname = "SAVE_STATS_FOR_ALL_APPS_INSTALLED_IN_THE_TERMINAL", 
    		notes = "Save Stats For All Apps Installed in the terminal", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<AppStatsDTO>>> saveStatsForAllAppsInstalledInTheTerminal(
            @ApiParam(name = "kid", value = "Kid Identifier", required = true)
            	@Valid @KidShouldExists(message = "{son.id.notvalid}")
             		@PathVariable String kid,
            @ApiParam(name = "terminal", value = "Terminal id", required = true)
        		@Valid @TerminalShouldExists(message = "{terminal.not.exists}")
         			@PathVariable String terminal,
         	@ApiParam(name="stats", value = "stats", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody ValidList<SaveAppStatsDTO> statsList) throws Throwable {
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Save App Stats
    	final Iterable<AppStatsDTO> appStatsDTO = 
    			terminalService.saveAppStats(statsList);
    	
    	// Create and send response
        return ApiHelper.<Iterable<AppStatsDTO>>createAndSendResponse(
        		AppsResponseCode.APP_STATS_SAVED, HttpStatus.OK, appStatsDTO);
    	
    }
        

    
    /**
     * Get Phone Numbers Blocked
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/phonenumbers-blocked", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_PHONE_NUMBER_BLOCKED", nickname = "GET_PHONE_NUMBER_BLOCKED",
    	notes = "Get Phone Number Blocked", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<PhoneNumberBlockedDTO>>> getPhoneNumberBlocked(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
     			@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
      				@PathVariable String terminal) throws Throwable {
    	
    	logger.debug("Get Phone Number Blocked");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Get Phone Numbers Blocked
    	final Iterable<PhoneNumberBlockedDTO> phoneNumbersBlockedList = 
    			terminalService.getPhoneNumbersBlocked(terminalDTO.getKid(), terminalDTO.getIdentity());
    	
    	if(Iterables.size(phoneNumbersBlockedList) == 0)
    		throw new NoPhoneNumberBlockedFound();
 
    	// Create and send response
        return ApiHelper.<Iterable<PhoneNumberBlockedDTO>>createAndSendResponse(ChildrenResponseCode.PHONE_NUMBER_BLOCKED_LIST, 
        		HttpStatus.OK, phoneNumbersBlockedList);

    }
    
    
    /**
     * Add Phone Number Blocked
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/phonenumbers-blocked", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "ADD_PHONE_NUMBER_BLOCKED", nickname = "ADD_PHONE_NUMBER_BLOCKED",
    	notes = "Add Phone Number Blocked", response = PhoneNumberBlockedDTO.class)
    public ResponseEntity<APIResponse<PhoneNumberBlockedDTO>> addPhoneNumberBlocked(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
     			@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
      				@PathVariable String terminal,
      		@ApiParam(name="phonenumber", value = "phonenumber", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody AddPhoneNumberBlockedDTO addPhoneNumberBlocked) throws Throwable {
    	
    	logger.debug("Add Phone Number Blocked");
    	
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Add Phone Number
    	final PhoneNumberBlockedDTO phoneNumberBlocked = 
    			terminalService.addPhoneNumberBlocked(addPhoneNumberBlocked);

    	// Publish Event
    	this.applicationEventPublisher
			.publishEvent(new AddPhoneNumberBlockedEvent(this, 
					phoneNumberBlocked.getIdentity(),
					phoneNumberBlocked.getKid(), 
					phoneNumberBlocked.getTerminal(),
					phoneNumberBlocked.getPrefix(), 
					phoneNumberBlocked.getNumber(),
					phoneNumberBlocked.getPhoneNumber(), 
					phoneNumberBlocked.getBlockedAt()));
    	
    	// Create and send response
        return ApiHelper.<PhoneNumberBlockedDTO>createAndSendResponse(ChildrenResponseCode.PHONE_NUMBER_BLOCKED_ADDED, 
        		HttpStatus.OK, phoneNumberBlocked);
    	
    }
    
    /**
     * Delete Phone Numbers Blocked
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/phonenumbers-blocked", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "DELETE_PHONE_NUMBER_BLOCKED", nickname = "DELETE_PHONE_NUMBER_BLOCKED",
    	notes = "Delete Phone Number Blocked", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAllPhoneNumberBlocked(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
     			@Valid @KidShouldExists(message = "{son.should.be.exists}")
      				@PathVariable String kid,
      		@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
 				@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
  					@PathVariable String terminal) throws Throwable {
    	
    	logger.debug("Delete Phone Number Blocked");
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Unblock All Phone Numbers
    	terminalService.unBlockAllPhoneNumbers();
    	
    	// Publish Event
    	this.applicationEventPublisher
			.publishEvent(new DeleteAllPhoneNumberBlockedEvent(this, 
					terminalDTO.getKid(), terminalDTO.getIdentity()));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.ALL_PHONE_NUMBERS_UNBLOCKED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("all.phone.numbers.unblocked.successfully"));
    	
    }
    
    
    /**
     * Get Phone Numbers Blocked
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/phonenumbers-blocked/{idOrPhoneNumber}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_PHONE_NUMBER_BLOCKED_BY_ID_OR_NUMBER", 
    		nickname = "DELETE_PHONE_NUMBER_BLOCKED_BY_ID_OR_NUMBER",
    		notes = "Delete Phone Number Blocked By Id or number", response = String.class)
    public ResponseEntity<APIResponse<String>> deletePhoneNumberBlockedById(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
     			@Valid @KidShouldExists(message = "{son.should.be.exists}")
      				@PathVariable String kid,
	      	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
	 			@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
	  				@PathVariable String terminal,
	  		@ApiParam(name = "idOrPhoneNumber", value = "Phone Number Blocked", required = true)
 				@Valid @PhoneNumberBlockedShouldExists(message = "{phonenumber.blocked.should.be.exists}")
  					@PathVariable("idOrPhoneNumber") String phoneNumberBlocked) throws Throwable {
    	
    	logger.debug("Delete Phone Number Blocked by id");
    	// Get Terminal
    	final TerminalDTO terminalDTO = Optional.ofNullable(terminalService.getTerminalByIdAndKidId(
    			new ObjectId(terminal), new ObjectId(kid)))
    			 .orElseThrow(() -> { throw new TerminalNotFoundException(); });
    	
    	// Unblock Phone Number
    	terminalService.unBlockPhoneNumber(new ObjectId(terminalDTO.getKid()), 
    			new ObjectId(terminalDTO.getIdentity()), phoneNumberBlocked);
    	
    	// Publish Event
    	this.applicationEventPublisher
			.publishEvent(new DeletePhoneNumberBlockedEvent(this, kid, terminal,
					phoneNumberBlocked));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.PHONE_NUMBER_UNBLOCKED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("phone.number.unblocked.successfully"));
    	
    }
    
    /**
     * Terminal Heartbeat
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/heartbeat", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "TERMINAL_HEARTBEAT", nickname = "TERMINAL_HEARTBEAT",
    notes = "Terminal Heartbeat", response = String.class)
    public ResponseEntity<APIResponse<String>> terminalHeartbeat(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
     			@Valid @KidShouldExists(message = "{son.should.be.exists}")
      				@PathVariable String kid,
	      	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
	 			@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
	  				@PathVariable String terminal,
	  		@ApiParam(name="heartbeat", value = "heartbeat", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody TerminalHeartbeatDTO terminalHeartbeat) throws Throwable {
    	
    	logger.debug("Terminal Heartbeat");
    	
    	// Save HeartBeat
    	terminalService.saveHeartbeat(terminalHeartbeat);
    	
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.TERMINAL_HEARTBEAT_NOTIFIED_SUCCESSFULLY, HttpStatus.OK, 
        		messageSourceResolver.resolver("terminal.heartbeat.notified.successfully"));
    
    }
    
    /**
     * Get Fun Time Scheduled
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/funtime-scheduled", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_FUNTIME_SCHEDULED", nickname = "GET_FUNTIME_SCHEDULED",
    	notes = "Get FunTime Scheduled", response = FunTimeScheduledDTO.class)
    public ResponseEntity<APIResponse<FunTimeScheduledDTO>> getFunTimeScheduled(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.not.exists}")
         			@PathVariable final String kid,
		    @ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
				@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
					@PathVariable String terminal
			) throws Throwable {
        
    	logger.debug("Get Fun Time Scheduled");
    	// Get Fun Time Scheduled
    	final FunTimeScheduledDTO funTimeScheduled = Optional.ofNullable(
    			terminalService.getFunTimeScheduledByKid(new ObjectId(kid), new ObjectId(terminal)))
    	.orElseThrow(() -> { throw new FunTimeScheduledNotFoundException(); });
    	
    	// Create and send response
    	return ApiHelper.<FunTimeScheduledDTO>createAndSendResponse(ChildrenResponseCode.FUN_TIME_SCHEDULED, HttpStatus.OK, 
    			funTimeScheduled);
    }
    
    /**
     * Get Fun Time Day Scheduled
     * @param kid
     * @param terminal
     * @return
     * @throws Throwable
     */
   @RequestMapping(value = "/{kid}/terminal/{terminal}/funtime-scheduled/{day}", method = RequestMethod.GET)
   @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
   		+ "&& @authorizationService.isYourGuardian(#kid) )")
   @ApiOperation(value = "GET_FUNTIME_DAY_SCHEDULED", nickname = "GET_FUNTIME_DAY_SCHEDULED",
   	notes = "Get FunTime Day Scheduled Scheduled", response = DayScheduledDTO.class)
   public ResponseEntity<APIResponse<DayScheduledDTO>> getFunTimeDayScheduled(
   		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
       		@Valid @KidShouldExists(message = "{son.not.exists}")
        			@PathVariable final String kid,
		    @ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
				@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
					@PathVariable String terminal,
			@ApiParam(name = "day", value = "Day", required = true)
				@Valid @DayNameValidator(message = "{day.name.not.valid}")
					@PathVariable String day
			) throws Throwable {
       
   	logger.debug("Get Fun Time Scheduled");
   	
   	// Get Fun Time Scheduled
   	final DayScheduledDTO dayScheduledDTO = Optional.ofNullable(
   			terminalService.getFunTimeDayScheduled(new ObjectId(kid), 
   					new ObjectId(terminal), FunTimeDaysEnum.valueOf(day)))
   	.orElseThrow(() -> { throw new FunTimeDayScheduledNotFoundException(); });
   	
   	// Create and send response
   	return ApiHelper.<DayScheduledDTO>createAndSendResponse(ChildrenResponseCode.FUN_TIME_DAY_SCHEDULED, HttpStatus.OK, 
   			dayScheduledDTO);
   }
   
   
   /**
    * Save Fun Time Day Scheduled
    * @param kid
    * @param terminal
    * @return
    * @throws Throwable
    */
  @RequestMapping(value = "/{kid}/terminal/{terminal}/funtime-scheduled/{day}", method = RequestMethod.POST)
  @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
  		+ "&& @authorizationService.isYourGuardian(#kid) )")
  @ApiOperation(value = "SAVE_FUNTIME_DAY_SCHEDULED", nickname = "SAVE_FUNTIME_DAY_SCHEDULED",
  	notes = "Save FunTime Day Scheduled Scheduled", response = DayScheduledDTO.class)
  public ResponseEntity<APIResponse<DayScheduledDTO>> saveFunTimeDayScheduled(
  		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
      		@Valid @KidShouldExists(message = "{son.not.exists}")
       			@PathVariable final String kid,
		    @ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
				@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
					@PathVariable String terminal,
			@ApiParam(name = "day", value = "Day", required = true)
				@Valid @DayNameValidator(message = "{day.not.valid}")
					@PathVariable String day,
			@ApiParam(name="day_scheduled", value = "day_scheduled", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody SaveDayScheduledDTO saveDayScheduled
			) throws Throwable {
      
  	logger.debug("Get Fun Time Scheduled");
  	
  	final DayScheduledDTO dayScheduledDTO = terminalService.saveFunTimeDayScheduled(new ObjectId(kid), 
				new ObjectId(terminal), FunTimeDaysEnum.valueOf(day), saveDayScheduled);
  
  	// Publish Event
	this.applicationEventPublisher
		.publishEvent(new DayScheduledSavedEvent(this, kid, terminal,
				dayScheduledDTO));
  	
  	// Create and send response
  	return ApiHelper.<DayScheduledDTO>createAndSendResponse(ChildrenResponseCode.FUN_TIME_DAY_SCHEDULED, HttpStatus.OK, 
  			dayScheduledDTO);
  }
    
    /**
     * Save Fun Time Scheduled
     */
    @RequestMapping(value = "/{kid}/terminal/{terminal}/funtime-scheduled", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_FUNTIME_SCHEDULED", nickname = "SAVE_FUNTIME_SCHEDULED",
    	notes = "Save FunTime Scheduled", response = FunTimeScheduledDTO.class)
    public ResponseEntity<APIResponse<FunTimeScheduledDTO>> saveFunTimeScheduled(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        		@Valid @KidShouldExists(message = "{son.not.exists}")
         			@PathVariable final String kid,
         	@ApiParam(name = "terminal", value = "Terminal Identifier", required = true)
    			@Valid @TerminalShouldExists(message = "{terminal.should.be.exists}")
    				@PathVariable String terminal,
         	@ApiParam(name="funtime", value = "funtime", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody SaveFunTimeScheduledDTO saveFunTimeScheduledDTO) 
    		throws Throwable {
        
    	logger.debug("Save Fun Time Scheduled");
    	
    	// Save Fun Time Scheduled
    	final FunTimeScheduledDTO funTimeScheduledDTO = 
    			terminalService.saveFunTimeScheduledByKid(
    					new ObjectId(kid), new ObjectId(terminal), saveFunTimeScheduledDTO);
    	
    	// Publish Event
    	this.applicationEventPublisher
			.publishEvent(new SaveFunTimeScheduledEvent(this, kid, terminal,
					funTimeScheduledDTO));
    	
    	// Create and send response
    	return ApiHelper.<FunTimeScheduledDTO>createAndSendResponse(ChildrenResponseCode.FUN_TIME_SCHEDULED_SAVED, HttpStatus.OK, 
    			funTimeScheduledDTO);
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
            @ApiParam(name="scheduled_block", value = "Scheduled Block", required = true) 
				@Validated(ICommonSequence.class) 
    				@RequestBody SaveScheduledBlockDTO saveScheduledBlockDTO) throws Throwable {
    	
    	logger.debug("Save Scheduled Block");
    	
    	// Save Scheduled Block
    	final ScheduledBlockDTO scheduledBlock = scheduledBlockService.save(saveScheduledBlockDTO);
    	    
    	// PUblish Event
    	this.applicationEventPublisher
    		.publishEvent(new ScheduledBlockSavedEvent(this, scheduledBlock, 
    				scheduledBlock.getKid()));
    	
    	// Create and Send Response
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
            response = String.class)
    public ResponseEntity<APIResponse<String>> saveScheduledBlocksStatus(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
        	@Valid @KidShouldExists(message = "{son.not.exists}")
         		@PathVariable String kid,
         	@ApiParam(name="scheduled_blocks", value = "Scheduled Blocks list", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody ValidList<SaveScheduledBlockStatusDTO> saveScheduledBlockStatus) throws Throwable {
    	
    	logger.debug("Save Scheduled Block Status DTO");
    	
    	// Save Status
    	scheduledBlockService.saveStatus(saveScheduledBlockStatus);
    	
    	// Publish Event
    	this.applicationEventPublisher
			.publishEvent(new ScheduledBlockStatusChangedEvent(this,
					kid, saveScheduledBlockStatus));
    	
    	// Create and send response
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
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new DeleteAllScheduledBlockEvent(this, kid));
      
    	// Create and send response
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
        
        // Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new DeleteScheduledBlockEvent(this, kid, block));
      
    	// Create and send response
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
    	notes = "Upload Scheduled Block Image", response = ImageDTO.class)
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
    	// Save Image
    	ImageDTO imageDTO = uploadFilesService.uploadScheduledBlockImage(new ObjectId(kid),
    			new ObjectId(block), uploadScheduledBlockImage);
    	
    	// Publish Event
    	this.applicationEventPublisher
    		.publishEvent(new ScheduledBlockImageChangedEvent(this,
    				kid, block, imageDTO.getIdentity()));
    	
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
     * Get Current Location
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/location", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "GET_CURRENT_LOCATION", nickname = "GET_CURRENT_LOCATION",
    notes = "Get Current Location", response = LocationDTO.class)
    public ResponseEntity<APIResponse<LocationDTO>> getCurrentLocation(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid) throws Throwable {
    	
    	logger.debug("Get Current Location for kid -> " + kid);
    	
    	// Get Current Location
    	return Optional.ofNullable(kidService.getCurrentLocation(kid))
    		.map(currentLocation -> ApiHelper.<LocationDTO>createAndSendResponse(ChildrenResponseCode.CURRENT_LOCATION_OF_KID, 
            		HttpStatus.OK, currentLocation))
    		.orElseThrow(() -> { throw new CurrentLocationException(); });
    	
    }
    
    
    /**
     * Get Current Location
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/location", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(value = "SAVE_CURRENT_LOCATION", nickname = "SAVE_CURRENT_LOCATION",
    	notes = "Save Current Location", response = LocationDTO.class)
    public ResponseEntity<APIResponse<LocationDTO>> saveCurrentLocation(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable final String kid,
          	@ApiParam(name="save_location", value = "Save Location", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody final SaveLocationDTO saveLocation) throws Throwable {
    	
    	logger.debug("Save Current Location for kid -> " + kid);
    	
    	// Save Current Location
    	final LocationDTO currentLocation = kidService.saveCurrentLocation(kid, saveLocation);
    	
    	// Push Event
    	this.applicationEventPublisher
    		.publishEvent(new CurrentLocationUpdateEvent(
    				this, kid, currentLocation));
    	
    	// Create and send response
    	return ApiHelper.<LocationDTO>createAndSendResponse(ChildrenResponseCode.CURRENT_LOCATION_UPDATED, 
        		HttpStatus.OK, currentLocation);
    }
    
    /**
     * 
     * @param kid
     * @param kidRequest
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/request", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "ADD_REQUEST_FOR_KID", nickname = "ADD_REQUEST_FOR_KID",
    		notes = "Add Request For Kid", response = KidRequestDTO.class)
    public ResponseEntity<APIResponse<KidRequestDTO>> addRequestForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name="request", value = "request", required = true) 
    			@Validated(ICommonSequence.class) 
    			@RequestBody AddKidRequestDTO kidRequest
          	) throws Throwable {
    	
    	logger.debug("Add Kid Request for kid -> " + kid);
    	
    	// Add Kid Request
    	final KidRequestDTO kidRequestDTO = terminalService.addKidRequest(kidRequest);
    	
    	// Push Event
    	this.applicationEventPublisher
    		.publishEvent(new KidRequestCreatedEvent(
    				this, kidRequestDTO.getIdentity(), kidRequestDTO.getType(),
    				kidRequestDTO.getLocation(), kidRequestDTO.getKid(),
    				kidRequestDTO.getTerminal().getIdentity()));
    	
    	// Create and send response
    	return ApiHelper.<KidRequestDTO>createAndSendResponse(ChildrenResponseCode.KID_REQUEST_SAVED, 
        		HttpStatus.OK, kidRequestDTO);
    	
    }
    
    
    /**
     * Get All Request for kid
     */
    @RequestMapping(value = "/{kid}/request", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "GET_ALL_REQUEST_FOR_KID", nickname = "GET_ALL_REQUEST_FOR_KID",
    		notes = "Get All Request For Kid", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<KidRequestDTO>>> getAllRequestForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid) throws Throwable {
    	
    	logger.debug("Get all request for kid -> " + kid);
    	
    	// Get all kid request by kid id
    	final Iterable<KidRequestDTO> kidRequests = terminalService
    			.getAllKidRequestForKid(new ObjectId(kid));
    	
    	
    	if(Iterables.size(kidRequests) == 0)
    		throw new NoKidRequestFoundException();
    	
    	
    	// Create and send response
    	return ApiHelper.<Iterable<KidRequestDTO>>createAndSendResponse(ChildrenResponseCode.ALL_KID_REQUEST, 
        		HttpStatus.OK, kidRequests);
    	
    }
    
    /**
     * Get Kid Request Detail
     * @param kid
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/request/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "GET_KID_REQUEST_DETAIL", nickname = "GET_KID_REQUEST_DETAIL",
    		notes = "Get Kid Request Detail", response = KidRequestDTO.class)
    public ResponseEntity<APIResponse<KidRequestDTO>> getKidRequestDetail(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "id", value = "Kid Request Identifier", required = true)
     			@Valid @KidRequestShouldExists(message = "{kid.request.should.be.exists}")
      			@PathVariable String id) throws Throwable {
    	
    	logger.debug("Get Kid Request Detail");
    	
   
    	return Optional.ofNullable(terminalService.getKidRequestDetail(new ObjectId(kid),
    				new ObjectId(id)))
    		.map(kidRequestDetail -> ApiHelper.<KidRequestDTO>createAndSendResponse(ChildrenResponseCode.KID_REQUEST_DETAIL, 
            		HttpStatus.OK, kidRequestDetail))
    		.orElseThrow(() -> { throw new KidRequestNotFoundException(); });
    }
    
    
    /**
     * Delete request for kid
     */
    @RequestMapping(value = "/{kid}/request/delete", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_REQUEST_FOR_KID", nickname = "DELETE_REQUEST_FOR_KID",
    		notes = "Delete Request For Kid", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteRequestForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name="ids", value = "ids", required = true) 
        		@Validated(ICommonSequence.class) 
        			@RequestBody ValidList<ObjectId> ids) throws Throwable {
    	
    	logger.debug("Delete request for kid -> " + kid);
    	
    	// Delete Kid Request
    	terminalService.deleteKidRequest(new ObjectId(kid), ids);
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.KID_REQUEST_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("kid.request.deleted"));
    	
    }
    
    /**
     * Delete all request for kid
     */
    @RequestMapping(value = "/{kid}/request", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_ALL_REQUEST_FOR_KID", nickname = "DELETE_ALL_REQUEST_FOR_KID",
    		notes = "Delete All Request For Kid", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAllRequestForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid) throws Throwable {
    	
    	logger.debug("Delete all request for kid -> " + kid);
    	
    	terminalService.deleteAllKidRequestByKid(new ObjectId(kid));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.ALL_KID_REQUEST_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("all.kid.request.deleted"));
    	
    }
    
    /**
     * Delete Kid Request by id
     */
    @RequestMapping(value = "/{kid}/request/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_KID_REQUEST_BY_ID", nickname = "DELETE_KID_REQUEST_BY_ID",
    		notes = "Delete Kid Request By Id", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteKidRequestById(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "id", value = "Kid Request Identifier", required = true)
 				@Valid @KidRequestShouldExists(message = "{kid.request.should.be.exists}")
  					@PathVariable String id) throws Throwable {
    	
    	logger.debug("Delete kid request by id -> " + id);
    	
    	// Delete Kid Request
    	terminalService.deleteKidRequest(new ObjectId(kid), new ObjectId(id));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(ChildrenResponseCode.SINGLE_KID_REQUEST_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("single.kid.request.deleted"));
    	
    }
    
    
    /**
     * Save Geofence For Kid
     * @param kid
     * @param saveGeofenceDTO
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{kid}/geofences", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "SAVE_GEOFENCE_FOR_KID", nickname = "SAVE_GEOFENCE_FOR_KID",
    		notes = "Save Geofence For Kid", response = GeofenceDTO.class)
    public ResponseEntity<APIResponse<GeofenceDTO>> saveGeofenceForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name="geofence", value = "geofence", required = true) 
    			@Validated(ICommonSequence.class) 
    			@RequestBody SaveGeofenceDTO saveGeofenceDTO
          	) throws Throwable {
    	
    	logger.debug("Save Geofence for kid -> " + kid);
    	
    	// Save Geofence
    	final GeofenceDTO geofenceDTO = geofenceService.save(saveGeofenceDTO);
    	
   
    	// Push Event
    	this.applicationEventPublisher
    		.publishEvent(new GeofenceAddedEvent(
    				this, geofenceDTO.getIdentity(), geofenceDTO.getName(),
    				geofenceDTO.getLat(), geofenceDTO.getLog(), geofenceDTO.getRadius(),
    				geofenceDTO.getAddress(), geofenceDTO.getType(), geofenceDTO.getKid(),
    				geofenceDTO.getCreateAt(), geofenceDTO.getUpdateAt(),
    				geofenceDTO.getIsEnabled()));
    	
    
    	// Create and send response
    	return ApiHelper.<GeofenceDTO>createAndSendResponse(GeofenceResponseCode.GEOFENCE_SAVED_SUCCESSFULLY, 
        		HttpStatus.OK, geofenceDTO);
    	
    }
    
    
    /**
     * Get All Geofences for kid
     */
    @RequestMapping(value = "/{kid}/geofences", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "GET_ALL_GEOFENCES_FOR_KID", nickname = "GET_ALL_GEOFENCES_FOR_KID",
    		notes = "Get All Geofences for kid", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<GeofenceDTO>>> getAllGeofencesForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid) throws Throwable {
    	
    	logger.debug("Get all geofences for kid -> " + kid);
    	
    	// Get All Geofences by kid 
    	final Iterable<GeofenceDTO> geofencesList = geofenceService.allByKid(new ObjectId(kid));
    	
    	// No Geofences Found Exception
    	if(Iterables.size(geofencesList) == 0)
    		throw new NoGeofenceFoundException();
    	
    	
    	// Create and send response
    	return ApiHelper.<Iterable<GeofenceDTO>>createAndSendResponse(GeofenceResponseCode.ALL_GEOFENCES_FOR_KID, 
        		HttpStatus.OK, geofencesList);
    	
    }
    
    /**
     * Get Geofence By Id
     */
    @RequestMapping(value = "/{kid}/geofences/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "GET_GEOFENCE_BY_ID", nickname = "GET_GEOFENCE_BY_ID",
    		notes = "Get Geofence By Id", response = Iterable.class)
    public ResponseEntity<APIResponse<GeofenceDTO>> getGeofenceById(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "id", value = "Geofence Id", required = true)
     			@Valid @GeofenceShouldExists(message = "{geofence.should.be.exists}")
      				@PathVariable String id) throws Throwable {
    	
    	logger.debug("Get Geofence By Id -> " + kid);
    	
    	// Get Geofence By Id
    	final GeofenceDTO geofenceDTO = geofenceService.findById(new ObjectId(kid), new ObjectId(id));
    	
    	// Create and send response
    	return ApiHelper.<GeofenceDTO>createAndSendResponse(GeofenceResponseCode.GEOFENCE_DETAIL, 
        		HttpStatus.OK, geofenceDTO);
    	
    }
    
    
    /**
     * Get Geofence Alerts
     */
    @RequestMapping(value = "/{kid}/geofences/{id}/alerts", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "GET_GEOFENCE_ALERTS", nickname = "GET_GEOFENCE_ALERTS",
    		notes = "Get Geofence Alerts", response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<GeofenceAlertDTO>>> getGeofenceAlerts(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "id", value = "Geofence Id", required = true)
     			@Valid @GeofenceShouldExists(message = "{geofence.should.be.exists}")
      				@PathVariable String id) throws Throwable {
    	
    	logger.debug("Get Geofence Alerts");
    	
    	// Get Geofence Alerts
    	final Iterable<GeofenceAlertDTO> geofenceAlertList = 
    			geofenceService.findAlerts(new ObjectId(kid), new ObjectId(id));
    	
    	if(Iterables.isEmpty(geofenceAlertList))
    		throw new NoGeofenceAlertsFoundException();
    	
    	// Create and send response
    	return ApiHelper.<Iterable<GeofenceAlertDTO>>createAndSendResponse(GeofenceResponseCode.GEOFENCE_ALERT_LIST, 
        		HttpStatus.OK, geofenceAlertList);
    	
    }
    
    /**
     * Delete Geofence Alerts
     */
    @RequestMapping(value = "/{kid}/geofences/{id}/alerts", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_GEOFENCE_ALERTS", nickname = "DELETE_GEOFENCE_ALERTS",
    		notes = "Delete Geofence Alerts", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteGeofenceAlerts(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "id", value = "Geofence Id", required = true)
     			@Valid @GeofenceShouldExists(message = "{geofence.should.be.exists}")
      				@PathVariable String id) throws Throwable {
    	
    	logger.debug("Delete Geofence Alerts");
    	
    	// Delete Geofence Alerts
    	geofenceService.deleteAlerts(new ObjectId(kid), new ObjectId(id));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GeofenceResponseCode.ALL_GEOFENCE_ALERTS_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("all.geofence.alerts.deleted"));
    	
    }
    
    
    /**
     * Post Geofence Alert
     */
    @RequestMapping(value = "/{kid}/geofences/{id}/alerts", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "SAVE_GEOFENCE_ALERTS", nickname = "SAVE_GEOFENCE_ALERTS",
    		notes = "Save Geofence Alerts", response = GeofenceAlertDTO.class)
    public ResponseEntity<APIResponse<GeofenceAlertDTO>> saveGeofenceAlerts(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "id", value = "Geofence Id", required = true)
     			@Valid @GeofenceShouldExists(message = "{geofence.should.be.exists}")
      				@PathVariable String id,
      		@ApiParam(name="alert", value = "alert", required = true) 
				@Validated(ICommonSequence.class) 
					@RequestBody SaveGeofenceAlertDTO saveGeofenceAlertDTO) throws Throwable {
    	
    	logger.debug("Save Geofence Alerts");
    	
    	final GeofenceDTO geofence = 
    			geofenceService.findById(new ObjectId(kid), new ObjectId(id));
    
    
    	final String title = "Título de la Alerta", description = "Descripción de la Alerta";
    	
    	// Save Geofence Alert
    	final GeofenceAlertDTO geofenceAlertDTO = 
    			geofenceService.saveAlert(saveGeofenceAlertDTO.getKid(), saveGeofenceAlertDTO.getGeofence(),
    					saveGeofenceAlertDTO.getType(), title, description);
    	
    	 // Save Alert
    	alertService.save(AlertLevelEnum.DANGER, geofenceAlertDTO.getTitle(), geofenceAlertDTO.getDescription(), 
    			new ObjectId(kid), AlertCategoryEnum.GEOFENCES);
    
    	// Create and send response
    	return ApiHelper.<GeofenceAlertDTO>createAndSendResponse(GeofenceResponseCode.GEOFENCE_ALERT_SAVED, 
        		HttpStatus.OK, geofenceAlertDTO);
    	
    }
    
    
    /**
     * Delete Geofences for ids
     */
    @RequestMapping(value = "/{kid}/geofences/delete", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_GEOFENCES_FOR_KID", nickname = "DELETE_GEOFENCES_FOR_KID",
    		notes = "Delete Geofences For Kid", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteGeofencesForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name="ids", value = "ids", required = true) 
        		@Validated(ICommonSequence.class) 
        			@RequestBody ValidList<ObjectId> ids) throws Throwable {
    	
    	logger.debug("Delete Geofences for kid -> " + kid);
    	
    	// Delete By Kid and ids
    	geofenceService.deleteByKidAndIds(new ObjectId(kid), ids);
    	
    	// Push Event
    	this.applicationEventPublisher
    		.publishEvent(new GeofencesDeletedEvent(this, ids, kid));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GeofenceResponseCode.GEOFENCES_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("geofences.deleted"));
    	
    }
    
    /**
     * Delete Geofence By Id
     */
    @RequestMapping(value = "/{kid}/geofences/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_GEOFENCE_BY_ID", nickname = "DELETE_GEOFENCE_BY_ID",
    		notes = "Delete Geofence By Id", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteGeofenceById(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid,
          	@ApiParam(name = "id", value = "Geofence Id", required = true)
 				@Valid @GeofenceShouldExists(message = "{geofence.should.be.exists}")
  					@PathVariable String id) throws Throwable {
    	
		logger.debug("Delete Geofences by id -> " + id);
    	
    	// Delete Geofence By Id
    	geofenceService.deleteById(new ObjectId(kid), new ObjectId(id));
    	
    	// Push Event
    	this.applicationEventPublisher
    		.publishEvent(new GeofenceDeletedEvent(this, id, kid));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GeofenceResponseCode.SINGLE_GEOFENCE_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("single.geofence.deleted"));
    }
    
    /**
     * Delete all Geofences for kid
     */
    @RequestMapping(value = "/{kid}/geofences", method = RequestMethod.DELETE)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() "
    		+ "&& @authorizationService.isYourGuardian(#kid) )")
    @ApiOperation(
    		value = "DELETE_ALL_GEOFENCES_FOR_KID", nickname = "DELETE_ALL_GEOFENCES_FOR_KID",
    		notes = "Delete All Geofences For Kid", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteAllGeofencesForKid(
    		@ApiParam(name = "kid", value = "Kid Identifier", required = true)
         		@Valid @KidShouldExists(message = "{son.should.be.exists}")
          			@PathVariable String kid) throws Throwable {
    	
    	logger.debug("Delete all geofences for kid -> " + kid);
    
    	// Delete All By Kid
    	geofenceService.deleteAllByKid(new ObjectId(kid));
    	
    	// Push Event
    	this.applicationEventPublisher
    		.publishEvent(new AllGeofencesDeletedEvent(this, kid));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GeofenceResponseCode.ALL_GEOFENCES_DELETED, 
        		HttpStatus.OK, messageSourceResolver.resolver("all.geofences.deleted"));
    	
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
