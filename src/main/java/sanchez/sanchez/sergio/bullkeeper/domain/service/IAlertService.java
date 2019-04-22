package sanchez.sanchez.sergio.bullkeeper.domain.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.KidEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsPageDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.AlertsStatisticsDTO;

/**
 * Alerts Service Interface
 * @author sergiosanchezsanchez
 *
 */
public interface IAlertService {
    
	/**
	 * Find by id
	 * @param id
	 * @return
	 */
    AlertDTO findById(ObjectId id);

    /**
     * Find by guardian paginated
     * @param id
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertDTO> findByGuardianPaginated(final ObjectId id, final Boolean delivered, final Pageable pageable);

    /**
     * Find By Guardian Paginated
     * @param id
     * @param type
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertDTO> findByGuardianPaginated(final ObjectId id, final AlertLevelEnum type, final Boolean delivered, final Pageable pageable);

    /**
     * Find Paginated
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertDTO> findPaginated(final Boolean delivered, final Pageable pageable);

    /**
     * Save
     * @param alert
     * @return
     */
    void save(final AddAlertDTO alert);
    
    /**
     * Save
     * @param level
     * @param title
     * @param payload
     * @param kid
     * @return
     */
    void save(final AlertLevelEnum level, final String title, final String payload, 
    		final ObjectId kid);
    
    /**
     * Save
     * @param level
     * @param title
     * @param payload
     * @param kid
     * @param category
     * @return
     */
    void save(final AlertLevelEnum level, final String title, final String payload, 
    		final ObjectId kid, final AlertCategoryEnum category);
    
    /**
     * Save
     * @param level
     * @param title
     * @param payload
     * @param kid
     * @param guardian
     * @param category
     * @return
     */
    void save(final AlertLevelEnum level, final String title, final String payload, 
    		final ObjectId kid, final ObjectId guardian, final AlertCategoryEnum category);
    
    /**
     * Get Total Alerts
     * @return
     */
    Long getTotalAlerts();

    /**
     * Find By GUardian
     * @param id
     * @param delivered
     * @return
     */
    Iterable<AlertDTO> findByGuardian(final ObjectId id, final Boolean delivered);
    
    /**
     * Find by Guardian
     * @param id
     * @return
     */
    Iterable<AlertDTO> findByGuardian(final ObjectId id);
    
    /**
     * Find Guardian Alerts
     * @param id
     * @param count
     * @param from
     * @param levels
     * @return
     */
    Iterable<AlertDTO> findGuardianAlerts(final ObjectId id, final Integer count,
    		final Date from, final AlertLevelEnum[] levels);
    
    /**
     * Find Guardian Warning Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findGuardianWarningAlerts(final ObjectId id, final Integer count, Date from);
    
    /**
     * Find Guardian Danger Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findGuardianDangerAlerts(final ObjectId id, final Integer count, 
    		final Date from);
    
    /**
     * Find GUardian Information Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findGuardianInformationAlerts(final ObjectId id, final Integer count,
    		final Date from);
    
    /**
     * Find Guardian Success Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findGuardianSuccessAlerts(final ObjectId id, final Integer count, 
    		final Date from);

    /**
     * Get Last Alerts
     * @param id
     * @param lastAccessToAlerts
     * @param count
     * @param levels
     * @return
     */
    AlertsPageDTO getLastAlerts(final ObjectId id, final Date lastAccessToAlerts, 
    		final Integer count, final String[] levels);
    
    /**
     * Get Last Alerts
     * @param id
     * @param count
     * @param levels
     * @return
     */
    AlertsPageDTO getLastAlerts(final ObjectId id, final Integer count, 
    		final String[] levels);

    /**
     * Delete Alerts Of Parent
     * @param id
     * @return
     */
    Long deleteAlertsOfGuardian(final ObjectId id);

    /**
     * Find By Kid
     * @param kid
     * @param guardian
     * @return
     */
    Iterable<AlertDTO> findByKidAndGuardian(final ObjectId kid, final ObjectId guardian);
    
    /**
     * Find Kid Alerts
     * @param id
     * @param count
     * @param from
     * @param levels
     * @return
     */
    Iterable<AlertDTO> findKidAlerts(final ObjectId id, final Integer count, final Date from, 
    		final AlertLevelEnum[] levels);
    
    /**
     * Find Kid Warning Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findKidWarningAlerts(final ObjectId id, final Integer count, final Date from);
    
    /**
     * Find Information SOn Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findInformationKidAlerts(final ObjectId id, final Integer count, 
    		final Date from);
    
    /**
     * Find Danger KId Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findDangerKidAlerts(final ObjectId id, final Integer count, final Date from);
    
    /**
     * Find Success Kid Alerts
     * @param id
     * @param count
     * @param from
     * @return
     */
    Iterable<AlertDTO> findSuccessKidAlerts(final ObjectId id, final Integer count, final  Date from);

    /**
     * Clear Child Alerts
     * @param id
     * @return
     */
    Long clearKidAlerts(final ObjectId id);
    
    /**
     * Clear Kid Alerts
     * @param id
     * @return
     */
    Long clearKidAlertsByLevel(final ObjectId id, final AlertLevelEnum level);
    
    /**
     * Delete By Id
     * @param id
     */
    void deleteById(ObjectId id);
    
    /**
     * Create Invalid Access Token Alert
     * @param payload
     * @param kid
     */
    void createInvalidAccessTokenAlert(String payload, KidEntity kid);
    
    /**
     * Get Alerts Statistics
     * @param guardian
     * @param kids
     * @param from
     * @return
     */
    AlertsStatisticsDTO getAlertsStatistics(ObjectId guardian, List<ObjectId> kids, Date from);
    
    /**
     * Get Total Alerts by kid
     * @param id
     * @return
     */
    Map<AlertLevelEnum, Long> getTotalAlertsByKidAndGuardianId(final ObjectId kid, final ObjectId guardian);
    
    
    /**
     * Delete Warning Alerts Of Guardian
     * @param id
     * @return
     */
    Long deleteWarningAlertsOfGuardian(final ObjectId id);
    
    /**
     * Delete Info Alerts Of Guardian
     * @param id
     * @return
     */
    Long deleteInfoAlertsOfGuardian(final ObjectId id);
    
    
    /**
     * Delete Danger Alerts Of Guardian
     * @param id
     * @return
     */
    Long deleteDangerAlertsOfGuardian(final ObjectId id);
    
    /**
     * Delete Success Alerts Of Parent
     * @param id
     * @return
     */
    Long deleteSuccessAlertsOfGuardian(final ObjectId id);
    
    
}
