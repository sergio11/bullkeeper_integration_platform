package es.bisite.usal.bulltect.persistence.repository;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

import es.bisite.usal.bulltect.persistence.entity.AlertEntity;
import es.bisite.usal.bulltect.persistence.entity.AlertLevelEnum;
import es.bisite.usal.bulltect.web.dto.response.AlertDTO;
import es.bisite.usal.bulltect.web.dto.response.AlertsStatisticsDTO;

/**
 * @author sergio
 */
public interface AlertRepositoryCustom {
	void setAsDelivered(List<ObjectId> alertIds);
	void setAsDelivered(ObjectId alertId);
	List<AlertsStatisticsDTO> getAlertsBySon(List<String> sonIds);
	Iterable<AlertEntity> findParentAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels);
	Iterable<AlertEntity> findSonAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels);
}
