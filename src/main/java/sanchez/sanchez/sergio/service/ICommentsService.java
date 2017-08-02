package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import sanchez.sanchez.sergio.dto.CommentDTO;

/**
 *
 * @author sergio
 */
public interface ICommentsService {
    
    Long getTotalComments();
    Page<CommentDTO> findPaginated(Integer page, Integer size);
    
}
