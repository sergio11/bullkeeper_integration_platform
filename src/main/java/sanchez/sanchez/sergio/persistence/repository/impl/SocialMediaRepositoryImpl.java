package sanchez.sanchez.sergio.persistence.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static Logger logger = LoggerFactory.getLogger(SocialMediaRepositoryImpl.class);
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void setAccessTokenAsInvalid(String accessToken, SocialMediaTypeEnum type) {
        
        logger.debug("UPDATE SOCIAL MEDIA WITH ACCESS_TOKEN: " + accessToken + " AND SOCIAL MEDIA: " + type.name());
        
        Query query = new Query(Criteria
                .where("access_token")
                .is(accessToken)
                .and("social_media_type")
                .is(type.name()));
        Update update = Update
            .update("invalid_token", "true");
        Boolean result = mongoTemplate.updateFirst(query, update, SocialMediaEntity.class).isUpdateOfExisting();
        
    }   
}
