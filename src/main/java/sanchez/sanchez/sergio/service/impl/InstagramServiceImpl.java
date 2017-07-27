/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanchez.sanchez.sergio.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.jinstagram.Instagram;
import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.common.Comments;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramBadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.exception.InvalidAccessTokenException;
import sanchez.sanchez.sergio.mapper.IInstagramCommentMapper;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.persistence.entity.SocialMediaTypeEnum;
import sanchez.sanchez.sergio.service.IInstagramService;

/**
 *
 * @author sergio
 */
@Service
public class InstagramServiceImpl implements IInstagramService {
    
    private Logger logger = LoggerFactory.getLogger(InstagramServiceImpl.class);
    
    @Value("${instagram.app.secret}")
    private String appSecret;
    
    @Autowired
    private IInstagramCommentMapper instagramMapper;

    @Override
    public List<CommentEntity> getComments(String accessToken) {
        
        logger.debug("Call Instagram API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());
        
        List<CommentData> userComments = new ArrayList<>();
        
        try {
            Instagram instagram = new Instagram(accessToken, appSecret);
            MediaFeed mediaFeed = instagram.getUserRecentMedia();
            List<MediaFeedData> mediaFeeds = mediaFeed.getData();
            for (MediaFeedData mediaData : mediaFeeds) { 
                Comments comments = mediaData.getComments();
                userComments.addAll(comments.getComments());
            }
        } catch (InstagramBadRequestException  e) {
            throw new InvalidAccessTokenException(SocialMediaTypeEnum.INSTAGRAM, accessToken);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        
        logger.debug("Total Instagram Comments: " + userComments.size());
        
        return instagramMapper.instagramCommentsToCommentEntities(userComments);
        
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(appSecret, "App Secret cannot be null");
    }
    
}
