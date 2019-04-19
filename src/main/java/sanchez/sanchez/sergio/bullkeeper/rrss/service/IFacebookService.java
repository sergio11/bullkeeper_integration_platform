package sanchez.sanchez.sergio.bullkeeper.rrss.service;

import java.util.Date;
import java.util.Set;

import com.restfb.exception.FacebookOAuthException;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByFacebookDTO;

/**
 * Facebook Service
 * @author sergio
 */
public interface IFacebookService {
	
	/**
	 * Get Comments for social media instance
	 * @param socialMedia
	 * @return
	 */
    Set<CommentEntity> getComments(final SocialMediaEntity socialMedia);
    
    /**
     * Get Comments For social media instance
     * @param startDate
     * @param accessToken
     * @return
     */
    Set<CommentEntity> getComments(Date startDate, final SocialMediaEntity socialMedia);
    
    /**
     * Get Registration Information For The Parent
     * @param fbId
     * @param token
     * @return
     */
    RegisterGuardianByFacebookDTO getRegistrationInformationForTheParent(final String fbId, final String token);
    
    /**
     * Get Facebook Id by Access Token
     * @param accessToken
     * @return
     */
    String getFbIdByAccessToken(final String accessToken);
    
    /**
     * Get Username for Access Token
     * @param accessToken
     * @return
     * @throws FacebookOAuthException
     */
    String getUserNameForAccessToken(final String accessToken) throws FacebookOAuthException;
    
    /**
     * Fetch User Picture
     * @param accessToken
     * @return
     */
    String fetchUserPicture(final String accessToken);
    
    /**
     * Obtain Extended Access Token
     * @param shortLivedToken
     * @return
     */
    String obtainExtendedAccessToken(final String shortLivedToken);
}
