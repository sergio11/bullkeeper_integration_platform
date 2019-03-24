package sanchez.sanchez.sergio.bullkeeper.sse.models.contact;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * 
 * @author ssanchez
 *
 */
public final class ContactDisabledSSE extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "CONTACT_DISABLED_EVENT";
	
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
	 * Contact
	 */
	@JsonProperty("contact")
	private String contact;
	
	/**
	 * Local Id
	 */
	@JsonProperty("local_id")
	private String localId;
	
	/**
	 * 
	 */
	public ContactDisabledSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param terminal
	 * @param contact
	 * @param localId
	 */
	public ContactDisabledSSE(String subscriberId, String kid, String terminal, String contact,
			String localId) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.terminal = terminal;
		this.contact = contact;
		this.localId = localId;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}

	@Override
	public String toString() {
		return "ContactDisabledSSE [kid=" + kid + ", terminal=" + terminal + ", contact=" + contact + ", localId="
				+ localId + "]";
	}

	
}
