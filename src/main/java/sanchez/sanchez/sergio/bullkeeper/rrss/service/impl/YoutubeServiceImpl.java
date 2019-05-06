
package sanchez.sanchez.sergio.bullkeeper.rrss.service.impl;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.ArrayMap;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentListResponse;
import com.google.api.services.youtube.model.CommentThreadListResponse;

import sanchez.sanchez.sergio.bullkeeper.exception.GetCommentsProcessException;
import sanchez.sanchez.sergio.bullkeeper.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.bullkeeper.i18n.service.IMessageSourceResolverService;
import sanchez.sanchez.sergio.bullkeeper.mapper.YoutubeCommentMapper;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.rrss.service.IYoutubeService;
import sanchez.sanchez.sergio.bullkeeper.util.StreamUtils;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import org.springframework.util.Assert;

/**
 *
 * @author sergio
 */
@Service
@Profile({"dev", "prod"})
public class YoutubeServiceImpl implements IYoutubeService {
    
    private Logger logger = LoggerFactory.getLogger(YoutubeServiceImpl.class);
    
    private final ApplicationContext appCtx;
    private final YoutubeCommentMapper youtubeCommentMapper;
    private final IMessageSourceResolverService messageSourceResolver;

    /**
     * 
     * @param appCtx
     * @param youtubeCommentMapper
     * @param messageSourceResolver
     */
    public YoutubeServiceImpl(ApplicationContext appCtx, YoutubeCommentMapper youtubeCommentMapper, IMessageSourceResolverService messageSourceResolver) {
        this.appCtx = appCtx;
        this.youtubeCommentMapper = youtubeCommentMapper;
        this.messageSourceResolver = messageSourceResolver;
    }
    
    
    /**
     * Get Comments
     * @param socialMedia
     */
    @Override
	public Set<CommentEntity> getComments(final SocialMediaEntity socialMedia) {
		return getComments(null, socialMedia);
	}

    /**
     * Get Comments
     * @param startDate
     * @param socialMedia
     */
	@Override
	public Set<CommentEntity> getComments(Date startDate, final SocialMediaEntity socialMedia) {
		logger.debug("Call Youtube Data API for access token : "
				+ socialMedia.getAccessToken() + " and refresh token " + socialMedia.getRefreshToken() + "on thread: " + Thread.currentThread().getName());
        Set<CommentEntity> userComments = new HashSet<>();
    
           
        try {
            YouTube youTube = appCtx.getBean(YouTube.class, socialMedia.getAccessToken(), 
            		socialMedia.getRefreshToken());
            // Get Channels for User
            userComments = Optional.ofNullable(getChannelsForCurrentUser(youTube))
            	.map(channelResult -> channelResult.getItems())
            	.map(Collection::stream)
            	.filter(Objects::nonNull)
            	.orElse(Stream.empty())
            	.flatMap(channel -> getCommentRelatedToChannelAfterThan(youTube, channel.getId(), startDate))
            	.map(comment -> youtubeCommentMapper.youtubeCommentToCommentEntity(comment))
            	.collect(Collectors.toSet());
            
            logger.debug("·Total Youtube Comments: " + userComments.size());
        
        } catch (Throwable e) {
        	logger.error("Exception on Youtube Service, Message -> " + e.getMessage());
        	if(e.getCause() instanceof GoogleJsonResponseException) {
        		int code = ((GoogleJsonResponseException)e.getCause()).getDetails().getCode();
        		
        		if(code == 401 || code == 403) {
        			logger.debug("InvalidAccessTokenException FOR YOUTUBE");
            		throw new InvalidAccessTokenException(
                    		messageSourceResolver.resolver("invalid.access.token", new Object[]{ SocialMediaTypeEnum.YOUTUBE.name() }),
                    		SocialMediaTypeEnum.YOUTUBE, socialMedia.getAccessToken());
        		}
        		
        	}
            throw new GetCommentsProcessException(e.toString());
        }
        return userComments;
	}
	
	
	/**
	 * 
	 * Private Methods
	 * =======================
	 * 
	 */
	
	/**
     * Get Channels For Current User
     * @param youTube
     * @return
     */
    private ChannelListResponse getChannelsForCurrentUser(final YouTube youTube) {
    	try {
    		return youTube.channels().list("snippet").setMine(Boolean.TRUE).execute();
    	}
    	catch (IOException e) {
			throw new UncheckedIOException(e);
		}
    	
    }
    
    /**
     * Get Comment Thread Lists
     * @param youTube
     * @param channelId
     * @return
     */
    private CommentThreadListResponse getCommentThreadLists(final YouTube youTube, final String channelId) {
    	try {
			return youTube.commentThreads().list("snippet, replies")
					.setAllThreadsRelatedToChannelId(channelId)
			        .execute();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
    }
    
    /**
     * Get Replies For Comment
     * @param youTube
     * @param parentId
     * @return
     */
    private CommentListResponse getRepliesForComment(final YouTube youTube, final String parentId){
    	try {
    		return youTube.comments().list("snippet")
    				.setParentId(parentId).execute();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
    	
    }
    
    /**
     * Get Comment Related to Channel After Than
     * @param youTube
     * @param channelId
     * @param startDate
     * @return
     */
    private Stream<Comment> getCommentRelatedToChannelAfterThan(final YouTube youTube, final String channelId, Date startDate) {

    	logger.debug("Get Comments for YouTube Channel Id -> " + channelId);
    	
		return Optional.ofNullable(getCommentThreadLists(youTube, channelId))
				.map(commentThreadListResult -> commentThreadListResult.getItems()).map(Collection::stream)
				.filter(Objects::nonNull).orElse(Stream.empty())

				.flatMap(commentThread -> {
					Comment topLevelComment = commentThread.getSnippet().getTopLevelComment();
					return commentThread.getReplies() != null ?

							StreamUtils.concat(
									Optional.ofNullable(getRepliesForComment(youTube, topLevelComment.getId()))
											.map(commentListResponse -> commentListResponse.getItems())
											.map(Collection::stream).filter(Objects::nonNull).orElse(Stream.empty()),
									topLevelComment)

							: Stream.of(topLevelComment);

				}).filter(comment -> {
					
					String authorChannelId = ((ArrayMap<String, String>)comment.getSnippet().getAuthorChannelId()).getValue(0);
					Date publishAt = new Date(comment.getSnippet().getPublishedAt().getValue());
					return authorChannelId.equals(channelId) && ( startDate != null ? publishAt.after(startDate) : true);
					
				});
    }
	
    
    @PostConstruct
    protected void init(){
        Assert.notNull(youtubeCommentMapper, "Youtube Comment Mapper cannot be null");
        Assert.notNull(appCtx, "ApplicationContext cannot be null");
        Assert.notNull(messageSourceResolver, "The Message Source Resolver can not be null");
    }

}
