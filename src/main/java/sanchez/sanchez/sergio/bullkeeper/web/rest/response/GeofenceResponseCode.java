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
	NO_GEOFENCES_FOUND_EXCEPTION(1504L);

	private Long code;

    private GeofenceResponseCode(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }
}
