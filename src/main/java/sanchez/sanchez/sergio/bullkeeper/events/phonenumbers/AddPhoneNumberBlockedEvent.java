package sanchez.sanchez.sergio.bullkeeper.events.phonenumbers;

import org.springframework.context.ApplicationEvent;

/**
 * Add Phone Number Blocked
 * @author sergio
 */
public class AddPhoneNumberBlockedEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	private final String identity;
	
	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Terminal
	 */
	private final String terminal;
	
	/**
	 * Phone NUmber
	 */
	private final String phoneNumber;
	
	/**
	 * Blocked At
	 */
	private final String blockedAt;

	public AddPhoneNumberBlockedEvent(Object source, String identity, String kid, 
			String terminal, String phoneNumber,
			String blockedAt) {
		super(source);
		this.identity = identity;
		this.kid = kid;
		this.terminal = terminal;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getBlockedAt() {
		return blockedAt;
	}

	@Override
	public String toString() {
		return "AddPhoneNumberBlockedEvent [identity=" + identity + ", kid=" + kid + ", terminal=" + terminal
				+ ", phoneNumber=" + phoneNumber + ", blockedAt=" + blockedAt + "]";
	}

	
	
	
}
