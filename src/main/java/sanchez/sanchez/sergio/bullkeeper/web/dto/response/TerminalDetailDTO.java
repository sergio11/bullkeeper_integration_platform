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
     * Location Permission Enabled
     */
	@JsonProperty("location_permission_enabled")
	protected Boolean locationPermissionEnabled;

    /**
     * Call History Permission Enabled
     */
	@JsonProperty("call_history_permission_enabled")
	protected Boolean callsHistoryPermissionEnabled;

    /**
     * Contacts List Permission Enabled
     */
	@JsonProperty("contacts_list_permission_enabled")
	protected Boolean contactsListPermissionEnabled;

    /**
     * Text Message Permission Enabled
     */
	@JsonProperty("text_message_permission_enabled")
	protected Boolean textMessagePermissionEnabled;

    /**
     * Storage Permission Enabled
     */
	@JsonProperty("storage_permission_enabled")
	protected Boolean storagePermissionEnabled;

    /**
     * Usage Stats Allowed
     */
	@JsonProperty("usage_stats_allowed")
	protected Boolean usageStatsAllowed;

    /**
     * Admin Access Allowed
     */
	@JsonProperty("admin_access_allowed")
	protected Boolean adminAccessAllowed;
	

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
	 * @param bedTimeEnabled
	 * @param screenEnabled
	 * @param cameraEnabled
	 * @param settingsEnabled
	 * @param totalApps
	 * @param totalSms
	 * @param totalCalls
	 * @param totalContacts
	 * @param lastTimeUsed
	 * @param screenStatus
	 * @param locationPermissionEnabled
	 * @param callsHistoryPermissionEnabled
	 * @param contactsListPermissionEnabled
	 * @param textMessagePermissionEnabled
	 * @param storagePermissionEnabled
	 * @param usageStatsAllowed
	 * @param adminAccessAllowed
	 */
	public TerminalDetailDTO(String identity, String appVersionName, String appVersionCode, String osVersion,
			String sdkVersion, String manufacturer, String marketName, String model, String codeName, String deviceName,
			String deviceId, String kid, Boolean bedTimeEnabled, Boolean screenEnabled, Boolean cameraEnabled,
			Boolean settingsEnabled, long totalApps, long totalSms, long totalCalls, long totalContacts,
			String lastTimeUsed, String screenStatus, Boolean locationPermissionEnabled,
			Boolean callsHistoryPermissionEnabled, Boolean contactsListPermissionEnabled,
			Boolean textMessagePermissionEnabled, Boolean storagePermissionEnabled, Boolean usageStatsAllowed,
			Boolean adminAccessAllowed) {
		super(identity, appVersionName, appVersionCode, osVersion, sdkVersion, manufacturer, marketName, model,
				codeName, deviceName, deviceId, kid, bedTimeEnabled, screenEnabled, cameraEnabled, settingsEnabled);
		this.totalApps = totalApps;
		this.totalSms = totalSms;
		this.totalCalls = totalCalls;
		this.totalContacts = totalContacts;
		this.lastTimeUsed = lastTimeUsed;
		this.screenStatus = screenStatus;
		this.locationPermissionEnabled = locationPermissionEnabled;
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
		this.storagePermissionEnabled = storagePermissionEnabled;
		this.usageStatsAllowed = usageStatsAllowed;
		this.adminAccessAllowed = adminAccessAllowed;
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


	public Boolean getLocationPermissionEnabled() {
		return locationPermissionEnabled;
	}


	public Boolean getCallsHistoryPermissionEnabled() {
		return callsHistoryPermissionEnabled;
	}


	public Boolean getContactsListPermissionEnabled() {
		return contactsListPermissionEnabled;
	}


	public Boolean getTextMessagePermissionEnabled() {
		return textMessagePermissionEnabled;
	}


	public Boolean getStoragePermissionEnabled() {
		return storagePermissionEnabled;
	}


	public Boolean getUsageStatsAllowed() {
		return usageStatsAllowed;
	}


	public Boolean getAdminAccessAllowed() {
		return adminAccessAllowed;
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


	public void setLocationPermissionEnabled(Boolean locationPermissionEnabled) {
		this.locationPermissionEnabled = locationPermissionEnabled;
	}


	public void setCallsHistoryPermissionEnabled(Boolean callsHistoryPermissionEnabled) {
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
	}


	public void setContactsListPermissionEnabled(Boolean contactsListPermissionEnabled) {
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
	}


	public void setTextMessagePermissionEnabled(Boolean textMessagePermissionEnabled) {
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
	}


	public void setStoragePermissionEnabled(Boolean storagePermissionEnabled) {
		this.storagePermissionEnabled = storagePermissionEnabled;
	}


	public void setUsageStatsAllowed(Boolean usageStatsAllowed) {
		this.usageStatsAllowed = usageStatsAllowed;
	}


	public void setAdminAccessAllowed(Boolean adminAccessAllowed) {
		this.adminAccessAllowed = adminAccessAllowed;
	}


	@Override
	public String toString() {
		return "TerminalDetailDTO [totalApps=" + totalApps + ", totalSms=" + totalSms + ", totalCalls=" + totalCalls
				+ ", totalContacts=" + totalContacts + ", lastTimeUsed=" + lastTimeUsed + ", screenStatus="
				+ screenStatus + ", locationPermissionEnabled=" + locationPermissionEnabled
				+ ", callsHistoryPermissionEnabled=" + callsHistoryPermissionEnabled
				+ ", contactsListPermissionEnabled=" + contactsListPermissionEnabled + ", textMessagePermissionEnabled="
				+ textMessagePermissionEnabled + ", storagePermissionEnabled=" + storagePermissionEnabled
				+ ", usageStatsAllowed=" + usageStatsAllowed + ", adminAccessAllowed=" + adminAccessAllowed + "]";
	}


	
}
