package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

/**
 * 
 * @author ssanchez
 *
 */
public final class AddDevicePhotoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid ID
	 */
	@ValidObjectId(message = "{id.not.valid}")
    @KidShouldExists(message = "{kid.should.be.exists}", groups = Extended.class)
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal Id
	 */
	@ValidObjectId(message = "{id.not.valid}")
    @TerminalShouldExists(message = "{terminal.not.exists}", groups = Extended.class)
	@JsonProperty("terminal")
	private String terminal;
	
	public AddDevicePhotoDTO() {}

	/**
	 * 
	 * @param kid
	 * @param terminal
	 */
	public AddDevicePhotoDTO(final String kid, final String terminal) {
		super();
		this.kid = kid;
		this.terminal = terminal;
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

	@Override
	public String toString() {
		return "AddDevicePhotoDTO [kid=" + kid + ", terminal=" + terminal + "]";
	}
	
}
