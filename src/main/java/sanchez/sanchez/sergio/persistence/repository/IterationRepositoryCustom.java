package sanchez.sanchez.sergio.persistence.repository;

import sanchez.sanchez.sergio.persistence.results.IterationAvgAndTotalDuration;
import sanchez.sanchez.sergio.persistence.results.IterationAvgDuration;

/**
 * @author sergio
 */
public interface IterationRepositoryCustom {
	IterationAvgDuration getAvgDuration();
	IterationAvgAndTotalDuration getAvgAndTotalDuration();
}
