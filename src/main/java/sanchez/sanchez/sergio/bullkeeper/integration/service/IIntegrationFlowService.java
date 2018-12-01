package sanchez.sanchez.sergio.bullkeeper.integration.service;

import java.util.Date;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.IterationWithTasksDTO;

public interface IIntegrationFlowService {
	Date getDateForNextPoll();
	void scheduleSocialMediaForNextIteration(IterationWithTasksDTO iteration);
	void generateAlertsForIteration(IterationWithTasksDTO iteration);
}
