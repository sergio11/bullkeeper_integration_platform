package es.bisite.usal.bulltect.rrss.service.impl;


import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import es.bisite.usal.bulltect.exception.GetCommentsProcessException;
import es.bisite.usal.bulltect.exception.InvalidAccessTokenException;
import es.bisite.usal.bulltect.i18n.service.IMessageSourceResolverService;
import es.bisite.usal.bulltect.mapper.IInstagramCommentMapper;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SocialMediaTypeEnum;
import es.bisite.usal.bulltect.rrss.service.IInstagramService;
import es.bisite.usal.bulltect.util.Unthrow;

/**
 *
 * @author sergio
 */
@Service
@Profile({"dev", "prod"})
public class InstagramServiceImpl implements IInstagramService {
    
    private Logger logger = LoggerFactory.getLogger(InstagramServiceImpl.class);
    
    @Value("${instagram.app.secret}")
    private String appSecret;
   
    private final IInstagramCommentMapper instagramMapper;
    private final IMessageSourceResolverService messageSourceResolver;
    

    public InstagramServiceImpl(IInstagramCommentMapper instagramMapper, IMessageSourceResolverService messageSourceResolver) {
		super();
		this.instagramMapper = instagramMapper;
		this.messageSourceResolver = messageSourceResolver;
	}
	
	@Override
	public Set<CommentEntity> getCommentsReceived(String accessToken) {
		return getCommentsReceived(null, accessToken);
	}

	@Override
	public Set<CommentEntity> getCommentsReceived(Date startDate, String accessToken) {
		logger.debug("Call Instagram API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());
        
        Set<CommentEntity> userComments = new HashSet<>();
        
        try {
        	
        	final Instagram instagram = new Instagram(accessToken, appSecret);
 
            final UserInfoData userInfo = instagram.getCurrentUserInfo().getData();

            userComments = instagram
            	.getUserRecentMedia()
            	.getData()
            	.stream()
            	.map(mediaFeed -> Unthrow.wrap(() -> instagram.getMediaComments(mediaFeed.getId())))
            	.map(mediaCommentsFeed -> mediaCommentsFeed.getCommentDataList())
            	.flatMap(List::stream)
            	.filter(commentData -> !commentData.getCommentFrom().getId().equals(userInfo.getId()) && 
                		( startDate != null ? new Date(Long.parseLong(commentData.getCreatedTime())).after(startDate) : true ))
            	.map(commentData -> instagramMapper.instagramCommentToCommentEntity(commentData))
            	.collect(Collectors.toSet());
            
            
            logger.debug("Total Instagram Comments: " + userComments.size());
        } catch (InstagramBadRequestException  e) {
        	throw new InvalidAccessTokenException(
            		messageSourceResolver.resolver("invalid.access.token", new Object[]{ SocialMediaTypeEnum.INSTAGRAM.name() }),
            		SocialMediaTypeEnum.INSTAGRAM, accessToken);
        } catch (Exception e) {
        	logger.error(e.fillInStackTrace().toString());
            throw new GetCommentsProcessException(e.toString());
        }
  
        return userComments;
	}
    
    @PostConstruct
    protected void init(){
        Assert.notNull(appSecret, "App Secret cannot be null");
        Assert.notNull(instagramMapper, "Instagram Comment Mapper cannot be null");
        Assert.notNull(messageSourceResolver, "The Message Source Resolver can not be null");
    }
}
