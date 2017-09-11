package sanchez.sanchez.sergio.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mobile.device.Device;
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
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	@Qualifier("ParentsDetailsService")
	private UserDetailsService parentUserDetails;
	
	@Autowired
	@Qualifier("AdminDetailsService") 
	private UserDetailsService adminUserDetails;
    
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
    
  
	@Override
	public JwtAuthenticationResponseDTO createAuthenticationTokenForParent(String username, String password,
			Device device) {
		
		
		UserDetails userDetails = parentUserDetails.loadUserByUsername(username);
		Authentication auth = new UsernamePasswordAuthenticationToken (
				userDetails.getUsername(),userDetails.getPassword (),userDetails.getAuthorities ());
		SecurityContextHolder.getContext().setAuthentication(auth);
        
        logger.debug("Principal Name: " + auth.getName());
      
		final String token = jwtTokenUtil.generateToken(userDetails, device);
        
        return new JwtAuthenticationResponseDTO(token);
	}

	@Override
	public JwtAuthenticationResponseDTO createAuthenticationTokenForAdmin(String username, String password,
			Device device) {
		
		UserDetails userDetails = adminUserDetails.loadUserByUsername(username);
		Authentication auth = new UsernamePasswordAuthenticationToken (
				userDetails.getUsername(),userDetails.getPassword (),userDetails.getAuthorities ());
		SecurityContextHolder.getContext().setAuthentication(auth);
        
        logger.debug("Principal Name: " + auth.getName());
      
		final String token = jwtTokenUtil.generateToken(userDetails, device);
        
        return new JwtAuthenticationResponseDTO(token);
	}

}
