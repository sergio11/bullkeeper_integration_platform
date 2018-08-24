package sanchez.sanchez.sergio.masoc.integration.transformer;


import java.util.Date;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import sanchez.sanchez.sergio.masoc.integration.constants.IntegrationConstants;
import sanchez.sanchez.sergio.masoc.persistence.entity.CommentEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.TaskEntity;

public class TaskEntityTransformer implements GenericTransformer<Message<Set<CommentEntity>>, TaskEntity> {
	
	private Logger logger = LoggerFactory.getLogger(TaskEntityTransformer.class);

	@Override
	public TaskEntity transform(Message<Set<CommentEntity>> message) {
		
		logger.debug("Create task from this set");
		
		MessageHeaders headers = message.getHeaders();
		// Get son entity from headers
		SonEntity sonEntity = (SonEntity)headers.get(IntegrationConstants.USER_HEADER);
		// Get Task Start Date from headers
        Date taskStart = (Date)headers.get(IntegrationConstants.TASK_START_HEADER);
        // Task Finish
        Date taskFinish = new Date();
        // Calculate duration
        Long duration = Math.abs((taskStart.getTime() - taskFinish.getTime()) / 1000);
        // Get Social Media Id From Headers
        ObjectId socialMediaId = (ObjectId)headers.get(IntegrationConstants.SOCIAL_MEDIA_ID_HEADER);
        // Check if task failed.
        Boolean isSuccess = !headers.containsKey(IntegrationConstants.TASK_ERROR_HEADER);
        Set<CommentEntity> comments = message.getPayload();
        for(CommentEntity comment: comments) {
            comment.setSonEntity(sonEntity);
        }
        
        return new TaskEntity(taskStart, taskFinish, duration, isSuccess, comments, sonEntity, socialMediaId);
   
	}

}
