package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.dto.UserDTO;

/**
 *
 * @author sergio
 */
public interface IUserService {
    Long getTotalUsers();
    UserDTO getUserById(String id);
    Page<UserDTO> findPaginated(Integer page, Integer size);
    Page<UserDTO> findPaginated(Pageable pageable);
}
