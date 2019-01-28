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
	 * Prefix
	 */
	private final String prefix;
	
	/**
	 * Number
	 */
	private final String number;
	
	/**
	 * Phone NUmber
	 */
	private final String phoneNumber;
	
	/**
	 * Blocked At
	 */
	private final String blockedAt;

	/**
	 * 
	 * @param source
	 * @param identity
	 * @param kid
	 * @param terminal
	 * @param prefix
	 * @param number
	 * @param phoneNumber
	 * @param blockedAt
	 */
	public AddPhoneNumberBlockedEvent(Object source, final String identity, final String kid, 
			final String terminal, final String prefix, final String number, final String phoneNumber,
			final String blockedAt) {
		super(source);
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

	@Override
	public String toString() {
		return "AddPhoneNumberBlockedEvent [identity=" + identity + ", kid=" + kid + ", terminal=" + terminal
				+ ", prefix=" + prefix + ", number=" + number + ", phoneNumber=" + phoneNumber + ", blockedAt="
				+ blockedAt + "]";
	}
}
