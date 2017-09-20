package es.bisite.usal.bulltect.domain.service;

import org.springframework.mobile.device.Device;

import es.bisite.usal.bulltect.web.dto.response.JwtAuthenticationResponseDTO;

public interface IAuthenticationService {
    JwtAuthenticationResponseDTO createAuthenticationTokenForParent(String username, String password, Device device);
    JwtAuthenticationResponseDTO createAuthenticationTokenForAdmin(String username, String password, Device device);
    
}
