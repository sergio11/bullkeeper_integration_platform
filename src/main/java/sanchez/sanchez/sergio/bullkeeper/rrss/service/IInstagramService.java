package sanchez.sanchez.sergio.bullkeeper.rrss.service;

import java.util.Date;
import java.util.Set;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IInstagramService {
	
	Set<CommentEntity> getCommentsReceived(String accessToken);
	Set<CommentEntity> getCommentsReceived(Date startDate, String accessToken);
}
