package es.bisite.usal.bulltect.persistence.repository;

import java.util.List;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.persistence.entity.CommentStatusEnum;
import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;



/**
 *
 * @author sergio
 */
public interface CommentRepositoryCustom {
    List<CommentsBySonDTO> getCommentsBySon();
    void updateCommentStatus(List<ObjectId> ids, CommentStatusEnum status);
    void cancelCommentsInprogress();
}
