package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GeofenceShouldExistsIfPresent;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GeofenceTransitionType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringDeserializer;

/**
 * Save Geofence DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveGeofenceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@GeofenceShouldExistsIfPresent(message = "id.not.exists")
	@JsonProperty("identity")
	private String identity;
	
	/**
     * Name
     */
	@NotBlank(message = "{geofence.name.not.blank}")
    @Size(min = 5, max = 20, message = "{geofence.name.size}", groups = Extended.class)
	@JsonProperty("name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
    private String name;
	
	/**
	 * Address
	 */
	@JsonProperty("address")
	private String address;
	
	/**
	 * Latitude
	 */
	@JsonProperty("latitude")
	private double lat;
	
	/**
	 * Longitude
	 */
	@JsonProperty("longitude")
	private double log;

	/**
	 * Radius
	 */
	@JsonProperty("radius")
	private float radius;
	
	/**
	 * Type
	 */
	@GeofenceTransitionType(message = "geofence.transition.not.valid")
	@JsonProperty("transition_type")
	private String type;
	
	/**
	 * Kid
	 */
	@ValidObjectId(message = "{id.notvalid}")
	@KidShouldExists(message = "{kid.not.exists}", groups = Extended.class)
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Is Enabled
	 */
	@JsonProperty("is_enabled")
	private Boolean isEnabled;
	
	
	public SaveGeofenceDTO() {}

	/**
	 * 
	 * @param identity
	 * @param name
	 * @param address
	 * @param lat
	 * @param log
	 * @param radius
	 * @param type
	 * @param kid
	 * @param isEnabled
	 */
	public SaveGeofenceDTO(
			final String identity, 
			final String name,
			final String address,
			final double lat, 
			final double log, 
			final float radius, 
			final String type,
			final String kid,
			final Boolean isEnabled) {
		super();
		this.identity = identity;
		this.name = name;
		this.address = address;
		this.lat = lat;
		this.log = log;
		this.radius = radius;
		this.type = type;
		this.kid = kid;
		this.isEnabled = isEnabled;
	}

	public String getIdentity() {
		return identity;
	}

	public String getName() {
		return name;
	}
	
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "SaveGeofenceDTO [identity=" + identity + ", name=" + name + ", address=" + address + ", lat=" + lat
				+ ", log=" + log + ", radius=" + radius + ", type=" + type + ", kid=" + kid + ", isEnabled=" + isEnabled
				+ "]";
	}

	
	
}
