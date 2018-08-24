package sanchez.sanchez.sergio.masoc.domain.service;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.masoc.web.dto.request.RegisterParentByFacebookDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.RegisterParentByGoogleDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.SaveUserSystemPreferencesDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.UpdateParentDTO;
import sanchez.sanchez.sergio.masoc.web.dto.request.UpdateSonDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.ParentDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.SonDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.UserSystemPreferencesDTO;

/**
 * Parents Service Interface
 */
public interface IParentsService {
	
	
	/**
	 * Find Paginated
	 * @param page
	 * @param size
	 * @return
	 */
    Page<ParentDTO> findPaginated(final Integer page, final Integer size);
    
    /**
     * Find Paginated
     * @param pageable
     * @return
     */
    Page<ParentDTO> findPaginated(final Pageable pageable);
    
    /**
     * Get Parent By Id
     * @param id
     * @return
     */
    ParentDTO getParentById(final String id);
    
    /**
     * Get Parents 
     * @param email
     * @return
     */
    ParentDTO getParentByEmail(final String email);
    
    /**
     * Get Parent By Id
     * @param id
     * @return
     */
    ParentDTO getParentById(final ObjectId id);
    
    /**
     * Get Children Of Parent
     * @param id
     * @return
     */
    Iterable<SonDTO> getChildrenOfParent(final String id);
    
    /**
     * Save
     * @param registerParent
     * @return
     */
    ParentDTO save(final RegisterParentDTO registerParent);
    
    /**
     * Save
     * @param registerParent
     * @return
     */
    ParentDTO save(final RegisterParentByFacebookDTO registerParent);
    
    /**
     * Save
     * @param registerParent
     * @return
     */
    ParentDTO save(final RegisterParentByGoogleDTO registerParent);
    
    /**
     * Add Son
     * @param parentId
     * @param registerSonDTO
     * @return
     */
    SonDTO addSon(final String parentId, final RegisterSonDTO registerSonDTO);
    
    /**
     * Update Son
     * @param parentId
     * @param registerSonDTO
     * @return
     */
    SonDTO updateSon(final String parentId, final UpdateSonDTO registerSonDTO);
    
    /**
     * Set As Not Active And ConfirmationToken
     * @param id
     * @param confirmationToken
     */
    void setAsNotActiveAndConfirmationToken(final String id, final String confirmationToken);
    
    /**
     * Update
     * @param id
     * @param updateParentDTO
     * @return
     */
    ParentDTO update(final ObjectId id, final UpdateParentDTO updateParentDTO);
    
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
     * Get Parent By Fb Id
     * @param fbId
     * @return
     */
    ParentDTO getParentByFbId(final String fbId);
    
    /**
     * Get Parent By Google Id
     * @param googleId
     * @return
     */
    ParentDTO getParentByGoogleId(String googleId);
    
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
     * @param idParent
     * @return
     */
    UserSystemPreferencesDTO savePreferences(final SaveUserSystemPreferencesDTO preferences, final ObjectId idParent);
    
    /**
     * Get Preferences
     * @param idParent
     * @return
     */
    UserSystemPreferencesDTO getPreferences(final ObjectId idParent);
}
