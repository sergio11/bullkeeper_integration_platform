package sanchez.sanchez.sergio.masoc.domain.service;

import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import sanchez.sanchez.sergio.masoc.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.masoc.web.dto.response.IterationWithTasksDTO;

/**
 * Analysis Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IAnalysisService {
	
	/**
	 * Start Analysis For
	 * @param type
	 * @param commentsBySon
	 */
	void startAnalysisFor(final AnalysisTypeEnum type, final Map<ObjectId, List<ObjectId>> commentsBySon);
	
	/**
	 * Start Sentiment Analysis For
	 * @param commentsBySon
	 */
	void startSentimentAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsBySon);
	
	/**
	 * Start Violence Analysis 
	 * @param commentsBySon
	 */
	void startViolenceAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsBySon);
	
	/**
	 * Start Drug Analysis For
	 * @param commentsBySon
	 */
	void startDrugAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsBySon);
	
	/**
	 * Start Adult Content Analysis For
	 * @param commentsBySon
	 */
	void startAdultContentAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsBySon);
	
	/**
	 * Start Bullying Analysis For
	 * @param commentsBySon
	 */
	void startBullyingAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsBySon);
	
	/**
	 * Start Analysis For
	 * @param iteration
	 */
	void startAnalysisFor(final IterationWithTasksDTO iteration);
	
}
