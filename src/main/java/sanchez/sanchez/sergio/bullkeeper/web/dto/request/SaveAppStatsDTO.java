package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.AppStatsShouldExistsIfPresent;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.PackageNameShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.DateTimeDeserializer;

/**
 * Save App Stats DTO
 * @author sergiosanchezsanchez
 *
 */
public class SaveAppStatsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * First Time
     */
    @JsonProperty("identity")
    @AppStatsShouldExistsIfPresent(message = "{app.stats.should.exists}")
    private String identity;
    
    /**
     * First Time
     */
    @JsonProperty("first_time")
	@JsonDeserialize(using = DateTimeDeserializer.class)
    private Date firstTime;
    
    /**
     * Last Time
     */
    @JsonProperty("last_time")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private Date lastTime;
    
    /**
     * Last Time Used
     */
    @JsonProperty("last_time_used")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private Date lastTimeUsed;
    
    /**
     * Total Time in foreground
     */
    @JsonProperty("total_time_in_foreground")
    private Long totalTimeInForeground;
    
    /**
     * Package Name
     */
    @JsonProperty("package_name")
    @PackageNameShouldExists(message = "{app.installed.not.exists}")
    private String packageName;
    
    /**
     * Terminal
     */
    @JsonProperty("terminal")
    @TerminalShouldExists(message = "{terminal.not.exists}")
    private String terminal;
    
    /**
     * Kid
     */
    @JsonProperty("kid")
    @KidShouldExists(message = "{kid.not.exists}")
    private String kid;
    

    /**
     * 
     */
    public SaveAppStatsDTO() {
    }

    /**
     * 
     * @param identity
     * @param firstTime
     * @param lastTime
     * @param lastTimeUsed
     * @param totalTimeInForeground
     * @param packageName
     * @param terminal
     * @param kid
     */
	public SaveAppStatsDTO(String identity,
			Date firstTime, Date lastTime, Date lastTimeUsed, Long totalTimeInForeground,
			String packageName, String terminal,
			String kid) {
		super();
		this.identity = identity;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
		this.lastTimeUsed = lastTimeUsed;
		this.totalTimeInForeground = totalTimeInForeground;
		this.packageName = packageName;
		this.terminal = terminal;
		this.kid = kid;
	}

	public String getIdentity() {
		return identity;
	}

	public Date getFirstTime() {
		return firstTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public Date getLastTimeUsed() {
		return lastTimeUsed;
	}

	public Long getTotalTimeInForeground() {
		return totalTimeInForeground;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getKid() {
		return kid;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public void setLastTimeUsed(Date lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public void setTotalTimeInForeground(Long totalTimeInForeground) {
		this.totalTimeInForeground = totalTimeInForeground;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "SaveAppStatsDTO [identity=" + identity + ", firstTime=" + firstTime + ", lastTime=" + lastTime
				+ ", lastTimeUsed=" + lastTimeUsed + ", totalTimeInForeground=" + totalTimeInForeground
				+ ", packageName=" + packageName + ", terminal=" + terminal + ", kid=" + kid + "]";
	}

    
}
