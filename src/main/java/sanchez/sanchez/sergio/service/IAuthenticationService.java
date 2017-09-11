package sanchez.sanchez.sergio.service;

import org.springframework.mobile.device.Device;

import sanchez.sanchez.sergio.dto.response.JwtAuthenticationResponseDTO;

public interface IAuthenticationService {
	JwtAuthenticationResponseDTO createAuthenticationTokenForParent(String username, String password, Device device);
	JwtAuthenticationResponseDTO createAuthenticationTokenForAdmin(String username, String password, Device device);
}
