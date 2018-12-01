package sanchez.sanchez.sergio.bullkeeper.persistence.repository;

import java.util.Date;
import java.util.List;
import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsStatisticsDTO;

/**
 * @author sergio
 */
public interface AlertRepositoryCustom {
	
	/**
	 * 
	 * @param alertIds
	 */
	void setAsDelivered(List<ObjectId> alertIds);
	
	/**
	 * 
	 * @param alertId
	 */
	void setAsDelivered(ObjectId alertId);
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param from
	 * @param levels
	 * @return
	 */
	Iterable<AlertEntity> findGuardianAlerts(final ObjectId id, final Integer count, 
			final Date from, final AlertLevelEnum[] levels);
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param from
	 * @return
	 */
	Iterable<AlertEntity> findGuardianWarningAlerts(final ObjectId id, final Integer count, 
			final Date from);
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param from
	 * @return
	 */
	Iterable<AlertEntity> findGuardianInformationAlerts(final ObjectId id, final Integer count, 
			final Date from);
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param from
	 * @return
	 */
	Iterable<AlertEntity> findGuardianSuccessAlerts(final ObjectId id, final Integer count, 
			final Date from);
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param from
	 * @return
	 */
	Iterable<AlertEntity> findGuardianDangerAlerts(final ObjectId id, final Integer count, 
			final Date from);
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param from
	 * @param levels
	 * @return
	 */
	Iterable<AlertEntity> findKidAlerts(final ObjectId id, final Integer count, final Date from,
			final AlertLevelEnum[] levels);
	
	/**
	 * 
	 * @param id
	 * @param count
	 * @param from
	 * @param level
	 * @return
	 */
	Iterable<AlertEntity> findKidAlerts(final ObjectId id, final Integer count, 
			final Date from, final AlertLevelEnum level);
}
