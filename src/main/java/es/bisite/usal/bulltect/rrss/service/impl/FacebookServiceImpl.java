package es.bisite.usal.bulltect.rrss.service.impl;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Album;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import com.restfb.types.ProfilePictureSource;
import com.restfb.types.User;

import es.bisite.usal.bulltect.exception.GetCommentsProcessException;
import es.bisite.usal.bulltect.exception.GetInformationFromFacebookException;
import es.bisite.usal.bulltect.exception.InvalidAccessTokenException;
import es.bisite.usal.bulltect.exception.InvalidFacebookIdException;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.mapper.IFacebookCommentMapper;
import es.bisite.usal.bulltect.mapper.UserFacebookMapper;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.rrss.service.IFacebookService;
import es.bisite.usal.bulltect.util.StreamUtils;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author sergio
 */
@Service
@Profile({"dev", "prod"})
public class FacebookServiceImpl implements IFacebookService {

    private Logger logger = LoggerFactory.getLogger(FacebookServiceImpl.class);

    @Value("${facebook.app.key}")
    private String appKey;
    @Value("${facebook.app.secret}")
    private String appSecret;

    private final IFacebookCommentMapper facebookCommentMapper;
    private final IMessageSourceResolverService messageSourceResolver;
    private final UserFacebookMapper userFacebookMapper;

    public FacebookServiceImpl(IFacebookCommentMapper facebookCommentMapper,
            IMessageSourceResolverService messageSourceResolver, UserFacebookMapper userFacebookMapper) {
        this.facebookCommentMapper = facebookCommentMapper;
        this.messageSourceResolver = messageSourceResolver;
        this.userFacebookMapper = userFacebookMapper;
    }
    
    /**
     * Get recursive comments by Object Id.
     * @param facebookClient
     * @param objectId
     * @param filter
     * @return
     */
    private Stream<Comment> getCommentsByObject(final FacebookClient facebookClient, final String objectId, final Predicate<Comment> filter) {
    	Assert.notNull(facebookClient, "Facebook Client should be not null");
    	Assert.notNull(objectId, "ObjectId should be not null");
    	Assert.notNull(filter, "Filter should be not null");
    
    	Connection<Comment> commentConnection
         	= facebookClient.fetchConnection(objectId + "/comments", Comment.class,
                 Parameter.with("fields", "id, comment_count, created_time, from, message, like_count"));
    	
    	return StreamUtils.asStream(commentConnection.iterator())
    	         .flatMap(List::stream)
    	         .flatMap(comment -> comment.getCommentCount() > 0 ? StreamUtils.concat(
	            		 getCommentsByObject(facebookClient, comment.getId(), filter), comment)
	                     : Stream.of(comment)).filter(filter);
    	
    }

