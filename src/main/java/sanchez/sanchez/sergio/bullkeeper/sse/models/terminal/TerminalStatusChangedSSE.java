package sanchez.sanchez.sergio.bullkeeper.sse.models.terminal;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Terminal Status Changed
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalStatusChangedSSE
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "TERMINAL_STATUS_CHANGED";
	
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
	 * Status
	 */
	@JsonProperty("status")
	private String status;
	
	/**
	 * 
	 */
	public TerminalStatusChangedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param status
	 */
	public TerminalStatusChangedSSE(String subscriberId, 
			String kid, String terminal, String status) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "TerminalStatusChangedSSE [kid=" + kid + ", terminal=" + terminal + ", status=" + status + "]";
	}

	
}
