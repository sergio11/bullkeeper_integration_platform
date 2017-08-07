package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.dto.response.CommentDTO;

/**
 *
 * @author sergio
 */
public interface ICommentsService {
    
    Long getTotalComments();
    CommentDTO getCommentById(String id);
    Page<CommentDTO> findPaginated(Integer page, Integer size);
    Page<CommentDTO> findPaginated(Pageable pageable);
    Page<CommentDTO> getCommentByUserId(Pageable pageable, String userId);
    
}
