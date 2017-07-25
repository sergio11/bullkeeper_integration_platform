package sanchez.sanchez.sergio.service.impl;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.mapper.IFacebookCommentMapper;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
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
    
    @Autowired
    private IFacebookCommentMapper facebookCommentMapper;
    
    private List<Comment> getAllCommentsForPosts(FacebookClient facebookClient) {
        List<Comment> comments = new ArrayList<>();
        Connection<Post> userFeed = facebookClient.fetchConnection("me/feed", Post.class);
        // Iterate over the feed to access the particular pages
        for (List<Post> userFeedPage : userFeed) {

            // Iterate over the list of contained data 
            // to access the individual object
            for (Post post : userFeedPage) {
                Connection<Comment> commentConnection
                        = facebookClient.fetchConnection(post.getId() + "/comments",
                                Comment.class, Parameter.with("limit", 10));
                for (List<Comment> commentPage : commentConnection) comments.addAll(commentPage);
            }
        }
        return comments;
    }
   
    @Override
    public List<CommentEntity> getComments(String accessToken) {
        
        List<Comment> comments = Collections.<Comment>emptyList();
        try {
            logger.info("Call Facebook API");
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            // Get all comments for all posts.
            List<Comment> commentsForPosts = getAllCommentsForPosts(facebookClient);
            logger.info("Total comments : " + comments.size());
            comments = commentsForPosts;
            
        } catch (FacebookOAuthException e) {
            throw new InvalidAccessTokenException(SocialMediaTypeEnum.FACEBOOK, accessToken);
        } catch (FacebookException e) {
            logger.error(e.toString());
        }
        // map all facebook comments to comments entities.
        return facebookCommentMapper.facebookCommentsToCommentEntities(comments);
    }
   
    @PostConstruct
    protected void init() {
        Assert.notNull(appKey, "The app key can not be null");
        Assert.notNull(appSecret, "The app secret can not be null");
        Assert.notNull(facebookCommentMapper, "The Facebook Comment Mapper can not be null");
    }
}
