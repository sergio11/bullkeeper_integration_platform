package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import sanchez.sanchez.sergio.bullkeeper.persistence.results.IterationAvgAndTotalDuration;
import sanchez.sanchez.sergio.bullkeeper.persistence.results.IterationAvgDuration;

/**
 * @author sergio
 */
public interface IterationRepositoryCustom {
	IterationAvgDuration getAvgDuration();
	IterationAvgAndTotalDuration getAvgAndTotalDuration();
}
