package es.bisite.usal.bulltect.persistence.repository;

import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;



/**
 *
 * @author sergio
 */
public interface CommentRepositoryCustom {
    List<CommentsBySonDTO> getCommentsBySon();
    void startSentimentAnalysisFor(Collection<ObjectId> ids);
    /*void updateCommentStatus(List<ObjectId> ids, AnalysisStatusEnum status);
    void cancelCommentsInprogress();*/
}
