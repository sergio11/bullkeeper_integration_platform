package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * Geofence Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum GeofenceResponseCode implements IResponseCodeTypes {
	
	GEOFENCE_SAVED_SUCCESSFULLY(1500L),
	ALL_GEOFENCES_FOR_KID(1501L),
	GEOFENCES_DELETED(1502L),
	ALL_GEOFENCES_DELETED(1503L),
	NO_GEOFENCES_FOUND_EXCEPTION(1504L),
	GEOFENCE_DETAIL(1505L),
	SINGLE_GEOFENCE_DELETED(1506L),
	GEOFENCE_ALERT_LIST(1507L),
	NO_GEOFENCE_ALERTS_FOUND(1508L),
	ALL_GEOFENCE_ALERTS_DELETED(1509L),
	GEOFENCE_ALERT_SAVED(1510L);

	private Long code;

    private GeofenceResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }
}
