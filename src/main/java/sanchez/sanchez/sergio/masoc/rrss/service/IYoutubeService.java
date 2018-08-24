package sanchez.sanchez.sergio.masoc.rrss.service;

import java.util.Date;
import java.util.Set;

import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IYoutubeService {
	
	Set<CommentEntity> getCommentsReceived(String accessToken, String refreshToken);
    Set<CommentEntity> getCommentsReceived(Date startDate, String accessToken, String refreshToken);
}
