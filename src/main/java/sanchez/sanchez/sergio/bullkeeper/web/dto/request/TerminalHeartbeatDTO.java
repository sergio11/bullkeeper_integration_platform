package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ScreenStatusType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;

/**
 * Terminal Heartbeat DTO
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalHeartbeatDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Screen Status
	 */
	@JsonProperty("screen_status")
	@ScreenStatusType(message="{screen.status.not.valid}")
	private String screenStatus;
	
	
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
	
	
	public TerminalHeartbeatDTO() {}

	/**
	 * 
	 * @param screenStatus
	 * @param terminal
	 * @param kid
	 */
	public TerminalHeartbeatDTO( String screenStatus, String terminal, String kid) {
		super();
		this.screenStatus = screenStatus;
		this.terminal = terminal;
		this.kid = kid;
	}

	public String getScreenStatus() {
		return screenStatus;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getKid() {
		return kid;
	}

	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "TerminalHeartbeatDTO [screenStatus=" + screenStatus + ", terminal=" + terminal + ", kid=" + kid + "]";
	}

}
