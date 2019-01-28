package sanchez.sanchez.sergio.bullkeeper.sse.models.location;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Geofence Deleted SSE
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceDeletedSSE 
		extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public static final String EVENT_TYPE = "GEOFENCE_DELETED_EVENT";
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * 
	 */
	public GeofenceDeletedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param identity
	 * @param kid
	 */
	public GeofenceDeletedSSE(String subscriberId, String identity, String name, double lat, double log,
			float radius, String type, String kid) {
		super(EVENT_TYPE, subscriberId);
		this.identity = identity;
		this.kid = kid;
	}

	public String getIdentity() {
		return identity;
	}

	public String getKid() {
		return kid;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "GeofenceDeletedSSE [identity=" + identity + ", kid=" + kid + "]";
	}
}
