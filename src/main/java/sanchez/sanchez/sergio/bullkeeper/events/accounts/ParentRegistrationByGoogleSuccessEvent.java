package sanchez.sanchez.sergio.bullkeeper.events.accounts;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author sergio
 */
public class ParentRegistrationByGoogleSuccessEvent extends ApplicationEvent {
    
	private static final long serialVersionUID = 1L;
	private final String identity;

    public ParentRegistrationByGoogleSuccessEvent(String identity, Object source) {
        super(source);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
