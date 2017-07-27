
package sanchez.sanchez.sergio.service.impl;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.service.IYoutubeService;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.Comment;
import com.google.api.services.youtube.model.CommentListResponse;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.mapper.IYoutubeCommentMapper;

/**
 *
 * @author sergio
 */
@Service
public class YoutubeServiceImpl implements IYoutubeService {
    
    private Logger logger = LoggerFactory.getLogger(YoutubeServiceImpl.class);
    
    private final ApplicationContext appCtx;
    private final IYoutubeCommentMapper youtubeCommentMapper;

    public YoutubeServiceImpl(ApplicationContext appCtx, IYoutubeCommentMapper youtubeCommentMapper) {
        this.appCtx = appCtx;
        this.youtubeCommentMapper = youtubeCommentMapper;
    }
    
    private List<Comment> getCommentRelatedToChannel(final YouTube youTube, final String channelId) throws IOException {

        List<Comment> commentsForChannel = new ArrayList<>();

        CommentThreadListResponse commentThreadListResult = youTube.commentThreads()
                .list("snippet")
                .setAllThreadsRelatedToChannelId(channelId)
                .execute();

        if (commentThreadListResult != null) {
            List<CommentThread> commentThreads = commentThreadListResult.getItems();
            for (CommentThread commentThread : commentThreads) {
                Comment topLevelComment = commentThread.getSnippet().getTopLevelComment();
                commentsForChannel.add(topLevelComment);
                if (commentThread.getReplies().size() > 0) {
                    CommentListResponse commentListResponse = youTube
                            .comments().list("snippet").setParentId(topLevelComment.getId()).execute();
                    if (commentListResponse != null) {
                        commentsForChannel.addAll(commentListResponse.getItems());
                    }

                }

            }
        }

        return commentsForChannel;
    }
    
    
    
    @Override
    public List<CommentEntity> getComments(String refreshToken) {
        
        logger.debug("Call Youtube Data API for refreshToken : " + refreshToken + " on thread: " + Thread.currentThread().getName());
        List<Comment> userComments = new ArrayList<>();
        try {
            YouTube youTube = appCtx.getBean(YouTube.class, refreshToken);
            // Get Channels for User
            ChannelListResponse channelResult = youTube.channels().list("snippet").setMine(Boolean.TRUE).execute();
            List<Channel> channelsList = channelResult.getItems();
            if (channelsList != null) {
                for (Channel channel : channelsList) {
                    userComments.addAll(getCommentRelatedToChannel(youTube, channel.getId()));
                }
            }
  
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return youtubeCommentMapper.youtubeCommentsToCommentEntities(userComments);
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(youtubeCommentMapper, "Youtube Comment Mapper cannot be null");
        Assert.notNull(appCtx, "ApplicationContext cannot be null");
    }

}
