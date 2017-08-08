package sanchez.sanchez.sergio.service.impl;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Album;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.mapper.IFacebookCommentMapper;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.service.IFacebookService;
import sanchez.sanchez.sergio.util.StreamUtils;

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
    
    private Stream<Comment> getCommentsByObjectAfterThan(final FacebookClient facebookClient, final String objectId, final Date startDate) {
    	
        Connection<Comment> commentConnection
                = facebookClient.fetchConnection(objectId + "/comments", Comment.class);
        
        return StreamUtils.asStream(commentConnection.iterator())
	        	.flatMap(List::stream)
	        	.flatMap(comment -> StreamUtils.defaultIfEmpty(
	        			getCommentsByObjectAfterThan(facebookClient, comment.getId(), startDate), () -> comment))
	        	.filter(comment -> startDate != null ? comment.getCreatedTime().after(startDate) : true);
    }
    
    private List<Comment> getAllCommentsFromPostsAfterThan(final FacebookClient facebookClient, final Date startDate) {
        Connection<Post> userFeed = facebookClient.fetchConnection("me/feed", Post.class);
        // Iterate over the feed to access the particular pages
        return StreamUtils.asStream(userFeed.iterator())
        		.flatMap(List::stream)
        		.flatMap(post -> {
        			logger.debug("POST ID -> " + post.getId());
        			return getCommentsByObjectAfterThan(facebookClient, post.getId(), startDate);
        		})
        		.collect(Collectors.toList());
  
    }
    
    private List<Comment> getAllCommentsFromAlbumsAfterThan(FacebookClient facebookClient, Date startDate) {
        Connection<Album> userAlbums = facebookClient.fetchConnection("me/albums", Album.class);
        // Iterate over the albums to access the particular pages
        return StreamUtils.asStream(userAlbums.iterator())
        		.flatMap(List::stream)
        		.flatMap(album -> getCommentsByObjectAfterThan(facebookClient, album.getId(), startDate))
        		.collect(Collectors.toList());
    }
   
    @Override
    public List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken) {
        
        List<Comment> comments = new ArrayList<Comment>();
        try {
            logger.debug("Call Facebook API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            // Get all comments for all posts.
            comments.addAll(getAllCommentsFromPostsAfterThan(facebookClient, startDate));
            // Get all Comments for all albums.
            comments.addAll(getAllCommentsFromAlbumsAfterThan(facebookClient, startDate));
            logger.debug("Total Facebook comments : " + comments.size());
        } catch (FacebookOAuthException e) {
        	logger.debug(accessToken + " IS INVALID");
            throw new InvalidAccessTokenException(SocialMediaTypeEnum.FACEBOOK, accessToken);
        } catch (Exception e) {
            throw new GetCommentsProcessException(e);
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
