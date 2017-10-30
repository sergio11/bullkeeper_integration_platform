package es.bisite.usal.bulltect.rrss.service;

import java.util.Date;
import java.util.Set;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IInstagramService {
	Set<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken);
}
