package sanchez.sanchez.sergio.service.impl;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Album;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import java.util.ArrayList;
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
    
    private List<Comment> getCommentsByObject(FacebookClient facebookClient, String objectId) {
        List<Comment> comments = new ArrayList<>();
        Connection<Comment> commentConnection
                = facebookClient.fetchConnection(objectId + "/comments",
                        Comment.class, Parameter.with("limit", 10));
        for (List<Comment> commentPage : commentConnection) {
            comments.addAll(commentPage);
        }
        return comments;
    }
    
    private List<Comment> getAllCommentsForPosts(FacebookClient facebookClient) {
        List<Comment> commentsForPosts = new ArrayList<Comment>();
        Connection<Post> userFeed = facebookClient.fetchConnection("me/feed", Post.class);
        // Iterate over the feed to access the particular pages
        for (List<Post> userFeedPage : userFeed) {
            logger.debug("Total Posts : " + userFeedPage.size());
            // Iterate over the list of contained data 
            // to access the individual object
            for (Post post : userFeedPage) {
                commentsForPosts.addAll(getCommentsByObject(facebookClient, post.getId()));
            }
        }
        return commentsForPosts;
    }
    
    private List<Comment> getAllCommentsForAlbums(FacebookClient facebookClient) {
        List<Comment> commentsForAlbums = new ArrayList<Comment>();
        Connection<Album> userAlbums = facebookClient.fetchConnection("me/albums", Album.class);
        for (List<Album> userAlbumsPage : userAlbums) {
            logger.debug("Total Albums : " + userAlbumsPage.size());
            for (Album album : userAlbumsPage) {
                commentsForAlbums.addAll(getCommentsByObject(facebookClient, album.getId()));
            }
        }
        return commentsForAlbums;
    }
   
    @Override
    public List<CommentEntity> getComments(String accessToken) {
        
        List<Comment> comments = new ArrayList<Comment>();
        try {
            logger.debug("Call Facebook API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            // Get all comments for all posts.
            comments.addAll(getAllCommentsForPosts(facebookClient));
            // Get all Comments for all albums.
            comments.addAll(getAllCommentsForAlbums(facebookClient));
            logger.debug("Total comments : " + comments.size());
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
