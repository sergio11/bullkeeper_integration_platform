package sanchez.sanchez.sergio.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.dto.request.UpdateParentDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;

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
    SonDTO addSon(String parentId, RegisterSonDTO registerSonDTO);
    void setAsNotActiveAndConfirmationToken(String id, String confirmationToken);
    ParentDTO update(final ObjectId id, final UpdateParentDTO updateParentDTO);
    void changeUserPassword(ObjectId id, String newPassword);
}
