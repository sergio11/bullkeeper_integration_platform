package sanchez.sanchez.sergio.bullkeeper.events.phonenumbers;

import org.springframework.context.ApplicationEvent;

/**
 * Delete Phone Number Blocked Event
 * @author sergio
 */
public class DeletePhoneNumberBlockedEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Terminal
	 */
	private final String terminal;
	
	/**
	 * Phone Number
	 */
	private final String idOrPhoneNumber;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 * @param idOrPhoneNumber
	 */
	public DeletePhoneNumberBlockedEvent(Object source, final String kid, final String terminal, 
			final String phoneNumber) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
		this.idOrPhoneNumber = phoneNumber;
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

	@Override
	public String toString() {
		return "DeletePhoneNumberBlockedEvent [kid=" + kid + ", terminal=" + terminal + ", idOrPhoneNumber=" + idOrPhoneNumber
				+ "]";
	}
}
