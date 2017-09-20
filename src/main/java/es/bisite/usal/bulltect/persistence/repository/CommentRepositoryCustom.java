package es.bisite.usal.bulltect.persistence.repository;

import java.util.List;

import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;



/**
 *
 * @author sergio
 */
public interface CommentRepositoryCustom {
    List<CommentsBySonDTO> getCommentsBySon();
}
