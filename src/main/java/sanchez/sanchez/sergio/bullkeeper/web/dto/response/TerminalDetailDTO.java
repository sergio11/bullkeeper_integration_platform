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
     * High Accuraccy Location Enabled
     */
	@JsonProperty("high_accuraccy_location_enabled")
    private Boolean highAccuraccyLocationEnabled;
    
    /**
     * Apps Overlay Enabled
     */
	@JsonProperty("apps_overlay_enabled")
    private Boolean appsOverlayEnabled;
	

	/**
	 * 
	 */
	public TerminalDetailDTO() {
		super();
	}


	/**
	 * 
	 * @param identity
	 * @param status
	 * @param appVersionName
	 * @param appVersionCode
	 * @param osVersion
	 * @param detached
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
	 * @param phoneCallsEnabled
	 * @param batteryLevel
	 * @param isBatteryCharging
	 * @param heartbeat
	 * @param carrierName
	 * @param phoneNumber
	 * @param totalApps
	 * @param totalSms
	 * @param totalCalls
	 * @param totalContacts
	 * @param screenStatus
	 * @param locationPermissionEnabled
	 * @param callsHistoryPermissionEnabled
	 * @param contactsListPermissionEnabled
	 * @param textMessagePermissionEnabled
	 * @param storagePermissionEnabled
	 * @param usageStatsAllowed
	 * @param adminAccessAllowed
	 * @param highAccuraccyLocationEnabled
	 * @param appsOverlayEnabled
	 */
	public TerminalDetailDTO(String identity, String status, String appVersionName, String appVersionCode,
			String osVersion, Boolean detached, String sdkVersion, String manufacturer, String marketName, String model,
			String codeName, String deviceName, String deviceId, String kid, Boolean bedTimeEnabled,
			Boolean screenEnabled, Boolean cameraEnabled, Boolean settingsEnabled, Boolean phoneCallsEnabled,
			Integer batteryLevel, Boolean isBatteryCharging, TerminalHeartbeatDTO heartbeat, String carrierName,
			String phoneNumber, long totalApps, long totalSms, long totalCalls, long totalContacts, String screenStatus,
			Boolean locationPermissionEnabled, Boolean callsHistoryPermissionEnabled,
			Boolean contactsListPermissionEnabled, Boolean textMessagePermissionEnabled,
			Boolean storagePermissionEnabled, Boolean usageStatsAllowed, Boolean adminAccessAllowed,
			Boolean highAccuraccyLocationEnabled, Boolean appsOverlayEnabled) {
		super(identity, status, appVersionName, appVersionCode, osVersion, detached, sdkVersion, manufacturer,
				marketName, model, codeName, deviceName, deviceId, kid, bedTimeEnabled, screenEnabled, cameraEnabled,
				settingsEnabled, phoneCallsEnabled, batteryLevel, isBatteryCharging, heartbeat, carrierName,
				phoneNumber);
		this.totalApps = totalApps;
		this.totalSms = totalSms;
		this.totalCalls = totalCalls;
		this.totalContacts = totalContacts;
		this.screenStatus = screenStatus;
		this.locationPermissionEnabled = locationPermissionEnabled;
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
		this.storagePermissionEnabled = storagePermissionEnabled;
		this.usageStatsAllowed = usageStatsAllowed;
		this.adminAccessAllowed = adminAccessAllowed;
		this.highAccuraccyLocationEnabled = highAccuraccyLocationEnabled;
		this.appsOverlayEnabled = appsOverlayEnabled;
	}

	public long getTotalApps() {
		return totalApps;
	}

	public void setTotalApps(long totalApps) {
		this.totalApps = totalApps;
	}

	public long getTotalSms() {
		return totalSms;
	}

	public void setTotalSms(long totalSms) {
		this.totalSms = totalSms;
	}

	public long getTotalCalls() {
		return totalCalls;
	}

	public void setTotalCalls(long totalCalls) {
		this.totalCalls = totalCalls;
	}

	public long getTotalContacts() {
		return totalContacts;
	}

	public void setTotalContacts(long totalContacts) {
		this.totalContacts = totalContacts;
	}

	public String getScreenStatus() {
		return screenStatus;
	}

	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}

	public Boolean getLocationPermissionEnabled() {
		return locationPermissionEnabled;
	}

	public void setLocationPermissionEnabled(Boolean locationPermissionEnabled) {
		this.locationPermissionEnabled = locationPermissionEnabled;
	}

	public Boolean getCallsHistoryPermissionEnabled() {
		return callsHistoryPermissionEnabled;
	}

	public void setCallsHistoryPermissionEnabled(Boolean callsHistoryPermissionEnabled) {
		this.callsHistoryPermissionEnabled = callsHistoryPermissionEnabled;
	}

	public Boolean getContactsListPermissionEnabled() {
		return contactsListPermissionEnabled;
	}

	public void setContactsListPermissionEnabled(Boolean contactsListPermissionEnabled) {
		this.contactsListPermissionEnabled = contactsListPermissionEnabled;
	}

	public Boolean getTextMessagePermissionEnabled() {
		return textMessagePermissionEnabled;
	}

	public void setTextMessagePermissionEnabled(Boolean textMessagePermissionEnabled) {
		this.textMessagePermissionEnabled = textMessagePermissionEnabled;
	}

	public Boolean getStoragePermissionEnabled() {
		return storagePermissionEnabled;
	}

	public void setStoragePermissionEnabled(Boolean storagePermissionEnabled) {
		this.storagePermissionEnabled = storagePermissionEnabled;
	}

	public Boolean getUsageStatsAllowed() {
		return usageStatsAllowed;
	}

	public void setUsageStatsAllowed(Boolean usageStatsAllowed) {
		this.usageStatsAllowed = usageStatsAllowed;
	}

	public Boolean getAdminAccessAllowed() {
		return adminAccessAllowed;
	}

	public void setAdminAccessAllowed(Boolean adminAccessAllowed) {
		this.adminAccessAllowed = adminAccessAllowed;
	}

	public Boolean getHighAccuraccyLocationEnabled() {
		return highAccuraccyLocationEnabled;
	}

	public void setHighAccuraccyLocationEnabled(Boolean highAccuraccyLocationEnabled) {
		this.highAccuraccyLocationEnabled = highAccuraccyLocationEnabled;
	}

	public Boolean getAppsOverlayEnabled() {
		return appsOverlayEnabled;
	}

	public void setAppsOverlayEnabled(Boolean appsOverlayEnabled) {
		this.appsOverlayEnabled = appsOverlayEnabled;
	}

	@Override
	public String toString() {
		return "TerminalDetailDTO [totalApps=" + totalApps + ", totalSms=" + totalSms + ", totalCalls=" + totalCalls
				+ ", totalContacts=" + totalContacts + ", screenStatus=" + screenStatus + ", locationPermissionEnabled="
				+ locationPermissionEnabled + ", callsHistoryPermissionEnabled=" + callsHistoryPermissionEnabled
				+ ", contactsListPermissionEnabled=" + contactsListPermissionEnabled + ", textMessagePermissionEnabled="
				+ textMessagePermissionEnabled + ", storagePermissionEnabled=" + storagePermissionEnabled
				+ ", usageStatsAllowed=" + usageStatsAllowed + ", adminAccessAllowed=" + adminAccessAllowed
				+ ", highAccuraccyLocationEnabled=" + highAccuraccyLocationEnabled + ", appsOverlayEnabled="
				+ appsOverlayEnabled + "]";
	}

}
