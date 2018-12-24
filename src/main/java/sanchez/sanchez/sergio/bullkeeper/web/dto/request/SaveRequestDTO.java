package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.RequestType;

/**
 * Save Request DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Type
	 */
	@RequestType(message = "{request.type.not.valid}")
	@JsonProperty("type")
	private String type;
	
	/**
	 * Location DTO
	 */
	@JsonProperty("location")
	private SaveLocationDTO locationDTO;
	
	public SaveRequestDTO() {}
	
	/**
	 * 
	 * @param type
	 * @param locationDTO
	 */
	public SaveRequestDTO(final String type, final SaveLocationDTO locationDTO) {
		super();
		this.type = type;
		this.locationDTO = locationDTO;
	}

	public String getType() {
		return type;
	}

	public SaveLocationDTO getLocationDTO() {
		return locationDTO;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLocationDTO(SaveLocationDTO locationDTO) {
		this.locationDTO = locationDTO;
	}

	@Override
	public String toString() {
		return "SaveRequestDTO [type=" + type + ", locationDTO=" + locationDTO + "]";
	}
	
	
	
}
