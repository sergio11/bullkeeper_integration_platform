package es.bisite.usal.bullytect.service;

import org.springframework.mobile.device.Device;

import es.bisite.usal.bullytect.dto.response.JwtAuthenticationResponseDTO;

public interface IAuthenticationService {
	JwtAuthenticationResponseDTO createAuthenticationTokenForParent(String username, String password, Device device);
	JwtAuthenticationResponseDTO createAuthenticationTokenForAdmin(String username, String password, Device device);
}
