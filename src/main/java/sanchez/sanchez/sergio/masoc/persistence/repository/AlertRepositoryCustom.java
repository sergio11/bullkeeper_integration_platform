package sanchez.sanchez.sergio.masoc.persistence.repository;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.masoc.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.masoc.web.dto.response.AlertsStatisticsDTO;

/**
 * @author sergio
 */
public interface AlertRepositoryCustom {
	void setAsDelivered(List<ObjectId> alertIds);
	void setAsDelivered(ObjectId alertId);
	Iterable<AlertEntity> findParentAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels);
	Iterable<AlertEntity> findSonAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels);
}
