package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * Geofence Response Code
 * @author sergiosanchezsanchez
 *
 */
public enum GeofenceResponseCode implements IResponseCodeTypes {
	
	GEOFENCE_SAVED_SUCCESSFULLY(1600L),
	ALL_GEOFENCES_FOR_KID(1601L),
	GEOFENCES_DELETED(1602L),
	ALL_GEOFENCES_DELETED(1603L),
	NO_GEOFENCES_FOUND_EXCEPTION(1604L),
	GEOFENCE_DETAIL(1605L),
	SINGLE_GEOFENCE_DELETED(1606L),
	GEOFENCE_ALERT_LIST(1607L),
	NO_GEOFENCE_ALERTS_FOUND(1608L),
	ALL_GEOFENCE_ALERTS_DELETED(1609L),
	GEOFENCE_ALERT_SAVED(1610L);

	private Long code;

    private GeofenceResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }
}
