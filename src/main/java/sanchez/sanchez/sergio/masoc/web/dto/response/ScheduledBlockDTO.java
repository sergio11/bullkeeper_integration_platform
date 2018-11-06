package sanchez.sanchez.sergio.masoc.web.dto.response;

import org.joda.time.LocalTime;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import sanchez.sanchez.sergio.masoc.web.rest.serializers.JodaTimeToStringSerializer;

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
	 * Son
	 */
	@JsonProperty("son")
	private String son;

	
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
	 * @param son
	 */
	public ScheduledBlockDTO(String identity, String name, boolean enable, boolean repeatable, LocalTime startAt,
			LocalTime endAt, int[] weeklyFrequency, String son) {
		super();
		this.identity = identity;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
		this.startAt = startAt;
		this.endAt = endAt;
		this.weeklyFrequency = weeklyFrequency;
		this.son = son;
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
		return son;
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


	public void setSon(String son) {
		this.son = son;
	}
	
}
