package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAlertService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthenticationService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IDeletePendingEmailService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IGuardianService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IKidService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IPasswordResetTokenService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.ITokenGeneratorService;
import sanchez.sanchez.sergio.bullkeeper.events.AccountDeletionRequestEvent;
import sanchez.sanchez.sergio.bullkeeper.events.ParentRegistrationByFacebookSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.ParentRegistrationByGoogleSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.ParentRegistrationSuccessEvent;
import sanchez.sanchez.sergio.bullkeeper.events.PasswordResetEvent;
import sanchez.sanchez.sergio.bullkeeper.exception.GuardianNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoAlertsFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoChildrenFoundForGuardianException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoChildrenFoundForSelfGuardianException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoGuardiansFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSupervisedChildrenConfirmedFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.NoSupervisedChildrenNoConfirmedFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.SupervisedChildrenNoConfirmedNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GuardianShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SupervisedChildrenShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.ICommonSequence;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IResendActivationEmailSequence;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.IResetPasswordSequence;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.EmailTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.rrss.service.IFacebookService;
import sanchez.sanchez.sergio.bullkeeper.rrss.service.IGoogleService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.JwtAuthenticationRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.JwtSocialAuthenticationRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByFacebookDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByGoogleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.ResendActivationEmailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.ResetPasswordRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveUserSystemPreferencesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.UpdateGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.UpdateKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsPageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ImageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.JwtAuthenticationResponseDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PasswordResetTokenDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SupervisedChildrenDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.UserSystemPreferencesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.IGuardianHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.hal.IKidHAL;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.GuardianResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForGuardian;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.models.RequestUploadFile;
import sanchez.sanchez.sergio.bullkeeper.web.uploads.service.IUploadFilesService;
import io.swagger.annotations.ApiResponse;
import javax.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@RestController("RestGuardiansController")
@Validated
@RequestMapping("/api/v1/guardians/")
@Api(tags = "guardians", value = "/guardians/", 
	description = "Administration of parents/guardians of the platform", 
		produces = "application/json")
public class GuardiansController extends BaseController implements IGuardianHAL, IKidHAL {

    private static Logger logger = LoggerFactory.getLogger(GuardiansController.class);
    
    private final IGuardianService guardiansService;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final IAuthenticationService authenticationService;
    private final IFacebookService facebookService;
    private final ITokenGeneratorService tokenGeneratorService;
    private final IUploadFilesService uploadFilesService;
    private final IAlertService alertService;
    private final IDeletePendingEmailService deletePendingEmailService;
    private final IGoogleService googleService;
    private final IKidService kidService;
 
