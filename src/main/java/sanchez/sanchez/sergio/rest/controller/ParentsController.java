package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Iterables;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import sanchez.sanchez.sergio.dto.request.JwtAuthenticationRequestDTO;
import sanchez.sanchez.sergio.dto.request.JwtFacebookAuthenticationRequestDTO;
import sanchez.sanchez.sergio.dto.request.RegisterParentByFacebookDTO;
import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.dto.request.UpdateParentDTO;
import sanchez.sanchez.sergio.dto.response.JwtAuthenticationResponseDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.PasswordResetTokenDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.events.ParentRegistrationSuccessEvent;
import sanchez.sanchez.sergio.events.PasswordResetEvent;
import sanchez.sanchez.sergio.persistence.constraints.ParentShouldExists;
import sanchez.sanchez.sergio.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundForParentException;
import sanchez.sanchez.sergio.rest.exception.NoChildrenFoundForSelfParentException;
import sanchez.sanchez.sergio.rest.exception.NoParentsFoundException;
import sanchez.sanchez.sergio.rest.exception.ParentNotFoundException;
import sanchez.sanchez.sergio.rest.hal.IParentHAL;
import sanchez.sanchez.sergio.rest.hal.ISonHAL;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.rest.response.ParentResponseCode;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.security.utils.CurrentUser;
import sanchez.sanchez.sergio.security.utils.OnlyAccessForAdmin;
import sanchez.sanchez.sergio.security.utils.OnlyAccessForParent;
import sanchez.sanchez.sergio.service.IAuthenticationService;
import sanchez.sanchez.sergio.service.IFacebookService;
import sanchez.sanchez.sergio.service.IParentsService;
import sanchez.sanchez.sergio.service.IPasswordResetTokenService;
import springfox.documentation.annotations.ApiIgnore;

@RestController("RestParentsController")
@Validated
@RequestMapping("/api/v1/parents/")
@Api(tags = "parents", value = "/parents/", description = "Manejo de la información del tutor", produces = "application/json")
public class ParentsController extends BaseController implements IParentHAL, ISonHAL {

    private static Logger logger = LoggerFactory.getLogger(ParentsController.class);
    
    private final IParentsService parentsService;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final IAuthenticationService authenticationService;
    private final IFacebookService facebookService;
 
    public ParentsController(IParentsService parentsService, IPasswordResetTokenService passwordResetTokenService, 
    		IAuthenticationService authenticationService, IFacebookService facebookService) {
        this.parentsService = parentsService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.authenticationService = authenticationService;
        this.facebookService = facebookService;
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
    	
    	JwtAuthenticationResponseDTO jwtResponseDTO =  Optional.ofNullable(parentsService.getParentByFbId(facebookInfo.getId()))
    			.map(parent -> {
    				parentsService.updateFbAccessToken(facebookInfo.getId(), facebookInfo.getToken());
    				return authenticationService.createAuthenticationTokenForParent(parent.getEmail(), parent.getFbId(), device);
    			})
    			.orElseGet(() -> {
    				RegisterParentByFacebookDTO registerParent = facebookService.getRegistrationInformationForTheParent(facebookInfo.getToken());
    				logger.debug(registerParent.toString());
    				ParentDTO parent = parentsService.save(registerParent);
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
    
    
    @RequestMapping(value = "/",  method = RequestMethod.POST)
    @ApiOperation(value = "REGISTER_PARENT", nickname = "REGISTER_PARENT", notes="Register Parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Register Parent", response = ParentDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<ParentDTO>> registerParent(
    		@ApiParam(value = "parent", required = true) 
    			@Valid @RequestBody RegisterParentDTO parent) throws Throwable {
    	logger.debug("Register Parent");
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
    
    
    @RequestMapping(value = "/self/reset-password",  method = RequestMethod.POST)
    @ApiOperation(value = "RESET_PASSWORD", nickname = "RESET_PASSWORD", notes="Reset Password")
    public ResponseEntity<APIResponse<String>> resetPassword(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent){
    	
    	logger.debug("Reset Password");
    	
    	final String userId = selfParent.getUserId().toString();
    	
    	PasswordResetTokenDTO resetPasswordToken  = Optional.ofNullable(passwordResetTokenService.getPasswordResetTokenForUser(userId))
    		.orElseGet(() -> passwordResetTokenService.createPasswordResetTokenForUser(userId));
    	
    	applicationEventPublisher.publishEvent(new PasswordResetEvent(this, resetPasswordToken));
    	
    	return ApiHelper.<String>createAndSendResponse(ParentResponseCode.PARENT_RESET_PASSWORD_REQUEST, 
        		HttpStatus.OK, messageSourceResolver.resolver("parent.password.reseted"));	
    }
    
    @RequestMapping(value = "/self/update",  method = RequestMethod.POST)
    @OnlyAccessForParent
    @ApiOperation(value = "UPDATE_SELF_PARENT", nickname = "UPDATE_SELF_PARENT", notes="Update information for self parent")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Update Parent", response = ParentDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
    public ResponseEntity<APIResponse<ParentDTO>> updateSelfParent(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> selfParent,
    		@ApiParam(value = "parent", required = true) 
    			@Valid @RequestBody UpdateParentDTO parent) throws Throwable {
    	
    	logger.debug("Update Parent");
        ParentDTO parentDTO = parentsService.update(selfParent.getUserId(), parent);
        
        return ApiHelper.<ParentDTO>createAndSendResponse(ParentResponseCode.SELF_PARENT_UPDATED_SUCCESSFULLY, 
        				HttpStatus.OK, addLinksToParent(parentDTO));
    }
    
    
    @RequestMapping(value = "/{id}/children/add", method = RequestMethod.PUT)
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
    
    @RequestMapping(value = "/self/children/add", method = RequestMethod.PUT)
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
    
}
