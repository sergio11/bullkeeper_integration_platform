/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanchez.sanchez.sergio.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.service.IYoutubeService;

/**
 *
 * @author sergio
 */
@Service
public class YoutubeServiceImpl implements IYoutubeService {
    
    private Logger logger = LoggerFactory.getLogger(YoutubeServiceImpl.class);

    @Override
    public List<CommentEntity> getComments(String accessToken) {
        return Collections.<CommentEntity>emptyList();
    }

}
