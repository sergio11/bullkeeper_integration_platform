package es.bisite.usal.bullytect.persistence.repository;

import es.bisite.usal.bullytect.persistence.results.IterationAvgAndTotalDuration;
import es.bisite.usal.bullytect.persistence.results.IterationAvgDuration;

/**
 * @author sergio
 */
public interface IterationRepositoryCustom {
	IterationAvgDuration getAvgDuration();
	IterationAvgAndTotalDuration getAvgAndTotalDuration();
}
