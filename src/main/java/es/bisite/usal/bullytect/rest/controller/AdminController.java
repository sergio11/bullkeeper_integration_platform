package es.bisite.usal.bullytect.rest.controller;

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

import es.bisite.usal.bullytect.dto.request.JwtAuthenticationRequestDTO;
import es.bisite.usal.bullytect.dto.response.AdminDTO;
import es.bisite.usal.bullytect.dto.response.JwtAuthenticationResponseDTO;
import es.bisite.usal.bullytect.dto.response.ValidationErrorDTO;
import es.bisite.usal.bullytect.rest.ApiHelper;
import es.bisite.usal.bullytect.rest.exception.AdminNotFoundException;
import es.bisite.usal.bullytect.rest.exception.ResourceNotFoundException;
import es.bisite.usal.bullytect.rest.response.APIResponse;
import es.bisite.usal.bullytect.rest.response.AdminResponseCode;
import es.bisite.usal.bullytect.security.userdetails.CommonUserDetailsAware;
import es.bisite.usal.bullytect.security.utils.CurrentUser;
import es.bisite.usal.bullytect.security.utils.OnlyAccessForAdmin;
import es.bisite.usal.bullytect.service.IAuthenticationService;
import es.bisite.usal.bullytect.service.IUserSystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
