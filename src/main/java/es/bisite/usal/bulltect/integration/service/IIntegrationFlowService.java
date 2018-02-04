package es.bisite.usal.bulltect.integration.service;

import java.util.Date;

import es.bisite.usal.bulltect.persistence.entity.IterationEntity;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;

public interface IIntegrationFlowService {
	Date getDateForNextPoll();
	void scheduleSocialMediaForNextIteration(IterationWithTasksDTO iteration);
	void generateAlertsForIteration(IterationWithTasksDTO iteration);
}
