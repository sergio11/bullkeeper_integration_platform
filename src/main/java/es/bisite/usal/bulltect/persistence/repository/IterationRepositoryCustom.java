package es.bisite.usal.bulltect.persistence.repository;

import es.bisite.usal.bulltect.persistence.results.IterationAvgAndTotalDuration;
import es.bisite.usal.bulltect.persistence.results.IterationAvgDuration;

/**
 * @author sergio
 */
public interface IterationRepositoryCustom {
	IterationAvgDuration getAvgDuration();
	IterationAvgAndTotalDuration getAvgAndTotalDuration();
}
