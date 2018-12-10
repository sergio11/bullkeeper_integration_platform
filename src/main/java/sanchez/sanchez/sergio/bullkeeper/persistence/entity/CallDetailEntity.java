package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Call Detail Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = CallDetailEntity.COLLECTION_NAME)
public class CallDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Collection Name
	 */
	public final static String COLLECTION_NAME = "call_details";
	
	/**
	 * Id
	 */
	@Id
	private ObjectId id;

	/**
	 * Phone Number
	 */
	@Field("phone_number")
	private String phoneNumber;
		
	/**
	 * Call Day Time
	 */
	@Field("call_day_time")
	private Date callDayTime;
	
	/**
	 * Call Duration
	 */
	@Field("call_duration")
	private String callDuration;
	
	/**
	 * Call Type
	 */
	@Field("call_type")
	private CallTypeEnum callType;
	
	/**
	 * Kid
	 */
	@Field("kid")
    @DBRef
    private KidEntity kid;
	
	/**
	 * Terminal
	 */
	@Field("terminal")
	@DBRef
	private TerminalEntity terminal;
	
	/**
	 * 
	 */
	public CallDetailEntity() {}
	
	/**
	 * 
	 * @param id
	 * @param phoneNumber
	 * @param callDayTime
	 * @param callDuration
	 * @param callType
	 * @param kid
	 * @param terminal
	 */
	@PersistenceConstructor
	public CallDetailEntity(ObjectId id, String phoneNumber, Date callDayTime, String callDuration,
			CallTypeEnum callType, KidEntity kid, TerminalEntity terminal) {
		super();
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.callDayTime = callDayTime;
		this.callDuration = callDuration;
		this.callType = callType;
		this.kid = kid;
		this.terminal = terminal;
	}

	public ObjectId getId() {
		return id;
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

	public CallTypeEnum getCallType() {
		return callType;
	}

	public KidEntity getKid() {
		return kid;
	}

	public TerminalEntity getTerminal() {
		return terminal;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public void setCallType(CallTypeEnum callType) {
		this.callType = callType;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public void setTerminal(TerminalEntity terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "CallDetailEntity [id=" + id + ", phoneNumber=" + phoneNumber + ", callDayTime=" + callDayTime
				+ ", callDuration=" + callDuration + ", callType=" + callType + ", kid=" + kid + ", terminal="
				+ terminal + "]";
	}

	
	
}
