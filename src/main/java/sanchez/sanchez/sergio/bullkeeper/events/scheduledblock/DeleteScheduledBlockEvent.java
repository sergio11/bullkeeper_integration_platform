package sanchez.sanchez.sergio.bullkeeper.events.scheduledblock;

import org.springframework.context.ApplicationEvent;

/**
 * Delete Scheduled Block
 * @author sergio
 */
public class DeleteScheduledBlockEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;

	
	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Scheduled Id
	 */
	private final String block;

	/**
	 * @param source
	 * @param kid
	 * @param block
	 */
	public DeleteScheduledBlockEvent(Object source, 
			final String kid, final String scheduledId) {
		super(source);
		this.kid = kid;
		this.block = scheduledId;
	}

	public String getKid() {
		return kid;
	}

	

	public String getBlock() {
		return block;
	}

	@Override
	public String toString() {
		return "DeleteScheduledBlockEvent [kid=" + kid + ", block=" + block + "]";
	}
	
}
