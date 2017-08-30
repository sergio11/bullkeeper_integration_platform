package sanchez.sanchez.sergio.persistence.repository;

import java.util.List;
import sanchez.sanchez.sergio.dto.response.CommentsBySonDTO;



/**
 *
 * @author sergio
 */
public interface CommentRepositoryCustom {
    List<CommentsBySonDTO> getCommentsBySon();
}
