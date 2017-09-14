package es.bisite.usal.bullytect.persistence.repository;

import java.util.List;

import es.bisite.usal.bullytect.dto.response.CommentsBySonDTO;



/**
 *
 * @author sergio
 */
public interface CommentRepositoryCustom {
    List<CommentsBySonDTO> getCommentsBySon();
}
