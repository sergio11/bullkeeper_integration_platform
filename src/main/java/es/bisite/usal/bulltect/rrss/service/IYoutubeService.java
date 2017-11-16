package es.bisite.usal.bulltect.rrss.service;

import java.util.Date;
import java.util.Set;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IYoutubeService {
	
	Set<CommentEntity> getCommentsReceived(String accessToken, String refreshToken);
    Set<CommentEntity> getCommentsReceived(Date startDate, String accessToken, String refreshToken);
}
