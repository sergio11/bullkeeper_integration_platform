package sanchez.sanchez.sergio.bullkeeper.rrss.service;

import java.util.Date;
import java.util.Set;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaEntity;

/**
 * Instagram Service
 * @author sergio
 */
public interface IInstagramService {
	
	/**
	 * 
	 * @param accessToken
	 * @return
	 */
	Set<CommentEntity> getComments(final SocialMediaEntity socialMediaEntity);
	
	/**
	 * 
	 * @param startDate
	 * @param accessToken
	 * @return
	 */
	Set<CommentEntity> getComments(Date startDate, final SocialMediaEntity socialMediaEntity);
}
