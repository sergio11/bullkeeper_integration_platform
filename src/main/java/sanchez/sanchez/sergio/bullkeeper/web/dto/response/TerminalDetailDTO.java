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
	private long totalApps;
	
	/**
	 * Last Time Used
	 */
	@JsonProperty("last_time_used")
	private String lastTimeUsed;

	
	/**
	 * 
	 */
	public TerminalDetailDTO() {
		super();
	}

	/**
	 * Terminal Detail DTO
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
	 * @param totalApps
	 * @param lastTimeUsed
	 */
	public TerminalDetailDTO(String identity, String appVersionName, String appVersionCode, String osVersion,
			String sdkVersion, String manufacturer, String marketName, String model, String codeName,
			String deviceName, String deviceId, final String kid, long totalApps, String lastTimeUsed) {
		super(identity, appVersionName, appVersionCode, osVersion, sdkVersion,
				manufacturer, marketName, model, codeName,
				deviceName, deviceId, kid);
		this.totalApps = totalApps;
		this.lastTimeUsed = lastTimeUsed;
	}

	public long getTotalApps() {
		return totalApps;
	}

	public void setTotalApps(long totalApps) {
		this.totalApps = totalApps;
	}

	public String getLastTimeUsed() {
		return lastTimeUsed;
	}

	public void setLastTimeUsed(String lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

}
