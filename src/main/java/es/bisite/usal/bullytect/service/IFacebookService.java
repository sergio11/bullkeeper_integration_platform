package es.bisite.usal.bullytect.service;

import java.util.Date;
import java.util.List;

import es.bisite.usal.bullytect.dto.request.RegisterParentByFacebookDTO;
import es.bisite.usal.bullytect.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IFacebookService {
    List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken);
    RegisterParentByFacebookDTO getRegistrationInformationForTheParent(String fbId, String token);
}
