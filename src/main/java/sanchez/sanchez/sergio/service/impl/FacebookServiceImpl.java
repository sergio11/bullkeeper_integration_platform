package sanchez.sanchez.sergio.service.impl;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.service.IFacebookService;

/**
 * @author sergio
 */
@Service
public class FacebookServiceImpl implements IFacebookService {
    
    private Logger logger = LoggerFactory.getLogger(FacebookServiceImpl.class);
    
    @Value("${facebook.app.key}")
    private String appKey;
    @Value("${facebook.app.secret}")
    private String appSecret;
    
    @Override
    public List<CommentEntity> getComments(String accessToken) {
        return Arrays.asList(new CommentEntity[]{
            new CommentEntity("Comentario 1 from facebook dirigido a "),
            new CommentEntity("Comentario 2 from facebook dirigido a ")
        });
    }
   
    @PostConstruct
    protected void init() {
        Assert.notNull(appKey, "The app key can not be null");
        Assert.notNull(appSecret, "The app secret can not be null");
    }
}
