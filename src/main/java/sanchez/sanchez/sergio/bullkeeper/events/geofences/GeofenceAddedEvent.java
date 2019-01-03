package sanchez.sanchez.sergio.bullkeeper.events.geofences;

import org.springframework.context.ApplicationEvent;

/**
 * Geofence Added Event
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceAddedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identity
	 */
	private String identity;
	
	/**
	 * Name
	 */
	private String name;
	
	/**
	 * Latitude
	 */
	private double lat;
	
	/**
	 * Longitude
	 */
	private double log;
	
	/**
	 * Radius
	 */
	private float radius;
	
	/**
	 * Type
	 */
	private String type;
	
	/**
	 * Kid
	 */
	private String kid;

	/**
	 * 
	 * @param source
	 * @param identity
	 * @param name
	 * @param lat
	 * @param log
	 * @param radius
	 * @param type
	 * @param kid
	 */
	public GeofenceAddedEvent(Object source, String identity, String name, double lat, double log, float radius,
			String type, String kid) {
		super(source);
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
		return "GeofenceAddedEvent [identity=" + identity + ", name=" + name + ", lat=" + lat + ", log=" + log
				+ ", radius=" + radius + ", type=" + type + ", kid=" + kid + "]";
	}
	
	
	
    
}
