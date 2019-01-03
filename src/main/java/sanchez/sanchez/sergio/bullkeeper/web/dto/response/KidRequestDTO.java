package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Kid Request DTO
 * @author sergiosanchezsanchez
 *
 */
public final class KidRequestDTO implements Serializable {

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
	 * Type
	 */
	@JsonProperty("type")
	private String type;
	
	/**
	 * Request At
	 */
	@JsonProperty("request_at")
	private String requestAt;
	
	/**
	 * Expired At
	 */
	@JsonProperty("expired_at")
	private String expiredAt;
	
	/**
     * Location
     */
	@JsonProperty("location")
    private LocationDTO location;
    
    /**
     * Kid
     */
	@JsonProperty("kid")
    private KidDTO kid;
    
    /**
     * Terminal
     */
	@JsonProperty("terminal")
    private TerminalDTO terminal;
	
	/**
	 * Since
	 */
	@JsonProperty("since")
	private String since;
	
	
	public KidRequestDTO() {}

	/**
	 * 
	 * @param identity
	 * @param type
	 * @param requestAt
	 * @param expiredAt
	 * @param location
	 * @param kid
	 * @param terminal
	 * @param since
	 */
	public KidRequestDTO(
			final String identity,
			final String type, 
			final String requestAt, 
			final String expiredAt, 
			final LocationDTO location, 
			final KidDTO kid,
			final TerminalDTO terminal,
			final String since) {
		super();
		this.identity = identity;
		this.type = type;
		this.requestAt = requestAt;
		this.expiredAt = expiredAt;
		this.location = location;
		this.kid = kid;
		this.terminal = terminal;
		this.since = since;
	}
	
	

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getType() {
		return type;
	}

	public String getRequestAt() {
		return requestAt;
	}

	public String getExpiredAt() {
		return expiredAt;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public KidDTO getKid() {
		return kid;
	}

	public TerminalDTO getTerminal() {
		return terminal;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setRequestAt(String requestAt) {
		this.requestAt = requestAt;
	}

	public void setExpiredAt(String expiredAt) {
		this.expiredAt = expiredAt;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public void setKid(KidDTO kid) {
		this.kid = kid;
	}

	public void setTerminal(TerminalDTO terminal) {
		this.terminal = terminal;
	}

	public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	@Override
	public String toString() {
		return "KidRequestDTO [type=" + type + ", requestAt=" + requestAt + ", expiredAt=" + expiredAt + ", location="
				+ location + ", kid=" + kid + ", terminal=" + terminal + ", since=" + since + "]";
	}

	
}
