package sanchez.sanchez.sergio.service;

import java.util.Date;
import java.util.List;

import sanchez.sanchez.sergio.dto.request.RegisterParentByFacebookDTO;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IFacebookService {
    List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken);
    RegisterParentByFacebookDTO getRegistrationInformationForTheParent(String token);
}
