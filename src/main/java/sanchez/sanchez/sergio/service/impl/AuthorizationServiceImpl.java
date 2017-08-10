package sanchez.sanchez.sergio.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import sanchez.sanchez.sergio.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.security.AuthoritiesConstants;
import sanchez.sanchez.sergio.security.userdetails.CommonUserDetailsAware;
import sanchez.sanchez.sergio.service.IAuthorizationService;

@Service("authorizationService")
public class AuthorizationServiceImpl implements IAuthorizationService {
	
	@Autowired
	protected SonRepository sonRepository;

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

	@SuppressWarnings("unchecked")
	@Override
	public Boolean isYourSon(String id) {
		Boolean isYourSon = Boolean.FALSE;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>)auth.getPrincipal();
			isYourSon = sonRepository.countByParentIdAndId(userDetails.getUserId(), new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
		}
		return isYourSon;
	}
}
