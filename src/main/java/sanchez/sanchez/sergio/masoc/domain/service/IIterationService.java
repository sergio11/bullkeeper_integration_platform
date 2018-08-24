package sanchez.sanchez.sergio.masoc.domain.service;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.masoc.persistence.entity.IterationEntity;
import sanchez.sanchez.sergio.masoc.web.dto.response.CommentsBySonDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.IterationDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.IterationWithTasksDTO;
import java.util.List;

/**
 * Iteration Service Interface
 * @author sergio
 */
public interface IIterationService {
	
	/**
	 * Save
	 * @param iterationResult
	 * @return
	 */
	IterationWithTasksDTO save(final IterationEntity iterationResult);
	
	/**
	 * Get Total Iterations
	 * @return
	 */
    Long getTotalIterations();
    
    /**
     * Find Paginated
     * @param page
     * @param size
     * @return
     */
    Page<IterationDTO> findPaginated(final Integer page, final Integer size);
    
    /**
     * Find Paginated
     * @param pageable
     * @return
     */
    Page<IterationDTO> findPaginated(Pageable pageable);
    
    /**
     * All Iterations
     * @return
     */
    List<IterationDTO> allIterations();
    
    /**
     * Get Last Probing
     * @return
     */
    Date getLastProbing();
    
    /**
     * Get Comments By Son For Last Iteration
     * @return
     */
    List<CommentsBySonDTO> getCommentsBySonForLastIteration();
    
    /**
     * Get Avg Duration
     * @return
     */
    Double getAvgDuration();
}
