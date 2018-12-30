package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Fun Time Scheduled Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class FunTimeScheduledEntity {
	
	/**
	 * Enabled
	 */
	@Field("enable")
	private Boolean enabled = false;

	/**
	 * Monday
	 */
	@Field("monday")
	private DayScheduledEntity monday = new DayScheduledEntity(FunTimeDaysEnum.MONDAY);
	
	/**
	 * Tuesday
	 */
	@Field("tuesday")
	private DayScheduledEntity tuesday = new DayScheduledEntity(FunTimeDaysEnum.TUESDAY);
	
	/**
	 * Wednesday
	 */
	@Field("wednesday")
	private DayScheduledEntity wednesday = new DayScheduledEntity(FunTimeDaysEnum.WEDNESDAY);
	
	/**
	 * Thursday
	 */
	@Field("thursday")
	private DayScheduledEntity thursday = new DayScheduledEntity(FunTimeDaysEnum.THURSDAY);
	
	/**
	 * Friday
	 */
	@Field("friday")
	private DayScheduledEntity friday = new DayScheduledEntity(FunTimeDaysEnum.FRIDAY);
	
	/**
	 * Saturday
	 */
	@Field("saturday")
	private DayScheduledEntity saturday = new DayScheduledEntity(FunTimeDaysEnum.SATURDAY);
	
	/**
	 * Sunday
	 */
	@Field("sunday")
	private DayScheduledEntity sunday = new DayScheduledEntity(FunTimeDaysEnum.SUNDAY);
	
	
	public FunTimeScheduledEntity(){}

	/**
	 * 
	 * @param enabled
	 * @param monday
	 * @param tuesday
	 * @param wednesday
	 * @param thursday
	 * @param friday
	 * @param saturday
	 * @param sunday
	 * @param kid
	 */
	@PersistenceConstructor
	public FunTimeScheduledEntity(
			final Boolean enabled, 
			final DayScheduledEntity monday, 
			final DayScheduledEntity tuesday,
			final DayScheduledEntity wednesday, 
			final DayScheduledEntity thursday, 
			final DayScheduledEntity friday,
			final DayScheduledEntity saturday, 
			final DayScheduledEntity sunday) {
		super();
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

	public DayScheduledEntity getMonday() {
		return monday;
	}

	public DayScheduledEntity getTuesday() {
		return tuesday;
	}

	public DayScheduledEntity getWednesday() {
		return wednesday;
	}

	public DayScheduledEntity getThursday() {
		return thursday;
	}

	public DayScheduledEntity getFriday() {
		return friday;
	}

	public DayScheduledEntity getSaturday() {
		return saturday;
	}

	public DayScheduledEntity getSunday() {
		return sunday;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setMonday(DayScheduledEntity monday) {
		this.monday = monday;
	}

	public void setTuesday(DayScheduledEntity tuesday) {
		this.tuesday = tuesday;
	}

	public void setWednesday(DayScheduledEntity wednesday) {
		this.wednesday = wednesday;
	}

	public void setThursday(DayScheduledEntity thursday) {
		this.thursday = thursday;
	}

	public void setFriday(DayScheduledEntity friday) {
		this.friday = friday;
	}

	public void setSaturday(DayScheduledEntity saturday) {
		this.saturday = saturday;
	}

	public void setSunday(DayScheduledEntity sunday) {
		this.sunday = sunday;
	}

	
	
}
