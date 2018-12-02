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
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.impl.UserDetailsImpl;

import org.bson.types.ObjectId;

/**
 * Authenticate a user from the database.
 */
@Service("GuardiansDetailsService")
public class GuardiansDetailsServiceImpl implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(GuardiansDetailsServiceImpl.class);
	
	/**
	 * 
	 */
	private final GuardianRepository guardianRepository;
	
	/**
	 * 
	 * @param guardianRepository
	 */
	public GuardiansDetailsServiceImpl(final GuardianRepository guardianRepository) {
		super();
		this.guardianRepository = guardianRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		logger.debug("Autenticate User with email: " + email);
		
		return Optional.ofNullable(guardianRepository.findOneByEmail(email))
        	.map(guardianEntity -> {
        
        		logger.debug(guardianEntity.toString());
        		
        		Set<SimpleGrantedAuthority> grantedAuthorities = 
        				new HashSet<SimpleGrantedAuthority>();
        		
            	grantedAuthorities.add(new SimpleGrantedAuthority(guardianEntity.getAuthority().getAuthority()));
            	
            	UserDetailsImpl<ObjectId> userDetails =  new UserDetailsImpl<ObjectId>(guardianEntity.getId(), guardianEntity.getEmail(),
                		guardianEntity.getPassword(), guardianEntity.getFirstName(), guardianEntity.getLastName(), guardianEntity.isLocked(),
                		guardianEntity.getLastPasswordResetDate(), guardianEntity.isActive(), grantedAuthorities, 
                		guardianEntity.getLastAccessToAlerts(), guardianEntity.getLastLoginAccess(), guardianEntity.isPendingDeletion(),
                		guardianEntity.isVisible());

                return userDetails;
                
        	}).orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the " +
        "database"));
	}

}
