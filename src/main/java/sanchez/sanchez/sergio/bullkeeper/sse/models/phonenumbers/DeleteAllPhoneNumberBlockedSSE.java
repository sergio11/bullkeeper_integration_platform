package sanchez.sanchez.sergio.bullkeeper.sse.models.phonenumbers;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Delete All Phone Number Blocked SSE
 * @author sergiosanchezsanchez
 *
 */
public class DeleteAllPhoneNumberBlockedSSE 
	extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "DELETE_ALL_PHONE_NUMBER_BLOCKED_EVENT";
	
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
	 * 
	 */
	public DeleteAllPhoneNumberBlockedSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 */
	public DeleteAllPhoneNumberBlockedSSE(String subscriberId, String kid, 
			String terminal) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "DeleteAllPhoneNumberBlockedSSE [kid=" + kid + ", terminal=" + terminal + "]";
	}
	
	

}
