package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import sanchez.sanchez.sergio.dto.request.JwtAuthenticationRequestDTO;
import sanchez.sanchez.sergio.dto.response.JwtAuthenticationResponseDTO;
import sanchez.sanchez.sergio.dto.response.ValidationErrorDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.service.IAuthenticationService;
import sanchez.sanchez.sergio.rest.response.AuthenticationResponseCode;


@RestController
@RequestMapping("/api/v1/auth/")
@Api(tags= "authentication", value = "/auth/", description = "Autenticación de usuarios del sistema", produces = "application/json")
public class AuthenticationController {
	
	private final IAuthenticationService authenticationService;
	
	public AuthenticationController(IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN", nickname = "GET_AUTHORIZATION_TOKEN", notes = "Get Authorization Token")
	@ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "Authentication Success", response = JwtAuthenticationResponseDTO.class),
    		@ApiResponse(code = 403, message = "Validation Errors", response = ValidationErrorDTO.class)
    })
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getAuthorizationToken(
			@Valid @RequestBody JwtAuthenticationRequestDTO credentials, Device device) throws Throwable {
		return Optional.ofNullable(authenticationService.createAuthenticationToken(credentials.getEmail(), credentials.getPassword(), device))
				.map(jwtResponse -> ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(AuthenticationResponseCode.AUTHENTICATION_SUCCESS, HttpStatus.OK, jwtResponse))
				.orElseThrow(() -> {
                    throw new ResourceNotFoundException();
                });
	}

}
