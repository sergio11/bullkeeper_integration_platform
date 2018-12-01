package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AnalysisTypeEnum;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.IterationWithTasksDTO;

/**
 * Analysis Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IAnalysisService {
	
	/**
	 * Start Analysis For
	 * @param type
	 * @param commentsByKid
	 */
	void startAnalysisFor(final AnalysisTypeEnum type, final Map<ObjectId, List<ObjectId>> commentsByKid);
	
	/**
	 * Start Sentiment Analysis For
	 * @param commentsByKid
	 */
	void startSentimentAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsByKid);
	
	/**
	 * Start Violence Analysis 
	 * @param commentsByKid
	 */
	void startViolenceAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsByKid);
	
	/**
	 * Start Drug Analysis For
	 * @param commentsByKid
	 */
	void startDrugAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsByKid);
	
	/**
	 * Start Adult Content Analysis For
	 * @param commentsByKid
	 */
	void startAdultContentAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsByKid);
	
	/**
	 * Start Bullying Analysis For
	 * @param commentsByKid
	 */
	void startBullyingAnalysisFor(final Map<ObjectId, List<ObjectId>> commentsByKid);
	
	/**
	 * Start Analysis For
	 * @param iteration
	 */
	void startAnalysisFor(final IterationWithTasksDTO iteration);
	
}
