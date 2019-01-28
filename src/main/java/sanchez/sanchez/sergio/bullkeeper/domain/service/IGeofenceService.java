package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceAlertDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceDTO;

/**
 * Geofence Service
 * @author sergiosanchezsanchez
 *
 */
public interface IGeofenceService {
	
	
	/**
	 * Save Geofence
	 * @param saveGeofenceDTO
	 * @return
	 */
	GeofenceDTO save(final SaveGeofenceDTO saveGeofenceDTO);
	
	/**
	 * All By Kid
	 * @param kid
	 * @return
	 */
	Iterable<GeofenceDTO> allByKid(final ObjectId kid);
	
	/**
	 * Delete All By Kid
	 * @param kid
	 */
	void deleteAllByKid(final ObjectId kid);
	
	
	/**
	 * Delete By Kid And Ids
	 * @param kid
	 * @param ids
	 */
	void deleteByKidAndIds(final ObjectId kid, final List<ObjectId> ids);
	
	/**
	 * Delete By Id
	 * @param kid
	 * @param id
	 */
	void deleteById(final ObjectId kid, final ObjectId id);
	
	/**
	 * Find By Id
	 * @param kid
	 * @param id
	 * @return
	 */
	GeofenceDTO findById(final ObjectId kid, final ObjectId id);
	
	/**
	 * Find Alerts
	 * @param kid
	 * @param geofence
	 * @return
	 */
	Iterable<GeofenceAlertDTO> findAlerts(final ObjectId kid, final ObjectId geofence);
	
	/**
	 * Delete Alerts
	 * @param kid
	 * @param geofence
	 */
	void deleteAlerts(final ObjectId kid, final ObjectId geofence);
	
	
	/**
	 * Save Alert
	 * @param kid
	 * @param geofence
	 * @param type
	 * @param title
	 * @param description
	 * @return
	 */
	GeofenceAlertDTO saveAlert(final String kid, final String geofence, final String type,
			final String title, final String description);
	

}
