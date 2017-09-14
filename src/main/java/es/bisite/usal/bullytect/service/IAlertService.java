package es.bisite.usal.bullytect.service;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.bisite.usal.bullytect.dto.request.AddAlertDTO;
import es.bisite.usal.bullytect.dto.response.AlertDTO;
import es.bisite.usal.bullytect.persistence.entity.AlertLevelEnum;

/**
 * @author sergio
 */
public interface IAlertService {
	Page<AlertDTO> findByParentPaginated(ObjectId id, Pageable pageable);
	Page<AlertDTO> findByParentPaginated(ObjectId id, AlertLevelEnum type, Pageable pageable);
	Page<AlertDTO> findPaginated(Pageable pageable);
	AlertDTO save(AddAlertDTO alert);
        Long getTotalAlerts();
}
