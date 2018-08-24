package sanchez.sanchez.sergio.masoc.domain.service.impl;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.masoc.domain.service.IAuthenticationService;
import sanchez.sanchez.sergio.masoc.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.masoc.web.dto.response.JwtAuthenticationResponseDTO;
import sanchez.sanchez.sergio.masoc.web.security.exception.AccountPendingToBeRemoveException;
import sanchez.sanchez.sergio.masoc.web.security.jwt.JwtTokenUtil;
import sanchez.sanchez.sergio.masoc.web.security.userdetails.CommonUserDetailsAware;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
	
	private static Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	@Qualifier("ParentsAuthenticationManager")
	private AuthenticationManager parentsAuthenticationManager;
	
	@Autowired
	@Qualifier("ParentsDetailsService") 
	private UserDetailsService parentDetails;
	
	
	@Autowired(required = false)
	@Qualifier("AdminAuthenticationManager")
	private AuthenticationManager adminAuthenticationManager;
	
	@Autowired
	private ParentRepository parentRepository;
	

	@Override
	public JwtAuthenticationResponseDTO createAuthenticationTokenForParent(String username, String password,
			Device device) {
		
        final Authentication authentication = parentsAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) authentication.getPrincipal();
        
        if(userDetails.isPendingDelete())
    		throw new AccountPendingToBeRemoveException();

		final String token = jwtTokenUtil.generateToken(userDetails, device);
		
		logger.debug("Update Last login access and last access to alerts for user -> " + userDetails.getUserId().toString());
		parentRepository.updateLastLoginAccessAndLastAccessToAlerts(userDetails.getUserId());
        
        return new JwtAuthenticationResponseDTO(token);
	}

	@Override
	public JwtAuthenticationResponseDTO createAuthenticationTokenForAdmin(String username, String password,
			Device device) {
		
		final Authentication authentication = parentsAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) authentication.getPrincipal();
        
        if(userDetails.isPendingDelete())
    		throw new AccountPendingToBeRemoveException();
     
		final String token = jwtTokenUtil.generateToken(userDetails, device);
        
        return new JwtAuthenticationResponseDTO(token);
	}

}
