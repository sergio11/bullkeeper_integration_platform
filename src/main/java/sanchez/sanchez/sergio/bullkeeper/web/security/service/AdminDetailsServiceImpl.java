package sanchez.sanchez.sergio.bullkeeper.web.security.service;


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

import sanchez.sanchez.sergio.bullkeeper.persistence.repository.UserSystemRepository;
import sanchez.sanchez.sergio.bullkeeper.web.security.exception.AccountPendingToBeRemoveException;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.impl.UserDetailsImpl;

import org.bson.types.ObjectId;

/**
 * Authenticate a user from the database.
 */
@Service("AdminDetailsService")
public class AdminDetailsServiceImpl implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(AdminDetailsServiceImpl.class);
	
	private final UserSystemRepository userSystemRepository;
	
	public AdminDetailsServiceImpl(UserSystemRepository userSystemRepository) {
		super();
		this.userSystemRepository = userSystemRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		
		return Optional.ofNullable(userSystemRepository.findOneByEmail(usernameOrEmail))
        	.map(userSystemEntity -> {
        		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<SimpleGrantedAuthority>();
            	
            	grantedAuthorities.add(new SimpleGrantedAuthority(userSystemEntity.getAuthority().getAuthority()));
            
            	UserDetailsImpl<ObjectId> userDetails = new UserDetailsImpl<ObjectId>(userSystemEntity.getId(), userSystemEntity.getEmail(),
                		userSystemEntity.getPassword(), userSystemEntity.getFirstName(), userSystemEntity.getLastName(), userSystemEntity.isLocked(),
                		userSystemEntity.getLastPasswordResetDate(), userSystemEntity.isActive(), grantedAuthorities,
                		userSystemEntity.getLastAccessToAlerts(), userSystemEntity.getLastLoginAccess(), userSystemEntity.isPendingDeletion());
            	
            	
            	return userDetails;
            	
        	}).orElseThrow(() -> new UsernameNotFoundException("User " + usernameOrEmail + " was not found in the " +
        "database"));
	}

}
