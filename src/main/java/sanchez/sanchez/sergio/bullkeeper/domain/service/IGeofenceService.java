package sanchez.sanchez.sergio.bullkeeper.domain.service;

import java.util.List;

import org.bson.types.ObjectId;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveGeofenceDTO;
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
	

}
