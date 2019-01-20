package sanchez.sanchez.sergio.bullkeeper.sse.models.funtime;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;


/**
 * Day Scheduled Saved SSE
 * @author sergiosanchezsanchez
 *
 */
public final class DayScheduledSavedSSE 
		extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public static final String EVENT_TYPE = "FUN_TIME_DAY_SCHEDULED_UPDATED_EVENT";

	/**
	 * Day
	 */
	@JsonProperty("day")
	private String day;
	
	/**
	 * Enabled
	 */
	@JsonProperty("enabled")
	private Boolean enabled = false;
	
	/**
	 * Total Hours
	 */
	@JsonProperty("total_hours")
	private Integer totalHours = 0;
	
	
	/**
	 * 
	 */
	public DayScheduledSavedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param day
	 * @param enabled
	 * @param totalHours
	 */
	public DayScheduledSavedSSE(final String subscriberId, final String day,
			Boolean enabled, Integer totalHours) {
		super(EVENT_TYPE, subscriberId);
		this.day = day;
		this.enabled = enabled;
		this.totalHours = totalHours;
	}
	
	

	public String getDay() {
		return day;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Integer getTotalHours() {
		return totalHours;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	@Override
	public String toString() {
		return "DayScheduledSavedSSE [day=" + day + ", enabled=" + enabled + ", totalHours=" + totalHours + "]";
	}

	
}
