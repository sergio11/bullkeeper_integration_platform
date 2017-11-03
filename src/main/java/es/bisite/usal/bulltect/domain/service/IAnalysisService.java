package es.bisite.usal.bulltect.domain.service;


import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import es.bisite.usal.bulltect.persistence.entity.AnalysisTypeEnum;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;

public interface IAnalysisService {
	void startAnalysisFor(final AnalysisTypeEnum type, final Map<ObjectId, List<ObjectId>> commentsBySon);
	void startSentimentAnalysisFor(Map<ObjectId, List<ObjectId>> commentsBySon);
	void startViolenceAnalysisFor(Map<ObjectId, List<ObjectId>> commentsBySon);
	void startDrugAnalysisFor(Map<ObjectId, List<ObjectId>> commentsBySon);
	void startAdultContentAnalysisFor(Map<ObjectId, List<ObjectId>> commentsBySon);
	void startBullyingAnalysisFor(Map<ObjectId, List<ObjectId>> commentsBySon);
	void startAnalysisFor(IterationWithTasksDTO iteration);
	
}
