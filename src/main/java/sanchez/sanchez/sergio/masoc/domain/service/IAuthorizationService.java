package sanchez.sanchez.sergio.masoc.domain.service;

import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.masoc.web.security.userdetails.CommonUserDetailsAware;

/**
 * Authorization Service
 * @author sergiosanchezsanchez
 *
 */
public interface IAuthorizationService {

	/**
	 * Has Admin Role
	 * @return
	 */
    Boolean hasAdminRole();

    /**
     * Has Parent Role
     * @return
     */
    Boolean hasParentRole();

    /**
     * Has Change Password Privilege
     * @return
     */
    Boolean hasChangePasswordPrivilege();

    /**
     * Is Your Son
     * @param id
     * @return
     */
    Boolean isYourSon(final String id);

    /**
     * Get User Details
     * @return
     */
    CommonUserDetailsAware<ObjectId> getUserDetails();

    /**
     * Is The Authenticated User
     * @param id
     * @return
     */
    Boolean isTheAuthenticatedUser(final String id);

    /**
     * Grant Change Password Privilege
     * @param id
     */
    void grantChangePasswordPrivilege(final String id);
    
    /**
     * Is Your Profile Image
     * @param id
     * @return
     */
    Boolean isYourProfileImage(final String id);
    
    /**
     * It is a profile image of your child
     * @param id
     * @return
     */
    Boolean itIsAProfileImageOfYourChild(final String id);
}
