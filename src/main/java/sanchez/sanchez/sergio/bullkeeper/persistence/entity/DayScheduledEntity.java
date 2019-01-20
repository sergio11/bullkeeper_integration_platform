package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Day Scheduled Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class DayScheduledEntity {
	
	/**
	 * Day
	 */
	@Field("day")
	private FunTimeDaysEnum day;
	
	/**
	 * Enabled
	 */
	@Field("enable")
	private Boolean enabled = false;
	
	/**
	 * Total Hours
	 */
	@Field("total_hours")
	private Integer totalHours = 0;

	
	public DayScheduledEntity() {}

	/**
	 * 
	 * @param day
	 */
	public DayScheduledEntity(final FunTimeDaysEnum day) {
		super();
		this.day = day;
	}
	
	/**
	 * 
	 * @param day
	 * @param enabled
	 * @param totalHours
	 * @param paused
	 */
	@PersistenceConstructor
	public DayScheduledEntity(final FunTimeDaysEnum day, 
			final Boolean enabled, final Integer totalHours) {
		super();
		this.day = day;
		this.enabled = enabled;
		this.totalHours = totalHours;
	}

	public FunTimeDaysEnum getDay() {
		return day;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public Integer getTotalHours() {
		return totalHours;
	}

	public void setDay(FunTimeDaysEnum day) {
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
		return "DayScheduledEntity [day=" + day + ", enabled=" + enabled + ", totalHours=" + totalHours + "]";
	}


	
}
