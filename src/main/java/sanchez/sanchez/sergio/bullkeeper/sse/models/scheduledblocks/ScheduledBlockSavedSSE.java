package sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import org.joda.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.GeofenceDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO.AppAllowedByScheduledBlockDTO;
import sanchez.sanchez.sergio.bullkeeper.web.rest.serializers.JodaTimeToStringSerializer;

/**
 * Scheduled Block Saved SSE
 * @author sergiosanchezsanchez
 *
 */
public class ScheduledBlockSavedSSE extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "SCHEDULED_BLOCK_SAVED_EVENT";
	
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

	public ScheduledBlockSavedSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	/**
	 * 
	 * @param subscriberId
	 * @param identity
	 * @param name
	 * @param enable
	 * @param repeatable
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
	public ScheduledBlockSavedSSE(final String subscriberId, final String identity, final String name, 
			boolean enable, boolean repeatable, boolean allowCalls, final String description, 
			final LocalTime startAt, final LocalTime endAt, final int[] weeklyFrequency,
			final String image, final String kid, final Iterable<AppAllowedByScheduledBlockDTO> appsAllowed,
			final GeofenceDTO geofence) {
		super(EVENT_TYPE, subscriberId);
		this.identity = identity;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
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
		return "ScheduledBlockSavedSSE [identity=" + identity + ", name=" + name + ", enable=" + enable
				+ ", repeatable=" + repeatable + ", allowCalls=" + allowCalls + ", description=" + description
				+ ", startAt=" + startAt + ", endAt=" + endAt + ", weeklyFrequency=" + Arrays.toString(weeklyFrequency)
				+ ", image=" + image + ", kid=" + kid + "]";
	}
}
