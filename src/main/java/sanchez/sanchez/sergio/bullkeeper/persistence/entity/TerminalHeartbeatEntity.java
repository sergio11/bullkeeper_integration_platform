package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Terminal Heart
 * @author ssanchez
 *
 */
@Document
public final class TerminalHeartbeatEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int DEFAULT_ALERT_THRESHOLD = 10;
	
	/**
	 * Alert Threshold
	 */
	@Field("alert_threshold_in_minutes")
	protected Integer alertThresholdInMinutes = DEFAULT_ALERT_THRESHOLD;
	
	/**
	 * Alert Mode Enabled
	 */
	@Field("alert_mode_enabled")
	protected Boolean alertModeEnabled = Boolean.TRUE;
	
	/**
	 * Last Time Notified
	 */
	@Field("last_time_notified")
	protected Date lastTimeNotified = new Date();

	
	public TerminalHeartbeatEntity() {}
	
	/**
	 * 
	 * @param alertThresholdInMinutes
	 * @param alertModeEnabled
	 * @param lastTimeNotified
	 */
	public TerminalHeartbeatEntity(final Integer alertThresholdInMinutes, final Boolean alertModeEnabled, 
			final Date lastTimeNotified) {
		super();
		this.alertThresholdInMinutes = alertThresholdInMinutes;
		this.alertModeEnabled = alertModeEnabled;
		this.lastTimeNotified = lastTimeNotified;
	}


	public Integer getAlertThresholdInMinutes() {
		return alertThresholdInMinutes;
	}

	public void setAlertThresholdInMinutes(Integer alertThresholdInMinutes) {
		this.alertThresholdInMinutes = alertThresholdInMinutes;
	}

	public Boolean getAlertModeEnabled() {
		return alertModeEnabled;
	}

	public void setAlertModeEnabled(Boolean alertModeEnabled) {
		this.alertModeEnabled = alertModeEnabled;
	}

	public Date getLastTimeNotified() {
		return lastTimeNotified;
	}

	public void setLastTimeNotified(Date lastTimeNotified) {
		this.lastTimeNotified = lastTimeNotified;
	}

	@Override
	public String toString() {
		return "TerminalHeartbeatEntity [alertThresholdInMinutes=" + alertThresholdInMinutes + ", alertModeEnabled="
				+ alertModeEnabled + ", lastTimeNotified=" + lastTimeNotified + "]";
	}

	

	
}
