package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Terminal HeartBeat DTO
 * @author ssanchez
 *
 */
public final class TerminalHeartbeatDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Alert Threshold in minutes
	 */
	@JsonProperty("alert_threshold_in_minutes")
	private int alertThresholdInMinutes;
	
	/**
	 * Alert Mode Enabled
	 */
	@JsonProperty("alert_mode_enabled")
	protected boolean alertModeEnabled;

	/**
	 * Last Time Notified Since
	 */
	@JsonProperty("last_time_notified_since")
	protected String lastTimeNotifiedSince;
	
	/**
	 * Last Time Notified
	 */
	@JsonProperty("last_time_notified")
	private String lastTimeNotified;

	
	public TerminalHeartbeatDTO() {}
	
	/**
	 * 
	 * @param alertThresholdInMinutes
	 * @param alertModeEnabled
	 * @param lastTimeNotifiedSince
	 * @param lastTimeNotified
	 */
	public TerminalHeartbeatDTO(int alertThresholdInMinutes, boolean alertModeEnabled, 
			final String lastTimeNotifiedSince, final String lastTimeNotified) {
		super();
		this.alertThresholdInMinutes = alertThresholdInMinutes;
		this.alertModeEnabled = alertModeEnabled;
		this.lastTimeNotifiedSince = lastTimeNotifiedSince;
		this.lastTimeNotified = lastTimeNotified;
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

	public String getLastTimeNotifiedSince() {
		return lastTimeNotifiedSince;
	}

	public void setLastTimeNotifiedSince(String lastTimeNotifiedSince) {
		this.lastTimeNotifiedSince = lastTimeNotifiedSince;
	}

	public String getLastTimeNotified() {
		return lastTimeNotified;
	}

	public void setLastTimeNotified(String lastTimeNotified) {
		this.lastTimeNotified = lastTimeNotified;
	}

	@Override
	public String toString() {
		return "TerminalHeartbeatDTO [alertThresholdInMinutes=" + alertThresholdInMinutes + ", alertModeEnabled="
				+ alertModeEnabled + ", lastTimeNotifiedSince=" + lastTimeNotifiedSince + ", lastTimeNotified="
				+ lastTimeNotified + "]";
	}

	
}
