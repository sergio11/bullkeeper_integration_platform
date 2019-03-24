package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Save Terminal Heart Beat Configuration
 * @author ssanchez
 *
 */
public final class SaveTerminalHeartBeatConfigurationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
     * Alert Threshold In Minutes
     */
    @JsonProperty("alert_threshold_in_minutes")
    private int alertThresholdInMinutes;
    
    /**
     * Is Alert Mode Enabled
     */
    @JsonProperty("alert_mode_enabled")
    private boolean alertModeEnabled;

    
    public SaveTerminalHeartBeatConfigurationDTO() {}
    
    /**
     * 
     * @param kid
     * @param terminal
     * @param alertThresholdInMinutes
     * @param isAlertModeEnabled
     */
	public SaveTerminalHeartBeatConfigurationDTO(
			final String kid, final String terminal, int alertThresholdInMinutes,
			boolean alertModeEnabled) {
		super();
		this.kid = kid;
		this.terminal = terminal;
		this.alertThresholdInMinutes = alertThresholdInMinutes;
		this.alertModeEnabled = alertModeEnabled;
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

	public int getAlertThresholdInMinutes() {
		return alertThresholdInMinutes;
	}

	public void setAlertThresholdInMinutes(int alertThresholdInMinutes) {
		this.alertThresholdInMinutes = alertThresholdInMinutes;
	}

	public boolean isAlertModeEnabled() {
		return alertModeEnabled;
	}

	public void setAlertModeEnabled(boolean alertModeEnabled) {
		this.alertModeEnabled = alertModeEnabled;
	}

	@Override
	public String toString() {
		return "SaveTerminalHeartBeatConfigurationDTO [kid=" + kid + ", terminal=" + terminal
				+ ", alertThresholdInMinutes=" + alertThresholdInMinutes + ", alertModeEnabled=" + alertModeEnabled
				+ "]";
	}

	
}
