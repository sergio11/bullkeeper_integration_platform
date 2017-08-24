package sanchez.sanchez.sergio.persistence.repository;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;

/**
 * @author sergio
 */
public interface SocialMediaRepositoryCustom {
    void setAccessTokenAsInvalid(String accessToken, SocialMediaTypeEnum type);
    void setScheduledFor(List<ObjectId> ids, Date scheduledFor);
    void setScheduledFor(ObjectId id, Date scheduledFor);
    void setScheduledForAndLastProbing(List<ObjectId> ids, Date scheduledFor, Date lastProbing);
    void setScheduledForAndLastProbing(ObjectId id, Date scheduledFor, Date lastProbing);
}
