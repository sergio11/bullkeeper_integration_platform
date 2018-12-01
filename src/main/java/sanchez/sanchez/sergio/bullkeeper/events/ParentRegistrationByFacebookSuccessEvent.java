package sanchez.sanchez.sergio.bullkeeper.events;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author sergio
 */
public class ParentRegistrationByFacebookSuccessEvent extends ApplicationEvent {
    
	private static final long serialVersionUID = 1L;
	private final String identity;

    public ParentRegistrationByFacebookSuccessEvent(String identity, Object source) {
        super(source);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
