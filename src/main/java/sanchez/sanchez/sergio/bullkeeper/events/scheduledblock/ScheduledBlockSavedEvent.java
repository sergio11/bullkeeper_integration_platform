package sanchez.sanchez.sergio.bullkeeper.events.scheduledblock;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ScheduledBlockDTO;

/**
 * Scheduled Block Saved Envent
 * @author sergio
 */
public class ScheduledBlockSavedEvent extends ApplicationEvent  {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Scheduled Block DTO
	 */
	private final ScheduledBlockDTO scheduledBlockDTO;
	
	/**
	 * Kid
	 */
	private final String kid;

	/**
	 * 
	 * @param source
	 * @param scheduledBlockDTO
	 * @param kid
	 */
	public ScheduledBlockSavedEvent(Object source, 
			final ScheduledBlockDTO scheduledBlockDTO, final String kid) {
		super(source);
		this.scheduledBlockDTO = scheduledBlockDTO;
		this.kid = kid;
	}

	public ScheduledBlockDTO getScheduledBlockDTO() {
		return scheduledBlockDTO;
	}

	public String getKid() {
		return kid;
	}

	@Override
	public String toString() {
		return "ScheduledBlockSavedEvent [scheduledBlockDTO=" + scheduledBlockDTO + ", kid=" + kid + "]";
	}

	
}
