package es.bisite.usal.bulltect.integration.transformer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import es.bisite.usal.bulltect.domain.service.IIterationService;
import es.bisite.usal.bulltect.persistence.entity.IterationEntity;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;


@Component
public class IterationEntityTransformer implements GenericTransformer<Message<IterationEntity>, IterationWithTasksDTO> {

	
	@Autowired
	private IIterationService iterationService;
	
	
	@Override
	public IterationWithTasksDTO transform(Message<IterationEntity> message) {
		return iterationService.save(message.getPayload());
	}

}
