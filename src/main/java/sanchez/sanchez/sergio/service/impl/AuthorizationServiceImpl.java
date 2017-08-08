package sanchez.sanchez.sergio.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.security.AuthoritiesConstants;
import sanchez.sanchez.sergio.service.IAuthorizationService;

@Service("authorizationService")
public class AuthorizationServiceImpl implements IAuthorizationService {

	@Override
	public Boolean hasAdminRole() {
		return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN)));
	}

	@Override
	public Boolean hasParentRole() {
		return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
				.contains(new SimpleGrantedAuthority(AuthoritiesConstants.PARENT)));
	}

}
