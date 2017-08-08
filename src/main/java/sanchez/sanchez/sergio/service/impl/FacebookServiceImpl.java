package sanchez.sanchez.sergio.service.impl;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Album;
import com.restfb.types.Comment;
import com.restfb.types.Conversation;
import com.restfb.types.Message;
import com.restfb.types.Post;
import com.restfb.types.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.mapper.IFacebookCommentMapper;
import sanchez.sanchez.sergio.mapper.IFacebookMessageMapper;
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
    
    private final IFacebookCommentMapper facebookCommentMapper;
    private final IFacebookMessageMapper facebookMessageMapper;

    public FacebookServiceImpl(IFacebookCommentMapper facebookCommentMapper, IFacebookMessageMapper facebookMessageMapper) {
        this.facebookCommentMapper = facebookCommentMapper;
        this.facebookMessageMapper = facebookMessageMapper;
    }
    
    private Stream<Comment> getCommentsByObjectAfterThan(final FacebookClient facebookClient, final String objectId, final Date startDate, User user) {

        Connection<Comment> commentConnection
                = facebookClient.fetchConnection(objectId + "/comments", Comment.class);

        return StreamUtils.asStream(commentConnection.iterator())
                .flatMap(List::stream)
                .flatMap(comment
                        -> StreamUtils.concat(
                        getCommentsByObjectAfterThan(facebookClient, comment.getId(), startDate, user), comment)
                )
                .filter(comment -> !comment.getFrom().getId().equals(user.getId()) &&
                       (startDate != null ? comment.getCreatedTime().after(startDate) : true));
    }
    
    // Get Comments Stream from all posts.
    private Stream<Comment> getAllCommentsFromPostsAfterThan(final FacebookClient facebookClient, final Date startDate, User user) {
        Connection<Post> userFeed = facebookClient.fetchConnection("me/feed", Post.class);
        // Iterate over the feed to access the particular pages
        return StreamUtils.asStream(userFeed.iterator())
        		.flatMap(List::stream)
        		.flatMap(post -> getCommentsByObjectAfterThan(facebookClient, post.getId(), startDate, user));
  
    }
    
    // Get Comments Stream from all albums.
    private Stream<Comment> getAllCommentsFromAlbumsAfterThan(FacebookClient facebookClient, Date startDate, User user) {
        Connection<Album> userAlbums = facebookClient.fetchConnection("me/albums", Album.class);
        // Iterate over the albums to access the particular pages
        return StreamUtils.asStream(userAlbums.iterator())
        		.flatMap(List::stream)
        		.flatMap(album -> getCommentsByObjectAfterThan(facebookClient, album.getId(), startDate, user));
    }
    
    // Get Message from Facebook Conversations
    private Stream<CommentEntity> getAllCommentsFromConversationsAfterThan(FacebookClient facebookClient, Date startDate, User user) {
    	   Connection<Conversation> conversations = facebookClient.fetchConnection("me/conversations", Conversation.class);
        return StreamUtils.asStream(conversations.iterator())
                .flatMap(List::stream)
                .flatMap(conversation -> {
                    Connection<Message> messages = facebookClient.fetchConnection(
                            conversation.getId() + "/messages", Message.class, Parameter.with("fields", "message,created_time,from,id"));
                    return StreamUtils.asStream(messages.iterator());
                })
                .flatMap(List::stream)
                .filter(message
                        -> !message.getFrom().getId().equals(user.getId())
                && (startDate != null ? message.getCreatedTime().after(startDate) : true))
                .map(message -> facebookMessageMapper.facebookMessageToCommentEntity(message));
    }
   
    @Override
    public List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken) {
        
        List<CommentEntity> comments = new ArrayList<CommentEntity>();
        try {
            logger.debug("Call Facebook API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            // Get Information about access token owner
            User user = facebookClient.fetchObject("me", User.class);
            
            StreamUtils.concat(
                StreamUtils.concat(
                    getAllCommentsFromPostsAfterThan(facebookClient, startDate, user),
                    getAllCommentsFromAlbumsAfterThan(facebookClient, startDate, user)
                )
                .map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment)),
                getAllCommentsFromConversationsAfterThan(facebookClient, startDate, user)
            )
            .collect(Collectors.toList());
            
            logger.debug("Total Facebook comments : " + comments.size());
        } catch (FacebookOAuthException e) {
            logger.error(e.getErrorMessage());
            throw new InvalidAccessTokenException(SocialMediaTypeEnum.FACEBOOK, accessToken);
        } catch (Exception e) {
            throw new GetCommentsProcessException(e);
        }
        return comments;
    }
   
    @PostConstruct
    protected void init() {
        Assert.notNull(appKey, "The app key can not be null");
        Assert.notNull(appSecret, "The app secret can not be null");
        Assert.notNull(facebookCommentMapper, "The Facebook Comment Mapper can not be null");
    }
}
