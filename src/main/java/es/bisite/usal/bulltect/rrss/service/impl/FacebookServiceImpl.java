package es.bisite.usal.bulltect.rrss.service.impl;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.Album;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import com.restfb.types.User;

import es.bisite.usal.bulltect.exception.GetCommentsProcessException;
import es.bisite.usal.bulltect.exception.InvalidAccessTokenException;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.mapper.IFacebookCommentMapper;
import es.bisite.usal.bulltect.mapper.UserFacebookMapper;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.rrss.service.IFacebookService;
import es.bisite.usal.bulltect.util.StreamUtils;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByFacebookDTO;
import es.bisite.usal.bulltect.web.rest.exception.GetInformationFromFacebookException;
import es.bisite.usal.bulltect.web.rest.exception.InvalidFacebookIdException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private Stream<Comment> getCommentsByObjectAfterThan(final FacebookClient facebookClient, final String objectId, final Date startDate, User user) {

        Connection<Comment> commentConnection
                = facebookClient.fetchConnection(objectId + "/comments", Comment.class,
                        Parameter.with("fields", "id, comment_count, created_time, from, message, like_count"));

        return StreamUtils.asStream(commentConnection.iterator())
                .flatMap(List::stream)
                .flatMap(comment
                        -> {

                    logger.debug("Comment -> " + comment.getMessage() + " Created Time : " + comment.getCreatedTime());
                    logger.debug("From -> " + comment.getFrom());

                    return comment.getCommentCount() > 0 ? StreamUtils.concat(
                            getCommentsByObjectAfterThan(facebookClient, comment.getId(), startDate, user), comment)
                            : Stream.of(comment);
                }
                ).filter(comment -> !comment.getFrom().getId().equals(user.getId())
                && (startDate != null ? comment.getCreatedTime().after(startDate) : true));
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
                .flatMap(album -> {
                    logger.debug("Album -> " + album.getName() + "Description " + album.getDescription());
                    return getCommentsByObjectAfterThan(facebookClient, album.getId(), startDate, user);
                });
    }

    @Override
    public List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken) {

        List<CommentEntity> comments = new ArrayList<CommentEntity>();
        try {
            logger.debug("Call Facebook API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());
            FacebookClient facebookClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_8);
            // Get Information about access token owner
            User user = facebookClient.fetchObject("me", User.class);

            comments = StreamUtils.concat(
                    getAllCommentsFromPostsAfterThan(facebookClient, startDate, user),
                    getAllCommentsFromAlbumsAfterThan(facebookClient, startDate, user)
            )
                    .map(comment -> facebookCommentMapper.facebookCommentToCommentEntity(comment))
                    .collect(Collectors.toList());

            logger.debug("Total Facebook comments : " + comments.size());
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
        String profileImageUrl = user.getPicture().getUrl();
        logger.debug("Profile Image " + profileImageUrl);
        return profileImageUrl;
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(appKey, "The app key can not be null");
        Assert.notNull(appSecret, "The app secret can not be null");
        Assert.notNull(facebookCommentMapper, "The Facebook Comment Mapper can not be null");
        Assert.notNull(messageSourceResolver, "The Message Source Resolver can not be null");
    }

}
