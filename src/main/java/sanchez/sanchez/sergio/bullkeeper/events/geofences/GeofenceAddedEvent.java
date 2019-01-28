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
	 * Address
	 */
	private String address;
	
	/**
	 * Type
	 */
	private String type;
	
	/**
	 * Kid
	 */
	private String kid;
	
	/**
	 * Create At
	 */
	private String createAt;
	
	/**
	 * Update At
	 */
	private String updateAt;
	
	/**
	 * Is Enabled
	 */
	private Boolean isEnabled;

	
	/**
	 * 
	 * @param source
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
	public GeofenceAddedEvent(Object source, String identity, String name, double lat, double log, float radius,
			String address, String type, String kid, String createAt, String updateAt, Boolean isEnabled) {
		super(source);
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
		return "GeofenceAddedEvent [identity=" + identity + ", name=" + name + ", lat=" + lat + ", log=" + log
				+ ", radius=" + radius + ", address=" + address + ", type=" + type + ", kid=" + kid + ", createAt="
				+ createAt + ", updateAt=" + updateAt + ", isEnabled=" + isEnabled + "]";
	}
    
}
