package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.CallDetailType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringNoSpacesDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.DateTimeDeserializer;

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
	 * Phone Number
	 */
	@JsonProperty("phone_number")
	@NotBlank(message = "{call.phonenumber.notnull}")
	@JsonDeserialize(using = ClearStringNoSpacesDeserializer.class)
	private String phoneNumber;
	
	
	/**
	 * Call Day Time
	 */
	@JsonProperty("call_day_time")
	@JsonDeserialize(using = DateTimeDeserializer.class)
	private Date callDayTime;
	
	
	/**
	 * Call Duration
	 */
	@JsonProperty("call_duration")
	@NotBlank(message = "{call.duration.notnull}")
	private String callDuration;
	
	/**
	 * Call Type
	 */
	@JsonProperty("call_type")
	@CallDetailType(message="{call.type.not.valid}")
	private String callType;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	@NotBlank(message = "{call.detail.localid.notnull}")
	private String localId;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	@TerminalShouldExists(message="{terminal.not.exists}")
	private String terminal;

	/**
	 * Kid
	 */
	@JsonProperty("kid")
	@KidShouldExists(message="{kid.not.exists}")
	private String kid;
	
	
	public SaveCallDetailDTO() {}

	/**
	 * 
	 * @param phoneNumber
	 * @param callDayTime
	 * @param callDuration
	 * @param callType
	 * @param localId
	 * @param terminal
	 * @param kid
	 */
	public SaveCallDetailDTO(String phoneNumber, Date callDayTime, String callDuration,
			String callType, String localId, String terminal,
			String kid) {
		super();
		this.phoneNumber = phoneNumber;
		this.callDayTime = callDayTime;
		this.callDuration = callDuration;
		this.callType = callType;
		this.localId = localId;
		this.terminal = terminal;
		this.kid = kid;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Date getCallDayTime() {
		return callDayTime;
	}

	public String getCallDuration() {
		return callDuration;
	}

	public String getCallType() {
		return callType;
	}

	public String getLocalId() {
		return localId;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getKid() {
		return kid;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setCallDayTime(Date callDayTime) {
		this.callDayTime = callDayTime;
	}

	public void setCallDuration(String callDuration) {
		this.callDuration = callDuration;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "SaveCallDetailDTO [phoneNumber=" + phoneNumber + ", callDayTime=" + callDayTime + ", callDuration="
				+ callDuration + ", callType=" + callType + ", localId=" + localId + ", terminal=" + terminal + ", kid="
				+ kid + "]";
	}
}
