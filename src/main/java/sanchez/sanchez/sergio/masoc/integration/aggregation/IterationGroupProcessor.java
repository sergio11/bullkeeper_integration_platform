package sanchez.sanchez.sergio.masoc.integration.aggregation;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import sanchez.sanchez.sergio.masoc.integration.constants.IntegrationConstants;
import sanchez.sanchez.sergio.masoc.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.TaskEntity;

public class IterationGroupProcessor extends AbstractAggregatingMessageGroupProcessor {
	
	private Logger logger = LoggerFactory.getLogger(IterationGroupProcessor.class);

	
	@Override
	protected Map<String, Object> aggregateHeaders(MessageGroup messageGroup) {
		
		Map<String, Object> headers = new HashMap<String, Object>();
		
		Date startAt = (Date)messageGroup.getOne().getHeaders().get(IntegrationConstants.ITERATION_START_HEADER);
		Date finishAt = new Date();
		Long duration = Math.abs((startAt.getTime() - finishAt.getTime()) / 1000);
		
		headers.put(IntegrationConstants.ITERATION_START_HEADER, startAt);
		headers.put(IntegrationConstants.ITERATION_FINISH_HEADER, finishAt);
		headers.put(IntegrationConstants.ITERATION_DURATION_HEADER, duration);
		headers.put(MessageHeaders.ERROR_CHANNEL, "errorChannel");
	
		return headers;
		
	}

	@Override
	protected Object aggregatePayloads(MessageGroup messageGroup, Map<String, Object> headers) {
		logger.debug("IterationGroupProcessor ... ");
		Date iterationStart = (Date)headers.get(IntegrationConstants.ITERATION_START_HEADER);
        Date iterationFinish = (Date)headers.get(IntegrationConstants.ITERATION_FINISH_HEADER);
        Long duration = (Long)headers.get(IntegrationConstants.ITERATION_DURATION_HEADER);
        IterationEntity iterationEntity = new IterationEntity(iterationStart, iterationFinish, duration);
        iterationEntity.setTotalTasks(messageGroup.getMessages().size());
        for(Message<?> message: messageGroup.getMessages()){
            TaskEntity task = (TaskEntity)message.getPayload();
            if(task.isSuccess())
                iterationEntity.setTotalComments(iterationEntity.getTotalComments() + task.getComments().size());
            else
                iterationEntity.setTotalFailedTasks(iterationEntity.getTotalFailedTasks() + 1);
            iterationEntity.addTask(task);
            
        }
        return iterationEntity;
	}

}
