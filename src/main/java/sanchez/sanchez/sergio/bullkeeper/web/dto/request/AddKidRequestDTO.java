package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidRequestType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;

/**
 * Add Kid Request DTO
 * @author sergiosanchezsanchez
 *
 */
public final class AddKidRequestDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Type
	 */
	@JsonProperty("type")
	@KidRequestType(message = "{kid.request.type.not.valid}")
	private String type;
	
	/**
	 * Location
	 */
	@JsonProperty("location")
	private SaveLocationDTO location;
    
    /**
     * Kid
     */
	@JsonProperty("kid")
	@KidShouldExists(message = "{kid.not.exists}")
    private String kid;
    
    /**
     * Terminal
     */
	@JsonProperty("terminal")
	@TerminalShouldExists(message = "{terminal.not.exists}")
    private String terminal;
	
	
	
	public AddKidRequestDTO() {}


	/**
	 * 
	 * @param type
	 * @param location
	 * @param kid
	 * @param terminal
	 */
	public AddKidRequestDTO(String type, SaveLocationDTO location,
			String kid, String terminal) {
		super();
		this.type = type;
		this.location = location;
		this.kid = kid;
		this.terminal = terminal;
	}


	public String getType() {
		return type;
	}


	public SaveLocationDTO getLocation() {
		return location;
	}


	public String getKid() {
		return kid;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setType(String type) {
		this.type = type;
	}


	public void setLocation(SaveLocationDTO location) {
		this.location = location;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}


	@Override
	public String toString() {
		return "AddKidRequestDTO [type=" + type + ", location=" + location + ", kid=" + kid + ", terminal=" + terminal
				+ "]";
	}

	
	
}
