package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Call Detail DTO
 * @author sergiosanchezsanchez
 *
 */
public final class CallDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
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
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;
	

	/**
	 * Is Blocked
	 */
	@JsonProperty("is_blocked")
	private boolean isBlocked;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	protected String localId;
	
	/**
	 * 
	 */
	public CallDetailDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param phoneNumber
	 * @param callDayTime
	 * @param callDuration
	 * @param callType
	 * @param kid
	 * @param terminal
	 * @param isBlocked
	 */
	public CallDetailDTO(String identity, String phoneNumber, String callDayTime, 
			String callDuration, String callType,
			String kid, String terminal, boolean isBlocked,
			String localId) {
		super();
		this.identity = identity;
		this.phoneNumber = phoneNumber;
		this.callDayTime = callDayTime;
		this.callDuration = callDuration;
		this.callType = callType;
		this.kid = kid;
		this.terminal = terminal;
		this.isBlocked = isBlocked;
		this.localId = localId;
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

	public String getKid() {
		return kid;
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

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	@Override
	public String toString() {
		return "CallDetailDTO [identity=" + identity + ", phoneNumber=" + phoneNumber + ", callDayTime=" + callDayTime
				+ ", callDuration=" + callDuration + ", callType=" + callType + ", kid=" + kid + ", terminal="
				+ terminal + ", isBlocked=" + isBlocked + ", localId=" + localId + "]";
	}
}
