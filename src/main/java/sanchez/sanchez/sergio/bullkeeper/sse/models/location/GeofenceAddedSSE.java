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
	 * Address
	 */
	@JsonProperty("address")
	private String address;
	
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
	 * Create At
	 */
	@JsonProperty("create_at")
	private String createAt;
	
	/**
	 * Update At
	 */
	@JsonProperty("update_at")
	private String updateAt;
	
	/**
	 * Is ENabled
	 */
	@JsonProperty("is_enabled")
	private Boolean isEnabled;
	
	/**
	 * 
	 */
	public GeofenceAddedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * Geofence Added SSE
	 * @param subscriberId
	 * @param identity
	 * @param name
	 * @param lat
	 * @param log
	 * @param radius
	 * @param address
	 * @param type
	 * @param kid
	 * @param createAt
	 * @param updateAt
	 * @param isEnabled
	 */
	public GeofenceAddedSSE(final String subscriberId, final String identity, 
			final String name, double lat, double log, float radius, final String address,
			final String type, final String kid, final String createAt, 
			final String updateAt, final Boolean isEnabled) {
		super();
		this.identity = identity;
		this.name = name;
		this.lat = lat;
		this.log = log;
		this.radius = radius;
		this.address = address;
		this.type = type;
		this.kid = kid;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.isEnabled = isEnabled;
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

	public String getAddress() {
		return address;
	}

	public String getType() {
		return type;
	}

	public String getKid() {
		return kid;
	}

	public String getCreateAt() {
		return createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
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

	public void setAddress(String address) {
		this.address = address;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "GeofenceAddedSSE [identity=" + identity + ", name=" + name + ", lat=" + lat + ", log=" + log
				+ ", radius=" + radius + ", address=" + address + ", type=" + type + ", kid=" + kid + ", createAt="
				+ createAt + ", updateAt=" + updateAt + ", isEnabled=" + isEnabled + "]";
	}

	
	

}
