package sanchez.sanchez.sergio.service;

import org.springframework.data.domain.Page;
import sanchez.sanchez.sergio.dto.IterationDTO;
import sanchez.sanchez.sergio.persistence.entity.IterationEntity;

/**
 *
 * @author sergio
 */
public interface IIterationService {
    void save(IterationEntity iterationResult);
    Long getTotalIterations();
    Page<IterationDTO> findPaginated(Integer page, Integer size);
}
