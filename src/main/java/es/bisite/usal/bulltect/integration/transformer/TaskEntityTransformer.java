package es.bisite.usal.bulltect.integration.transformer;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import es.bisite.usal.bulltect.integration.constants.IntegrationConstants;
import es.bisite.usal.bulltect.persistence.entity.CommentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.persistence.entity.TaskEntity;

public class TaskEntityTransformer implements GenericTransformer<Message<List<CommentEntity>>, TaskEntity> {

	@Override
	public TaskEntity transform(Message<List<CommentEntity>> message) {
		
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
        List<CommentEntity> comments = message.getPayload();
        for(CommentEntity comment: comments) {
            comment.setSonEntity(sonEntity);
        }
        return new TaskEntity(taskStart, taskFinish, duration, isSuccess, comments, sonEntity, socialMediaId);
	}

}
