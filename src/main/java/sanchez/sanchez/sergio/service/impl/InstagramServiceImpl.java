/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanchez.sanchez.sergio.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sanchez.sanchez.sergio.exception.GetCommentsProcessException;
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
@Profile({"dev", "prod"})
public class InstagramServiceImpl implements IInstagramService {
    
    private Logger logger = LoggerFactory.getLogger(InstagramServiceImpl.class);
    
    @Value("${instagram.app.secret}")
    private String appSecret;
    
    @Autowired
    private IInstagramCommentMapper instagramMapper;

    @Override
    public List<CommentEntity> getCommentsLaterThan(Date startDate, String accessToken) {
        
        logger.debug("Call Instagram API for accessToken : " + accessToken + " on thread: " + Thread.currentThread().getName());
        
        List<CommentEntity> userComments = new ArrayList<>();
        
        try {
            Instagram instagram = new Instagram(accessToken, appSecret);
            String userId = instagram.getCurrentUserInfo().getData().getId();

            userComments = instagram
            	.getUserRecentMedia()
            	.getData()
            	.stream()
            	.map(mediaFeed -> mediaFeed.getComments())
            	.map(comments -> comments.getComments())
            	.flatMap(List::stream)
            	.filter(commentData -> !commentData.getCommentFrom().getId().equals(userId) && 
                		( startDate != null ? new Date(Long.parseLong(commentData.getCreatedTime())).after(startDate) : true ))
            	.map(commentData -> instagramMapper.instagramCommentToCommentEntity(commentData))
            	.collect(Collectors.toList());
            
            logger.debug("Total Instagram Comments: " + userComments.size());
        } catch (InstagramBadRequestException  e) {
            throw new InvalidAccessTokenException(SocialMediaTypeEnum.INSTAGRAM, accessToken);
        } catch (Exception e) {
            throw new GetCommentsProcessException(e);
        }
  
        return userComments;
        
    }
    
    @PostConstruct
    protected void init(){
        Assert.notNull(appSecret, "App Secret cannot be null");
    }
    
}
