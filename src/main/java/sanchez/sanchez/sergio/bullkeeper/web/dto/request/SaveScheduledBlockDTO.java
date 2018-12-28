package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import java.util.Arrays;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalTime;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.IsWeeklyFrequencyValid;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.LocalTimeCompare;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ScheduledBlockShouldExistsIfPresent;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.WeeklyFrequencyValidate;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.JodaLocalTimeDeserializer;

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
	 * Allow Calls
	 */
	@JsonProperty("allow_calls")
	private boolean allowCalls;
	
	/**
	 * Description
	 */
	@Field("description")
	private String description;
	
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
	@KidShouldExists(message = "{kid.should.be.exists}")
	@JsonProperty("kid")
	private String kid;
	
	
	public SaveScheduledBlockDTO(){}

	/**
	 * 
	 * @param identity
	 * @param name
	 * @param enable
	 * @param repeatable
	 * @param allowCalls
	 * @param description
	 * @param startAt
	 * @param endAt
	 * @param weeklyFrequency
	 * @param kid
	 */
	public SaveScheduledBlockDTO(
			String identity, String name,
			boolean enable, boolean repeatable, boolean allowCalls,
			final String description, LocalTime startAt,
			LocalTime endAt, int[] weeklyFrequency, String kid) {
		super();
		this.identity = identity;
		this.name = name;
		this.enable = enable;
		this.repeatable = repeatable;
		this.allowCalls = allowCalls;
		this.description = description;
		this.startAt = startAt;
		this.endAt = endAt;
		this.weeklyFrequency = weeklyFrequency;
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

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "SaveScheduledBlockDTO [identity=" + identity + ", name=" + name + ", enable=" + enable + ", repeatable="
				+ repeatable + ", allowCalls=" + allowCalls + ", description=" + description + ", startAt=" + startAt
				+ ", endAt=" + endAt + ", weeklyFrequency=" + Arrays.toString(weeklyFrequency) + ", kid=" + kid + "]";
	}

}
