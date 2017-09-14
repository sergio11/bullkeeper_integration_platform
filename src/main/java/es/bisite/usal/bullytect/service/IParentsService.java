package es.bisite.usal.bullytect.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bullytect.dto.request.RegisterParentByFacebookDTO;
import es.bisite.usal.bullytect.dto.request.RegisterParentDTO;
import es.bisite.usal.bullytect.dto.request.RegisterSonDTO;
import es.bisite.usal.bullytect.dto.request.UpdateParentDTO;
import es.bisite.usal.bullytect.dto.response.ParentDTO;
import es.bisite.usal.bullytect.dto.response.SonDTO;

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
    SonDTO addSon(String parentId, RegisterSonDTO registerSonDTO);
    void setAsNotActiveAndConfirmationToken(String id, String confirmationToken);
    ParentDTO update(final ObjectId id, final UpdateParentDTO updateParentDTO);
    void changeUserPassword(ObjectId id, String newPassword);
    Boolean activateAccount(String token);
    void lockAccount(String id);
    void unlockAccount(String id);
    ParentDTO getParentByFbId(String fbId);
    void updateFbAccessToken(String fbId, String fbAccessToken);
    void cancelAccountDeletionProcess(String confirmationToken);
    void startAccountDeletionProcess(ObjectId id, String confirmationToken);
    Long deleteAccount(String confirmationToken);
    Long deleteUnactivatedAccounts();
    void cancelAllAccountDeletionProcess();
}
