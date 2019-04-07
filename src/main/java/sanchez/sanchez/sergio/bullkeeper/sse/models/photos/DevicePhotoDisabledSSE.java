package sanchez.sanchez.sergio.bullkeeper.sse.models.photos;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * 
 * @author ssanchez
 *
 */
public final class DevicePhotoDisabledSSE extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "DEVICE_PHOTO_DISABLED_EVENT";
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;
	
	/**
	 * Photo
	 */
	@JsonProperty("photo")
	private String photo;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	private String localId;
	
	/**
	 * Path
	 */
	@JsonProperty("path")
	private String path;
	
	/**
	 * 
	 */
	public DevicePhotoDisabledSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param photo
	 * @param localId
	 * @param path
	 */
	public DevicePhotoDisabledSSE(String subscriberId, String kid, String terminal, String photo,
			String localId, String path) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.photo = photo;
		this.localId = localId;
		this.path = path;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "DevicePhotoDisabledSSE [kid=" + kid + ", terminal=" + terminal + ", photo=" + photo + ", localId="
				+ localId + ", path=" + path + "]";
	}
	
}
