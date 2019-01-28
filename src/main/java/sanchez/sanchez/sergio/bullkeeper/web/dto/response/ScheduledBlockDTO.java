package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import org.joda.time.LocalTime;
import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sanchez.sanchez.sergio.bullkeeper.web.rest.serializers.JodaTimeToStringSerializer;

/**
 * Scheduled Block DTO
 * @author sergiosanchezsanchez
 *
 */
public class ScheduledBlockDTO extends ResourceSupport {
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Name
	 */
	@JsonProperty("name")
	private String name;
	
	/**
	 * Enable
	 */
	@JsonProperty("enable")
	private boolean enable;
	
	
	/**
	 * Repeatable
	 */
	@JsonProperty("repeatable")
	private boolean repeatable;
	
	/**
	 * Create At
	 */
	@JsonProperty("create_at")
	private String createAt;
	
	
	/**
	 * Allow Calls
	 */
	@JsonProperty("allow_calls")
	private boolean allowCalls;
	
	/**
	 * Description
	 */
	@JsonProperty("description")
	private String description;
	
	
	/**
	 * StartAt
	 */
	@JsonSerialize(using = JodaTimeToStringSerializer.class)
	@JsonProperty("start_at")
	private LocalTime startAt;
	
	/**
	 * EndAt
	 */
	@JsonSerialize(using = JodaTimeToStringSerializer.class)
	@JsonProperty("end_at")
	private LocalTime endAt;
	
	/**
	 * Weekly Frequency
	 */
	@JsonProperty("weekly_frequency")
	private int[] weeklyFrequency;
	
	/**
	 * IMage
	 */
	@JsonProperty("image")
	private String image;
	
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Apps Allowed
	 */
	@JsonProperty("apps_allowed")
	private Iterable<AppAllowedByScheduledBlockDTO> appsAllowed = new ArrayList<AppAllowedByScheduledBlockDTO>();

	/**
	 * Geofence
	 */
	@JsonProperty("geofence")
	private GeofenceDTO geofence;
	
	
	public ScheduledBlockDTO() {}


	/**
	 * 
	 * @param identity
	 * @param name
	 * @param enable
	 * @param repeatable
	 * @param createAt
	 * @param allowCalls
	 * @param description
	 * @param startAt
	 * @param endAt
	 * @param weeklyFrequency
	 * @param image
	 * @param kid
	 * @param appsAllowed
	 * @param geofence
	 */
	public ScheduledBlockDTO(String identity, String name, boolean enable, boolean repeatable, 
			final String createAt, boolean allowCalls,
			String description, LocalTime startAt, LocalTime endAt, int[] weeklyFrequency, 
			String image, String kid, final Iterable<AppAllowedByScheduledBlockDTO> appsAllowed,
			final GeofenceDTO geofence) {
		super();
		this.identity = identity;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
		this.createAt = createAt;
		this.allowCalls = allowCalls;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.weeklyFrequency = weeklyFrequency;
		this.image = image;
		this.kid = kid;
		this.appsAllowed = appsAllowed;
		this.geofence = geofence;
	}


	public String getIdentity() {
		return identity;
	}


	public String getName() {
		return name;
	}


	public boolean isEnable() {
		return enable;
	}


	public boolean isRepeatable() {
		return repeatable;
	}


	public String getCreateAt() {
		return createAt;
	}


	public boolean isAllowCalls() {
		return allowCalls;
	}


	public String getDescription() {
		return description;
	}


	public LocalTime getStartAt() {
		return startAt;
	}


	public LocalTime getEndAt() {
		return endAt;
	}


	public int[] getWeeklyFrequency() {
		return weeklyFrequency;
	}


	public String getImage() {
		return image;
	}


	public String getKid() {
		return kid;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public void setRepeatable(boolean repeatable) {
		this.repeatable = repeatable;
	}


	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}


	public void setAllowCalls(boolean allowCalls) {
		this.allowCalls = allowCalls;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}


	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}


	public void setWeeklyFrequency(int[] weeklyFrequency) {
		this.weeklyFrequency = weeklyFrequency;
	}


	public void setImage(String image) {
		this.image = image;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public Iterable<AppAllowedByScheduledBlockDTO> getAppsAllowed() {
		return appsAllowed;
	}

	public void setAppsAllowed(Iterable<AppAllowedByScheduledBlockDTO> appsAllowed) {
		this.appsAllowed = appsAllowed;
	}

	public GeofenceDTO getGeofence() {
		return geofence;
	}

	public void setGeofence(GeofenceDTO geofence) {
		this.geofence = geofence;
	}


	@Override
	public String toString() {
		return "ScheduledBlockDTO [identity=" + identity + ", name=" + name + ", enable=" + enable + ", repeatable="
				+ repeatable + ", createAt=" + createAt + ", allowCalls=" + allowCalls + ", description=" + description
				+ ", startAt=" + startAt + ", endAt=" + endAt + ", weeklyFrequency=" + Arrays.toString(weeklyFrequency)
				+ ", image=" + image + ", kid=" + kid + "]";
	}
	
	/**
	 * App Allowed By Scheduled Block DTO
	 * @author sergiosanchezsanchez
	 *
	 */
	public static class AppAllowedByScheduledBlockDTO implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * App 
		 */
		@JsonProperty("app")
		private AppInstalledDTO app;
		
		/**
		 * Terminal
		 */
		@JsonProperty("terminal")
		private TerminalDTO terminal;
		
		public AppAllowedByScheduledBlockDTO() {}
		
		/**
		 * 
		 * @param app
		 * @param terminal
		 */
		public AppAllowedByScheduledBlockDTO(final AppInstalledDTO app, final TerminalDTO terminal) {
			super();
			this.app = app;
			this.terminal = terminal;
		}

		public AppInstalledDTO getApp() {
			return app;
		}

		public TerminalDTO getTerminal() {
			return terminal;
		}

		public void setApp(AppInstalledDTO app) {
			this.app = app;
		}

		public void setTerminal(TerminalDTO terminal) {
			this.terminal = terminal;
		}

		@Override
		public String toString() {
			return "AppAllowedByScheduledBlockDTO [app=" + app + ", terminal=" + terminal + "]";
		}
		
		
			
	}
	
}
