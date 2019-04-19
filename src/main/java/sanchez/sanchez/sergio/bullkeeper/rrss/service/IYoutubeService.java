package sanchez.sanchez.sergio.bullkeeper.rrss.service;

import java.util.Date;
import java.util.Set;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaEntity;

/**
 * YouTube Service
 * @author sergio
 */
public interface IYoutubeService {
	
	/**
	 * Get Comments for Social Media instance
	 * @param accessToken
	 * @param refreshToken
	 * @return
	 */
	Set<CommentEntity> getComments(final SocialMediaEntity socialMediaEntity);
	
	/**
	 * Get Comments for Social Media Instance
	 * @param startDate
	 * @param accessToken
	 * @param refreshToken
	 * @return
	 */
    Set<CommentEntity> getComments(Date startDate, final SocialMediaEntity socialMediaEntity);
}
