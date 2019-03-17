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
	
	 /**
     * Access Fine Location
     */
    @JsonProperty("access_fine_location_enabled")
    private boolean accessFineLocationEnabled = false;

    /**
     * Read Contacts
     */
    @JsonProperty("read_contacts_enabled")
    private boolean readContactsEnabled= false;

    /**
     * Read Call Log Enabled
     */
    @JsonProperty("read_call_log_enabled")
    private boolean readCallLogEnabled = false;

    /**
     * Write External Storage Enabled
     */
    @JsonProperty("write_external_storage_enabled")
    private boolean writeExternalStorageEnabled = false;

    /**
     * Usage Stats Allowed
     */
    @JsonProperty("usage_stats_allowed")
    private boolean usageStatsAllowed = false;

    /**
     * Admin Access Enabled
     */
    @JsonProperty("admin_access_enabled")
    private boolean adminAccessEnabled = false;
    
    /**
     * Battery Level
     */
    @JsonProperty("battery_level")
    private int batteryLevel = 0;
    
    /**
     * Is Battery Charging
     */
    @JsonProperty("is_battery_charging")
    private boolean isBatteryCharging = false;
    
    
    /**
     * High Accuraccy Location Enabled
     */
	@JsonProperty("high_accuraccy_location_enabled")
    private boolean highAccuraccyLocationEnabled;
    
    /**
     * Apps Overlay Enabled
     */
	@JsonProperty("apps_overlay_enabled")
    private boolean appsOverlayEnabled;
	
    
	
	
	public TerminalHeartbeatDTO() {}

	/**
	 * 
	 * @param screenStatus
	 * @param terminal
	 * @param kid
	 * @param accessFineLocationEnabled
	 * @param readContactsEnabled
	 * @param readCallLogEnabled
	 * @param writeExternalStorageEnabled
	 * @param usageStatsAllowed
	 * @param adminAccessEnabled
	 * @param batteryLevel
	 * @param isBatteryCharging
	 * @param highAccuraccyLocationEnabled
	 * @param appsOverlayEnabled
	 */
	public TerminalHeartbeatDTO(String screenStatus, String terminal, String kid, boolean accessFineLocationEnabled,
			boolean readContactsEnabled, boolean readCallLogEnabled, boolean writeExternalStorageEnabled,
			boolean usageStatsAllowed, boolean adminAccessEnabled, final int batteryLevel,
			final boolean isBatteryCharging, final boolean highAccuraccyLocationEnabled,
			final boolean appsOverlayEnabled) {
		super();
		this.screenStatus = screenStatus;
		this.terminal = terminal;
		this.kid = kid;
		this.accessFineLocationEnabled = accessFineLocationEnabled;
		this.readContactsEnabled = readContactsEnabled;
		this.readCallLogEnabled = readCallLogEnabled;
		this.writeExternalStorageEnabled = writeExternalStorageEnabled;
		this.usageStatsAllowed = usageStatsAllowed;
		this.adminAccessEnabled = adminAccessEnabled;
		this.batteryLevel = batteryLevel;
		this.isBatteryCharging = isBatteryCharging;
		this.highAccuraccyLocationEnabled = highAccuraccyLocationEnabled;
		this.appsOverlayEnabled = appsOverlayEnabled;
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

	public boolean isAccessFineLocationEnabled() {
		return accessFineLocationEnabled;
	}

	public boolean isReadContactsEnabled() {
		return readContactsEnabled;
	}

	public boolean isReadCallLogEnabled() {
		return readCallLogEnabled;
	}

	public boolean isWriteExternalStorageEnabled() {
		return writeExternalStorageEnabled;
	}

	public boolean isUsageStatsAllowed() {
		return usageStatsAllowed;
	}

	public boolean isAdminAccessEnabled() {
		return adminAccessEnabled;
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

	public void setAccessFineLocationEnabled(boolean accessFineLocationEnabled) {
		this.accessFineLocationEnabled = accessFineLocationEnabled;
	}

	public void setReadContactsEnabled(boolean readContactsEnabled) {
		this.readContactsEnabled = readContactsEnabled;
	}

	public void setReadCallLogEnabled(boolean readCallLogEnabled) {
		this.readCallLogEnabled = readCallLogEnabled;
	}

	public void setWriteExternalStorageEnabled(boolean writeExternalStorageEnabled) {
		this.writeExternalStorageEnabled = writeExternalStorageEnabled;
	}

	public void setUsageStatsAllowed(boolean usageStatsAllowed) {
		this.usageStatsAllowed = usageStatsAllowed;
	}

	public void setAdminAccessEnabled(boolean adminAccessEnabled) {
		this.adminAccessEnabled = adminAccessEnabled;
	}
	
	public int getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public boolean isBatteryCharging() {
		return isBatteryCharging;
	}

	public void setBatteryCharging(boolean isBatteryCharging) {
		this.isBatteryCharging = isBatteryCharging;
	}
	
	

	public boolean isHighAccuraccyLocationEnabled() {
		return highAccuraccyLocationEnabled;
	}

	public void setHighAccuraccyLocationEnabled(boolean highAccuraccyLocationEnabled) {
		this.highAccuraccyLocationEnabled = highAccuraccyLocationEnabled;
	}

	public boolean isAppsOverlayEnabled() {
		return appsOverlayEnabled;
	}

	public void setAppsOverlayEnabled(boolean appsOverlayEnabled) {
		this.appsOverlayEnabled = appsOverlayEnabled;
	}

	@Override
	public String toString() {
		return "TerminalHeartbeatDTO [screenStatus=" + screenStatus + ", terminal=" + terminal + ", kid=" + kid
				+ ", accessFineLocationEnabled=" + accessFineLocationEnabled + ", readContactsEnabled="
				+ readContactsEnabled + ", readCallLogEnabled=" + readCallLogEnabled + ", writeExternalStorageEnabled="
				+ writeExternalStorageEnabled + ", usageStatsAllowed=" + usageStatsAllowed + ", adminAccessEnabled="
				+ adminAccessEnabled + ", batteryLevel=" + batteryLevel + ", isBatteryCharging=" + isBatteryCharging
				+ ", highAccuraccyLocationEnabled=" + highAccuraccyLocationEnabled + ", appsOverlayEnabled="
				+ appsOverlayEnabled + "]";
	}

	
}
