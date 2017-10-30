package es.bisite.usal.bulltect.integration.transformer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.domain.service.IIterationService;
import es.bisite.usal.bulltect.persistence.entity.IterationEntity;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;


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
