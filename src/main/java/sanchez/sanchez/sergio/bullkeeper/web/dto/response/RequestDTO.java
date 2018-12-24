package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request DTO
 * @author sergiosanchezsanchez
 *
 */
public final class RequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	 * Request DTO
	 */
	public RequestDTO() {}
	

	/**
	 * Request DTO
	 * @param type
	 * @param requestAt
	 * @param expiredAt
	 */
	public RequestDTO(final String type, final String requestAt, final String expiredAt,
			final LocationDTO location) {
		super();
		this.type = type;
		this.requestAt = requestAt;
		this.expiredAt = expiredAt;
		this.location = location;
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

	public void setType(String type) {
		this.type = type;
	}

	public void setRequestAt(String requestAt) {
		this.requestAt = requestAt;
	}

	public void setExpiredAt(String expiredAt) {
		this.expiredAt = expiredAt;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}


	@Override
	public String toString() {
		return "RequestDTO [type=" + type + ", requestAt=" + requestAt + ", expiredAt=" + expiredAt + ", location="
				+ location + "]";
	}

	
}
