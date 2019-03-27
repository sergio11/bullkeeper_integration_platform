package sanchez.sanchez.sergio.bullkeeper.events.accounts;

import org.springframework.context.ApplicationEvent;

/**
 * Email Changed Event
 * @author sergio
 */
public class EmailChangedEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	
	private final String currentEmail;
	private final String newEmail;

	/**
	 * 
	 * @param source
	 * @param currentEmail
	 * @param newEmail
	 */
	public EmailChangedEvent(final Object source, final String currentEmail,
			final String newEmail) {
		super(source);
		this.currentEmail = currentEmail;
		this.newEmail = newEmail;
	}

	public String getCurrentEmail() {
		return currentEmail;
	}

	public String getNewEmail() {
		return newEmail;
	}

	@Override
	public String toString() {
		return "EmailChangedEvent [currentEmail=" + currentEmail + ", newEmail=" + newEmail + "]";
	}
}
