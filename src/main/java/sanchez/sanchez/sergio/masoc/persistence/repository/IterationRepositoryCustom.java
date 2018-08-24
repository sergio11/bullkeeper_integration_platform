package sanchez.sanchez.sergio.masoc.persistence.repository;

import sanchez.sanchez.sergio.masoc.persistence.results.IterationAvgAndTotalDuration;
import sanchez.sanchez.sergio.masoc.persistence.results.IterationAvgDuration;

/**
 * @author sergio
 */
public interface IterationRepositoryCustom {
	IterationAvgDuration getAvgDuration();
	IterationAvgAndTotalDuration getAvgAndTotalDuration();
}
