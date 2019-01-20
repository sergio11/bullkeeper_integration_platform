package sanchez.sanchez.sergio.bullkeeper.sse.models.terminal;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Terminal Bed Time Status Changed
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalSettingsStatusChangedSSE
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "TERMINAL_SETTINGS_STATUS_CHANGED";
	
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
	 * Enabled
	 */
	@JsonProperty("enabled")
	private Boolean enabled;
	
	/**
	 * 
	 */
	public TerminalSettingsStatusChangedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param enabled
	 */
	public TerminalSettingsStatusChangedSSE(String subscriberId, 
			String kid, String terminal, Boolean enabled) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.enabled = enabled;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "TerminalBedTimeStatusChangedSSE [kid=" + kid + ", terminal=" + terminal + ", enabled=" + enabled + "]";
	}
}
