package es.bisite.usal.bullytect.service;

import java.util.Date;
import java.util.List;

import es.bisite.usal.bullytect.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IInstagramService {
    List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken);
}
