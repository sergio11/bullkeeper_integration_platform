package es.bisite.usal.bulltect.domain.service;


import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.web.dto.request.AddAlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;

/**
 * @author sergio
 */
public interface IAlertService {
	Page<AlertDTO> findByParentPaginated(ObjectId id, Boolean delivered, Pageable pageable);
	Page<AlertDTO> findByParentPaginated(ObjectId id, AlertLevelEnum type, Boolean delivered, Pageable pageable);
	Page<AlertDTO> findPaginated(Boolean delivered, Pageable pageable);
	AlertDTO save(AddAlertDTO alert);
    Long getTotalAlerts();
    Iterable<AlertDTO> findByParent(ObjectId id, Boolean delivered);
}
