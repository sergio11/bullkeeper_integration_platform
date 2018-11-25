package sanchez.sanchez.sergio.bullkeeper.web.rest.controller;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthenticationService;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IUserSystemService;
import sanchez.sanchez.sergio.bullkeeper.exception.AdminNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.JwtAuthenticationRequestDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AdminDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.JwtAuthenticationResponseDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.ApiHelper;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.APIResponse;
import sanchez.sanchez.sergio.bullkeeper.web.rest.response.AdminResponseCode;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.CurrentUser;
import sanchez.sanchez.sergio.bullkeeper.web.security.utils.OnlyAccessForAdmin;
import springfox.documentation.annotations.ApiIgnore;

@RestController("RestAdminController")
@Validated
@RequestMapping("/api/v1/admin/")
@Api(tags = "admin", value = "/admin/", description = "Manejo de la información de los administradores", produces = "application/json")
public class AdminController extends BaseController {
	
	private static Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private final IAuthenticationService authenticationService;
	private final IUserSystemService userSystemService;
	
	public AdminController(IAuthenticationService authenticationService, IUserSystemService userSystemService){
		this.authenticationService = authenticationService;
		this.userSystemService = userSystemService;
	}
	

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN", nickname = "GET_AUTHORIZATION_TOKEN", notes = "Get Admin Authorization Token ")
	@ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Authentication Success", response = JwtAuthenticationResponseDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getAuthorizationToken(
			@Valid @RequestBody JwtAuthenticationRequestDTO credentials, Device device) throws Throwable {
    	
    
		return Optional.ofNullable(authenticationService.createAuthenticationTokenForAdmin(credentials.getEmail(), credentials.getPassword(), device))
				.map(jwtResponse -> ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(
						AdminResponseCode.AUTHENTICATION_SUCCESS, HttpStatus.OK, jwtResponse))
				.orElseThrow(() -> {
                    throw new ResourceNotFoundException();
                });
	}
	
	
	@RequestMapping(value = "/self", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    @ApiOperation(value = "GET_ADMIN_SELF_INFORMATION", nickname = "GET_ADMIN_SELF_INFORMATION", notes = "Get information from the currently authenticated admin")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message= "Self Admin", response = AdminDTO.class),
    		@ApiResponse(code = 404, message= "Admin Not Found")
    })
    public ResponseEntity<APIResponse<AdminDTO>> getSelfInformation(
    		@ApiIgnore @CurrentUser CommonUserDetailsAware<ObjectId> self) throws Throwable {
        logger.debug("Get Information for Admin with id: " + self.getUserId());
        
        
        return Optional.ofNullable(userSystemService.getUserById(self.getUserId()))
                .map(adminResource -> ApiHelper.<AdminDTO>createAndSendResponse(AdminResponseCode.SELF_ADMIN, 
                		HttpStatus.OK, adminResource))
                .orElseThrow(() -> {
                    throw new AdminNotFoundException();
                });
    }
	
	@PostConstruct
	protected void init(){
		Assert.notNull(authenticationService, "Authentication Service can not be null");
		Assert.notNull(userSystemService, "User System Service can not be null");
	}

}
