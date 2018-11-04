package sanchez.sanchez.sergio.masoc.rrss.service.impl;

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
import com.restfb.types.Video;
import sanchez.sanchez.sergio.masoc.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.masoc.exception.GetInformationFromFacebookException;
import sanchez.sanchez.sergio.masoc.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.masoc.exception.InvalidFacebookIdException;
import sanchez.sanchez.sergio.masoc.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.masoc.mapper.IFacebookCommentMapper;
import sanchez.sanchez.sergio.masoc.mapper.IFacebookPostMapper;
import sanchez.sanchez.sergio.masoc.mapper.UserFacebookMapper;
import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.masoc.rrss.service.IFacebookService;
import sanchez.sanchez.sergio.masoc.util.StreamUtils;
import sanchez.sanchez.sergio.masoc.web.dto.request.RegisterParentByFacebookDTO;

import com.restfb.types.Photo;

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
    private final IFacebookPostMapper facebookPostMapper;
    private final IMessageSourceResolverService messageSourceResolver;
    private final UserFacebookMapper userFacebookMapper;

    public FacebookServiceImpl(IFacebookCommentMapper facebookCommentMapper, IFacebookPostMapper facebookPostMapper,
            IMessageSourceResolverService messageSourceResolver, UserFacebookMapper userFacebookMapper) {
        this.facebookCommentMapper = facebookCommentMapper;
        this.facebookPostMapper = facebookPostMapper;
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
    private Stream<Comment> getCommentsByObject(final FacebookClient facebookClient, final String objectId, final Integer offset,
    		final Integer limit, final Predicate<Comment> filter) {
    	Assert.notNull(facebookClient, "Facebook Client should be not null");
    	Assert.notNull(objectId, "ObjectId should be not null");
    	Assert.notNull(offset, "Offset can not be null");
    	Assert.notNull(limit, "Limit can not be null");
    	Assert.isTrue(offset >= 0, "offset should be greater than zero");
    	Assert.isTrue(limit > 0, "limit should be greater than zero");
    	Assert.notNull(filter, "Filter should be not null");
    	
    
    	Connection<Comment> commentConnection
         	= facebookClient.fetchConnection(objectId + "/comments", Comment.class,
                 Parameter.with("fields", "id, comment_count, created_time, from, message, like_count"),
                 Parameter.with("limit", limit),
                 Parameter.with("offset", offset));

    	return StreamUtils.asStream(commentConnection.iterator())
    	         .flatMap(List::stream)
    	         .map(comment -> {
    	        	 logger.debug("Comment " + comment.getMessage());
    	        	 return comment;
    	         })
    	         .flatMap(comment -> comment.getCommentCount() > 0 ? StreamUtils.concat(
	            		 getCommentsByObject(facebookClient, comment.getId(), filter), comment)
	                     : Stream.of(comment));
    	
    }
    
    private Stream<Comment> getCommentsByObject(final FacebookClient facebookClient, final String objectId,  final Predicate<Comment> filter) {
    	return getCommentsByObject(facebookClient, objectId, 0, 100000, filter);
    }

    /**
     * Get all the comments received in the posts from.
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<CommentEntity> getAllCommentsFromPostsReceived(final FacebookClient facebookClient, final Date startDate, User user) {
        
    	Connection<Post> userFeed = facebookClient.fetchConnection("me/feed", 
        		Post.class, Parameter.with("fields", "message, link, from, created_time, comments.summary(true) , likes.limit(1).summary(true)"));
    	

        return StreamUtils.asStream(userFeed.iterator())
                .flatMap(List::stream)
                .filter(post -> ( !post.getFrom().getId().equals(user.getId()) &&
                		( post.getMessage() != null && !post.getMessage().isEmpty()) ) || 
                		post.getCommentsCount() > 0)
                .flatMap(post -> {
                	
                	Stream<CommentEntity> streamComments;
                	
                	
                	if((!post.getFrom().getId().equals(user.getId()) &&
                    		( post.getMessage() != null && !post.getMessage().isEmpty())) && post.getCommentsCount() > 0) {
                		
                		// Obtemos texto de la publicación y sus comentarios
                		
                		streamComments = StreamUtils.concat(Stream.of(facebookPostMapper.facebookPostToCommentEntity(post)), getCommentsByObject(facebookClient, post.getId(), comment -> !comment.getFrom().getId().equals(user.getId())
                     	         && (startDate != null ? comment.getCreatedTime().after(startDate) : true)).map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment)));
                		
                	} else {
                		
                		if(post.getCommentsCount() > 0){
                			
                			streamComments = getCommentsByObject(facebookClient, post.getId(), comment -> !comment.getFrom().getId().equals(user.getId())
                          	         && (startDate != null ? comment.getCreatedTime().after(startDate) : true)).map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment));
                			
                		} else {
                			
                			streamComments = Stream.of(facebookPostMapper.facebookPostToCommentEntity(post));
                		}
                	
                	}
                	
                	return streamComments;
                	
                	
                });

    }
    
    /**
     * Get All Comments From All Uploaded Videos
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<CommentEntity> getAllCommentsFromUploadedVideos(FacebookClient facebookClient, Date startDate, User user){
    	Connection<Video> userUploadedVideos = facebookClient.fetchConnection("me/videos/uploaded", Video.class,
    			Parameter.with("fields", "created_time, from, title"));
    	
    	return StreamUtils.asStream(userUploadedVideos.iterator())
    			.flatMap(List::stream)
                .flatMap(video -> getCommentsByObject(facebookClient, video.getId(), comment -> !comment.getFrom().getId().equals(user.getId())
            	         && (startDate != null ? comment.getCreatedTime().after(startDate) : true)))
                .map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment));
    	
    }
    
    /**
     * Get All Comments From Tagged videos
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<CommentEntity> getAllCommentsFromTaggedVideos(FacebookClient facebookClient, Date startDate, User user){
    	
    	Connection<Video> userTaggedVideos = facebookClient.fetchConnection("me/videos/tagged", Video.class ,
    			Parameter.with("fields", "created_time, from, title"));
    	
    	logger.debug(userTaggedVideos.toString());
    	
    	return StreamUtils.asStream(userTaggedVideos.iterator())
    			.flatMap(List::stream)
    			.map(video -> {
    				logger.debug(video.toString());
    				return video;
    			})
                .flatMap(video -> getCommentsByObject(facebookClient, video.getId(), comment -> (comment.getMessage() != null && !comment.getMessage().isEmpty()) 
                		&& !comment.getFrom().getId().equals(user.getId()) && (startDate != null ? comment.getCreatedTime().after(startDate) : true) 
                		&&  comment.getMessage().contains(user.getName()) ))
                .map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment));
    
    	
    }
    
    /**
     * Get all comments from photos uploaded for user
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<CommentEntity> getAllCommentsFromPhotosUploaded(FacebookClient facebookClient, Date startDate, User user){
    	final Connection<Photo> photosUploaded = facebookClient.fetchConnection("me/photos/uploaded", Photo.class);
    	
    	return StreamUtils
			.asStream(photosUploaded.iterator())
			.flatMap(List::stream)
			.flatMap(photo -> getCommentsByObject(facebookClient, photo.getId(), comment ->  !comment.getFrom().getId().equals(user.getId())
        	         && (startDate != null ? comment.getCreatedTime().after(startDate) : true)))
			.map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment));
    	
    }
    
    /**
     * Get all comments from tagged photos
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<CommentEntity> getAllCommentsFromTaggedPhotos(FacebookClient facebookClient, Date startDate, User user){
    	final Connection<Photo> photosUploaded = facebookClient.fetchConnection("me/photos/tagged", Photo.class);
    	
    	return StreamUtils
			.asStream(photosUploaded.iterator())
			.flatMap(List::stream)
			.flatMap(photo -> getCommentsByObject(facebookClient, photo.getId(), comment -> (comment.getMessage() != null && !comment.getMessage().isEmpty()) 
            		&& !comment.getFrom().getId().equals(user.getId()) && (startDate != null ? comment.getCreatedTime().after(startDate) : true) 
            		&&  comment.getMessage().contains(user.getName())   ))
			.map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment));
    	
    }
   
    
 
    /**
     * Get All Comments From Albums Received After Than
     * @param facebookClient
     * @param startDate
     * @param user
     * @return
     */
    private Stream<CommentEntity> getAllCommentsFromAlbumsReceived(FacebookClient facebookClient, Date startDate, User user) {
        Connection<Album> userAlbums = facebookClient.fetchConnection("me/albums", Album.class);
        // Iterate over the albums to access the particular pages
        return StreamUtils.asStream(userAlbums.iterator())
                .flatMap(List::stream)
                .flatMap(album -> getCommentsByObject(facebookClient, album.getId(), comment -> !comment.getFrom().getId().equals(user.getId())
            	         && (startDate != null ? comment.getCreatedTime().after(startDate) : true))
                ).map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment));
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
            		getAllCommentsFromAlbumsReceived(facebookClient, startDate, user),
            		getAllCommentsFromPhotosUploaded(facebookClient, startDate, user),
            		getAllCommentsFromTaggedPhotos(facebookClient, startDate, user)
            		//getAllCommentsFromUploadedVideos(facebookClient, startDate, user),
            		//getAllCommentsFromTaggedVideos(facebookClient, startDate, user)
            )
             .collect(Collectors.toSet());

            // 
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
    
    @Override
	public String getUserNameForAccessToken(String accessToken) throws FacebookOAuthException {
    	Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");
        
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
        User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "name"));
        return user != null ? user.getName() : "";
	}

    @PostConstruct
    protected void init() {
        Assert.notNull(appKey, "The app key can not be null");
        Assert.notNull(appSecret, "The app secret can not be null");
        Assert.notNull(facebookCommentMapper, "The Facebook Comment Mapper can not be null");
        Assert.notNull(facebookPostMapper, "The Facebook Post Mapper can not be null");
        Assert.notNull(messageSourceResolver, "The Message Source Resolver can not be null");
    }
}
