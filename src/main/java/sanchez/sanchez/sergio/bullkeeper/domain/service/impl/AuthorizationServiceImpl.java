package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.lang.Assert;
import sanchez.sanchez.sergio.bullkeeper.domain.service.IAuthorizationService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.SupervisedChildrenRepository;
import sanchez.sanchez.sergio.bullkeeper.web.security.AuthoritiesConstants;
import sanchez.sanchez.sergio.bullkeeper.web.security.userdetails.CommonUserDetailsAware;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service("authorizationService")
public class AuthorizationServiceImpl implements IAuthorizationService {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    private final SupervisedChildrenRepository supervisedChildrenRepository;
    private final GuardianRepository guardianRepository;

    /**
     * 
     * @param supervisedChildrenRepository
     * @param guardianRepository
     */
    @Autowired
    public AuthorizationServiceImpl(final SupervisedChildrenRepository supervisedChildrenRepository, 
    		final GuardianRepository guardianRepository) {
        super();
        this.supervisedChildrenRepository = supervisedChildrenRepository;
        this.guardianRepository = guardianRepository;
    }

    /**
     * Has Admin Role
     */
    @Override
    public Boolean hasAdminRole() {
        return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN)));
    }

    /**
     * Has Guardian Role
     */
    @Override
    public Boolean hasGuardianRole() {
        return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(AuthoritiesConstants.GUARDIAN)));
    }

    /**
     * Has Change Password Privilege
     */
    @Override
    public Boolean hasChangePasswordPrivilege() {
        return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
    }

    /**
     * Get User Details
     */
    @Override
    public CommonUserDetailsAware<ObjectId> getUserDetails() {
        CommonUserDetailsAware<ObjectId> userDetails = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
        }
        return userDetails;
    }

    /**
     * Is Your Guardian
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean isYourGuardian(String id) {
    	
        boolean isYourGuardian = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) && 
        		id != null && !id.isEmpty() && ObjectId.isValid(id)) {
            
        	final CommonUserDetailsAware<ObjectId> userDetails = 
            		(CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            
        	// Check is your guardian
            isYourGuardian = supervisedChildrenRepository
            	.countByGuardianIdAndIsConfirmed(new ObjectId(id), true) > 0;
            
        }
        return isYourGuardian;
    }

    /**
     * 
     */
    @Override
    public Boolean isTheAuthenticatedUser(String id) {
        Boolean isTheAuthenticatedUser = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectId.isValid(id) && !(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            isTheAuthenticatedUser = userDetails.getUserId().equals(new ObjectId(id));
        }
        return isTheAuthenticatedUser;
    }

    /**
     * Grant Change Password Privilege
     */
    @Override
    public void grantChangePasswordPrivilege(String id) {
    	Assert.notNull(id, "Id can not be null");
        final GuardianEntity guardianEntity = guardianRepository
        		.findOne(new ObjectId(id));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                guardianEntity, null, Arrays.asList(
                        new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
    
    /**
     * Is Your Profile Image
     */
    @Override
    public Boolean isYourProfileImage(final String id) {
        Boolean isYourProfileImage = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("isYourProfileImage");
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            String profileImageId = guardianRepository.getGuardianImageIdByUserId(userDetails.getUserId());
            logger.debug("profileImageId -> " + profileImageId);
            logger.debug("check with  -> " + id);
            isYourProfileImage = profileImageId != null &&  profileImageId.equals(id);
        }
        return isYourProfileImage;
    }
    
    /**
     * It Is a profile image of Supervised Child
     */
    @Override
    public Boolean itIsAProfileImageOfSupervisedKid(String id) {
        Assert.notNull(id, "Id can not be null");
    	boolean itIsAProfileImageOfYourSupervisedKid = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            itIsAProfileImageOfYourSupervisedKid = 
            		supervisedChildrenRepository.countByGuardianIdAndKidProfileImage(
            				userDetails.getUserId(), id) > 0;
        }
        return itIsAProfileImageOfYourSupervisedKid;
    }

    /**
     * Init
     */
    @PostConstruct
    protected void init() {
        Assert.notNull(supervisedChildrenRepository, "Supervised Children Repository can not be null");
        Assert.notNull(guardianRepository, "Guardian Repository can not be null");

    }

}
