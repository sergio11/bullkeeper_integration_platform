package sanchez.sanchez.sergio.masoc.domain.service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sanchez.sanchez.sergio.masoc.persistence.entity.AlertCategoryEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.AlertLevelEnum;
import sanchez.sanchez.sergio.masoc.persistence.entity.ParentEntity;
import sanchez.sanchez.sergio.masoc.persistence.entity.SonEntity;
import sanchez.sanchez.sergio.masoc.web.dto.request.AddAlertDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.AlertDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.AlertsPageDTO;
import sanchez.sanchez.sergio.masoc.web.dto.response.AlertsStatisticsDTO;

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
     * Find by parent paginated
     * @param id
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertDTO> findByParentPaginated(ObjectId id, Boolean delivered, Pageable pageable);

    /**
     * Find By Parent Paginated
     * @param id
     * @param type
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertDTO> findByParentPaginated(ObjectId id, AlertLevelEnum type, Boolean delivered, Pageable pageable);

    /**
     * Find Paginated
     * @param delivered
     * @param pageable
     * @return
     */
    Page<AlertDTO> findPaginated(Boolean delivered, Pageable pageable);

    /**
     * Save
     * @param alert
     * @return
     */
    AlertDTO save(AddAlertDTO alert);
    
    /**
     * Save
     * @param level
     * @param title
     * @param payload
     * @param sonId
     * @return
     */
    AlertDTO save(AlertLevelEnum level, String title, String payload, ObjectId sonId);
    
    /**
     * Save
     * @param level
     * @param title
     * @param payload
     * @param sonId
     * @param category
     * @return
     */
    AlertDTO save(AlertLevelEnum level, String title, String payload, ObjectId sonId, AlertCategoryEnum category);
    
    /**
     * Get Total Alerts
     * @return
     */
    Long getTotalAlerts();

    /**
     * Find By Parent
     * @param id
     * @param delivered
     * @return
     */
    Iterable<AlertDTO> findByParent(ObjectId id, Boolean delivered);
    
    /**
     * Find by Parent
     * @param id
     * @return
     */
    Iterable<AlertDTO> findByParent(ObjectId id);
    
    /**
     * Find Parent Alerts
     * @param id
     * @param count
     * @param from
     * @param levels
     * @return
     */
    Iterable<AlertDTO> findParentAlerts(ObjectId id, Integer count, Date from, AlertLevelEnum[] levels);

    /**
     * Get Last Alerts
     * @param parent
     * @param lastAccessToAlerts
     * @param count
     * @param levels
     * @return
     */
    AlertsPageDTO getLastAlerts(ObjectId parent, Date lastAccessToAlerts, Integer count, String[] levels);
    
    /**
     * Get Last Alerts
     * @param parent
     * @param count
     * @param levels
     * @return
     */
    AlertsPageDTO getLastAlerts(ObjectId parent, Integer count, String[] levels);

    /**
     * Delete Alerts Of Parent
     * @param parent
     * @return
     */
    Long deleteAlertsOfParent(ObjectId parent);

    /**
     * Find By Son
     * @param son
     * @return
     */
    Iterable<AlertDTO> findBySon(ObjectId son);
    
    /**
     * Find Son Alerts
     * @param id
     * @param count
     * @param from
     * @param levels
     * @return
     */
    Iterable<AlertDTO> findSonAlerts(ObjectId id,  Integer count, Date from, AlertLevelEnum[] levels);

    /**
     * Clear Child Alerts
     * @param son
     * @return
     */
    Long clearChildAlerts(ObjectId son);
    
    /**
     * Delete By Id
     * @param id
     */
    void deleteById(ObjectId id);
    
    /**
     * Create Invalid Access Token Alert
     * @param payload
     * @param parent
     * @param son
     */
    void createInvalidAccessTokenAlert(String payload, ParentEntity parent, SonEntity son);
    
    /**
     * Get Alerts Statistics
     * @param parentId
     * @param sonIds
     * @param from
     * @return
     */
    AlertsStatisticsDTO getAlertsStatistics(ObjectId parentId, List<ObjectId> sonIds, Date from);
    
    /**
     * Get Total Alerts by son
     * @param SonId
     * @return
     */
    Map<AlertLevelEnum, Long> getTotalAlertsBySon(ObjectId SonId);
}
