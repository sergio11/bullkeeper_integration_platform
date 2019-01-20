package sanchez.sanchez.sergio.bullkeeper.sse.models.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.KidDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.LocationDTO;

/**
 * Kid Request Created
 * @author sergiosanchezsanchez
 *
 */
public final class KidRequestCreatedSSE extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "KID_REQUEST_CREATED_EVENT";
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private KidDTO kid;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;
	
	/**
	 * type
	 */
	@JsonProperty("type")
	private String type;
	
	/**
	 * Location DTO
	 */
	@JsonProperty("location")
	private LocationDTO location;


	/**
	 * 
	 */
	public KidRequestCreatedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param identity
	 * @param kid
	 * @param terminal
	 * @param type
	 * @param location
	 */
	public KidRequestCreatedSSE(String subscriberId, String identity, KidDTO kid, String terminal,
			String type, LocationDTO location) {
		super(EVENT_TYPE, subscriberId);
		this.identity = identity;
		this.kid = kid;
		this.terminal = terminal;
		this.type = type;
		this.location = location;
	}

	public String getIdentity() {
		return identity;
	}

	public KidDTO getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getType() {
		return type;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setKid(KidDTO kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "KidRequestCreatedSSE [identity=" + identity + ", kid=" + kid + ", terminal=" + terminal + ", type="
				+ type + ", location=" + location + "]";
	}
}
