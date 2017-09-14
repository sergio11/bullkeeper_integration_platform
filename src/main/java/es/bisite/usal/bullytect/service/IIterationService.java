package es.bisite.usal.bullytect.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bullytect.dto.response.CommentsBySonDTO;
import es.bisite.usal.bullytect.dto.response.IterationDTO;
import es.bisite.usal.bullytect.persistence.entity.IterationEntity;

import java.util.List;

/**
 *
 * @author sergio
 */
public interface IIterationService {
    void save(IterationEntity iterationResult);
    Long getTotalIterations();
    Page<IterationDTO> findPaginated(Integer page, Integer size);
    Page<IterationDTO> findPaginated(Pageable pageable);
    List<IterationDTO> allIterations();
    Date getLastProbing();
    List<CommentsBySonDTO> getCommentsBySonForLastIteration();
}
