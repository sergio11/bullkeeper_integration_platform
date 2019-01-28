package sanchez.sanchez.sergio.bullkeeper.sse.models.location;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * All Geofence Deleted SSE
 * @author sergiosanchezsanchez
 *
 */
public final class AllGeofenceDeletedSSE 
		extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public static final String EVENT_TYPE = "ALL_GEOFENCE_DELETED_EVENT";

	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * 
	 */
	public AllGeofenceDeletedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * @param kid
	 */
	public AllGeofenceDeletedSSE(String subscriberId, String kid) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "AllGeofenceDeletedSSE [kid=" + kid + "]";
	}
}
