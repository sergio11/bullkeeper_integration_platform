package sanchez.sanchez.sergio.bullkeeper.domain.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ConversationEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SupervisedChildrenEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.ConversationRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.GuardianRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
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
    private final KidRepository kidRepository;
    private final ConversationRepository conversationRepository;

    /**
     * 
     * @param supervisedChildrenRepository
     * @param guardianRepository
     * @param kidRepository
     */
    @Autowired
    public AuthorizationServiceImpl(final SupervisedChildrenRepository supervisedChildrenRepository, 
    		final GuardianRepository guardianRepository, 
    		final KidRepository kidRepository, final ConversationRepository conversationRepository) {
        super();
        this.supervisedChildrenRepository = supervisedChildrenRepository;
        this.guardianRepository = guardianRepository;
        this.kidRepository = kidRepository;
        this.conversationRepository = conversationRepository;
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
            	.countByGuardianIdAndKidIdAndIsConfirmedTrue(userDetails.getUserId(), 
            			new ObjectId(id)) > 0;
            
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
            final KidEntity kidEntity = kidRepository.findByProfileImage(id);
            itIsAProfileImageOfYourSupervisedKid = 
            		supervisedChildrenRepository.countByGuardianIdAndKidId(
            				userDetails.getUserId(), kidEntity.getId()) > 0;
        }
        return itIsAProfileImageOfYourSupervisedKid;
    }
   
    /**
     * is your guardian and can edit parental control rules
     */
    @Override
	public Boolean isYourGuardianAndCanEditParentalControlRules(String id) {
		Assert.notNull(id, "Id can not be null");
		
		boolean isAllowed = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            isAllowed = 
            		supervisedChildrenRepository
            		.countByGuardianIdAndKidIdAndRoleInAndIsConfirmedTrue(userDetails.getUserId(), new ObjectId(id),
            				Arrays.asList(GuardianRolesEnum.ADMIN, GuardianRolesEnum.PARENTAL_CONTROL_RULE_EDITOR)) > 0;
        }
        return isAllowed;
       
	}

    /**
     * Is Your Guardian and can edit kid information
     */
	@Override
	public Boolean isYourGuardianAndCanEditKidInformation(String id) {
		Assert.notNull(id, "Id can not be null");
		
		boolean isAllowed = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
            
            isAllowed = 
            		supervisedChildrenRepository
            			.countByGuardianIdAndKidIdAndRoleInAndIsConfirmedTrue(userDetails.getUserId(), new ObjectId(id),
            					Arrays.asList(GuardianRolesEnum.ADMIN)) > 0;
        }
        return isAllowed;
	}
	
	/**
	 * Is Your Profile Public
	 */
	@Override
	public Boolean isYourProfilePublic(final String id) {
		Assert.notNull(id, "Id can not be null");
		boolean isPublic = Boolean.FALSE;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
        	logger.debug("ID -> " + id);
        	final GuardianEntity guardianEntity = guardianRepository.findById(new ObjectId(id));
        	if(guardianEntity != null) {
        		isPublic = guardianEntity.isVisible();
        		logger.debug("Guardian Entity is not null");
        	} else {
        		logger.debug("Guradian ENtity is null");
        	}
        }
        return isPublic;
	}
	
	/**
	 * Get Current User ID
	 */
	@Override
	public ObjectId getCurrentUserId() {
		final CommonUserDetailsAware<ObjectId> userDetails = getUserDetails();
		return userDetails.getUserId();
	}
	

	/**
	 * Is Your Guardian
	 */
	@Override
	public Boolean isYourGuardian(final String guardian, final String kid) {
		Assert.notNull(guardian, "Guardian can not be null");
		Assert.notNull(kid, "Kid can not be null");
		
		boolean isYourGuardian = Boolean.FALSE;
		final Authentication auth = 
				SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) 
        		&& ObjectId.isValid(guardian) && ObjectId.isValid(kid) ) {
        	// Check is your guardian
            isYourGuardian = supervisedChildrenRepository
            	.countByGuardianIdAndKidIdAndIsConfirmedTrue(new ObjectId(guardian), 
            			new ObjectId(kid)) > 0;
            
        }
        return isYourGuardian;
	}
	
	/**
	 * Is Member of the conversation
	 * @param id
	 */
	@Override
	public Boolean isMemberOfTheConversation(final String id) {
		Assert.notNull(id, "Id can not be null");
		boolean isMember = Boolean.FALSE;
		final Authentication auth = 
				SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) 
        		&& ObjectId.isValid(id)) {
        	
        	final ConversationEntity conversation = 
        			conversationRepository.findOne(new ObjectId(id));
        	
        	if(conversation != null) {
        		
        		CommonUserDetailsAware<ObjectId> userDetails = (CommonUserDetailsAware<ObjectId>) auth.getPrincipal();
        		
        		isMember = conversation.getMemberOne().getId().equals(userDetails.getUserId()) || 
        				conversation.getMemberTwo().getId().equals(userDetails.getUserId());
        	}
        
            
        }
        return isMember;
	}

	/**
	 * Check If they can talk
	 * @param memberOne
	 * @param memberTwo
	 */
	@Override
	public Boolean checkIfTheyCanTalk(final String memberOne, final String memberTwo) {
		Assert.notNull(memberOne, "Member One can not be null");
		Assert.notNull(memberTwo, "Member Two can not be null");
		
		boolean canTalk = Boolean.FALSE;
		final Authentication auth = 
				SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken) 
        		&& ObjectId.isValid(memberOne) && ObjectId.isValid(memberTwo) ) {
        	
        	
        	boolean memberOneIsGuardian = 
        			guardianRepository.countById(new ObjectId(memberOne)) > 0;
        	boolean memberTwoIsGuardian = 
                	guardianRepository.countById(new ObjectId(memberTwo)) > 0;
                		
            if(memberOneIsGuardian && memberTwoIsGuardian) {
            	
            	logger.debug("CAN_TALK: Member one and member two are guardians");
            	final GuardianEntity memberOneEntity = guardianRepository.findOne(new ObjectId(memberOne));
            	final GuardianEntity memberTwoEntity = guardianRepository.findOne(new ObjectId(memberTwo));
            	canTalk = memberOneEntity.isVisible() && memberTwoEntity.isVisible();
            } else {
            	
            	final ObjectId guardianId = memberOneIsGuardian ? new ObjectId(memberOne): new ObjectId(memberTwo);
            	final ObjectId kidId = memberOneIsGuardian ? new ObjectId(memberTwo): new ObjectId(memberOne);
            	
            	logger.debug("CAN_TALK: Guardian Id -> " + guardianId.toString());
            	logger.debug("CAN_TALK: Kid Id -> " + kidId.toString());
            	
            	// Get Supervised Children
        		final List<SupervisedChildrenEntity> supervisedChildrenListSaved =
        					supervisedChildrenRepository.findByGuardianId(guardianId);
        		
        		logger.debug("CAN_TALK: Total supervised childs -> " + supervisedChildrenListSaved.size());
        		
        		canTalk = supervisedChildrenListSaved != null 
        				&& !supervisedChildrenListSaved.isEmpty() &&
        				supervisedChildrenListSaved
							.stream().map(model -> model.getKid().getId())
							.collect(Collectors.toList()).contains(kidId);
        		
            	
            }
            
        } else {
        	logger.debug("CAN_TALK:NO valid");
        }
		return canTalk;
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
