package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GeofenceShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GeofenceTransitionType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

/**
 * Save Geofence Alert
 * @author sergiosanchezsanchez
 *
 */
public final class SaveGeofenceAlertDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 241991735311913644L;

	/**
	 * Geofence
	 */
	@GeofenceShouldExists(message = "{geofence.not.exists}")
	@JsonProperty("geofence")
	private String geofence;
	
	/**
	 * Kid
	 */
	@ValidObjectId(message = "{id.not.valid}")
	@KidShouldExists(message = "{kid.should.be.exists}", groups = Extended.class)
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Type
	 */
	@GeofenceTransitionType(message = "{geofence.transition.not.valid}")
	@JsonProperty("type")
	private String type;

	/**
	 * 
	 */
	public SaveGeofenceAlertDTO() {}
	
	/**
	 * 
	 * @param geofence
	 * @param kid
	 * @param type
	 */
	public SaveGeofenceAlertDTO(String geofence, String kid, String type) {
		super();
		this.geofence = geofence;
		this.kid = kid;
		this.type = type;
	}

	public String getGeofence() {
		return geofence;
	}

	public String getKid() {
		return kid;
	}

	public String getType() {
		return type;
	}

	public void setGeofence(String geofence) {
		this.geofence = geofence;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SaveGeofenceAlertDTO [geofence=" + geofence + ", kid=" + kid + ", type=" + type + "]";
	}
}
