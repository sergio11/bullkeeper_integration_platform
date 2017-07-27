package sanchez.sanchez.sergio.persistence.repository;

import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;

/**
 * @author sergio
 */
public interface SocialMediaRepositoryCustom {
    void setAccessTokenAsInvalid(String accessToken, SocialMediaTypeEnum type);
}
