package es.bisite.usal.bulltect.integration.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public interface IItegrationFlowService {
	Date getDateForNextPoll();
	void startSentimentAnalysisFor(List<ObjectId> commentsId);
}
