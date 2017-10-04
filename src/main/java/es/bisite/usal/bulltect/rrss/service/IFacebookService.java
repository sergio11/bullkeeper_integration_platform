package es.bisite.usal.bulltect.rrss.service;

import java.util.Date;
import java.util.List;

import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;

/**
 *
 * @author sergio
 */
public interface IFacebookService {
    List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken);
    RegisterParentByFacebookDTO getRegistrationInformationForTheParent(String fbId, String token);
    String getFbIdByAccessToken(String accessToken);
}