package sanchez.sanchez.sergio.persistence.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.persistence.repository.SocialMediaRepositoryCustom;

/**
 * @author sergio
 */
public class SocialMediaRepositoryImpl implements SocialMediaRepositoryCustom {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void setAccessTokenAsInvalid(String accessToken, SocialMediaTypeEnum type) {
        Query query = new Query(Criteria
                .where("access_token")
                .is(accessToken)
                .and("social_media_type")
                .is(type.toString()));
        Update update = Update
            .update("invalid_token", "true");
        mongoTemplate.updateFirst(query, update, SocialMediaEntity.class);
    }   
}
