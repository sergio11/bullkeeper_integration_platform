package sanchez.sanchez.sergio.masoc.web.dto.request;

import java.io.Serializable;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sanchez.sanchez.sergio.masoc.persistence.constraints.IsWeeklyFrequencyValid;
import sanchez.sanchez.sergio.masoc.persistence.constraints.LocalTimeCompare;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ScheduledBlockShouldExistsIfPresent;
import sanchez.sanchez.sergio.masoc.persistence.constraints.SonShouldExists;
import sanchez.sanchez.sergio.masoc.persistence.constraints.WeeklyFrequencyValidate;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.masoc.web.rest.deserializers.ClearStringDeserializer;
import sanchez.sanchez.sergio.masoc.web.rest.deserializers.JodaLocalTimeDeserializer;

/**
 * 
 * Save Scheduled Block DTO
 * @author sergiosanchezsanchez
 *
 */
@LocalTimeCompare(first = "startAt", second = "endAt", 
	message = "{scheduled.local.time.compare}")
@WeeklyFrequencyValidate(startAtFieldName = "startAt",
	weeklyFrequency = "weeklyFrequency")
public final class SaveScheduledBlockDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@ScheduledBlockShouldExistsIfPresent(message = "{scheduled.block.not.exists}")
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Name
	 */
	@NotBlank(message = "{scheduled.block.name.not.blank}")
    @Size(min = 5, max = 20, message = "{scheduled.block.name.size}", groups = Extended.class)
	@JsonProperty("name")
	@JsonDeserialize(using = ClearStringDeserializer.class)
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
	 * Start At
	 */
	@JsonProperty("start_at")
	@JsonDeserialize(using = JodaLocalTimeDeserializer.class)
	private LocalTime startAt;
	
	/**
	 * End At
	 */
	@JsonProperty("end_at")
	@JsonDeserialize(using = JodaLocalTimeDeserializer.class)
	private LocalTime endAt;
	
	/**
	 * Weekly Frequency
	 */
	@IsWeeklyFrequencyValid(message = "{scheduled.block.invalid.weekly.frequency}")
	@JsonProperty("weekly_frequency")
	private int[] weeklyFrequency;
	
	/**
	 * Son
	 */
	@SonShouldExists(message = "{son.should.be.exists}")
	@JsonProperty("son")
	private String son;
	
	
	public SaveScheduledBlockDTO(){}

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
	public SaveScheduledBlockDTO(
			String identity, String name,
			boolean enable, boolean repeatable, LocalTime startAt, LocalTime endAt, int[] weeklyFrequency, String son) {
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