    /**
     * Get all the comments received in the posts from.
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<Comment> getAllCommentsFromPostsReceived(final FacebookClient facebookClient, final Date startDate, User user) {
        Connection<Post> userFeed = facebookClient.fetchConnection("me/feed", Post.class);
        // Iterate over the feed to access the particular pages
        return StreamUtils.asStream(userFeed.iterator())
                .flatMap(List::stream)
                .flatMap(post -> getCommentsByObject(facebookClient, post.getId(), comment -> !comment.getFrom().getId().equals(user.getId())
           	         && (startDate != null ? comment.getCreatedTime().after(startDate) : true)));

    }
    
 
    /**
     * Get All Comments From Albums Received After Than
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<Comment> getAllCommentsFromAlbumsReceived(FacebookClient facebookClient, Date startDate, User user) {
        Connection<Album> userAlbums = facebookClient.fetchConnection("me/albums", Album.class);
        // Iterate over the albums to access the particular pages
        return StreamUtils.asStream(userAlbums.iterator())
                .flatMap(List::stream)
                .flatMap(album -> {
                    logger.debug("Album -> " + album.getName() + "Description " + album.getDescription());
                    return getCommentsByObject(facebookClient, album.getId(), comment -> !comment.getFrom().getId().equals(user.getId())
                  	         && (startDate != null ? comment.getCreatedTime().after(startDate) : true));
                });
    }
    
    
    @Override
	public Set<CommentEntity> getCommentsReceived(String accessToken) {
		return getCommentsReceived(null, accessToken);
	}

    @Override
    public Set<CommentEntity> getCommentsReceived(Date startDate, String accessToken) {

        Set<CommentEntity> comments = new HashSet<CommentEntity>();
        try {
            logger.debug("Call Facebook API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());            
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            // Get Information about access token owner
            User user = facebookClient.fetchObject("me", User.class);
            logger.debug("Get Comments For User : " + user.getName() + " after than " + startDate);
            comments = StreamUtils.concat(
            		getAllCommentsFromPostsReceived(facebookClient, startDate, user),
            		getAllCommentsFromAlbumsReceived(facebookClient, startDate, user)
            )
                    .map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment))
                    .collect(Collectors.toSet());

            logger.debug("Total Facebook comments : " + comments.size());
            
            comments.forEach(commentToSaved -> {
            	 logger.debug("Comment -> " + commentToSaved.getMessage() + " Created Time : " + commentToSaved.getCreatedTime());
                 logger.debug("From -> " + commentToSaved.getAuthor());
            });
            
           
        } catch (FacebookOAuthException e) {
            logger.error(e.getErrorMessage());
            throw new InvalidAccessTokenException(
                    messageSourceResolver.resolver("invalid.access.token", new Object[]{SocialMediaTypeEnum.FACEBOOK.name()}),
                    SocialMediaTypeEnum.FACEBOOK, accessToken);
        } catch (Exception e) {
            throw new GetCommentsProcessException(e.toString());
        }
        return comments;
    }

    @Override
    public RegisterParentByFacebookDTO getRegistrationInformationForTheParent(String fbId, String accessToken) {

        Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");

        RegisterParentByFacebookDTO registerParent = null;

        try {
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            // Get Information about access token owner
            User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "email, name, first_name, last_name, birthday, locale"));
            if (!user.getId().equals(fbId)) {
                throw new InvalidFacebookIdException();
            }
            registerParent = userFacebookMapper.userFacebookToRegisterParentByFacebookDTO(user);
            registerParent.setFbAccessToken(accessToken);
        } catch (FacebookOAuthException e) {
            throw new GetInformationFromFacebookException();
        }

        return registerParent;

    }

    @Override
    public String getFbIdByAccessToken(String accessToken) {
        Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");

        String fbId = null;

        try {
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            User user = facebookClient.fetchObject("me", User.class);
            fbId = user.getId();
        } catch (FacebookOAuthException e) {
            throw new GetInformationFromFacebookException();
        }

        return fbId;

    }
    
    @Override
    public String fetchUserPicture(String accessToken) {
        FacebookClient client = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
        User user = client.fetchObject("me", User.class, Parameter.with("fields", "picture"));
        String profileImageUrl = null;
        final ProfilePictureSource picture = user.getPicture();
        if(picture != null) {
        	picture.setWidth(100);
            picture.setHeight(100);
            profileImageUrl = picture.getUrl();
            logger.debug("Profile Image " + profileImageUrl);
        }
        return profileImageUrl;
    }
    
    @Override
	public String obtainExtendedAccessToken(String shortLivedToken) {
    	FacebookClient facebookClient = new DefaultFacebookClient(shortLivedToken, Version.VERSION_2_8);
    	AccessToken extendedAccessToken = facebookClient.obtainExtendedAccessToken(appKey, appSecret, shortLivedToken);
    	return extendedAccessToken.getAccessToken();
	}

    @PostConstruct
    protected void init() {
        Assert.notNull(appKey, "The app key can not be null");
        Assert.notNull(appSecret, "The app secret can not be null");
        Assert.notNull(facebookCommentMapper, "The Facebook Comment Mapper can not be null");
        Assert.notNull(messageSourceResolver, "The Message Source Resolver can not be null");
    }
}
