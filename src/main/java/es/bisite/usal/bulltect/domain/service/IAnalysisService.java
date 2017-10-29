package es.bisite.usal.bulltect.domain.service;


import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;

public interface IAnalysisService {

	void startSentimentAnalysisFor(Map<ObjectId, List<ObjectId>> commentsBySon);
	void startAnalysisFor(IterationWithTasksDTO iteration);
	
}
