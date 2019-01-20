package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Save Fun Time Scheduled DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveFunTimeScheduledDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Enabled
	 */
	@JsonProperty("enabled")
	private Boolean enabled = false;
	
	/**
	 * Monday
	 */
	@JsonProperty("monday")
	private SaveDayScheduledDTO monday;
	
	
	/**
	 * Tuesday
	 */
	@JsonProperty("tuesday")
	private SaveDayScheduledDTO tuesday;
	
	
	/**
	 * wednesday
	 */
	@JsonProperty("wednesday")
	private SaveDayScheduledDTO wednesday;
	
	
	/**
	 * Thursday
	 */
	@JsonProperty("thursday")
	private SaveDayScheduledDTO thursday;
	
	
	/**
	 * Friday
	 */
	@JsonProperty("friday")
	private SaveDayScheduledDTO friday;
	
	/**
	 * Saturday
	 */
	@JsonProperty("saturday")
	private SaveDayScheduledDTO saturday;
	
	
	/**
	 * Sunday
	 */
	@JsonProperty("sunday")
	private SaveDayScheduledDTO sunday;

	
	public SaveFunTimeScheduledDTO() {}

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
	 */
	public SaveFunTimeScheduledDTO(Boolean enabled, SaveDayScheduledDTO monday, SaveDayScheduledDTO tuesday,
			SaveDayScheduledDTO wednesday, SaveDayScheduledDTO thursday, SaveDayScheduledDTO friday,
			SaveDayScheduledDTO saturday, SaveDayScheduledDTO sunday) {
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


	public SaveDayScheduledDTO getMonday() {
		return monday;
	}


	public SaveDayScheduledDTO getTuesday() {
		return tuesday;
	}


	public SaveDayScheduledDTO getWednesday() {
		return wednesday;
	}


	public SaveDayScheduledDTO getThursday() {
		return thursday;
	}


	public SaveDayScheduledDTO getFriday() {
		return friday;
	}


	public SaveDayScheduledDTO getSaturday() {
		return saturday;
	}


	public SaveDayScheduledDTO getSunday() {
		return sunday;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public void setMonday(SaveDayScheduledDTO monday) {
		this.monday = monday;
	}


	public void setTuesday(SaveDayScheduledDTO tuesday) {
		this.tuesday = tuesday;
	}


	public void setWednesday(SaveDayScheduledDTO wednesday) {
		this.wednesday = wednesday;
	}


	public void setThursday(SaveDayScheduledDTO thursday) {
		this.thursday = thursday;
	}


	public void setFriday(SaveDayScheduledDTO friday) {
		this.friday = friday;
	}


	public void setSaturday(SaveDayScheduledDTO saturday) {
		this.saturday = saturday;
	}


	public void setSunday(SaveDayScheduledDTO sunday) {
		this.sunday = sunday;
	}


	@Override
	public String toString() {
		return "SaveFunTimeScheduledDTO [enabled=" + enabled + ", monday=" + monday + ", tuesday=" + tuesday
				+ ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday + ", saturday="
				+ saturday + ", sunday=" + sunday + "]";
	}

}
