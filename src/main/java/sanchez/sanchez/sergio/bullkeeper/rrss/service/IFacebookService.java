package sanchez.sanchez.sergio.bullkeeper.rrss.service;

import java.util.Date;
import java.util.Set;

import com.restfb.exception.FacebookOAuthException;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByFacebookDTO;

/**
 *
 * @author sergio
 */
public interface IFacebookService {
    Set<CommentEntity> getCommentsReceived(String accessToken);
    Set<CommentEntity> getCommentsReceived(Date startDate, String accessToken);
    RegisterGuardianByFacebookDTO getRegistrationInformationForTheParent(String fbId, String token);
    String getFbIdByAccessToken(String accessToken);
    String getUserNameForAccessToken(String accessToken) throws FacebookOAuthException;
    String fetchUserPicture(String accessToken);
    String obtainExtendedAccessToken(String shortLivedToken);
}
