package es.bisite.usal.bulltect.rrss.service;

import java.util.Date;
import java.util.List;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IYoutubeService {
    List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken);
}
