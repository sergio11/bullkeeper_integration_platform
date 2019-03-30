package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author ssanchez
 *
 */
public final class DevicePhotoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@JsonProperty("identity")
	private String identity;

	public DevicePhotoDTO() {}
	
	/**
	 * 
	 * @param identity
	 */
	public DevicePhotoDTO(final String identity) {
		super();
		this.identity = identity;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return "DevicePhotoDTO [identity=" + identity + "]";
	}
}
