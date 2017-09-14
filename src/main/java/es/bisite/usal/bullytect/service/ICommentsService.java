package es.bisite.usal.bullytect.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bullytect.dto.response.CommentDTO;

/**
 *
 * @author sergio
 */
public interface ICommentsService {
    
    Long getTotalComments();
    CommentDTO getCommentById(String id);
    Page<CommentDTO> findPaginated(Integer page, Integer size);
    Page<CommentDTO> findPaginated(Pageable pageable);
    Page<CommentDTO> getCommentBySonId(Pageable pageable, String userId);
    
}
