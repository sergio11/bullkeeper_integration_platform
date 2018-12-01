package sanchez.sanchez.sergio.bullkeeper.integration.transformer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import sanchez.sanchez.sergio.bullkeeper.domain.service.IIterationService;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.IterationWithTasksDTO;


@Component
public class IterationEntityTransformer implements GenericTransformer<Message<IterationEntity>, IterationWithTasksDTO> {

	private Logger logger = LoggerFactory.getLogger(IterationEntityTransformer.class);
	
	@Autowired
	private IIterationService iterationService;
	
	
	@Override
	public IterationWithTasksDTO transform(Message<IterationEntity> message) {
		return iterationService.save(message.getPayload());
	}

}
