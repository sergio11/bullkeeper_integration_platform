package sanchez.sanchez.sergio.service;

import org.springframework.mobile.device.Device;

import sanchez.sanchez.sergio.dto.response.JwtAuthenticationResponseDTO;

public interface IAuthenticationService {
	JwtAuthenticationResponseDTO createAuthenticationToken(String username, String password, Device device);
}
