package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

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

	
	public ScheduledBlockDTO() {}
	
	
	/**
	 * 
	 * @param identity
	 * @param name
	 * @param enable
	 * @param repeatable
	 * @param startAt
	 * @param endAt
	 * @param weeklyFrequency
	 * @param kid
	 */
	public ScheduledBlockDTO(String identity, String name, boolean enable, boolean repeatable, LocalTime startAt,
			LocalTime endAt, int[] weeklyFrequency, final String image, String kid) {
		super();
		this.identity = identity;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
		this.startAt = startAt;
		this.endAt = endAt;
		this.weeklyFrequency = weeklyFrequency;
		this.image = image;
		this.kid = kid;
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


	public LocalTime getStartAt() {
		return startAt;
	}


	public LocalTime getEndAt() {
		return endAt;
	}


	public int[] getWeeklyFrequency() {
		return weeklyFrequency;
	}


	public String getSon() {
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


	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}


	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}


	public void setWeeklyFrequency(int[] weeklyFrequency) {
		this.weeklyFrequency = weeklyFrequency;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getKid() {
		return kid;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}


	
	
}