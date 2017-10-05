package es.bisite.usal.bulltect.domain.service;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bulltect.persistence.entity.IterationEntity;
import es.bisite.usal.bulltect.web.dto.response.CommentsBySonDTO;
import es.bisite.usal.bulltect.web.dto.response.IterationDTO;
import es.bisite.usal.bulltect.web.dto.response.IterationWithTasksDTO;

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
    List<IterationDTO> getLastIterationsByParent(ObjectId id, Integer count);
    IterationWithTasksDTO getLastIterationByParent(ObjectId id);
}
