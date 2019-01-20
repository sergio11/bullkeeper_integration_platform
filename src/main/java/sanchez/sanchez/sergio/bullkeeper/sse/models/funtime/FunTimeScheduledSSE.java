package sanchez.sanchez.sergio.bullkeeper.sse.models.funtime;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.DayScheduledDTO;

/**
 * Fun Time Scheduled SSE
 * @author sergiosanchezsanchez
 *
 */
public final class FunTimeScheduledSSE 
		extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public static final String EVENT_TYPE = "FUN_TIME_UPDATED_EVENT";

	/**
	 * Enabled
	 */
	@JsonProperty("enabled")
	private Boolean enabled = false;
	
	/**
	 * Monday
	 */
	@JsonProperty("monday")
	private DayScheduledDTO monday;
	
	/**
	 * Tuesday
	 */
	@JsonProperty("tuesday")
	private DayScheduledDTO tuesday;
	
	/**
	 * Wednesday
	 */
	@JsonProperty("wednesday")
	private DayScheduledDTO wednesday;
	
	/**
	 * Thursday
	 */
	@JsonProperty("thursday")
	private DayScheduledDTO thursday;
	
	/**
	 * Friday
	 */
	@JsonProperty("friday")
	private DayScheduledDTO friday;
	
	/**
	 * Saturday
	 */
	@JsonProperty("saturday")
	private DayScheduledDTO saturday;
	
	/**
	 * Sunday
	 */
	@JsonProperty("sunday")
	private DayScheduledDTO sunday;
	
	/**
	 * 
	 */
	public FunTimeScheduledSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param enabled
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 * @param saturday
	 * @param sunday
	 */
	public FunTimeScheduledSSE(final String subscriberId, final Boolean enabled, final DayScheduledDTO monday,
			final DayScheduledDTO tuesday, final DayScheduledDTO wednesday, final DayScheduledDTO thursday, 
			final DayScheduledDTO friday, final DayScheduledDTO saturday, 
			final DayScheduledDTO sunday) {
		super(EVENT_TYPE, subscriberId);
		this.enabled = enabled;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public DayScheduledDTO getMonday() {
		return monday;
	}

	public DayScheduledDTO getTuesday() {
		return tuesday;
	}

	public DayScheduledDTO getWednesday() {
		return wednesday;
	}

	public DayScheduledDTO getThursday() {
		return thursday;
	}

	public DayScheduledDTO getFriday() {
		return friday;
	}

	public DayScheduledDTO getSaturday() {
		return saturday;
	}

	public DayScheduledDTO getSunday() {
		return sunday;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setMonday(DayScheduledDTO monday) {
		this.monday = monday;
	}

	public void setTuesday(DayScheduledDTO tuesday) {
		this.tuesday = tuesday;
	}

	public void setWednesday(DayScheduledDTO wednesday) {
		this.wednesday = wednesday;
	}

	public void setThursday(DayScheduledDTO thursday) {
		this.thursday = thursday;
	}

	public void setFriday(DayScheduledDTO friday) {
		this.friday = friday;
	}

	public void setSaturday(DayScheduledDTO saturday) {
		this.saturday = saturday;
	}

	public void setSunday(DayScheduledDTO sunday) {
		this.sunday = sunday;
	}

	@Override
	public String toString() {
		return "FunTimeScheduledSSE [enabled=" + enabled + ", monday=" + monday + ", tuesday=" + tuesday
				+ ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday + ", saturday="
				+ saturday + ", sunday=" + sunday + "]";
	}
}
