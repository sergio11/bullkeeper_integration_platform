package sanchez.sanchez.sergio.bullkeeper.domain.service;


import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByFacebookDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByGoogleDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveUserSystemPreferencesDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.UpdateGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.UpdateKidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ChildrenOfGuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GuardianDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SupervisedChildrenDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.UserSystemPreferencesDTO;

/**
 * Guardian Service Interface
 */
public interface IGuardianService {
	
	
	/**
	 * Find Paginated
	 * @param page
	 * @param size
	 * @return
	 */
    Page<GuardianDTO> findPaginated(final Integer page, final Integer size);
    
    /**
     * Find Paginated
     * @param pageable
     * @return
     */
    Page<GuardianDTO> findPaginated(final Pageable pageable);
    
    /**
     * Get Guardian By Id
     * @param id
     * @return
     */
    GuardianDTO getGuardianById(final String id);
    
    /**
     * Get Guardian By Email 
     * @param email
     * @return
     */
    GuardianDTO getGuardianByEmail(final String email);
    
    /**
     * Get Guardian By Id
     * @param id
     * @return
     */
    GuardianDTO getGuardianById(final ObjectId id);
    
    /**
     * Get Kids Of Guardian
     * @param id
     * @return
     */
    ChildrenOfGuardianDTO getKidsOfGuardian(final String id);
    
    /**
     * Get Kids Of Guardian
     * @param id
     * @param patternText
     * @return
     */
    ChildrenOfGuardianDTO getKidsOfGuardian(final String id, final String patternText);
    
    /**
     * Save
     * @param registerGuardian
     * @return
     */
    GuardianDTO save(final RegisterGuardianDTO registerGuardian);
    
    /**
     * Save
     * @param registerGuardian
     * @return
     */
    GuardianDTO save(final RegisterGuardianByFacebookDTO registerGuardian);
    
    /**
     * Save
     * @param registerParent
     * @return
     */
    GuardianDTO save(final RegisterGuardianByGoogleDTO registerParent);
    
    /**
     * Add Kid
     * @param id
     * @param registerKidDTO
     * @return
     */
    KidDTO addKid(final String id, final RegisterKidDTO registerKidDTO);
    
    /**
     * Update Kid
     * @param guardian
     * @param registerSonDTO
     * @return
     */
    KidDTO updateKid(final String guardian, final UpdateKidDTO registerSonDTO);
    
    /**
     * Set As Not Active And ConfirmationToken
     * @param id
     * @param confirmationToken
     */
    void setAsNotActiveAndConfirmationToken(final String id, final String confirmationToken);
    
    /**
     * Update
     * @param id
     * @param updateGuardianDTO
     * @return
     */
    GuardianDTO update(final ObjectId id, final UpdateGuardianDTO updateGuardianDTO);
    
    /**
     * Change User Password
     * @param id
     * @param newPassword
     */
    void changeUserPassword(final ObjectId id, final String newPassword);
    
    /**
     * Activate Account
     * @param token
     * @return
     */
    Boolean activateAccount(final String token);
    
    /**
     * Lock Account
     * @param id
     */
    void lockAccount(final String id);
    
    /**
     * UnLock Account
     * @param id
     */
    void unlockAccount(final String id);
    
    /**
     * Get Guardian By Fb Id
     * @param fbId
     * @return
     */
    GuardianDTO getGuardianByFbId(final String fbId);
    
    /**
     * Get Guardian By Google Id
     * @param googleId
     * @return
     */
    GuardianDTO getGuardianByGoogleId(String googleId);
    
    /**
     * Update Fb Access Token
     * @param fbId
     * @param fbAccessToken
     */
    void updateFbAccessToken(final String fbId, final String fbAccessToken);
    
    /**
     * Cancel Account Deletion Process
     * @param confirmationToken
     */
    void cancelAccountDeletionProcess(final String confirmationToken);
    
    /**
     * Start Account Deletion Process
     * @param id
     * @param confirmationToken
     */
    void startAccountDeletionProcess(final ObjectId id, final String confirmationToken);
    
    /**
     * Delete Account
     * @param confirmationToken
     */
    void deleteAccount(final String confirmationToken);
    
    /**
     * Delete Unactivated Accounts
     * @return
     */
    Long deleteUnactivatedAccounts();
    
    /**
     * Cancel All Account Deletion Process
     */
    void cancelAllAccountDeletionProcess();
    
    /**
     * Update Last Acess To Alerts
     * @param id
     */
    void updateLastAccessToAlerts(final ObjectId id);
    
    /**
     * Get Profile Image
     * @param id
     * @return
     */
    String getProfileImage(final ObjectId id);
    
    /**
     * Save Preferences
     * @param preferences
     * @param guardian
     * @return
     */
    UserSystemPreferencesDTO savePreferences(final SaveUserSystemPreferencesDTO preferences, 
    		final ObjectId guardian);
    
    /**
     * Get Preferences
     * @param guardian
     * @return
     */
    UserSystemPreferencesDTO getPreferences(final ObjectId guardian);

    /**
     * Search
     * @param text
     * @param exclude
     * @return
     */
    Iterable<GuardianDTO> search(final String text, final List<ObjectId> exclude);
    
    /**
     * Change Email
     * @param currentEmail
     * @param newEmail
     */
    void changeEmail(final String currentEmail, final String newEmail);
    
    /**
     * Change Password
     * @param guardian
     * @param newPassword
     */
    void changePassword(final ObjectId guardian, final String newPassword);

}
