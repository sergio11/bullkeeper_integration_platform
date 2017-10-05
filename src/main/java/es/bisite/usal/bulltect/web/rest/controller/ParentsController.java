package es.bisite.usal.bulltect.web.rest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;

import es.bisite.usal.bulltect.domain.service.IAlertService;
import es.bisite.usal.bulltect.domain.service.IAuthenticationService;
import es.bisite.usal.bulltect.domain.service.IIterationService;
import es.bisite.usal.bulltect.domain.service.IParentsService;
import es.bisite.usal.bulltect.domain.service.IPasswordResetTokenService;
import es.bisite.usal.bulltect.domain.service.ITokenGeneratorService;
import es.bisite.usal.bulltect.events.AccountDeletionRequestEvent;
import es.bisite.usal.bulltect.events.ParentRegistrationByFacebookSuccessEvent;
import es.bisite.usal.bulltect.events.ParentRegistrationSuccessEvent;
import es.bisite.usal.bulltect.events.PasswordResetEvent;
import es.bisite.usal.bulltect.persistence.constraints.ParentShouldExists;
import es.bisite.usal.bulltect.persistence.constraints.ValidObjectId;
import es.bisite.usal.bulltect.persistence.constraints.group.ICommonSequence;
import es.bisite.usal.bulltect.persistence.constraints.group.IResetPasswordSequence;
import es.bisite.usal.bulltect.rrss.service.IFacebookService;
import es.bisite.usal.bulltect.web.dto.request.JwtAuthenticationRequestDTO;
import es.bisite.usal.bulltect.web.dto.request.JwtFacebookAuthenticationRequestDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterSonDTO;
import es.bisite.usal.bulltect.web.dto.request.ResetPasswordRequestDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateParentDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateSonDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsPageDTO;
import es.bisite.usal.bulltect.web.dto.response.ImageDTO;
import es.bisite.usal.bulltect.web.dto.response.IterationDTO;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;
import es.bisite.usal.bulltect.web.dto.response.JwtAuthenticationResponseDTO;
import es.bisite.usal.bulltect.web.dto.response.ParentDTO;
import es.bisite.usal.bulltect.web.dto.response.PasswordResetTokenDTO;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import es.bisite.usal.bulltect.web.dto.response.ValidationErrorDTO;
import es.bisite.usal.bulltect.web.rest.ApiHelper;
import es.bisite.usal.bulltect.web.rest.exception.NoChildrenFoundForParentException;
import es.bisite.usal.bulltect.web.rest.exception.NoChildrenFoundForSelfParentException;
import es.bisite.usal.bulltect.web.rest.exception.NoIterationsFoundForSelfParentException;
import es.bisite.usal.bulltect.web.rest.exception.NoNewAlertsFoundException;
import es.bisite.usal.bulltect.web.rest.exception.NoParentsFoundException;
import es.bisite.usal.bulltect.web.rest.exception.ParentNotFoundException;
import es.bisite.usal.bulltect.web.rest.hal.IImageHAL;
import es.bisite.usal.bulltect.web.rest.hal.IParentHAL;
import es.bisite.usal.bulltect.web.rest.hal.ISonHAL;
import es.bisite.usal.bulltect.web.rest.response.APIResponse;
import es.bisite.usal.bulltect.web.rest.response.ParentResponseCode;
import es.bisite.usal.bulltect.web.security.userdetails.CommonUserDetailsAware;
import es.bisite.usal.bulltect.web.security.utils.CurrentUser;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessForAdmin;
import es.bisite.usal.bulltect.web.security.utils.OnlyAccessForParent;
import es.bisite.usal.bulltect.web.uploads.models.RequestUploadFile;
import es.bisite.usal.bulltect.web.uploads.models.UploadFileInfo;
import es.bisite.usal.bulltect.web.uploads.service.IUploadFilesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import javax.annotation.PostConstruct;

import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.annotations.ApiIgnore;

@RestController("RestParentsController")
@Validated
@RequestMapping("/api/v1/parents/")
@Api(tags = "parents", value = "/parents/", description = "Manejo de la información del tutor", produces = "application/json")
public class ParentsController extends BaseController implements IParentHAL, ISonHAL, IImageHAL {

