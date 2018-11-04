package sanchez.sanchez.sergio.masoc.domain.service.impl;

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
import sanchez.sanchez.sergio.masoc.domain.service.IAuthorizationService;
import sanchez.sanchez.sergio.masoc.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.masoc.persistence.repository.ParentRepository;
import sanchez.sanchez.sergio.masoc.persistence.repository.SonRepository;
import sanchez.sanchez.sergio.masoc.web.security.AuthoritiesConstants;
import sanchez.sanchez.sergio.masoc.web.security.userdetails.CommonUserDetailsAware;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
@Service("authorizationService")
public class AuthorizationServiceImpl implements IAuthorizationService {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);

    private final SonRepository sonRepository;
    private final ParentRepository parentRepository;

    /**
     * 
     * @param sonRepository
     * @param parentRepository
     */
    @Autowired
    public AuthorizationServiceImpl(SonRepository sonRepository, ParentRepository parentRepository) {
        super();
        this.sonRepository = sonRepository;
        this.parentRepository = parentRepository;
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
     * Has Parent Role
     */
    @Override
    public Boolean hasParentRole() {
        return (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(AuthoritiesConstants.PARENT)));
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
     * Is Your Son
     */
    @SuppressWarnings("unchecked")
    @Override
    public Boolean isYourSon(String id) {
        Boolean isYourSon = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectId.isValid(id) && !(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            isYourSon = sonRepository.countByParentIdAndId(userDetails.getUserId(), new ObjectId(id)) > 0 ? Boolean.TRUE : Boolean.FALSE;
        }
        return isYourSon;
    }

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

    @Override
    public void grantChangePasswordPrivilege(String id) {

        ParentEntity parentEntity = parentRepository.findOne(new ObjectId(id));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                parentEntity, null, Arrays.asList(
                        new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));

        SecurityContextHolder.getContext().setAuthentication(auth);

    }
    
    @Override
    public Boolean isYourProfileImage(String id) {
        Boolean isYourProfileImage = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("isYourProfileImage");
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            String profileImageId = parentRepository.getProfileImageIdByUserId(userDetails.getUserId());
            logger.debug("profileImageId -> " + profileImageId);
            logger.debug("check with  -> " + id);
            isYourProfileImage = profileImageId != null &&  profileImageId.equals(id);
        }
        return isYourProfileImage;
    }
    
    @Override
    public Boolean itIsAProfileImageOfYourChild(String id) {
        Boolean itIsAProfileImageOfYourChild = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            itIsAProfileImageOfYourChild = sonRepository.countByParentIdAndProfileImage(userDetails.getUserId(), id) > 0;
        }
        return itIsAProfileImageOfYourChild;
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(sonRepository, "Son Repository can not be null");
        Assert.notNull(parentRepository, "Parent Repository can not be null");

    }

}
