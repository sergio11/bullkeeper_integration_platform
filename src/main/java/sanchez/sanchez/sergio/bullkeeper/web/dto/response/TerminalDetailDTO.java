package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Terminal Detail DTO
 * @author sergiosanchezsanchez
 *
 */
public final class TerminalDetailDTO extends TerminalDTO {

	private static final long serialVersionUID = 1L;
	
	/**
     * Total Apps
     */
    @JsonProperty("total_apps")
    protected long totalApps;

    /**
     * Total SMS
     */
    @JsonProperty("total_sms")
    protected long totalSms;

    /**
     * Total Calls
     */
    @JsonProperty("total_calls")
    protected long totalCalls;

    /**
     * Total Contacts
     */
    @JsonProperty("total_contacts")
    protected long totalContacts;
	
	/**
	 * Last Time Used
	 */
	@JsonProperty("last_time_used")
	protected String lastTimeUsed;
	
	/**
	 * Screen Status
	 */
	@JsonProperty("screen_status")
	protected String screenStatus;
	
	/**
	 * Bed Time Enabled
	 */
	@JsonProperty("bed_time_enabled")
    private Boolean bedTimeEnabled;

    /**
     * Lock Screen Enabled
     */
	@JsonProperty("lock_screen_enabled")
    private Boolean lockScreenEnabled;

    /**
     * Lock Camera Enabled
     */
	@JsonProperty("lock_camera_enabled")
	private Boolean lockCameraEnabled;

	
	/**
	 * 
	 */
	public TerminalDetailDTO() {
		super();
	}

	/**
	 * 
	 * @param identity
	 * @param appVersionName
	 * @param appVersionCode
	 * @param osVersion
	 * @param sdkVersion
	 * @param manufacturer
	 * @param marketName
	 * @param model
	 * @param codeName
	 * @param deviceName
	 * @param deviceId
	 * @param kid
	 * @param totalApps
	 * @param totalSms
	 * @param totalCalls
	 * @param totalContacts
	 * @param lastTimeUsed
	 * @param screenStatus
	 */

	public TerminalDetailDTO(String identity, String appVersionName, String appVersionCode, String osVersion,
			String sdkVersion, String manufacturer, String marketName, String model, String codeName, String deviceName,
			String deviceId, String kid, long totalApps, long totalSms, long totalCalls, long totalContacts,
			String lastTimeUsed, String screenStatus, Boolean bedTimeEnabled,
			Boolean lockScreenEnabled, Boolean lockCameraEnabled) {
		super(identity, appVersionName, appVersionCode, osVersion, sdkVersion, manufacturer, marketName, model,
				codeName, deviceName, deviceId, kid);
		this.totalApps = totalApps;
		this.totalSms = totalSms;
		this.totalCalls = totalCalls;
		this.totalContacts = totalContacts;
		this.lastTimeUsed = lastTimeUsed;
		this.screenStatus = screenStatus;
		this.bedTimeEnabled = bedTimeEnabled;
		this.lockScreenEnabled = lockScreenEnabled;
		this.lockCameraEnabled = lockCameraEnabled;
	}

	public long getTotalApps() {
		return totalApps;
	}

	public long getTotalSms() {
		return totalSms;
	}

	public long getTotalCalls() {
		return totalCalls;
	}

	public long getTotalContacts() {
		return totalContacts;
	}

	public String getLastTimeUsed() {
		return lastTimeUsed;
	}

	public String getScreenStatus() {
		return screenStatus;
	}

	public void setTotalApps(long totalApps) {
		this.totalApps = totalApps;
	}

	public void setTotalSms(long totalSms) {
		this.totalSms = totalSms;
	}

	public void setTotalCalls(long totalCalls) {
		this.totalCalls = totalCalls;
	}

	public void setTotalContacts(long totalContacts) {
		this.totalContacts = totalContacts;
	}

	public void setLastTimeUsed(String lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}
	
	public Boolean getBedTimeEnabled() {
		return bedTimeEnabled;
	}

	public Boolean getLockScreenEnabled() {
		return lockScreenEnabled;
	}

	public Boolean getLockCameraEnabled() {
		return lockCameraEnabled;
	}

	public void setBedTimeEnabled(Boolean bedTimeEnabled) {
		this.bedTimeEnabled = bedTimeEnabled;
	}

	public void setLockScreenEnabled(Boolean lockScreenEnabled) {
		this.lockScreenEnabled = lockScreenEnabled;
	}

	public void setLockCameraEnabled(Boolean lockCameraEnabled) {
		this.lockCameraEnabled = lockCameraEnabled;
	}

	@Override
	public String toString() {
		return "TerminalDetailDTO [totalApps=" + totalApps + ", totalSms=" + totalSms + ", totalCalls=" + totalCalls
				+ ", totalContacts=" + totalContacts + ", lastTimeUsed=" + lastTimeUsed + ", screenStatus="
				+ screenStatus + ", bedTimeEnabled=" + bedTimeEnabled + ", lockScreenEnabled=" + lockScreenEnabled
				+ ", lockCameraEnabled=" + lockCameraEnabled + "]";
	}

}
