package es.bisite.usal.bulltect.domain.service;


import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import es.bisite.usal.bulltect.persistence.entity.ParentEntity;
import es.bisite.usal.bulltect.persistence.entity.SonEntity;
import es.bisite.usal.bulltect.web.dto.request.AddAlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsStatisticsDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsPageDTO;

/**
 * @author sergio
 */
public interface IAlertService {
    
    AlertDTO findById(ObjectId id);

    Page<AlertDTO> findByParentPaginated(ObjectId id, Boolean delivered, Pageable pageable);

    Page<AlertDTO> findByParentPaginated(ObjectId id, AlertLevelEnum type, Boolean delivered, Pageable pageable);

    Page<AlertDTO> findPaginated(Boolean delivered, Pageable pageable);

    AlertDTO save(AddAlertDTO alert);
    
    AlertDTO save(AlertLevelEnum level, String title, String payload, ObjectId sonId);
    
    Long getTotalAlerts();

    Iterable<AlertDTO> findByParent(ObjectId id, Boolean delivered);
    
    Iterable<AlertDTO> findByParent(ObjectId id);

    AlertsPageDTO getLastAlerts(ObjectId parent, Date lastAccessToAlerts, Integer count, String[] levels);

    Long deleteAlertsOfParent(ObjectId parent);

    Iterable<AlertDTO> findBySon(ObjectId son);

    Long clearChildAlerts(ObjectId son);
    
    void deleteById(ObjectId id);
    
    void createInvalidAccessTokenAlert(String payload, ParentEntity parent, SonEntity son);
    
    AlertsStatisticsDTO getAlertsStatistics(List<ObjectId> sonIds, Date from);
}
