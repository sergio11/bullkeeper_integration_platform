package sanchez.sanchez.sergio.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.dto.request.RegisterParentDTO;
import sanchez.sanchez.sergio.dto.request.RegisterSonDTO;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.SonDTO;
import sanchez.sanchez.sergio.dto.response.UserDTO;

/**
 *
 * @author sergio
 */
public interface IParentsService {
    Page<ParentDTO> findPaginated(Integer page, Integer size);
    Page<ParentDTO> findPaginated(Pageable pageable);
    ParentDTO getParentById(String id);
    ParentDTO getParentById(ObjectId id);
    Iterable<SonDTO> getChildrenOfParent(String id);
    ParentDTO save(final RegisterParentDTO registerParent);
    SonDTO addSon(String parentId, RegisterSonDTO registerSonDTO);
}
