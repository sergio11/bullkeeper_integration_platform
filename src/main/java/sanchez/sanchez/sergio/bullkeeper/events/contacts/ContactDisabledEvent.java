package sanchez.sanchez.sergio.bullkeeper.events.contacts;

import org.springframework.context.ApplicationEvent;

/**
 * Contact Disabled Event Event
 * @author sergiosanchezsanchez
 */
public final class ContactDisabledEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid
	 */
	private String kid;
	
	/**
	 * Terminal
	 */
	private String terminal;
	
	
	/**
	 * Contact
	 */
	private String contact;
	
	/**
	 * Local Id
	 */
	private String localId;
	

	/**
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param contact
	 * @param localId
	 */
	public ContactDisabledEvent(final Object source,
			final String kid, final String terminal,
			final String contact, final String localId) {
		super(source);
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
		return "ContactDisabledEvent [kid=" + kid + ", terminal=" + terminal + ", contact=" + contact + ", localId="
				+ localId + "]";
	}
}
