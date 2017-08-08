package sanchez.sanchez.sergio.rest.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sanchez.sanchez.sergio.dto.request.JwtAuthenticationRequestDTO;
import sanchez.sanchez.sergio.dto.request.JwtAuthenticationRequestDTO.OrderedChecks;
import sanchez.sanchez.sergio.dto.response.JwtAuthenticationResponseDTO;
import sanchez.sanchez.sergio.rest.ApiHelper;
import sanchez.sanchez.sergio.rest.exception.ResourceNotFoundException;
import sanchez.sanchez.sergio.rest.response.APIResponse;
import sanchez.sanchez.sergio.service.IAuthenticationService;
import sanchez.sanchez.sergio.rest.response.AuthenticationResponseCode;


@Api
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
	
	private final IAuthenticationService authenticationService;
	
	public AuthenticationController(IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	@PostMapping("/")
    @ApiOperation(value = "GET_AUTHORIZATION_TOKEN", nickname = "GET_AUTHORIZATION_TOKEN", notes = "Get Authorization Token", response = ResponseEntity.class)
	public ResponseEntity<APIResponse<JwtAuthenticationResponseDTO>> getAuthorizationToken(
			@Validated(OrderedChecks.class) @RequestBody JwtAuthenticationRequestDTO authenticationRequest, Device device) throws Throwable {
		return Optional.ofNullable(authenticationService.createAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword(), device))
				.map(jwtResponse -> ApiHelper.<JwtAuthenticationResponseDTO>createAndSendResponse(AuthenticationResponseCode.AUTHENTICATION_SUCCESS, jwtResponse, HttpStatus.OK))
				.orElseThrow(() -> {
                    throw new ResourceNotFoundException();
                });
	}

}
