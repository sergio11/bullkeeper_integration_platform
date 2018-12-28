package sanchez.sanchez.sergio.bullkeeper.events.phonenumbers;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author sergio
 */
public class DeleteAllPhoneNumberBlockedEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Terminla
	 */
	private final String terminal;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param terminal
	 */
	public DeleteAllPhoneNumberBlockedEvent(Object source, final String kid, 
			final String terminal) {
		super(source);
		this.kid = kid;
		this.terminal = terminal;
	}

	public String getKid() {
		return kid;
	}

	public String getTerminal() {
		return terminal;
	}

	@Override
	public String toString() {
		return "DeleteAllPhoneNumberBlockedEvent [kid=" + kid + ", terminal=" + terminal + "]";
	}
}
