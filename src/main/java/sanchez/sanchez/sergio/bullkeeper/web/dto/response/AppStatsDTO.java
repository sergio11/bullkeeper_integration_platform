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
     * @param kid
     * @param terminal
     */
	public AppStatsDTO(String identity, String firstTime, String lastTime, String lastTimeUsed,
			Long totalTimeInForeground, String packageName, String kid, String terminal) {
		super();
		this.identity = identity;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
		this.lastTimeUsed = lastTimeUsed;
		this.totalTimeInForeground = totalTimeInForeground;
		this.packageName = packageName;
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
				+ ", packageName=" + packageName + ", kid=" + kid + ", terminal=" + terminal + "]";
	}
}
