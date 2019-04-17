package sanchez.sanchez.sergio.bullkeeper.sse.models.location;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Geofence Status Changed SSE
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceStatusChangedSSE 
		extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public static final String EVENT_TYPE = "GEOFENCE_STATUS_CHANGED_EVENT";
	
	/**
	 * Geofence
	 */
	@JsonProperty("geofence")
	private String geofence;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Is ENabled
	 */
	@JsonProperty("is_enabled")
	private Boolean isEnabled;
	
	/**
	 * 
	 */
	public GeofenceStatusChangedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param geofence
	 * @param kid
	 * @param isEnabled
	 */
	public GeofenceStatusChangedSSE(final String subscriberId, final String geofence, 
			final String kid, final Boolean isEnabled) {
		super();
		this.geofence = geofence;
		this.kid = kid;
		this.isEnabled = isEnabled;
	}

	public String getGeofence() {
		return geofence;
	}

	public void setGeofence(String geofence) {
		this.geofence = geofence;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "GeofenceStatusChangedSSE [geofence=" + geofence + ", kid=" + kid + ", isEnabled=" + isEnabled + "]";
	}

	

}
