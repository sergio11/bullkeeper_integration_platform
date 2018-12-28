package sanchez.sanchez.sergio.bullkeeper.sse.models.phonenumbers;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Delete Phone Number Blocked SSE
 * @author sergiosanchezsanchez
 *
 */
public class DeletePhoneNumberBlockedSSE 
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "DELETE_PHONE_NUMBER_BLOCKED_EVENT";
	
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
	 * Id or Phone Number
	 */
	@JsonProperty("id_or_phonenumber")
	private String idOrPhoneNumber;
	
	/**
	 * 
	 */
	public DeletePhoneNumberBlockedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param idOrPhoneNumber
	 */
	public DeletePhoneNumberBlockedSSE(String subscriberId, String kid, String terminal, String idOrPhoneNumber) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.idOrPhoneNumber = idOrPhoneNumber;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getIdOrPhoneNumber() {
		return idOrPhoneNumber;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setIdOrPhoneNumber(String idOrPhoneNumber) {
		this.idOrPhoneNumber = idOrPhoneNumber;
	}

	@Override
	public String toString() {
		return "DeletePhoneNumberBlockedSSE [kid=" + kid + ", terminal=" + terminal + ", idOrPhoneNumber="
				+ idOrPhoneNumber + "]";
	}

}
