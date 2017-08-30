package sanchez.sanchez.sergio.events;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.persistence.entity.ParentEntity;

/**
 *
 * @author sergio
 */
public class RegistrationSuccessEvent extends ApplicationEvent  {
    
    private final ParentEntity parent;

    public RegistrationSuccessEvent(ParentEntity parent, Object source) {
        super(source);
        this.parent = parent;
    }

    public ParentEntity getParent() {
        return parent;
    }

}
