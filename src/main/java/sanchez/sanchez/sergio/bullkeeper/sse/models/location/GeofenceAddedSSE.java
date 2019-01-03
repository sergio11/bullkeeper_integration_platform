package sanchez.sanchez.sergio.bullkeeper.sse.models.location;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Geofence Added SSE
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceAddedSSE 
		extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public static final String EVENT_TYPE = "GEOFENCE_ADDED_EVENT";
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Name
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * Latitude
	 */
	@JsonProperty("lat")
	private double lat;
	
	/**
	 * Longitude
	 */
	@JsonProperty("log")
	private double log;
	
	/**
	 * Radius
	 */
	@JsonProperty("radius")
	private float radius;
	
	/**
	 * Type
	 */
	@JsonProperty("type")
	private String type;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * 
	 */
	public GeofenceAddedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param identity
	 * @param name
	 * @param lat
	 * @param log
	 * @param radius
	 * @param type
	 * @param kid
	 */
	public GeofenceAddedSSE(String subscriberId, String identity, String name, double lat, double log,
			float radius, String type, String kid) {
		super(EVENT_TYPE, subscriberId);
		this.identity = identity;
		this.name = name;
		this.lat = lat;
		this.log = log;
		this.radius = radius;
		this.type = type;
		this.kid = kid;
	}

	public String getIdentity() {
		return identity;
	}

	public String getName() {
		return name;
	}

	public double getLat() {
		return lat;
	}

	public double getLog() {
		return log;
	}

	public float getRadius() {
		return radius;
	}

	public String getType() {
		return type;
	}

	public String getKid() {
		return kid;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLog(double log) {
		this.log = log;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "GeofenceAddedSSE [identity=" + identity + ", name=" + name + ", lat=" + lat + ", log=" + log
				+ ", radius=" + radius + ", type=" + type + ", kid=" + kid + "]";
	}
	
	

}
