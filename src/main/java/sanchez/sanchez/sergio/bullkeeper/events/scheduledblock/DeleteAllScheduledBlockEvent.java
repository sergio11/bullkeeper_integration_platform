package sanchez.sanchez.sergio.bullkeeper.events.scheduledblock;

import org.springframework.context.ApplicationEvent;

/**
 * Delete All Scheduled Block
 * @author sergio
 */
public class DeleteAllScheduledBlockEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * Kid
	 */
	private final String kid;

	/**
	 * @param source
	 * @param kid
	 */
	public DeleteAllScheduledBlockEvent(Object source, 
			final String kid) {
		super(source);
		this.kid = kid;
	}

	public String getKid() {
		return kid;
	}

	@Override
	public String toString() {
		return "DeleteAllScheduledBlockEvent [kid=" + kid + "]";
	}
}
