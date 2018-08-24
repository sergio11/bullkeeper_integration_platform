package sanchez.sanchez.sergio.masoc.integration.service;

import java.util.Date;

import sanchez.sanchez.sergio.masoc.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.masoc.web.dto.response.IterationWithTasksDTO;

public interface IIntegrationFlowService {
	Date getDateForNextPoll();
	void scheduleSocialMediaForNextIteration(IterationWithTasksDTO iteration);
	void generateAlertsForIteration(IterationWithTasksDTO iteration);
}
