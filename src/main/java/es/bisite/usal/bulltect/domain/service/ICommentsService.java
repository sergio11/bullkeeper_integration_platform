package es.bisite.usal.bulltect.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bulltect.web.dto.response.CommentDTO;

/**
 *
 * @author sergio
 */
public interface ICommentsService {
    
    Long getTotalComments();
    CommentDTO getCommentById(String id);
    Page<CommentDTO> findPaginated(Integer page, Integer size);
    Page<CommentDTO> findPaginated(Pageable pageable);
    Page<CommentDTO> getCommentBySonIdPaginated(Pageable pageable, String userId);
    Iterable<CommentDTO> getCommentBySonId(String userId);
}
