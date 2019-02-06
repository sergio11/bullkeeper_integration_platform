package sanchez.sanchez.sergio.bullkeeper.sse.models.phonenumbers;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Add Phone Number Blocked
 * @author sergiosanchezsanchez
 *
 */
public class AddPhoneNumberBlockedSSE extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "ADD_PHONE_NUMBER_BLOCKED_EVENT";
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
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
	 * Prefix
	 */
	@JsonProperty("prefix")
	private String prefix;
	
	/**
	 * Number
	 */
	@JsonProperty("number")
	private String number;
	
	/**
	 * Phone NUmber
	 */
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	/**
	 * Blocked At
	 */
	@JsonProperty("blocked_at")
	private String blockedAt;

	
	public AddPhoneNumberBlockedSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	/**
	 * 
	 * @param subscriberId
	 * @param identity
	 * @param kid
	 * @param terminal
	 * @param prefix
	 * @param number
	 * @param phoneNumber
	 * @param blockedAt
	 */
	public AddPhoneNumberBlockedSSE(String subscriberId, String identity, String kid, String terminal,
			final String prefix, final String number, String phoneNumber, String blockedAt) {
		super(EVENT_TYPE, subscriberId);
		this.identity = identity;
		this.kid = kid;
		this.terminal = terminal;
		this.prefix = prefix;
		this.number = number;
		this.phoneNumber = phoneNumber;
		this.blockedAt = blockedAt;
	}

	

	public String getIdentity() {
		return identity;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getNumber() {
		return number;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getBlockedAt() {
		return blockedAt;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setBlockedAt(String blockedAt) {
		this.blockedAt = blockedAt;
	}

	@Override
	public String toString() {
		return "AddPhoneNumberBlockedSSE [identity=" + identity + ", kid=" + kid + ", terminal=" + terminal
				+ ", phoneNumber=" + phoneNumber + ", blockedAt=" + blockedAt + "]";
	}

	
	
}