    /**
     * 
     * @param guardiansService
     * @param passwordResetTokenService
     * @param authenticationService
     * @param facebookService
     * @param tokenGeneratorService
     * @param uploadFilesService
     * @param alertService
     * @param deletePendingEmailService
     * @param googleService
     * @param kidService
     */
    public GuardiansController(IGuardianService guardiansService, IPasswordResetTokenService passwordResetTokenService, 
    		IAuthenticationService authenticationService, IFacebookService facebookService, 
                ITokenGeneratorService tokenGeneratorService, IUploadFilesService uploadFilesService, IAlertService alertService,
                 IDeletePendingEmailService deletePendingEmailService, IGoogleService googleService,
                 final IKidService kidService) {
        this.guardiansService = guardiansService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.authenticationService = authenticationService;
        this.facebookService = facebookService;
        this.tokenGeneratorService = tokenGeneratorService;
        this.uploadFilesService = uploadFilesService;
        this.alertService = alertService;
        this.deletePendingEmailService = deletePendingEmailService;
        this.googleService = googleService;
        this.kidService = kidService;
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
    @ApiOperation(value = "GET_ALL_PARENTS", nickname = "GET_ALL_PARENTS", 
            notes = "Get all Parents")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "", response = PagedResources.class)
    })
    public ResponseEntity<APIResponse<PagedResources<Resource<GuardianDTO>>>> getAllParents(
    		@ApiIgnore @PageableDefault Pageable pageable, 
    		@ApiIgnore PagedResourcesAssembler<GuardianDTO> pagedAssembler) throws Throwable {
        logger.debug("Get all Parents");
        
        Page<GuardianDTO> parentPage = guardiansService.findPaginated(pageable);
        
        if(parentPage.getTotalElements() == 0)
        	throw new NoGuardiansFoundException();
        
        return ApiHelper.<PagedResources<Resource<GuardianDTO>>>createAndSendResponse(GuardianResponseCode.ALL_GUARDIANS, 
        		HttpStatus.OK, pagedAssembler.toResource(addLinksToGuardian((parentPage))));
    }
    
    /**
     * 
     * @param credentials
     * @param device
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN", nickname = "GET_AUTHORIZATION_TOKEN", notes = "Get Parent Authorization Token ")
	@ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Authentication Success", response = JwtAuthenticationResponseDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getParentAuthorizationToken(
			@Valid @RequestBody JwtAuthenticationRequestDTO credentials, Device device) throws Throwable {
    	
    	JwtAuthenticationResponseDTO jwtResponseDTO = authenticationService.createAuthenticationTokenForGuardian(credentials.getEmail(), credentials.getPassword(), device);
    
    	return ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(
				GuardianResponseCode.AUTHENTICATION_SUCCESS, HttpStatus.OK, jwtResponseDTO);
	}
    
    /**
     * 
     * @param socialAuthRequest
     * @param device
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/auth/facebook", method = RequestMethod.POST)
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN_VIA_FACEBOOK", nickname = "GET_AUTHORIZATION_TOKEN_VIA_FACEBOOK", notes = "Get Parent Authorization Token vi Facebook ")
	@ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Authentication Success", response = JwtAuthenticationResponseDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getParentAuthorizationTokenViaFacebook(
			@Valid @RequestBody JwtSocialAuthenticationRequestDTO socialAuthRequest, Device device) throws Throwable {
    	
    	final String fbId = facebookService.getFbIdByAccessToken(socialAuthRequest.getToken());
    	
    	logger.debug("Facebook ID -> " + fbId);
        
    	JwtAuthenticationResponseDTO jwtResponseDTO =  Optional.ofNullable(guardiansService.getGuardianByFbId(fbId))
    			.map(parent -> {
    				logger.debug("User Already Registered, update token with  -> " + socialAuthRequest.getToken());
    				guardiansService.updateFbAccessToken(parent.getFbId(), socialAuthRequest.getToken());
    				return authenticationService.createAuthenticationTokenForGuardian(parent.getEmail(), parent.getFbId(), device);
    			})
    			.orElseGet(() -> {
    				
    				logger.debug("Register user with facebook information ");
    				RegisterGuardianByFacebookDTO registerParent = 
    						facebookService.getRegistrationInformationForTheParent(fbId, socialAuthRequest.getToken());
    			
    				logger.debug(registerParent.toString());
    				
    				GuardianDTO parent = guardiansService.save(registerParent);
                    String profileImageUrl = facebookService.fetchUserPicture(socialAuthRequest.getToken());
                    if(profileImageUrl != null && !profileImageUrl.isEmpty())
                            uploadFilesService.uploadGuardianProfileImageFromUrl(new ObjectId(parent.getIdentity()), profileImageUrl);
    				// notify event
    				applicationEventPublisher.publishEvent(new ParentRegistrationByFacebookSuccessEvent(parent.getIdentity(), this));
    				return authenticationService.createAuthenticationTokenForGuardian(parent.getEmail(), parent.getFbId(), device);
    			});
       
    	
    	return ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(
				GuardianResponseCode.AUTHENTICATION_VIA_FACEBOOK_SUCCESS, HttpStatus.OK, jwtResponseDTO);
	}
    
    /**
     * 
     * @param socialAuthRequest
     * @param device
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/auth/google", method = RequestMethod.POST)
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN_VIA_GOOGLE", nickname = "GET_AUTHORIZATION_TOKEN_VIA_GOOGLE", notes = "Get Parent Authorization Token vi Google ")
	@ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Authentication Success", response = JwtAuthenticationResponseDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getParentAuthorizationTokenViaGoogle(
			@Valid @RequestBody JwtSocialAuthenticationRequestDTO socialAuthRequest, Device device) throws Throwable {
    	
    	logger.debug("Get Authorization Token via Google -> " + socialAuthRequest.getToken());
    	
    	final String googleId = googleService.getGoogleIdByAccessToken(socialAuthRequest.getToken());
    	
    	logger.debug("Google ID -> " + googleId);
        
    	JwtAuthenticationResponseDTO jwtResponseDTO =  Optional.ofNullable(guardiansService.getGuardianByGoogleId(googleId))
    			.map(parent -> {
    				logger.debug("User Already Registered, update token with  -> " + socialAuthRequest.getToken());
    				return authenticationService.createAuthenticationTokenForGuardian(parent.getEmail(), parent.getGoogleId(), device);
    			})
    			.orElseGet(() -> {
    				
    				logger.debug("Register user with Google information ");
    				RegisterGuardianByGoogleDTO registerParent = googleService.getUserInfo(socialAuthRequest.getToken());
    			
    				logger.debug(registerParent.toString());

    				GuardianDTO parent = guardiansService.save(registerParent);
    				
    				if(registerParent.getPicture() != null && !registerParent.getPicture().isEmpty())
    					uploadFilesService.uploadGuardianProfileImageFromUrl(new ObjectId(parent.getIdentity()), registerParent.getPicture());

    				// notify event
    				applicationEventPublisher.publishEvent(new ParentRegistrationByGoogleSuccessEvent(parent.getIdentity(), this));
    				return authenticationService.createAuthenticationTokenForGuardian(parent.getEmail(), parent.getGoogleId(), device);
    			});
       
    	return ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(
				GuardianResponseCode.AUTHENTICATION_VIA_GOOGLE_SUCCESS, HttpStatus.OK, jwtResponseDTO);
	}
    
    /**
     * 
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "GET_GUARDIAN_BY_ID", nickname = "GET_GUARDIAN_BY_ID", 
            notes = "Get Guardian By Id")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Guardian By Id", response = GuardianDTO.class),
    		@ApiResponse(code = 404, message= "Guardian Not Found")
    })
    public ResponseEntity<APIResponse<GuardianDTO>> getGuardianById(
    		@ApiParam(name = "id", value = "Guardian Identifier", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}")
    				@PathVariable String id) throws Throwable {
        logger.debug("Get Guardian with id: " + id);
        return Optional.ofNullable(guardiansService.getGuardianById(id))
                .map(guardianResource -> addLinksToParent(guardianResource))
                .map(guardianResource -> ApiHelper.<GuardianDTO>createAndSendResponse(GuardianResponseCode.SINGLE_GUARDIAN, 
                		HttpStatus.OK, guardianResource))
                .orElseThrow(() -> { throw new GuardianNotFoundException(); });
    }
    
    /**
     * Lock Guardian Account
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/lock", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "LOCK_GUARDIAN_ACCOUNT", nickname = "LOCK_GUARDIAN_ACCOUNT", 
            notes = "Lock Guardian Account")
    public ResponseEntity<APIResponse<String>> lockAccount(
    		@ApiParam(name = "id", value = "Guardian Identifier", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}")
    				@GuardianShouldExists(message = "{parent.not.exists}")
    				@PathVariable String id) throws Throwable {
    	
        logger.debug("Lock Guardian Account with id: " + id);
        // Lock Account
        guardiansService.lockAccount(id);
        // Create and Send Response
        return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.ACCOUNT_LOCKED, HttpStatus.OK, 
        		messageSourceResolver.resolver("parents.locked"));
    }
    
    /**
     * Unlock Guardian Account
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/unlock", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "UNLOCK_GUARDIAN_ACCOUNT", nickname = "UNLOCK_GUARDIAN_ACCOUNT", 
            notes = "Unlock Guardian Account")
    public ResponseEntity<APIResponse<String>> unlockAccount(
    		@ApiParam(name = "id", value = "Guardian Identifier", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}")
    				@GuardianShouldExists(message = "{parent.not.exists}")
    				@PathVariable String id) throws Throwable {
    	
        logger.debug("Unlock Guardian Account with id: " + id);
        // Unlock Account
        guardiansService.unlockAccount(id);
        // Crate and send response
        return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.ACCOUNT_UNLOCKED, 
        		HttpStatus.OK, messageSourceResolver.resolver("parents.unlocked"));
    }
    
    /**
     * 
     * @param selfGuardian
     * @param id
     * @param updateGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/update",  method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "UPDATE_GUARDIAN", nickname = "UPDATE_GUARDIAN", 
    	notes="Update information for guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Update any Guardian", response = GuardianDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<GuardianDTO>> updateGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "id", value = "Guardian Identifier", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}") 
    				@GuardianShouldExists(message = "{parent.not.exists}")
    					@PathVariable String id,
    		@ApiParam(value = "parent", required = true) 
				@Valid @RequestBody UpdateGuardianDTO updateGuardian) throws Throwable {
    	
    	logger.debug("Update Guardian");
        final GuardianDTO parentDTO = guardiansService.update(new ObjectId(id), updateGuardian);
        // Create and Send Response
        return ApiHelper.<GuardianDTO>createAndSendResponse(GuardianResponseCode.GUARDIAN_UPDATED_SUCCESSFULLY, 
        				HttpStatus.OK, addLinksToParent(parentDTO));
    }
    
    
    /**
     * 
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_GUARDIAN_SELF_INFORMATION", nickname = "GET_GUARDIAN_SELF_INFORMATION",
    	notes = "Get information from the currently authenticated guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Self Guardian", response = GuardianDTO.class),
    		@ApiResponse(code = 404, message= "Guardian Not Found")
    })
    public ResponseEntity<APIResponse<GuardianDTO>> getSelfInformation(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
        logger.debug("Get Information for Guardian with id: " + selfGuardian.getUserId());
        return Optional.ofNullable(guardiansService.getGuardianById(selfGuardian.getUserId()))
                .map(guardianResource -> addLinksToSelfGuardian(guardianResource))
                .map(guardianResource -> ApiHelper.<GuardianDTO>createAndSendResponse(GuardianResponseCode.SELF_GUARDIAN, 
                		HttpStatus.OK, guardianResource))
                .orElseThrow(() -> { throw new GuardianNotFoundException(); });
    }
    
    /**
     * 
     * @param profileImage
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/image", method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", 
    	nickname = "UPLOAD_PROFILE_IMAGE_FOR_SELF_USER", 
    	notes = "Upload Profile Image For Self User")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message= "Profile Image", response = ImageDTO.class),
    	@ApiResponse(code = 500, message= "Upload Failed")
    })
    public ResponseEntity<APIResponse<ImageDTO>> uploadProfileImageForSelfUser(
            @RequestPart("profile_image") MultipartFile profileImage,
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
    	RequestUploadFile uploadProfileImage = new RequestUploadFile(profileImage.getBytes(), 
                profileImage.getContentType() != null ? profileImage.getContentType() : MediaType.IMAGE_PNG_VALUE, profileImage.getOriginalFilename());
    	ImageDTO imageDTO = uploadFilesService.uploadGuardianProfileImage(selfGuardian.getUserId(), uploadProfileImage);
        return ApiHelper.<ImageDTO>createAndSendResponse(GuardianResponseCode.PROFILE_IMAGE_UPLOAD_SUCCESSFULLY, 
        		HttpStatus.OK, imageDTO);

    }
    
    /**
     * 
     * @param selfGuardian
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/self/image", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DOWNLOAD_SELF_PROFILE_IMAGE", nickname = "DOWNLOAD_SELF_PROFILE_IMAGE", notes = "Download Self Profile Image")
    public ResponseEntity<byte[]> downloadProfileImage(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian
        ) throws IOException {
    	
    	// Get Profile Image
        final String profileImage = guardiansService.getProfileImage(selfGuardian.getUserId());
        logger.debug("Download self profile image image with id -> " + profileImage);
        // Download Profile Image
        return controllerHelper.downloadProfileImage(profileImage);
    }
    
    /**
     * 
     * @param selfGuardian
     * @return
     */
    @RequestMapping(value = "/self/image", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_SELF_PROFILE_IMAGE", nickname = "DELETE_SELF_PROFILE_IMAGE", notes = "Delete Self Profile Image")
    public ResponseEntity<APIResponse<String>> deleteProfileImage(
            @ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) {
        
    	// Get Profile Image
    	final String profileImage = guardiansService.getProfileImage(selfGuardian.getUserId());
    	// Delete Image
        uploadFilesService.deleteImage(profileImage);
        // Create and Send response
        return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.PROFILE_IMAGE_DELETED_SUCCESSFULLY, 
        		HttpStatus.OK, messageSourceResolver.resolver("image.deleted.successfully"));
    }
    
    /**
     * 
     * @param guardian
     * @param locale
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/",  method = RequestMethod.POST)
    @ApiOperation(value = "REGISTER_GUARDIAN", nickname = "REGISTER_GUARDIAN", notes="Register Guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Register Guardian", response = GuardianDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<GuardianDTO>> registerGuardian(
    		@ApiParam(value = "guardian", required = true) 
    			@Validated(ICommonSequence.class) 
    				@RequestBody final RegisterGuardianDTO guardian,
    		@ApiIgnore final Locale locale) throws Throwable {
    	
    	guardian.setLocale(locale);
    	logger.debug("Register Guardian -> " + guardian.toString());
    	// Save Guardian
        GuardianDTO guardianDTO = guardiansService.save(guardian);
        applicationEventPublisher.publishEvent(new ParentRegistrationSuccessEvent(guardianDTO.getIdentity(), this));
        // Create and Send Response
        return ApiHelper.<GuardianDTO>createAndSendResponse(GuardianResponseCode.GUARDIAN_REGISTERED_SUCCESSFULLY, 
        				HttpStatus.OK, addLinksToParent(guardianDTO));
    }
    
    /**
     * 
     * @param resendActivation
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/resend-activation-email",  method = RequestMethod.POST)
    @ApiOperation(value = "RESEND_ACTIVATION_EMAIL", nickname = "RESEND_ACTIVATION_EMAIL", notes="Resend Activation Email")
    public ResponseEntity<APIResponse<String>> resendActivationEmail(
    		@ApiParam(value = "parent", required = true) 
    			@Validated(IResendActivationEmailSequence.class) 
    			@RequestBody ResendActivationEmailDTO resendActivation) throws Throwable {
    	
    	logger.debug("Resend Activation Email");
    	
    	deletePendingEmailService.deleteBySendToAndType(resendActivation.getEmail(), EmailTypeEnum.ACTIVATE_ACCOUNT);
        GuardianDTO parentDTO = guardiansService.getGuardianByEmail(resendActivation.getEmail());
        applicationEventPublisher.publishEvent(new ParentRegistrationSuccessEvent(parentDTO.getIdentity(), this));
        // Create and Send Response
        return ApiHelper.<String>createAndSendResponse(
                GuardianResponseCode.ACCOUNT_ACTIVATION_EMAIL_SENT, HttpStatus.OK, 
                messageSourceResolver.resolver("account.activation.email.sent"));
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "GET_CHILDREN_OF_GUARDIAN", nickname = "GET_CHILDREN_OF_GUARDIAN", 
            notes = "Get Children of Guardian", response = ResponseEntity.class)
    public ResponseEntity<APIResponse<Iterable<SupervisedChildrenDTO>>> getChildrenOfGuardian(
    		@ApiParam(name = "id", value = "Guardian Identfier", required = true)
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}") 
    				@GuardianShouldExists(message = "{parent.not.exists}")
    					@PathVariable String id) throws Throwable {
        logger.debug("Get Children of Guardian with id: " + id);
        // Get Kids Of Guardian
        Iterable<SupervisedChildrenDTO> supervisedChildren = guardiansService.getKidsOfGuardian(id);
        // check list
        if(Iterables.size(supervisedChildren) == 0)
        	throw new NoChildrenFoundForGuardianException();
        // Create and Send Response
        return ApiHelper.<Iterable<SupervisedChildrenDTO>>createAndSendResponse(GuardianResponseCode.CHILDREN_OF_GUARDIAN, 
        		HttpStatus.OK, addLinksToSupervisedChildren((supervisedChildren)));
        
    }
    
    /**
     * Get Children of self Guardian
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_CHILDREN_OF_SELF_GUARDIAN", nickname = "GET_CHILDREN_OF_SELF_GUARDIAN", 
            notes = "Get Children for the currently authenticated guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Children of Guardian", response = KidDTO.class)
    })
    public ResponseEntity<APIResponse<Iterable<SupervisedChildrenDTO>>> getChildrenOfSelfGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
        
    	logger.debug("Get Children of Self Guardian");
        
        // Get Supervised Children
        Iterable<SupervisedChildrenDTO> supervisedChildrenList = 
        		guardiansService.getKidsOfGuardian(selfGuardian.getUserId().toString());
        
        if(Iterables.size(supervisedChildrenList) == 0)
        	throw new NoChildrenFoundForSelfGuardianException();
        
        // Create and Send Response
        return ApiHelper.<Iterable<SupervisedChildrenDTO>>createAndSendResponse(GuardianResponseCode.CHILDREN_OF_SELF_GUARDIAN, 
        		HttpStatus.OK, addLinksToSupervisedChildren((supervisedChildrenList)));
   
    }
    
    /**
     * Get Alerts For Self Guardian
     * @param selfGuardian
     * @param count
     * @param from
     * @param levels
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_ALERTS_FOR_SELF_GUARDIAN", nickname = "GET_ALERTS_FOR_SELF_GUARDIAN", 
            notes = "Get Alerts For Self Guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Alerts", response = AlertDTO.class)
    })
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getAlertsForSelfGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "count", value = "Number of alerts", required = false)
				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from,
			@ApiParam(name = "levels", value = "Alert Levels", required = false)	
				@RequestParam(name="levels" , required=false)
        			AlertLevelEnum[] levels) throws Throwable {
    	
        logger.debug("Get Alerts For Self Guardian");
        logger.debug("Count -> " + count);
        logger.debug("From -> " + from);
        if(levels != null && levels.length > 0)
        	logger.debug("Levels -> " + levels.toString());
        
        // Find Guardian Alerts
        Iterable<AlertDTO> alerts = alertService.findGuardianAlerts(selfGuardian.getUserId(), 
        		count, from, levels);
        
        // Update Last Access To Alerts
        //guardiansService.updateLastAccessToAlerts(selfParent.getUserId());
        
        if(Iterables.size(alerts) == 0)
        	throw new NoAlertsFoundException();
        // Create and Send Response
        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(
        		GuardianResponseCode.ALERTS_FOR_SELF_GUARDIAN, HttpStatus.OK, alerts);
   
    }
    
    /**
     * Get Warning Alerts For Self Guardian
     * @param selfGuardian
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/warning", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_WARNING_ALERTS_FOR_SELF_GUARDIAN", 
    	nickname = "GET_WARNING_ALERTS_FOR_SELF_GUARDIAN", 
            	notes = "Get Warning Alerts For Self Guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Alerts", response = AlertDTO.class)
    })
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getWarningAlertsForSelfGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "count", value = "Number of alerts", required = false)
				@RequestParam(name = "count", defaultValue = "0", required = false) 
    				Integer count,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) 
    				Date from
				) throws Throwable {
    	
        logger.debug("Get Warning Alerts For Self Guardian");
        logger.debug("Count -> " + count);
        logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService
        		.findGuardianWarningAlerts(selfGuardian.getUserId(), count, from);
        
        // Update Last Access To Alerts
        //guardiansService.updateLastAccessToAlerts(selfParent.getUserId());
        
        if(Iterables.size(alerts) == 0)
        	throw new NoAlertsFoundException();
        // Create and Send Response
        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(
        		GuardianResponseCode.ALERTS_FOR_SELF_GUARDIAN, HttpStatus.OK, alerts);
   
    }
    
    /**
     * Get Information Alerts For self Guardian
     * @param selfGuardian
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/info", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_INFORMATION_ALERTS_FOR_SELF_GUARDIAN", 
    	nickname = "GET_INFORMATION_ALERTS_FOR_SELF_GUARDIAN", 
            notes = "Get Information Alerts For Self GUardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Alerts", response = AlertDTO.class)
    })
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getInformationAlertsForSelfGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "count", value = "Number of alerts", required = false)
				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from
				) throws Throwable {
    	
        logger.debug("Get Information Alerts For Self Guardian");
        logger.debug("Count -> " + count);
        logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService
        		.findGuardianInformationAlerts(selfGuardian.getUserId(), count, from);
        
        // Update Last Access To Alerts
        //guardiansService.updateLastAccessToAlerts(selfParent.getUserId());
        
        if(Iterables.size(alerts) == 0)
        	throw new NoAlertsFoundException();
        // Create and Send REsponse
        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(
        		GuardianResponseCode.ALERTS_FOR_SELF_GUARDIAN, HttpStatus.OK, alerts);
   
    }
    
    /**
     * Get Danger Alerts For Self Guardian
     * @param selfGuardian
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/danger", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_DANGER_ALERTS_FOR_SELF_GUARDIAN", 
    	nickname = "GET_DANGER_ALERTS_FOR_SELF_GUARDIAN", 
            notes = "Get Danger Alerts For Self Guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Alerts", response = AlertDTO.class)
    })
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getDangerAlertsForSelfGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "count", value = "Number of alerts", required = false)
				@RequestParam(name = "count", defaultValue = "0", required = false) Integer count,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false) Date from
				) throws Throwable {
    	
        logger.debug("Get Danger Alerts For Self Guardian");
        logger.debug("Count -> " + count);
        logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService
        		.findGuardianDangerAlerts(selfGuardian.getUserId(), count, from);
        
        // Update Last Access To Alerts
        //guardiansService.updateLastAccessToAlerts(selfParent.getUserId());
        
        if(Iterables.size(alerts) == 0)
        	throw new NoAlertsFoundException();
        // Create and send response
        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(
        		GuardianResponseCode.ALERTS_FOR_SELF_GUARDIAN, HttpStatus.OK, alerts);
   
    }
    
    /**
     * Get Success Alerts For Self Guardian
     * @param selfGuardian
     * @param count
     * @param from
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/success", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SUCCESS_ALERTS_FOR_SELF_GUARDIAN", 
    	nickname = "GET_SUCCESS_ALERTS_FOR_SELF_GUARDIAN", 
            notes = "Get Success Alerts For Self Guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Alerts", response = AlertDTO.class)
    })
    public ResponseEntity<APIResponse<Iterable<AlertDTO>>> getSuccessAlertsForSelfGuardian(
    		@ApiIgnore 
    			@CurrentUser 
    				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(name = "count", value = "Number of alerts", required = false)
				@RequestParam(name = "count", defaultValue = "0", required = false) 
    				final Integer count,
    		@ApiParam(name = "days_ago", value = "Days Ago", required = false)
				@RequestParam(name = "days_ago", defaultValue = "1", required = false)
    				final Date from
				) throws Throwable {
    	
        logger.debug("Get Success Alerts For Self Guardian");
        logger.debug("Count -> " + count);
        logger.debug("From -> " + from);
        
        Iterable<AlertDTO> alerts = alertService
        		.findGuardianSuccessAlerts(selfGuardian.getUserId(), count, from);
        
        // Update Last Access To Alerts
        //guardiansService.updateLastAccessToAlerts(selfParent.getUserId());
        
        if(Iterables.size(alerts) == 0)
        	throw new NoAlertsFoundException();
        // Create and Send Response
        return ApiHelper.<Iterable<AlertDTO>>createAndSendResponse(
        		GuardianResponseCode.ALERTS_FOR_SELF_GUARDIAN, HttpStatus.OK, alerts);
   
    }
    
    /**
     * Get LASt Alerts For Self Guardian
     * @param count
     * @param lastMinutes
     * @param levels
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/last", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_LAST_ALERTS_FOR_SELF_GUARDIAN",
    	nickname = "GET_LAST_ALERTS_FOR_SELF_GUARDIAN", 
            notes = "Get Last Alerts For Self GUardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Alerts", response = AlertsPageDTO.class)
    })
    public ResponseEntity<APIResponse<AlertsPageDTO>> getLastAlertsForSelfGuardian(
    		@Valid 
    			@Min(value= 20, message = "{alerts.count.min}") 
    			@Max(value = 80, message = "{alerts.count.max}")
    				@RequestParam(name="count", value = "count", 
    					required=false, defaultValue="20") final Integer count,
    		@Valid 
    			@Min(value= 0, message = "{alerts.count.last.minutes}")
    				@RequestParam(name="last_minutes", value = "last_minutes",
    					required=false, defaultValue="0") final Integer lastMinutes,
    		@RequestParam(name="levels", value="levels" , 
    			required=false) final String[] levels,
    		@ApiIgnore 
    			@CurrentUser 
    				final CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
        logger.debug("Count -> " + count);
        logger.debug("Only News -> " + lastMinutes);
        if(levels != null) logger.debug("Levels -> " + String.join(",", levels));
        
        AlertsPageDTO alertsPageDTO;
        
        if(lastMinutes > 0) {
        	
        	Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -lastMinutes);
            Date lastAccessToAlerts = calendar.getTime();
           
            alertsPageDTO = alertService.getLastAlerts(selfGuardian.getUserId(), lastAccessToAlerts, count, levels);
        } else {
        	
        	alertsPageDTO = alertService.getLastAlerts(selfGuardian.getUserId(), count, levels);
        }
        
        
        // Update Last Access To Alerts
        guardiansService.updateLastAccessToAlerts(selfGuardian.getUserId());
        // Create and Send Response
        return ApiHelper.<AlertsPageDTO>createAndSendResponse(
        		GuardianResponseCode.ALERTS_FOR_SELF_GUARDIAN, HttpStatus.OK, alertsPageDTO);
   
    }
    
    /**
     * Delete Alert of self guardian
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_ALERT_OF_SELF_GUARDIAN", 
    	nickname = "DELETE_ALERT_OF_SELF_GUARDIAN", 
            notes = "Delete all alerts of self guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Number of alerts deleted", response = Long.class)
    })
    public ResponseEntity<APIResponse<Long>> deleteAlertsOfSelfGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) 
    					throws Throwable {
    	
    	// Delete alerts of guardian
        Long countDeleted = alertService.deleteAlertsOfGuardian(selfGuardian.getUserId());
        
        // Create and Send Response
        return ApiHelper.<Long>createAndSendResponse(
        		GuardianResponseCode.ALERTS_OF_SELF_GUARDIAN_DELETED, HttpStatus.OK, countDeleted);
   
    }
    
    /**
     * Delete warning alert of self guardian
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/warning", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_WARNING_ALERT_OF_SELF_GUARDIAN", 
    nickname = "DELETE_WARNING_ALERT_OF_SELF_GUARDIAN", 
            notes = "Delete warning alerts of self Guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Number of alerts deleted", 
    				response = Long.class)
    })
    public ResponseEntity<APIResponse<Long>> deleteWarningAlertsOfSelfGuardian(
    		@ApiIgnore 
    			@CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
    	// Delete Warning Alerts of guardian
        Long countDeleted = alertService.deleteWarningAlertsOfGuardian(selfGuardian.getUserId());
        // Create and Send response
        return ApiHelper.<Long>createAndSendResponse(GuardianResponseCode.WARNING_ALERTS_OF_SELF_GUARDIAN_DELETED, 
        		HttpStatus.OK, countDeleted);
   
    }
    
    /**
     * Delete info alert of self guardian
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/info", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_INFO_ALERT_OF_SELF_GUARDIAN", 
    nickname = "DELETE_INFO_ALERT_OF_SELF_GUARDIAN", 
            notes = "Delete info alerts of self guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Number of alerts deleted", 
    					response = Long.class)
    })
    public ResponseEntity<APIResponse<Long>> deleteInfoAlertsOfSelfGuardian(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian)
    					throws Throwable {
    	
    	// Delete info alerts of guardian
        Long countDeleted = alertService.deleteInfoAlertsOfGuardian(selfGuardian.getUserId());
        // Create and Send Response
        return ApiHelper.<Long>createAndSendResponse(GuardianResponseCode.INFO_ALERTS_OF_SELF_GUARDIAN_DELETED, 
        		HttpStatus.OK, countDeleted);
   
    }
    
   /**
    * Delete Danger Alert of self guardian
    * @param selfGuardian
    * @return
    * @throws Throwable
    */
    @RequestMapping(value = "/self/alerts/danger", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_DANGER_ALERT_OF_SELF_GUARDIAN", 
    nickname = "DELETE_DANGER_ALERT_OF_SELF_GUARDIAN", 
            notes = "Delete Danger alerts of self guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Number of alerts deleted", 
    				response = Long.class)
    })
    public ResponseEntity<APIResponse<Long>> deleteDangerAlertsOfSelfGuardian(
    		@ApiIgnore 
    			@CurrentUser 
    				CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
    	// Delete Danger Alerts of guardian
        Long countDeleted = alertService.deleteDangerAlertsOfGuardian(selfGuardian.getUserId());
        // Create and Send Response
        return ApiHelper.<Long>createAndSendResponse(GuardianResponseCode.DANGER_ALERTS_OF_SELF_GUARDIAN_DELETED, 
        		HttpStatus.OK, countDeleted);
   
    }
    
    /**
     * Delete success alert of self guardian
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/alerts/success", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_SUCCESS_ALERT_OF_SELF_GUARDIAN", 
    nickname = "DELETE_SUCCESS_ALERT_OF_SELF_GUARDIAN", 
            notes = "Delete Success alerts of self Guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Number of alerts deleted", 
    				response = Long.class)
    })
    public ResponseEntity<APIResponse<Long>> deleteSuccessAlertsOfSelfGuardian(
    		@ApiIgnore 
    			@CurrentUser 
    				CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
    	// Delete Success Alerts Of Guardian
        Long countDeleted = alertService.deleteSuccessAlertsOfGuardian(selfGuardian.getUserId());
        // Create and Send Response
        return ApiHelper.<Long>createAndSendResponse(GuardianResponseCode.SUCCESS_ALERTS_OF_SELF_GUARDIAN_DELETED, 
        		HttpStatus.OK, countDeleted);
   
    }
    
   
    /**
     * Self Reset Password
     * @param selfGuardian
     * @return
     */
    @RequestMapping(value = "/self/reset-password",  method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "SELF_RESET_PASSWORD", nickname = "SELF_RESET_PASSWORD", 
    	notes="Reset Password For Self User")
    public ResponseEntity<APIResponse<String>> selfResetPassword(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian){
    	
    	logger.debug("Reset Password");
    	
    	final String userId = selfGuardian.getUserId().toString();
    	
    	PasswordResetTokenDTO resetPasswordToken  = Optional.ofNullable(passwordResetTokenService.getPasswordResetTokenForUser(userId))
    		.orElseGet(() -> passwordResetTokenService.createPasswordResetTokenForUser(userId));
    	
    	applicationEventPublisher.publishEvent(new PasswordResetEvent(this, resetPasswordToken));
    	
    	return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.GUARDIAN_RESET_PASSWORD_REQUEST, 
        		HttpStatus.OK, messageSourceResolver.resolver("parent.password.reseted"));	
    }
    
    
    /**
     * Reset Password
     * @param request
     * @return
     */
    @RequestMapping(value = "/reset-password",  method = RequestMethod.POST)
    @ApiOperation(value = "RESET_PASSWORD", nickname = "RESET_PASSWORD", notes="Reset Password")
    public ResponseEntity<APIResponse<String>> resetPassword(
    		@ApiParam(value = "resetPasswordRequest", required = true) 
			@Validated(IResetPasswordSequence.class) @RequestBody  ResetPasswordRequestDTO request){
    	
    	logger.debug("Reset Password");
    	
    	final GuardianDTO parent = guardiansService.getGuardianByEmail(request.getEmail());
    	
    	PasswordResetTokenDTO resetPasswordToken  = Optional.ofNullable(passwordResetTokenService.getPasswordResetTokenForUser(parent.getIdentity()))
    		.orElseGet(() -> passwordResetTokenService.createPasswordResetTokenForUser(parent.getIdentity()));
    	
    	applicationEventPublisher.publishEvent(new PasswordResetEvent(this, resetPasswordToken));
    	
    	return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.GUARDIAN_RESET_PASSWORD_REQUEST, 
        		HttpStatus.OK, messageSourceResolver.resolver("parent.password.reseted"));	
    }
    
    /**
     * Update Self Guardian
     * @param selfGuardian
     * @param guardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = { "/self", "/self/update" },  method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "UPDATE_SELF_GUARDIAN", nickname = "UPDATE_SELF_GUARDIAN", 
    	notes="Update information for self guardian")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Update Guardian", response = GuardianDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<GuardianDTO>> updateSelfGuardian(
    		@ApiIgnore 
    			@CurrentUser 
    				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(value = "guardian", required = true) 
    			@Validated(ICommonSequence.class)
    				@RequestBody final UpdateGuardianDTO guardian) throws Throwable {
    	
    	logger.debug("Update Guardian");
        final GuardianDTO guardianDTO = guardiansService.update(selfGuardian.getUserId(), guardian);
        // Create and send response
        return ApiHelper.<GuardianDTO>createAndSendResponse(GuardianResponseCode.SELF_GUARDIAN_UPDATED_SUCCESSFULLY, 
        				HttpStatus.OK, addLinksToParent(guardianDTO));
    }
    
    /**
     * Delete Self Guardian
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = { "/self", "/self/delete" },  method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_SELF_GUARDIAN", nickname = "DELETE_SELF_GUARDIAN", 
    	notes="Request deletion of the guardian account")
    public ResponseEntity<APIResponse<String>> deleteSelfGuardian(
    		@ApiIgnore 
    			@CurrentUser 
    				final CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
    	logger.debug("Delete Guardian");
    	logger.debug(selfGuardian.toString());
    	// Generate Token
    	final String confirmationToken = tokenGeneratorService
    			.generateToken(selfGuardian.getFirstName());
    	
    	// Start Account Deletion Process
    	guardiansService.startAccountDeletionProcess(selfGuardian.getUserId(), 
    			confirmationToken);
    	
    	//notify event
    	applicationEventPublisher.publishEvent(new AccountDeletionRequestEvent(selfGuardian.getUserId().toString(), confirmationToken, this));
    	
    	// Create and Send Response
        return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.SUCCESSFUL_ACCOUNT_DELETION_REQUEST, 
        				HttpStatus.OK, messageSourceResolver.resolver("parents.delete.pending"));
    }
    
    /**
     * Add kid to guardian
     * @param id
     * @param kid
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/{id}/children/add", method = RequestMethod.POST)
    @PreAuthorize("@authorizationService.hasAdminRole() || ( @authorizationService.hasGuardianRole() && @authorizationService.isTheAuthenticatedUser(#id) )")
    @ApiOperation(value = "ADD_KID_TO_GUARDIAN", nickname = "ADD_KID_TO_GUARDIAN", 
    	notes="Add kid to guardian for analysis")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Kid Saved", response = KidDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<KidDTO>> addKidToGuardian(
    		@ApiParam(name = "guardian", value = "Guardian Identifier", required = true) 
    			@Valid @ValidObjectId(message = "{parent.id.notvalid}") 
    				@GuardianShouldExists(message = "{parent.not.exists}")
    					@PathVariable String id,
    		@ApiParam(value = "kid", required = true) 
    			@Valid 
    				@RequestBody final RegisterKidDTO kid) throws Throwable {
    	logger.debug("Add Son To Parent");
    	
    	// Add Kid
    	final KidDTO sonDTO = guardiansService.addKid(id, kid);
    	// Create and Send Response
    	return ApiHelper.<KidDTO>createAndSendResponse(GuardianResponseCode.ADDED_KID_TO_GUARDIAN, 
				HttpStatus.OK, addLinksToKid(sonDTO));
    }
    
    /**
     * Add kid to self guardian
     * @param selfGuardian
     * @param kid
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/add", method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "ADD_KID_TO_SELF_GUARDIAN", nickname = "ADD_KID_TO_SELF_GUARDIAN", 
    	notes="Add kid to currently authenticated guardian for analysis")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Kid Registered to self guardian",
    				response = KidDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", 
    			response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<KidDTO>> addKidToSelfGuardian(
    		@ApiParam(hidden = true) 
    			@CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(value = "kid", required = true) 
    			@Valid 
    				@RequestBody final RegisterKidDTO kid) throws Throwable {
    	
    	logger.debug("Add Kid To Self Guardian");
    	
    	// Add Kid
    	final KidDTO sonDTO = guardiansService.addKid(selfGuardian.getUserId().toString(), kid);
    	// Create and Send Response
    	return ApiHelper.<KidDTO>createAndSendResponse(GuardianResponseCode.ADDED_KID_TO_SELF_GUARDIAN, 
				HttpStatus.OK, addLinksToKid(sonDTO));
    }
    
    /**
     * Save Kid Information
     * @param selfGuardian
     * @param son
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/update", method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "SAVE_KID_INFORMATION", nickname = "SAVE_KID_INFORMATION", 
    	notes="Update son for currently authenticated parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Kid To Add or Update", response = KidDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<KidDTO>> saveKidInformation(
    		@ApiParam(hidden = true) 
    			@CurrentUser 
    				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(value = "son", required = true) 
    			@Validated(ICommonSequence.class) @RequestBody UpdateKidDTO son) throws Throwable {
    	
    	logger.debug("Save Son Information");
    	
    	// Updat Kid
    	final KidDTO sonDTO = guardiansService.updateKid(selfGuardian.getUserId().toString(), son);
    	// Create and Send Response
    	return ApiHelper.<KidDTO>createAndSendResponse(GuardianResponseCode.SAVE_KID_INFORMATION, 
				HttpStatus.OK, addLinksToKid(sonDTO));
    }
    
    
    /**
     * Get Supervised Children COnfirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/confirmed", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SUPERVISED_CHILDREN_CONFIRMED", 
    	nickname = "GET_SUPERVISED_CHILDREN_CONFIRMED", notes = "Get Supervised Children Confirmed", 
    	response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<KidGuardianDTO>>> getSupervisedChildrenConfirmed(
    		@ApiParam(hidden = true) 
				@CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
        
    	logger.debug("Get Supervised Children Confirmed");
    	
    	// Get Supervised Children Confirmed
    	final Iterable<KidGuardianDTO> supervisedChildrenList = 
    			kidService.findSupervisedChildrenConfirmed(selfGuardian.getUserId());
    	
    	
    	if(Iterables.isEmpty(supervisedChildrenList))
    		throw new NoSupervisedChildrenConfirmedFoundException();
    	
    	
    	// Create and send response
    	return ApiHelper.<Iterable<KidGuardianDTO>>createAndSendResponse(GuardianResponseCode.GET_SUPERVISED_CHILDREN_CONFIRMED, 
				HttpStatus.OK, supervisedChildrenList);
    }
    
    /**
     * Get Supervised Children No confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/no-confirmed", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    	nickname = "GET_SUPERVISED_CHILDREN_NO_CONFIRMED", notes = "Get Supervised Children No Confirmed", 
    		response = Iterable.class)
    public ResponseEntity<APIResponse<Iterable<KidGuardianDTO>>> getSupervisedChildrenNoConfirmed(
    		@ApiParam(hidden = true) 
				@CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
        
    	logger.debug("Get Supervised Children No Confirmed");
    	
    	// Get Supervised Children No Confirmed
    	final Iterable<KidGuardianDTO> supervisedChildrenList = 
    			kidService.findSupervisedChildrenNoConfirmed(selfGuardian.getUserId());
    	
    	if(Iterables.isEmpty(supervisedChildrenList))
    		throw new NoSupervisedChildrenNoConfirmedFoundException();
    	
    	// Create and send response
    	return ApiHelper.<Iterable<KidGuardianDTO>>createAndSendResponse(GuardianResponseCode.GET_SUPERVISED_CHILDREN_NO_CONFIRMED, 
				HttpStatus.OK, supervisedChildrenList);
    }
    
    /**
     * Get Supervised Children No confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/no-confirmed", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    	nickname = "DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    		notes = "Delete Supervised Children No Confirmed", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteSupervisedChildrenNoConfirmed(
    		@ApiParam(hidden = true) 
				@CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
        
    	logger.debug("Delete Supervised Children No Confirmed");
    	
    	// Delete Supervised Children No Confirmed
    	kidService.deleteSupervisedChildrenNoConfirmed(selfGuardian.getUserId());
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED, 
				HttpStatus.OK, messageSourceResolver.resolver("parents.delete.pending"));
    }
    
    
    /**
     * Accept Supervised Children No confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = { "/self/children/no-confirmed", "/self/children/no-confirmed/all" }, 
    			method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    	nickname = "ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    		notes = "Accept Supervised Children No Confirmed", response = String.class)
    public ResponseEntity<APIResponse<String>> acceptSupervisedChildrenNoConfirmed(
    		@ApiParam(hidden = true) 
				@CurrentUser CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
        
    	logger.debug("Accept Supervised Children No Confirmed");
    	
    	// Accept Supervised Children No Confirmed
    	kidService.acceptSupervisedChildrenNoConfirmed(selfGuardian.getUserId());
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED, 
				HttpStatus.OK, messageSourceResolver.resolver("parents.delete.pending"));
    }
    
    
    /**
     * Get Supervised Children No confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/no-confirmed/{id}", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SUPERVISED_CHILDREN_NO_CONFIRMED_DETAIL", 
    	nickname = "GET_SUPERVISED_CHILDREN_NO_CONFIRMED_DETAIL", 
    		notes = "Get Supervised Children No Confirmed Detail", response = KidGuardianDTO.class)
    public ResponseEntity<APIResponse<KidGuardianDTO>> getSupervisedChildrenNoConfirmedDetail(
    		@ApiParam(name= "id", value = "Supervised Children Identifier", required = true)
    			@Valid @SupervisedChildrenShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable final String id) throws Throwable {
        
    	logger.debug("Get Supervised Children No Confirmed");
    	
    	return Optional.ofNullable(kidService.findSupervisedChildrenNoConfirmedById(new ObjectId(id)))
    				.map(kidGuardianDTO -> ApiHelper.<KidGuardianDTO>createAndSendResponse(GuardianResponseCode.GET_SUPERVISED_CHILDREN_NO_CONFIRMED_DETAIL, 
        		HttpStatus.OK, kidGuardianDTO))
        .orElseThrow(() -> { throw new SupervisedChildrenNoConfirmedNotFoundException(); });
    }
    	
    
    /**
     * Delete Supervised Children No confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/no-confirmed/{id}", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    	nickname = "DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    		notes = "Delete Supervised Children No Confirmed", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteSupervisedChildrenNoConfirmed(
    		@ApiParam(name= "id", value = "Supervised Children Identifier", required = true)
    			@Valid @SupervisedChildrenShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable final String id) throws Throwable {
        
    	logger.debug("Delete Supervised Children No Confirmed");
    	
    	// Delete Supervised Children No Confirmed By Id
    	kidService.deleteSupervisedChildrenNoConfirmedById(new ObjectId(id));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.DELETE_SUPERVISED_CHILDREN_NO_CONFIRMED_BY_ID, 
				HttpStatus.OK, messageSourceResolver.resolver("parents.delete.pending"));
    }
    
    /**
     * Accept Supervised Children No confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/no-confirmed/{id}", method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    	nickname = "ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED", 
    		notes = "Accept Supervised Children No Confirmed", response = String.class)
    public ResponseEntity<APIResponse<String>> acceptSupervisedChildrenNoConfirmed(
    		@ApiParam(name= "id", value = "Supervised Children Identifier", required = true)
    			@Valid @SupervisedChildrenShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable final String id) throws Throwable {
        
    	logger.debug("Accept Supervised Children No Confirmed");
    	
    	// Accept Supervised Children No Confirmed By Id
    	kidService.acceptSupervisedChildrenNoConfirmedById(new ObjectId(id));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.ACCEPT_SUPERVISED_CHILDREN_NO_CONFIRMED_BY_ID, 
				HttpStatus.OK, messageSourceResolver.resolver("parents.delete.pending"));
    }
    
    
    /**
     * Get Supervised Children confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/confirmed/{id}", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SUPERVISED_CHILDREN_CONFIRMED_DETAIL", 
    	nickname = "GET_SUPERVISED_CHILDREN_CONFIRMED_DETAIL", 
    		notes = "Get Supervised Children Confirmed Detail", response = KidGuardianDTO.class)
    public ResponseEntity<APIResponse<KidGuardianDTO>> getSupervisedChildrenConfirmedDetail(
    		@ApiParam(name= "id", value = "Supervised Children Identifier", required = true)
    			@Valid @SupervisedChildrenShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable final String id) throws Throwable {
        
    	logger.debug("Get Supervised Children Confirmed");
    	
    	return Optional.ofNullable(kidService.findSupervisedChildrenConfirmedById(new ObjectId(id)))
				.map(kidGuardianDTO -> ApiHelper.<KidGuardianDTO>createAndSendResponse(GuardianResponseCode.GET_SUPERVISED_CHILDREN_CONFIRMED_DETAIL, 
    		HttpStatus.OK, kidGuardianDTO))
				.orElseThrow(() -> { throw new SupervisedChildrenNoConfirmedNotFoundException(); });
    	
   
    }
    
    /**
     * Delete Supervised Children confirmed
     * @param id
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/children/confirmed/{id}", method = RequestMethod.DELETE)
    @OnlyAccessForGuardian
    @ApiOperation(value = "DELETE_SUPERVISED_CHILDREN_CONFIRMED", 
    	nickname = "DELETE_SUPERVISED_CHILDREN_CONFIRMED", 
    		notes = "Delete Supervised Children Confirmed", response = String.class)
    public ResponseEntity<APIResponse<String>> deleteSupervisedChildrenConfirmed(
    		@ApiParam(name= "id", value = "Supervised Children Identifier", required = true)
    			@Valid @SupervisedChildrenShouldExists(message = "{son.id.notvalid}")
    		 		@PathVariable final String id) throws Throwable {
        
    	logger.debug("Delete Supervised Children Confirmed");
    	
    	// Delete Supervised Children Confirmed By Id
    	kidService.deleteSupervisedChildrenConfirmedById(new ObjectId(id));
    	
    	// Create and send response
    	return ApiHelper.<String>createAndSendResponse(GuardianResponseCode.DELETE_SUPERVISED_CHILDREN_CONFIRMED_BY_ID, 
				HttpStatus.OK, messageSourceResolver.resolver("parents.delete.pending"));
    }
    
    
    
    /**
     * Save Preferences
     * @param selfGuardian
     * @param preferences
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/preferences", method = RequestMethod.POST)
    @OnlyAccessForGuardian
    @ApiOperation(value = "SAVE_PREFERENCES", nickname = "SAVE_PREFERENCES", 
    	notes="Save Preferences")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Guardian Preferences", response = UserSystemPreferencesDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<UserSystemPreferencesDTO>> savePreferences(
    		@ApiParam(hidden = true) 
    			@CurrentUser 
    				final CommonUserDetailsAware<ObjectId> selfGuardian,
    		@ApiParam(value = "preferences", required = true) 
    			@RequestBody 
    				final SaveUserSystemPreferencesDTO preferences) throws Throwable {
    	
    	logger.debug("Save User System Preferences");
    
    	UserSystemPreferencesDTO preferencesSaved = guardiansService.savePreferences(preferences, selfGuardian.getUserId());
    	// Create and Send Response
    	return ApiHelper.<UserSystemPreferencesDTO>createAndSendResponse(GuardianResponseCode.USER_SYSTEM_PREFERENCES_SAVED, 
				HttpStatus.OK, preferencesSaved);
    }
    
    /**
     * Get Self Preferences
     * @param selfGuardian
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/self/preferences", method = RequestMethod.GET)
    @OnlyAccessForGuardian
    @ApiOperation(value = "GET_SELF_PREFERENCES", nickname = "GET_SELF_PREFERENCES", 
    	notes="Get self preferences")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Guardian's Preferences", response = UserSystemPreferencesDTO.class)
    })
    public ResponseEntity<APIResponse<UserSystemPreferencesDTO>> getPreferences(
    		@ApiParam(hidden = true) 
    			@CurrentUser 
    				final CommonUserDetailsAware<ObjectId> selfGuardian) throws Throwable {
    	
    	logger.debug("Get User System Preferences");
    	// Get Prefereces
    	UserSystemPreferencesDTO preferences = 
    			guardiansService.getPreferences(selfGuardian.getUserId());
    	// Create and Send Response
    	return ApiHelper.<UserSystemPreferencesDTO>createAndSendResponse(
    			GuardianResponseCode.USER_SYSTEM_PREFERENCES, 
				HttpStatus.OK, preferences);
    }
    
    
    @PostConstruct
    protected void init(){
        Assert.notNull(guardiansService, "Parent Service can not be null");
        Assert.notNull(uploadFilesService, "Upload Files Service can not be null");
        Assert.notNull(passwordResetTokenService, "Password Reset Token Service can not be null");
        Assert.notNull(authenticationService, "Authentication Service can not be null");
        Assert.notNull(facebookService, "FacebookService can not be null");
        Assert.notNull(tokenGeneratorService, "TokenGeneratorService can not be null");
        Assert.notNull(uploadFilesService, "UploadFilesService can not be null");
        Assert.notNull(alertService, "Alert Service can not be null");
        Assert.notNull(deletePendingEmailService, "Delete Pending Email Service can not be null");

    }
    
}