    private static Logger logger = LoggerFactory.getLogger(ParentsController.class);
    
    private final IParentsService parentsService;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final IAuthenticationService authenticationService;
    private final IFacebookService facebookService;
    private final ITokenGeneratorService tokenGeneratorService;
    private final IUploadFilesService uploadFilesService;
    private final IAlertService alertService;
    private final IIterationService iterationService;
 
    public ParentsController(IParentsService parentsService, IPasswordResetTokenService passwordResetTokenService, 
    		IAuthenticationService authenticationService, IFacebookService facebookService, 
                ITokenGeneratorService tokenGeneratorService, IUploadFilesService uploadFilesService, IAlertService alertService,
                IIterationService iterationService) {
        this.parentsService = parentsService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.authenticationService = authenticationService;
        this.facebookService = facebookService;
        this.tokenGeneratorService = tokenGeneratorService;
        this.uploadFilesService = uploadFilesService;
        this.alertService = alertService;
        this.iterationService = iterationService;
    }
   
    
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ALL_PARENTS", nickname = "GET_ALL_PARENTS", 
            notes = "Get all Parents")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "", response = PagedResources.class)
    })
    public ResponseEntity<APIResponse<PagedResources<Resource<ParentDTO>>>> getAllParents(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<ParentDTO> pagedAssembler) throws Throwable {
        logger.debug("Get all Parents");
        
        Page<ParentDTO> parentPage = parentsService.findPaginated(pageable);
        
        if(parentPage.getTotalElements() == 0)
        	throw new NoParentsFoundException();
        
        return ApiHelper.<PagedResources<Resource<ParentDTO>>>createAndSendResponse(ParentResponseCode.ALL_PARENTS, 
        		HttpStatus.OK, pagedAssembler.toResource(addLinksToParents((parentPage))));
    }
    
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN", nickname = "GET_AUTHORIZATION_TOKEN", notes = "Get Parent Authorization Token ")
	@ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Authentication Success", response = JwtAuthenticationResponseDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getParentAuthorizationToken(
			@Valid @RequestBody JwtAuthenticationRequestDTO credentials, Device device) throws Throwable {
    	
    	JwtAuthenticationResponseDTO jwtResponseDTO = authenticationService.createAuthenticationTokenForParent(credentials.getEmail(), credentials.getPassword(), device);
    
    	return ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(
				ParentResponseCode.AUTHENTICATION_SUCCESS, HttpStatus.OK, jwtResponseDTO);
	}
    
    
    @RequestMapping(value = "/auth/facebook", method = RequestMethod.POST)
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN_VIA_FACEBOOK", nickname = "GET_AUTHORIZATION_TOKEN_VIA_FACEBOOK", notes = "Get Parent Authorization Token vi Facebook ")
	@ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Authentication Success", response = JwtAuthenticationResponseDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getParentAuthorizationTokenViaFacebook(
			@Valid @RequestBody JwtFacebookAuthenticationRequestDTO facebookInfo, Device device) throws Throwable {
    	
    	final String fbId = facebookService.getFbIdByAccessToken(facebookInfo.getToken());
    	
    	JwtAuthenticationResponseDTO jwtResponseDTO =  Optional.ofNullable(parentsService.getParentByFbId(fbId))
    			.map(parent -> {
    				parentsService.updateFbAccessToken(parent.getFbId(), facebookInfo.getToken());
    				return authenticationService.createAuthenticationTokenForParent(parent.getEmail(), parent.getFbId(), device);
    			})
    			.orElseGet(() -> {
    				RegisterParentByFacebookDTO registerParent = 
    						facebookService.getRegistrationInformationForTheParent(fbId, facebookInfo.getToken());
    				logger.debug(registerParent.toString());
    				ParentDTO parent = parentsService.save(registerParent);
    				// notify event
    				applicationEventPublisher.publishEvent(new ParentRegistrationByFacebookSuccessEvent(parent.getIdentity(), this));
    				return authenticationService.createAuthenticationTokenForParent(parent.getEmail(), parent.getFbId(), device);
    			});
    	
    	
    	return ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(
				ParentResponseCode.AUTHENTICATION_VIA_FACEBOOK_SUCCESS, HttpStatus.OK, jwtResponseDTO);
	}
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "GET_PARENT_BY_ID", nickname = "GET_PARENT_BY_ID", 
            notes = "Get Parent By Id")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Parent By Id", response = ParentDTO.class),
    		@ApiResponse(code = 404, message= "Parent Not Found")
    })
    public ResponseEntity<APIResponse<ParentDTO>> getParentById(
    		@ApiParam(name = "id", value = "Identificador del Padre", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}")
    				@PathVariable String id) throws Throwable {
        logger.debug("Get Parent with id: " + id);
        return Optional.ofNullable(parentsService.getParentById(id))
                .map(parentResource -> addLinksToParent(parentResource))
                .map(parentResource -> ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.SINGLE_PARENT, 
                		HttpStatus.OK, parentResource))
                .orElseThrow(() -> { throw new ParentNotFoundException(); });
    }
    
    @RequestMapping(value = "/{id}/lock", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "LOCK_PARENT_ACCOUNT", nickname = "LOCK_PARENT_ACCOUNT", 
            notes = "Lock Parent Account")
    public ResponseEntity<APIResponse<String>> lockAccount(
    		@ApiParam(name = "id", value = "Identificador del Padre", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}")
    				@ParentShouldExists(message = "{parent.not.exists}")
    				@PathVariable String id) throws Throwable {
        logger.debug("Lock Parent Account with id: " + id);
        
        parentsService.lockAccount(id);
 
        return ApiHelper.<String>createAndSendResponse(ParentResponseCode.ACCOUNT_LOCKED, HttpStatus.OK, 
        		messageSourceResolver.resolver("parents.locked"));
    }
    
    
    @RequestMapping(value = "/{id}/unlock", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "UNLOCK_PARENT_ACCOUNT", nickname = "UNLOCK_PARENT_ACCOUNT", 
            notes = "Unlock Parent Account")
    public ResponseEntity<APIResponse<String>> unlockAccount(
    		@ApiParam(name = "id", value = "Identificador del Padre", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}")
    				@ParentShouldExists(message = "{parent.not.exists}")
    				@PathVariable String id) throws Throwable {
        logger.debug("Unlock Parent Account with id: " + id);
        
        parentsService.unlockAccount(id);
 
        return ApiHelper.<String>createAndSendResponse(ParentResponseCode.ACCOUNT_UNLOCKED, HttpStatus.OK, 
        		messageSourceResolver.resolver("parents.unlocked"));
    }
    
    
    @RequestMapping(value = "/{id}/update",  method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "UPDATE_PARENT", nickname = "UPDATE_PARENT", notes="Update information for parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Update any Parent", response = ParentDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<ParentDTO>> updateParent(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
    		@ApiParam(name = "id", value = "Identificador del Padre", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}") 
    				@ParentShouldExists(message = "{parent.not.exists}")
    					@PathVariable String id,
    		@ApiParam(value = "parent", required = true) 
				@Valid @RequestBody UpdateParentDTO parent) throws Throwable {
    	
    	logger.debug("Update Parent");
        ParentDTO parentDTO = parentsService.update(new ObjectId(id), parent);
        
        return ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.PARENT_UPDATED_SUCCESSFULLY, 
        				HttpStatus.OK, addLinksToParent(parentDTO));
    }
    
    @RequestMapping(value = "/self", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_PARENT_SELF_INFORMATION", nickname = "GET_PARENT_SELF_INFORMATION", notes = "Get information from the currently authenticated parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Self Parent", response = ParentDTO.class),
    		@ApiResponse(code = 404, message= "Parent Not Found")
    })
    public ResponseEntity<APIResponse<ParentDTO>> getSelfInformation(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
    	
        logger.debug("Get Information for Parent with id: " + selfParent.getUserId());
        return Optional.ofNullable(parentsService.getParentById(selfParent.getUserId()))
                .map(parentResource -> addLinksToSelfParent(parentResource))
                .map(parentResource -> ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.SELF_PARENT, 
                		HttpStatus.OK, parentResource))
                .orElseThrow(() -> { throw new ParentNotFoundException(); });
    }
    
    
    @RequestMapping(value = "/self/image", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", nickname = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", notes = "Upload Profile Image For Self User")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message= "Profile Image", response = ImageDTO.class),
    	@ApiResponse(code = 500, message= "Upload Failed")
    })
    public ResponseEntity<APIResponse<ImageDTO>> uploadProfileImageForSelfUser(
            @RequestPart("profile_image") MultipartFile profileImage,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
        
        
        RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
        ImageDTO imageDto = uploadFilesService.uploadParentProfileImage(selfParent.getUserId(), uploadProfileImage);
        return ApiHelper.<ImageDTO>createAndSendResponse(ParentResponseCode.PROFILE_IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, addLinksToImage(imageDto));

    }

    @RequestMapping(value = "/self/image", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "DOWNLOAD_SELF_PROFILE_IMAGE", nickname = "DOWNLOAD_SELF_PROFILE_IMAGE", notes = "Download Self Profile Image")
    public ResponseEntity<byte[]> downloadProfileImage(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent
        ) throws IOException {
    	
    	logger.debug("DOWNLOAD_SELF_PROFILE_IMAGE");
        
    	UploadFileInfo imageInfo = null;
    	try {
    		imageInfo = uploadFilesService.getProfileImage(selfParent.getProfileImageId());
    		
    		if(imageInfo == null) {
    			
    			final org.springframework.core.io.Resource userDefault = resourceLoader.getResource("classpath:user_default.png");
        		imageInfo = new UploadFileInfo(userDefault.contentLength(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(userDefault.getInputStream()));
    		}
    		
    		
    	} catch (Exception ex) {
    		logger.debug("DOWNLOAD USER DEFAULT IMAGE");
    		final org.springframework.core.io.Resource userDefault = resourceLoader.getResource("classpath:user_default.png");
    		imageInfo = new UploadFileInfo(userDefault.contentLength(), MediaType.IMAGE_PNG_VALUE, IOUtils.toByteArray(userDefault.getInputStream()));

    	}
    	
        return ResponseEntity.ok()
                .contentLength(imageInfo.getSize())
                .contentType( imageInfo.getContentType() != null ?  MediaType.parseMediaType(imageInfo.getContentType()) : MediaType.IMAGE_PNG)
                .body(imageInfo.getContent());
    }
    
    @RequestMapping(value = "/self/image", method = RequestMethod.DELETE)
    @OnlyAccessForParent
    @ApiOperation(value = "DELETE_SELF_PROFILE_IMAGE", nickname = "DELETE_SELF_PROFILE_IMAGE", notes = "Delete Self Profile Image")
    public ResponseEntity<APIResponse<String>> deleteProfileImage(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) {
        uploadFilesService.deleteProfileImage(selfParent.getProfileImageId());
        return ApiHelper.<String>createAndSendResponse(ParentResponseCode.PROFILE_IMAGE_DELETED_SUCCESSFULLY, 
        		HttpStatus.OK, messageSourceResolver.resolver("image.deleted.successfully"));
    }
    
    
    @RequestMapping(value = "/",  method = RequestMethod.POST)
    @ApiOperation(value = "REGISTER_PARENT", nickname = "REGISTER_PARENT", notes="Register Parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Register Parent", response = ParentDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<ParentDTO>> registerParent(
    		@ApiParam(value = "parent", required = true) 
    			@Validated(ICommonSequence.class) @RequestBody RegisterParentDTO parent,
    		@ApiIgnore Locale locale) throws Throwable {
    	logger.debug("Register Parent");
    	logger.debug("Locale -> " + locale.toString());
    	parent.setLocale(locale);
        ParentDTO parentDTO = parentsService.save(parent);
        applicationEventPublisher.publishEvent(new ParentRegistrationSuccessEvent(parentDTO.getIdentity(), this));
        return ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.PARENT_REGISTERED_SUCCESSFULLY, 
        				HttpStatus.OK, addLinksToParent(parentDTO));
    }
   
    
    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "GET_CHILDREN_OF_PARENT", nickname = "GET_CHILDREN_OF_PARENT", 
            notes = "Get Children of Parent", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<Iterable<SonDTO>>> getChildrenOfParent(
    		@ApiParam(name = "id", value = "Identificador del Padre", required = true)
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}") 
    				@ParentShouldExists(message = "{parent.not.exists}")
    					@PathVariable String id) throws Throwable {
        logger.debug("Get Children of Parent with id: " + id);
        
        Iterable<SonDTO> childrenOfParent = parentsService.getChildrenOfParent(id);
        if(Iterables.size(childrenOfParent) == 0)
        	throw new NoChildrenFoundForParentException();
        return ApiHelper.<Iterable<SonDTO>>createAndSendResponse(ParentResponseCode.CHILDREN_OF_PARENT, 
        		HttpStatus.OK, addLinksToChildren((childrenOfParent)));
        
    }
    
    @RequestMapping(value = "/self/children", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_CHILDREN_OF_SELF_PARENT", nickname = "GET_CHILDREN_OF_SELF_PARENT", 
            notes = "Get Children for the currently authenticated parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Children of Parent", response = SonDTO.class)
    })
    public ResponseEntity<APIResponse<Iterable<SonDTO>>> getChildrenOfSelfParent(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
        logger.debug("Get Children of Self Parent");
        
        Iterable<SonDTO> childrenOfParent = parentsService.getChildrenOfParent(selfParent.getUserId().toString());
        
        if(Iterables.size(childrenOfParent) == 0)
        	throw new NoChildrenFoundForSelfParentException();
        
        return ApiHelper.<Iterable<SonDTO>>createAndSendResponse(ParentResponseCode.CHILDREN_OF_SELF_PARENT, 
        		HttpStatus.OK, addLinksToChildren((childrenOfParent)));
   
    }
    
    
    @RequestMapping(value = "/self/alerts", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_ALERTS_FOR_SELF_PARENT", nickname = "GET_ALERTS_FOR_SELF_PARENT", 
            notes = "Get Alerts For Self Parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Alerts", response = AlertsPageDTO.class)
    })
    public ResponseEntity<APIResponse<AlertsPageDTO>> getAlertsForSelfParent(
    		@RequestParam(value = "count", required=false, defaultValue="10") Integer count,
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
    	
        logger.debug("Get " + count + " Alerts For Self Parent");
        
        AlertsPageDTO alertsPageDTO = alertService.getAlerts(selfParent.getUserId(), selfParent.getLastAccessToAlerts(), count);
        // Update Last Access To Alerts
        parentsService.updateLastAccessToAlerts(selfParent.getUserId());
        
        if(Iterables.size(alertsPageDTO.getAlerts()) == 0)
        	throw new NoNewAlertsFoundException();
  
        return ApiHelper.<AlertsPageDTO>createAndSendResponse(ParentResponseCode.ALERTS_FOR_SELF_PARENT, 
        		HttpStatus.OK, alertsPageDTO);
   
    }
    
    @RequestMapping(value = "/self/iterations", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_LAST_ITERATIONS_FOR_SELF_PARENT", nickname = "GET_LAST_ITERATIONS_FOR_SELF_PARENT", 
            notes = "Get Last Iteration For Self Parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Iterations List", response = IterationDTO.class)
    })
    public ResponseEntity<APIResponse<List<IterationDTO>>> getIterationsForSelfParent(
    		@RequestParam(value = "count", required=false, defaultValue="10") Integer count,
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
    	
    	
        logger.debug("Get " + count + " Iterations For Self Parent");
        
        List<IterationDTO> iterations = iterationService.getLastIterationsByParent(selfParent.getUserId(), count);
        
        if(iterations.size() == 0)
        	throw new NoIterationsFoundForSelfParentException();
        
  
        return ApiHelper.<List<IterationDTO>>createAndSendResponse(ParentResponseCode.LAST_ITERATIONS_FOR_SELF_PARENT, 
        		HttpStatus.OK, iterations);
   
    }
    
    @RequestMapping(value = "/self/iterations/last", method = RequestMethod.GET)
    @OnlyAccessForParent
    @ApiOperation(value = "GET_LAST_ITERATION_FOR_SELF_PARENT", nickname = "GET_LAST_ITERATION_FOR_SELF_PARENT", 
            notes = "Get Last Iteration For Self Parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Get Last Iteration", response = IterationDTO.class)
    })
    public ResponseEntity<APIResponse<IterationWithTasksDTO>> getLastIterationForSelfParent(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
    	
        logger.debug("Get Last Iteration");
        
        return Optional.ofNullable(iterationService.getLastIterationByParent(selfParent.getUserId()))
                .map(lastIteration -> ApiHelper.<IterationWithTasksDTO>createAndSendResponse(ParentResponseCode.LAST_ITERATION_FOR_SELF_PARENT, 
                		HttpStatus.OK, lastIteration))
                .orElseThrow(() -> { throw new NoIterationsFoundForSelfParentException(); });
   
    }
    
    
    @RequestMapping(value = "/self/reset-password",  method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "SELF_RESET_PASSWORD", nickname = "SELF_RESET_PASSWORD", notes="Reset Password For Self User")
    public ResponseEntity<APIResponse<String>> selfResetPassword(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent){
    	
    	logger.debug("Reset Password");
    	
    	final String userId = selfParent.getUserId().toString();
    	
    	PasswordResetTokenDTO resetPasswordToken  = Optional.ofNullable(passwordResetTokenService.getPasswordResetTokenForUser(userId))
    		.orElseGet(() -> passwordResetTokenService.createPasswordResetTokenForUser(userId));
    	
    	applicationEventPublisher.publishEvent(new PasswordResetEvent(this, resetPasswordToken));
    	
    	return ApiHelper.<String>createAndSendResponse(ParentResponseCode.PARENT_RESET_PASSWORD_REQUEST, 
        		HttpStatus.OK, messageSourceResolver.resolver("parent.password.reseted"));	
    }
    
    
    @RequestMapping(value = "/reset-password",  method = RequestMethod.POST)
    @ApiOperation(value = "RESET_PASSWORD", nickname = "RESET_PASSWORD", notes="Reset Password")
    public ResponseEntity<APIResponse<String>> resetPassword(
    		@ApiParam(value = "resetPasswordRequest", required = true) 
			@Validated(IResetPasswordSequence.class) @RequestBody  ResetPasswordRequestDTO request){
    	
    	logger.debug("Reset Password");
    	
    	final ParentDTO parent = parentsService.getParentByEmail(request.getEmail());
    	
    	PasswordResetTokenDTO resetPasswordToken  = Optional.ofNullable(passwordResetTokenService.getPasswordResetTokenForUser(parent.getIdentity()))
    		.orElseGet(() -> passwordResetTokenService.createPasswordResetTokenForUser(parent.getIdentity()));
    	
    	applicationEventPublisher.publishEvent(new PasswordResetEvent(this, resetPasswordToken));
    	
    	return ApiHelper.<String>createAndSendResponse(ParentResponseCode.PARENT_RESET_PASSWORD_REQUEST, 
        		HttpStatus.OK, messageSourceResolver.resolver("parent.password.reseted"));	
    }
    
    @RequestMapping(value = { "/self", "/self/update" },  method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "UPDATE_SELF_PARENT", nickname = "UPDATE_SELF_PARENT", notes="Update information for self parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Update Parent", response = ParentDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<ParentDTO>> updateSelfParent(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
    		@ApiParam(value = "parent", required = true) 
    			@Validated(ICommonSequence.class) @RequestBody UpdateParentDTO parent) throws Throwable {
    	
    	logger.debug("Update Parent");
        ParentDTO parentDTO = parentsService.update(selfParent.getUserId(), parent);
        
        return ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.SELF_PARENT_UPDATED_SUCCESSFULLY, 
        				HttpStatus.OK, addLinksToParent(parentDTO));
    }
    
    
    @RequestMapping(value = { "/self", "/self/delete" },  method = RequestMethod.DELETE)
    @OnlyAccessForParent
    @ApiOperation(value = "DELETE_SELF_PARENT", nickname = "DELETE_SELF_PARENT", notes="Request deletion of the father's account")
    public ResponseEntity<APIResponse<String>> deleteSelfParent(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent) throws Throwable {
    	
    	logger.debug("Delete Parent");
    	logger.debug(selfParent.toString());
    	
    	String confirmationToken = tokenGeneratorService.generateToken(selfParent.getFirstName());
    	
    	parentsService.startAccountDeletionProcess(selfParent.getUserId(), confirmationToken);
    	
    	//notify event
    	applicationEventPublisher.publishEvent(new AccountDeletionRequestEvent(selfParent.getUserId().toString(), confirmationToken, this));
    	
        return ApiHelper.<String>createAndSendResponse(ParentResponseCode.SUCCESSFUL_ACCOUNT_DELETION_REQUEST, 
        				HttpStatus.OK, messageSourceResolver.resolver("parents.delete.pending"));
    }
    
    
    @RequestMapping(value = "/{id}/children/add", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasParentRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "ADD_SON_TO_PARENT", nickname = "ADD_SON_TO_PARENT", notes="Add son to parent for analysis")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Son Registered", response = SonDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<SonDTO>> addSonToParent(
    		@ApiParam(name = "id", value = "Identificador del Padre", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}") 
    				@ParentShouldExists(message = "{parent.not.exists}")
    					@PathVariable String id,
    		@ApiParam(value = "son", required = true) 
    			@Valid @RequestBody RegisterSonDTO son) throws Throwable {
    	logger.debug("Add Son To Parent");
    	
    	SonDTO sonDTO = parentsService.addSon(id, son);
    	
    	return ApiHelper.<SonDTO>createAndSendResponse(ParentResponseCode.ADDED_SON_TO_PARENT, 
				HttpStatus.OK, addLinksToSon(sonDTO));
    }
    
    @RequestMapping(value = "/self/children/add", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "ADD_SON_TO_SELF_PARENT", nickname = "ADD_SON_TO_SELF_PARENT", 
    	notes="Add son to currently authenticated parent for analysis")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Son Registered to self parent", response = SonDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<SonDTO>> addSonToSelfParent(
    		@ApiParam(hidden = true) 
    			@CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
    		@ApiParam(value = "son", required = true) 
    			@Valid @RequestBody RegisterSonDTO son) throws Throwable {
    	
    	logger.debug("Add Son To Self Parent");
    	
    	SonDTO sonDTO = parentsService.addSon(selfParent.getUserId().toString(), son);
    	
    	return ApiHelper.<SonDTO>createAndSendResponse(ParentResponseCode.ADDED_SON_TO_SELF_PARENT, 
				HttpStatus.OK, addLinksToSon(sonDTO));
    }
    
    
    @RequestMapping(value = "/self/children/update", method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "SAVE_SON_INFORMATION", nickname = "SAVE_SON_INFORMATION", 
    	notes="Update son for currently authenticated parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Son To Add or Update", response = SonDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<SonDTO>> saveSonInformation(
    		@ApiParam(hidden = true) 
    			@CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
    		@ApiParam(value = "son", required = true) 
    			@Valid @RequestBody UpdateSonDTO son) throws Throwable {
    	
    	logger.debug("Save Son Information");
    	
    	SonDTO sonDTO = parentsService.updateSon(selfParent.getUserId().toString(), son);
    	
    	return ApiHelper.<SonDTO>createAndSendResponse(ParentResponseCode.SAVE_SON_INFORMATION, 
				HttpStatus.OK, addLinksToSon(sonDTO));
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(parentsService, "Parent Service can not be null");
        Assert.notNull(uploadFilesService, "Upload Files Service can not be null");
        Assert.notNull(passwordResetTokenService, "Password Reset Token Service can not be null");
        Assert.notNull(authenticationService, "Authentication Service can not be null");
        Assert.notNull(facebookService, "FacebookService can not be null");
        Assert.notNull(tokenGeneratorService, "TokenGeneratorService can not be null");
        Assert.notNull(uploadFilesService, "UploadFilesService can not be null");
        Assert.notNull(alertService, "Alert Service can not be null");
        Assert.notNull(iterationService, "Iteration Service can not be null");

    }
    
}
