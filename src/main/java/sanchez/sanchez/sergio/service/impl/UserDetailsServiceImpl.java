package sanchez.sanchez.sergio.service.impl;


import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sanchez.sanchez.sergio.persistence.entity.UserSystemEntity;
import sanchez.sanchez.sergio.persistence.repository.UserSystemRepository;
import sanchez.sanchez.sergio.security.userdetails.impl.UserDetailsImpl;
import org.bson.types.ObjectId;

/**
 * Authenticate a user from the database.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	private final UserSystemRepository userSystemRepository;
	
	public UserDetailsServiceImpl(UserSystemRepository userSystemRepository) {
		super();
		this.userSystemRepository = userSystemRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserSystemEntity> userSystemEntityOptional = userSystemRepository.findOneByEmail(email);
        
        return userSystemEntityOptional.map(userSystemEntity -> {
        	
        	Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<SimpleGrantedAuthority>();
        	
        	grantedAuthorities.add(new SimpleGrantedAuthority(userSystemEntity.getAuthority().getAuthority()));
        
            return new UserDetailsImpl<ObjectId>(userSystemEntity.getId(), userSystemEntity.getEmail(),
            		userSystemEntity.getPassword(), userSystemEntity.getFirstName(), userSystemEntity.getLastName(), userSystemEntity.isLocked(),
            		grantedAuthorities);

        }).orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the " +
        "database"));
	}

}
