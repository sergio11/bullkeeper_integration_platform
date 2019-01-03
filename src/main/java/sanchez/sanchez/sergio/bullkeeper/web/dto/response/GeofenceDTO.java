package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Geofence DTO
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	public GeofenceDTO() {}

	/**
	 * 
	 * @param identity
	 * @param name
	 * @param lat
	 * @param log
	 * @param radius
	 * @param type
	 * @param kid
	 */
	public GeofenceDTO(
			final String identity, 
			final String name, 
			final double lat, 
			final double log, 
			final float radius, 
			final String type, 
			final String kid) {
		super();
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
		return "GeofenceDTO [identity=" + identity + ", name=" + name + ", lat=" + lat + ", log=" + log + ", radius="
				+ radius + ", type=" + type + ", kid=" + kid + "]";
	}

}
