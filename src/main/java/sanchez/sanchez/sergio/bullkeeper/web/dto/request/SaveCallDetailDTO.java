package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Add Call Detail 
 * @author sergiosanchezsanchez
 *
 */
public final class SaveCallDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Idetity
	 */
	@JsonProperty("identity")
	private String identity;
	
	
	/**
	 * Phone Number
	 */
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	
	/**
	 * Call Day Time
	 */
	@JsonProperty("call_day_time")
	private String callDayTime;
	
	
	/**
	 * Call Duration
	 */
	@JsonProperty("call_duration")
	private String callDuration;
	
	/**
	 * Call Type
	 */
	@JsonProperty("call_type")
	private String callType;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;

	
	
	public SaveCallDetailDTO() {}
	
	
	/**
	 * 
	 * @param identity
	 * @param phoneNumber
	 * @param callDayTime
	 * @param callDuration
	 * @param callType
	 * @param terminal
	 */
	public SaveCallDetailDTO(String identity, String phoneNumber, String callDayTime, String callDuration,
			String callType, String terminal) {
		super();
		this.identity = identity;
		this.phoneNumber = phoneNumber;
		this.callDayTime = callDayTime;
		this.callDuration = callDuration;
		this.callType = callType;
		this.terminal = terminal;
	}


	public String getIdentity() {
		return identity;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public String getCallDayTime() {
		return callDayTime;
	}


	public String getCallDuration() {
		return callDuration;
	}


	public String getCallType() {
		return callType;
	}


	public String getTerminal() {
		return terminal;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public void setCallDayTime(String callDayTime) {
		this.callDayTime = callDayTime;
	}


	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}


	public void setCallType(String callType) {
		this.callType = callType;
	}


	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "SaveCallDetailDTO [identity=" + identity + ", phoneNumber=" + phoneNumber + ", callDayTime="
				+ callDayTime + ", callDuration=" + callDuration + ", callType=" + callType + ", terminal=" + terminal
				+ "]";
	}
	

}
