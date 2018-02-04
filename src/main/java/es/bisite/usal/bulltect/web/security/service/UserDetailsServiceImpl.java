package es.bisite.usal.bulltect.web.security.service;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.bisite.usal.bulltect.persistence.repository.ParentRepository;
import es.bisite.usal.bulltect.persistence.repository.UserSystemRepository;
import es.bisite.usal.bulltect.web.security.exception.AccountPendingToBeRemoveException;
import es.bisite.usal.bulltect.web.security.userdetails.impl.UserDetailsImpl;

import org.bson.types.ObjectId;

/**
 * Authenticate a user from the database.
 */
@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private final UserSystemRepository userSystemRepository;
	private final ParentRepository parentRepository;
	
	public UserDetailsServiceImpl(UserSystemRepository userSystemRepository, ParentRepository parentRepository) {
		super();
		this.userSystemRepository = userSystemRepository;
		this.parentRepository = parentRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		return Optional.ofNullable(userSystemRepository.findOneByEmail(email))
			.map(Optional::of)
        	.orElseGet(() -> Optional.ofNullable(parentRepository.findOneByEmail(email)))
        	.map(userSystemEntity -> {
        		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<SimpleGrantedAuthority>();
            	
            	grantedAuthorities.add(new SimpleGrantedAuthority(userSystemEntity.getAuthority().getAuthority()));
            
            	UserDetailsImpl<ObjectId> userDetails =  new UserDetailsImpl<ObjectId>(userSystemEntity.getId(), userSystemEntity.getEmail(),
                		userSystemEntity.getPassword(), userSystemEntity.getFirstName(), userSystemEntity.getLastName(), userSystemEntity.isLocked(),
                		userSystemEntity.getLastPasswordResetDate(), userSystemEntity.isActive(),
                		grantedAuthorities, userSystemEntity.getLastAccessToAlerts(), userSystemEntity.getLastLoginAccess(), userSystemEntity.isPendingDeletion());
                
            	
            	return userDetails;
                
        	}).orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the " +
        "database"));
	}

}
