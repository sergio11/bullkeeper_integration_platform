package sanchez.sanchez.sergio.bullkeeper.events.accounts;

import org.springframework.context.ApplicationEvent;

/**
 *
 * @author sergio
 */
public class PasswordChangedEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;
	
	private final String parentId;

	public PasswordChangedEvent(Object source, String parentId) {
		super(source);
		this.parentId = parentId;
	}

	public String getParentId() {
		return parentId;
	}
}
