package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Day Scheduled DTO
 * @author sergiosanchezsanchez
 *
 */
public final class DayScheduledDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	public DayScheduledDTO() {}

	/**
	 * 
	 * @param day
	 * @param enabled
	 * @param totalHours
	 */
	public DayScheduledDTO(final String day, final Boolean enabled, 
			final Integer totalHours) {
		super();
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
		return "DayScheduledDTO [day=" + day + ", enabled=" + enabled + ", totalHours=" + totalHours + "]";
	}

	

}
