package es.bisite.usal.bullytect.events;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author sergio
 */
public class AccountDeletionRequestEvent extends ApplicationEvent {
    
	private static final long serialVersionUID = 1L;
	private final String identity;
	private final String confirmationToken;

    public AccountDeletionRequestEvent(String identity, String confirmationToken, Object source) {
        super(source);
        this.identity = identity;
        this.confirmationToken = confirmationToken;
    }

    public String getIdentity() {
        return identity;
    }

	public String getConfirmationToken() {
		return confirmationToken;
	}
    
}
