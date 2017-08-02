package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import sanchez.sanchez.sergio.dto.UserDTO;

/**
 *
 * @author sergio
 */
public interface IUserService {
    Long getTotalUsers();
    Page<UserDTO> findPaginated(Integer page, Integer size);
}
