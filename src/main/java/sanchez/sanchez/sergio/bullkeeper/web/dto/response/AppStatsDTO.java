package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * App Stats DTO
 * @author sergiosanchezsanchez
 *
 */
public class AppStatsDTO extends ResourceSupport {

	/**
	 * Identity
	 */
    @JsonProperty("identity")
    private String identity;
    
    /**
     * First Time
     */
    @JsonProperty("first_time")
    private String firstTime;
    
    /**
     * Last Time
     */
    @JsonProperty("last_time")
    private String lastTime;
    
    /**
     * Last Time Used
     */
    @JsonProperty("last_time_used")
    private String lastTimeUsed;
    
    /**
     * Total Time in foreground
     */
    @JsonProperty("total_time_in_foreground")
    private Long totalTimeInForeground;
    
    /**
     * Package Name
     */
    @JsonProperty("package_name")
    private String packageName;
    
    
    /**
	 * App 
	 */
    @JsonProperty("app")
    private String app;
    
    /**
	 * App Name
	 */
    @JsonProperty("app_name")
    private String appName;
	
	/**
	 * Icon Encoded String
	 */
    @JsonProperty("icon_encoded_string")
	private String iconEncodedString;
    
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
     * 
     */
    public AppStatsDTO() {
    }

    
    /**
     * 
     * @param identity
     * @param firstTime
     * @param lastTime
     * @param lastTimeUsed
     * @param totalTimeInForeground
     * @param packageName
     * @param app
     * @param appName
     * @param iconEncodedString
     * @param kid
     * @param terminal
     */
	public AppStatsDTO(final String identity, final String firstTime, final String lastTime,
			final String lastTimeUsed, final Long totalTimeInForeground, final String packageName, 
			final String app, final String appName, final String iconEncodedString, final String kid,
			final String terminal) {
		super();
		this.identity = identity;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
		this.lastTimeUsed = lastTimeUsed;
		this.totalTimeInForeground = totalTimeInForeground;
		this.packageName = packageName;
		this.app = app;
		this.appName = appName;
		this.iconEncodedString = iconEncodedString;
		this.kid = kid;
		this.terminal = terminal;
	}



	public String getIdentity() {
		return identity;
	}


	public String getFirstTime() {
		return firstTime;
	}


	public String getLastTime() {
		return lastTime;
	}


	public String getLastTimeUsed() {
		return lastTimeUsed;
	}


	public Long getTotalTimeInForeground() {
		return totalTimeInForeground;
	}


	public String getPackageName() {
		return packageName;
	}

	public String getApp() {
		return app;
	}


	public void setApp(String app) {
		this.app = app;
	}


	public String getAppName() {
		return appName;
	}


	public String getIconEncodedString() {
		return iconEncodedString;
	}


	public String getKid() {
		return kid;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}


	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}


	public void setLastTimeUsed(String lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}


	public void setTotalTimeInForeground(Long totalTimeInForeground) {
		this.totalTimeInForeground = totalTimeInForeground;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}


	public void setIconEncodedString(String iconEncodedString) {
		this.iconEncodedString = iconEncodedString;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}


	@Override
	public String toString() {
		return "AppStatsDTO [identity=" + identity + ", firstTime=" + firstTime + ", lastTime=" + lastTime
				+ ", lastTimeUsed=" + lastTimeUsed + ", totalTimeInForeground=" + totalTimeInForeground
				+ ", packageName=" + packageName + ", app=" + app + ", appName=" + appName + ", iconEncodedString="
				+ iconEncodedString + ", kid=" + kid + ", terminal=" + terminal + "]";
	}
}
