package sanchez.sanchez.sergio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.dto.response.JwtAuthenticationResponseDTO;
import sanchez.sanchez.sergio.security.jwt.JwtTokenUtil;
import sanchez.sanchez.sergio.service.IAuthenticationService;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
	
	@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

	@Override
	public JwtAuthenticationResponseDTO createAuthenticationToken(String username, String password, Device device) {
		// Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        
        return new JwtAuthenticationResponseDTO(token);
	}

}
