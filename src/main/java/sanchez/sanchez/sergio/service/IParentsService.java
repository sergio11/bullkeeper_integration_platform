package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.dto.response.ParentDTO;
import sanchez.sanchez.sergio.dto.response.UserDTO;

/**
 *
 * @author sergio
 */
public interface IParentsService {
    Page<ParentDTO> findPaginated(Integer page, Integer size);
    Page<ParentDTO> findPaginated(Pageable pageable);
    ParentDTO getParentById(String id);
    Iterable<UserDTO> getChildrenOfParent(String id);
}
