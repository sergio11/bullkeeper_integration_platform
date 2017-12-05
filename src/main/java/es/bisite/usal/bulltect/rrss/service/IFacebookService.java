package es.bisite.usal.bulltect.rrss.service;

import java.util.Date;
import java.util.Set;

import com.restfb.exception.FacebookOAuthException;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;

/**
 *
 * @author sergio
 */
public interface IFacebookService {
    Set<CommentEntity> getCommentsReceived(String accessToken);
    Set<CommentEntity> getCommentsReceived(Date startDate, String accessToken);
    RegisterParentByFacebookDTO getRegistrationInformationForTheParent(String fbId, String token);
    String getFbIdByAccessToken(String accessToken);
    String getUserNameForAccessToken(String accessToken) throws FacebookOAuthException;
    String fetchUserPicture(String accessToken);
    String obtainExtendedAccessToken(String shortLivedToken);
}
