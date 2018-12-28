package sanchez.sanchez.sergio.bullkeeper.events.scheduledblock;

import org.springframework.context.ApplicationEvent;

/**
 * Scheduled Block Image 
 * @author sergiosanchezsanchez
 *
 */
public final class ScheduledBlockImageChangedEvent extends ApplicationEvent {
	

	private static final long serialVersionUID = 1L;
	
	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Block
	 */
	private final String block;
	
	
	/**
	 * Image
	 */
	private final String image;

	/**
	 * 
	 * @param source
	 * @param kid
	 * @param block
	 * @param image
	 */
	public ScheduledBlockImageChangedEvent(Object source, String kid, String block, String image) {
		super(source);
		this.kid = kid;
		this.block = block;
		this.image = image;
	}

	public String getKid() {
		return kid;
	}

	public String getBlock() {
		return block;
	}

	public String getImage() {
		return image;
	}

	@Override
	public String toString() {
		return "ScheduledBlockImageChangedEvent [kid=" + kid + ", block=" + block + ", image=" + image + "]";
	}
}
