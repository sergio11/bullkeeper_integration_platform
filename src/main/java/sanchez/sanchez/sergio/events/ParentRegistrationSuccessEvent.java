package sanchez.sanchez.sergio.events;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author sergio
 */
public class ParentRegistrationSuccessEvent extends ApplicationEvent {
    
    private final String identity;

    public ParentRegistrationSuccessEvent(String identity, Object source) {
        super(source);
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }
}
