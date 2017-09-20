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
import es.bisite.usal.bulltect.web.security.userdetails.impl.UserDetailsImpl;

import org.bson.types.ObjectId;

/**
 * Authenticate a user from the database.
 */
@Service("ParentsDetailsService")
public class ParentsDetailsServiceImpl implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(ParentsDetailsServiceImpl.class);
	
	private final ParentRepository parentRepository;
	
	public ParentsDetailsServiceImpl(ParentRepository parentRepository) {
		super();
		this.parentRepository = parentRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		logger.debug("Autenticate User with email: " + email);
		
		return Optional.ofNullable(parentRepository.findOneByEmail(email))
        	.map(parentEntity -> {
        
        		logger.debug(parentEntity.toString());
        		
        		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<SimpleGrantedAuthority>();
            	grantedAuthorities.add(new SimpleGrantedAuthority(parentEntity.getAuthority().getAuthority()));
            	
                return new UserDetailsImpl<ObjectId>(parentEntity.getId(), parentEntity.getEmail(),
                		parentEntity.getPassword(), parentEntity.getFirstName(), parentEntity.getLastName(), parentEntity.isLocked(),
                		parentEntity.getLastPasswordResetDate(), parentEntity.isActive(), grantedAuthorities, parentEntity.getProfileImageId());
        	}).orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the " +
        "database"));
	}

}
