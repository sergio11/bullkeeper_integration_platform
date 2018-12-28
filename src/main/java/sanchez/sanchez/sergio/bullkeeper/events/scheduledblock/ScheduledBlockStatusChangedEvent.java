package sanchez.sanchez.sergio.bullkeeper.events.scheduledblock;

import java.util.List;
import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveScheduledBlockStatusDTO;

/**
 * Scheduled Block Status Changed Event
 * @author sergiosanchezsanchez
 *
 */
public final class ScheduledBlockStatusChangedEvent extends ApplicationEvent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Kid
	 */
	private final String kid;
	
	/**
	 * Scheduled Block Status List
	 */
	private final List<SaveScheduledBlockStatusDTO> scheduledBlockStatusList;
	
	
	/**
	 * 
	 * @param source
	 * @param kid
	 * @param scheduledBlockStatusList
	 */
	public ScheduledBlockStatusChangedEvent(Object source,
			final String kid, final List<SaveScheduledBlockStatusDTO> scheduledBlockStatusList) {
		super(source);
		this.kid = kid;
		this.scheduledBlockStatusList = scheduledBlockStatusList;
	}


	public String getKid() {
		return kid;
	}


	public List<SaveScheduledBlockStatusDTO> getScheduledBlockStatusList() {
		return scheduledBlockStatusList;
	}


	@Override
	public String toString() {
		return "ScheduledBlockStatusChangedEvent [kid=" + kid + ", scheduledBlockStatusList=" + scheduledBlockStatusList
				+ "]";
	}
}
