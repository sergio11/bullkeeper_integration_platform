package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * App Stats Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = AppStatsEntity.COLLECTION_NAME)
public class AppStatsEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "app_stats";
	
	/**
	 * App id
	 */
	@Id
	private ObjectId id;
	
	/**
	 * First Time
	 */
	@Field("first_time")
	private Date firstTime;
	
	/**
	 * Last Time
	 */
	@Field("last_time")
	private Date lastTime;
	
	/**
	 * Last Time Used
	 */
	@Field("last_time_used")
	private Date lastTimeUsed;
	
	/**
	 * Total Time In Foreground
	 */
	@Field("total_time_in_foreground")
	private Long totalTimeInForeground;
	
	
	/**
	 * App
	 */
	@Field("app")
    @DBRef
    private AppInstalledEntity app;
	
	/**
	 * Kid
	 */
	@Field("kid")
    @DBRef
    private KidEntity kid;
	
	/**
	 * Terminal
	 */
	@Field("terminal")
	@DBRef
	private TerminalEntity terminal;

	
	/**
	 * 
	 */
	public AppStatsEntity() {}

	/**
	 * 
	 * @param id
	 * @param firstTime
	 * @param lastTime
	 * @param lastTimeUsed
	 * @param totalTimeInForeground
	 * @param app
	 * @param kid
	 * @param terminal
	 */
	public AppStatsEntity(ObjectId id, Date firstTime, Date lastTime, Date lastTimeUsed, Long totalTimeInForeground,
			AppInstalledEntity app, KidEntity kid, TerminalEntity terminal) {
		super();
		this.id = id;
		this.firstTime = firstTime;
		this.lastTime = lastTime;
		this.lastTimeUsed = lastTimeUsed;
		this.totalTimeInForeground = totalTimeInForeground;
		this.app = app;
		this.kid = kid;
		this.terminal = terminal;
	}

	public ObjectId getId() {
		return id;
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

	public AppInstalledEntity getApp() {
		return app;
	}

	public KidEntity getKid() {
		return kid;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public void setApp(AppInstalledEntity app) {
		this.app = app;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "AppStatsEntity [id=" + id + ", firstTime=" + firstTime + ", lastTime=" + lastTime + ", lastTimeUsed="
				+ lastTimeUsed + ", totalTimeInForeground=" + totalTimeInForeground + ", app=" + app + ", kid=" + kid
				+ ", terminal=" + terminal + "]";
	}
	
}
