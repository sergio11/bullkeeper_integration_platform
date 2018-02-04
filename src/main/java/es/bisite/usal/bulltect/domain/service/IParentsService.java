package es.bisite.usal.bulltect.domain.service;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByGoogleDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentDTO;
import es.bisite.usal.bulltect.web.dto.request.RegisterSonDTO;
import es.bisite.usal.bulltect.web.dto.request.SaveUserSystemPreferencesDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateParentDTO;
import es.bisite.usal.bulltect.web.dto.request.UpdateSonDTO;
import es.bisite.usal.bulltect.web.dto.response.ParentDTO;
import es.bisite.usal.bulltect.web.dto.response.SonDTO;
import es.bisite.usal.bulltect.web.dto.response.UserSystemPreferencesDTO;

/**
 *
 * @author sergio
 */
public interface IParentsService {
    Page<ParentDTO> findPaginated(Integer page, Integer size);
    Page<ParentDTO> findPaginated(Pageable pageable);
    ParentDTO getParentById(String id);
    ParentDTO getParentByEmail(String email);
    ParentDTO getParentById(ObjectId id);
    Iterable<SonDTO> getChildrenOfParent(String id);
    ParentDTO save(final RegisterParentDTO registerParent);
    ParentDTO save(final RegisterParentByFacebookDTO registerParent);
    ParentDTO save(final RegisterParentByGoogleDTO registerParent);
    SonDTO addSon(String parentId, RegisterSonDTO registerSonDTO);
    SonDTO updateSon(String parentId, UpdateSonDTO registerSonDTO);
    void setAsNotActiveAndConfirmationToken(String id, String confirmationToken);
    ParentDTO update(final ObjectId id, final UpdateParentDTO updateParentDTO);
    void changeUserPassword(ObjectId id, String newPassword);
    Boolean activateAccount(String token);
    void lockAccount(String id);
    void unlockAccount(String id);
    ParentDTO getParentByFbId(String fbId);
    ParentDTO getParentByGoogleId(String googleId);
    void updateFbAccessToken(String fbId, String fbAccessToken);
    void cancelAccountDeletionProcess(String confirmationToken);
    void startAccountDeletionProcess(ObjectId id, String confirmationToken);
    void deleteAccount(String confirmationToken);
    Long deleteUnactivatedAccounts();
    void cancelAllAccountDeletionProcess();
    void updateLastAccessToAlerts(ObjectId id);
    String getProfileImage(ObjectId id);
    UserSystemPreferencesDTO savePreferences(SaveUserSystemPreferencesDTO preferences, ObjectId idParent);
    UserSystemPreferencesDTO getPreferences(ObjectId idParent);
}
