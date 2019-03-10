package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalStatusType;

/**
 * Terminal Status DTO
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalStatusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Screen Status
	 */
	@JsonProperty("status")
	@TerminalStatusType(message="{terminal.status.not.valid}")
	private String status;
	
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	@TerminalShouldExists(message="{terminal.not.exists}")
	private String terminal;

	/**
	 * Kid
	 */
	@JsonProperty("kid")
	@KidShouldExists(message="{kid.not.exists}")
	private String kid;

	
	public TerminalStatusDTO() {}

	/**
	 * 
	 * @param status
	 * @param terminal
	 * @param kid
	 */
	public TerminalStatusDTO(final String status, final String terminal,
			final String kid) {
		super();
		this.status = status;
		this.terminal = terminal;
		this.kid = kid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "TerminalStatusDTO [status=" + status + ", terminal=" + terminal + ", kid=" + kid + "]";
	}

}
